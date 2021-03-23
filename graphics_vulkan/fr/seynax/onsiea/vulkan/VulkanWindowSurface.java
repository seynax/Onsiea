package fr.seynax.onsiea.vulkan;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.utils.VKUtil;

// VulkanSurface
// Get window surface thank to GLFW

public class VulkanWindowSurface
{
	// Constructor variables

	private VulkanInstance				instance;
	private VulkanDevice				device;
	private VulkanPhysicalDevice		physicalDevice;

	// Variables

	private long						vulkanWindowSurfaceHandle;

	private VkSurfaceCapabilitiesKHR	surfaceCapabilities;

	private int							surfacePresentModeCount;
	private IntBuffer					passSurfacePresentModeBuffer;

	private int							surfaceFormatCount;
	private VkSurfaceFormatKHR.Buffer	passSurfaceFormatBuffer;

	// Constructor

	VulkanWindowSurface(final VulkanInstance instanceIn, final VulkanPhysicalDevice physicalDeviceIn,
			final VulkanDevice deviceIn, final IWindow windowIn)
	{
		this.setInstance(instanceIn);
		this.setDevice(deviceIn);
		this.setPhysicalDevice(physicalDeviceIn);

		// Window surface

		final var	passVulkanSurfacePointer	= MemoryUtil.memAllocLong(1);

		var			err							= GLFWVulkan.glfwCreateWindowSurface(instanceIn.getInstance(),
				windowIn.getWindowHandle(), null, passVulkanSurfacePointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create window surface : " + VKUtil.translateVulkanResult(err));
		}

		this.setVulkanWindowSurfaceHandle(passVulkanSurfacePointer.get(0));

		// Surface capabilities

		this.setSurfaceCapabilities(VkSurfaceCapabilitiesKHR.calloc());

		err = KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurfaceHandle(), this.getSurfaceCapabilities());

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface capabilities khr : " + VKUtil.translateVulkanResult(err));
		}

		// Surface formats

		final var passSurfaceFormatCount = MemoryUtil.memAllocInt(1);

		err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurfaceHandle(), passSurfaceFormatCount, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface formats count khr : " + VKUtil.translateVulkanResult(err));
		}

		this.setSurfaceFormatCount(passSurfaceFormatCount.get(0));

		this.setPassSurfaceFormatBuffer(VkSurfaceFormatKHR.calloc(this.surfaceFormatCount));

		err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurfaceHandle(), passSurfaceFormatCount, this.getPassSurfaceFormatBuffer());
		MemoryUtil.memFree(passSurfaceFormatCount);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface formats khr : " + VKUtil.translateVulkanResult(err));
		}

		// Surface presents mode

		final var passSurfacePresentModeCount = MemoryUtil.memAllocInt(1);

		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurfaceHandle(), passSurfaceFormatCount, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface formats count khr : " + VKUtil.translateVulkanResult(err));
		}

		this.setSurfacePresentModeCount(passSurfacePresentModeCount.get(0));

		this.setPassSurfacePresentModeBuffer(MemoryUtil.memAllocInt(this.getSurfacePresentModeCount()));

		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurfaceHandle(), passSurfacePresentModeCount, this.passSurfacePresentModeBuffer);
		MemoryUtil.memFree(passSurfacePresentModeCount);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface formats khr : " + VKUtil.translateVulkanResult(err));
		}

		{
			final var pQueueFamilyPropertyCount = MemoryUtil.memAllocInt(1);
			VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDeviceIn.getDevice(), pQueueFamilyPropertyCount,
					null);
			final var	queueCount	= pQueueFamilyPropertyCount.get(0);
			final var	queueProps	= VkQueueFamilyProperties.calloc(queueCount);
			VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDeviceIn.getDevice(), pQueueFamilyPropertyCount,
					queueProps);
			MemoryUtil.memFree(pQueueFamilyPropertyCount);

			// Iterate over each queue to learn whether it supports presenting:
			final var supportsPresent = MemoryUtil.memAllocInt(queueCount);
			for (var i = 0; i < queueCount; i++)
			{
				supportsPresent.position(i);
				err = KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDeviceIn.getDevice(), i,
						this.getVulkanWindowSurfaceHandle(), supportsPresent);
				if (err != VK10.VK_SUCCESS)
				{
					throw new AssertionError(
							"Failed to physical device surface support: " + VKUtil.translateVulkanResult(err));
				}
			}

			// Search for a graphics and a present queue in the array of queue families, try
			// to find one that supports both
			var	graphicsQueueNodeIndex	= Integer.MAX_VALUE;
			var	presentQueueNodeIndex	= Integer.MAX_VALUE;
			for (var i = 0; i < queueCount; i++)
			{
				if ((queueProps.get(i).queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0)
				{
					if (graphicsQueueNodeIndex == Integer.MAX_VALUE)
					{
						graphicsQueueNodeIndex = i;
					}
					if (supportsPresent.get(i) == VK10.VK_TRUE)
					{
						graphicsQueueNodeIndex	= i;
						presentQueueNodeIndex	= i;
						break;
					}
				}
			}
			queueProps.free();
			if (presentQueueNodeIndex == Integer.MAX_VALUE)
			{
				// If there's no queue that supports both present and graphics try to find a
				// separate present queue
				for (var i = 0; i < queueCount; ++i)
				{
					if (supportsPresent.get(i) == VK10.VK_TRUE)
					{
						presentQueueNodeIndex = i;
						break;
					}
				}
			}
			MemoryUtil.memFree(supportsPresent);
		}
	}

	// Methods

	public VulkanSwapchain createSwapchain(final IWindow windowIn)
	{
		final var swapchain = new VulkanSwapchain(this.getDevice(), this.getPhysicalDevice(),
				this.getVulkanWindowSurfaceHandle(), windowIn, this.getSurfaceCapabilities(),
				this.getPassSurfaceFormatBuffer(), this.getSurfaceFormatCount(), this.getPassSurfacePresentModeBuffer(),
				this.getSurfacePresentModeCount());

		MemoryUtil.memFree(this.getPassSurfacePresentModeBuffer());
		MemoryUtil.memFree(this.getPassSurfaceFormatBuffer());

		return swapchain;
	}

	public void cleanup()
	{
		KHRSurface.vkDestroySurfaceKHR(this.getInstance().getInstance(), this.getVulkanWindowSurfaceHandle(), null);
	}

	// Getter | Setter

	public VulkanInstance getInstance()
	{
		return this.instance;
	}

	public void setInstance(final VulkanInstance instanceIn)
	{
		this.instance = instanceIn;
	}

	public VulkanDevice getDevice()
	{
		return this.device;
	}

	public void setDevice(final VulkanDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public VulkanPhysicalDevice getPhysicalDevice()
	{
		return this.physicalDevice;
	}

	public void setPhysicalDevice(final VulkanPhysicalDevice physicalDeviceIn)
	{
		this.physicalDevice = physicalDeviceIn;
	}

	public long getVulkanWindowSurfaceHandle()
	{
		return this.vulkanWindowSurfaceHandle;
	}

	public void setVulkanWindowSurfaceHandle(final long vulkanWindowSurfaceHandleIn)
	{
		this.vulkanWindowSurfaceHandle = vulkanWindowSurfaceHandleIn;
	}

	public VkSurfaceCapabilitiesKHR getSurfaceCapabilities()
	{
		return this.surfaceCapabilities;
	}

	public void setSurfaceCapabilities(final VkSurfaceCapabilitiesKHR surfaceCapabilitiesIn)
	{
		this.surfaceCapabilities = surfaceCapabilitiesIn;
	}

	public int getSurfacePresentModeCount()
	{
		return this.surfacePresentModeCount;
	}

	public void setSurfacePresentModeCount(final int surfacePresentModeCountIn)
	{
		this.surfacePresentModeCount = surfacePresentModeCountIn;
	}

	public IntBuffer getPassSurfacePresentModeBuffer()
	{
		return this.passSurfacePresentModeBuffer;
	}

	public void setPassSurfacePresentModeBuffer(final IntBuffer passSurfacePresentModeBufferIn)
	{
		this.passSurfacePresentModeBuffer = passSurfacePresentModeBufferIn;
	}

	public int getSurfaceFormatCount()
	{
		return this.surfaceFormatCount;
	}

	public void setSurfaceFormatCount(final int surfaceFormatCountIn)
	{
		this.surfaceFormatCount = surfaceFormatCountIn;
	}

	public VkSurfaceFormatKHR.Buffer getPassSurfaceFormatBuffer()
	{
		return this.passSurfaceFormatBuffer;
	}

	public void setPassSurfaceFormatBuffer(final VkSurfaceFormatKHR.Buffer passSurfaceFormatBufferIn)
	{
		this.passSurfaceFormatBuffer = passSurfaceFormatBufferIn;
	}
}