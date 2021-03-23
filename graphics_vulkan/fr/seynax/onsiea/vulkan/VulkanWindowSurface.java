package fr.seynax.onsiea.vulkan;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.utils.VKUtil;

// VulkanSurface
// Get window surface thank to GLFW

public class VulkanWindowSurface
{
	// Variables

	private long	vulkanWindowSurface;

	private long	swapchain;

	// Constructor

	VulkanWindowSurface(final VulkanInstance instanceIn, final VulkanPhysicalDevice physicalDeviceIn,
			final VulkanDevice deviceIn, final IWindow windowIn)
	{
		// Window surface

		final var	passVulkanSurfacePointer	= MemoryUtil.memAllocLong(1);

		var			err							= GLFWVulkan.glfwCreateWindowSurface(instanceIn.getInstance(),
				windowIn.getWindowHandle(), null, passVulkanSurfacePointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create window surface : " + VKUtil.translateVulkanResult(err));
		}

		this.setVulkanWindowSurface(passVulkanSurfacePointer.get(0));

		// Surface capabilities

		final var surfaceCapabilities = VkSurfaceCapabilitiesKHR.calloc();

		err = KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurface(), surfaceCapabilities);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface capabilities khr : " + VKUtil.translateVulkanResult(err));
		}

		// Surface formats

		final var passSurfaceFormatCount = MemoryUtil.memAllocInt(1);

		err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurface(), passSurfaceFormatCount, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface formats count khr : " + VKUtil.translateVulkanResult(err));
		}

		final var	surfaceFormatCount		= passSurfaceFormatCount.get(0);

		final var	passSurfaceFormatBuffer	= VkSurfaceFormatKHR.calloc(surfaceFormatCount);

		err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurface(), passSurfaceFormatCount, passSurfaceFormatBuffer);
		MemoryUtil.memFree(passSurfaceFormatCount);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface formats khr : " + VKUtil.translateVulkanResult(err));
		}

		// Surface presents mode

		final var passSurfacePresentModeCount = MemoryUtil.memAllocInt(1);

		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurface(), passSurfaceFormatCount, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failted to get physical device surface formats count khr : " + VKUtil.translateVulkanResult(err));
		}

		final var	surfacePresentModeCount			= passSurfacePresentModeCount.get(0);

		final var	passSurfacePresentModeBuffer	= MemoryUtil.memAllocInt(surfacePresentModeCount);

		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDeviceIn.getDevice(),
				this.getVulkanWindowSurface(), passSurfacePresentModeCount, passSurfacePresentModeBuffer);
		MemoryUtil.memFree(passSurfacePresentModeCount);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface formats khr : " + VKUtil.translateVulkanResult(err));
		}

		final var hasSupport = this.checkSwapchainSupport(physicalDeviceIn.getDevice(), deviceIn.getDevice(),
				windowIn.getWidth(), windowIn.getHeight(), this.getVulkanWindowSurface(), surfaceCapabilities,
				passSurfaceFormatBuffer, surfaceFormatCount, passSurfacePresentModeBuffer, surfacePresentModeCount);

		MemoryUtil.memFree(passSurfacePresentModeBuffer);
		MemoryUtil.memFree(passSurfaceFormatBuffer);

		if (!hasSupport)
		{
			throw new AssertionError("Failed to check swapchain support !");
		}
	}

	// Methods

	private boolean checkSwapchainSupport(final VkPhysicalDevice physicalDeviceIn, final VkDevice deviceIn,
			final int screenWidthIn, final int screenHeightIn, final long windowSurfaceIn,
			final VkSurfaceCapabilitiesKHR surfaceCapabilitiesIn, final VkSurfaceFormatKHR.Buffer passSurfaceFormatsIn,
			final int surfaceFormatCountIn, final IntBuffer presentModesIn, final int presentModeCountIn)
	{

		final var choosenSurfaceFormat = this.chooseSwapChainSurfaceFormat(passSurfaceFormatsIn, surfaceFormatCountIn);

		if (choosenSurfaceFormat == null)
		{
			throw new AssertionError("Failed to choose physical device surface format khr !");
		}

		final var	choosenPresentMode	= this.chooseSwapChainPresentMode(presentModesIn, presentModeCountIn);

		final var	extent				= this.chooseSwapchainExtent(surfaceCapabilitiesIn, screenWidthIn,
				screenHeightIn);

		var			imageCount			= surfaceCapabilitiesIn.minImageCount() + 1;

		if (surfaceCapabilitiesIn.maxImageCount() > 0 && imageCount > surfaceCapabilitiesIn.maxImageCount())
		{
			imageCount = surfaceCapabilitiesIn.maxImageCount();
		}

		final var swapchainCreateInfo = VkSwapchainCreateInfoKHR.create();
		swapchainCreateInfo.sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR);
		swapchainCreateInfo.surface(this.getVulkanWindowSurface());
		swapchainCreateInfo.minImageCount(imageCount);
		swapchainCreateInfo.imageFormat(choosenSurfaceFormat.format());
		swapchainCreateInfo.imageColorSpace(choosenSurfaceFormat.colorSpace());
		swapchainCreateInfo.imageExtent(extent);
		swapchainCreateInfo.imageArrayLayers(1);
		swapchainCreateInfo.imageUsage(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
		swapchainCreateInfo.presentMode(choosenPresentMode);
		swapchainCreateInfo.clipped(true);
		swapchainCreateInfo.oldSwapchain(VK10.VK_NULL_HANDLE);
		swapchainCreateInfo.imageSharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE);
		swapchainCreateInfo.pQueueFamilyIndices(null);
		swapchainCreateInfo.preTransform(surfaceCapabilitiesIn.currentTransform());
		swapchainCreateInfo.compositeAlpha(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);

		if ((surfaceCapabilitiesIn.supportedUsageFlags()
				& VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT) == VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT)
		{
			swapchainCreateInfo.imageUsage(swapchainCreateInfo.imageUsage() | VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT);
		}

		if ((surfaceCapabilitiesIn.supportedUsageFlags()
				& VK10.VK_IMAGE_USAGE_TRANSFER_SRC_BIT) == VK10.VK_IMAGE_USAGE_TRANSFER_SRC_BIT)
		{
			swapchainCreateInfo.imageUsage(swapchainCreateInfo.imageUsage() | VK10.VK_IMAGE_USAGE_TRANSFER_SRC_BIT);
		}

		final var	passSwapchainPointer	= MemoryUtil.memAllocLong(1);

		final var	err						= KHRSwapchain.vkCreateSwapchainKHR(deviceIn, swapchainCreateInfo, null,
				passSwapchainPointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failted to create swap chain khr : " + VKUtil.translateVulkanResult(err));
		}

		this.setSwapchain(passSwapchainPointer.get(0));
		MemoryUtil.memFree(passSwapchainPointer);
		swapchainCreateInfo.free();

		return true;
	}

	private VkSurfaceFormatKHR chooseSwapChainSurfaceFormat(final VkSurfaceFormatKHR.Buffer surfaceFormatsIn,
			final int formatCountIn)
	{
		for (var i = 0; i < formatCountIn; i++)
		{
			if (surfaceFormatsIn.get(i).format() == VK10.VK_FORMAT_B8G8R8A8_UNORM
					&& surfaceFormatsIn.get(i).colorSpace() == KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR)
			{
				return surfaceFormatsIn.get(i);
			}
		}

		if (formatCountIn > 0)
		{
			return surfaceFormatsIn.get(0);
		}

		return null;
	}

	private int chooseSwapChainPresentMode(final IntBuffer modesBufferIn, final int modeCountIn)
	{
		for (var i = 0; i < modeCountIn; i++)
		{
			if (modesBufferIn.get(i) == KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR)
			{
				return modesBufferIn.get(i);
			}
		}

		return KHRSurface.VK_PRESENT_MODE_FIFO_KHR;
	}

	private VkExtent2D chooseSwapchainExtent(final VkSurfaceCapabilitiesKHR capabilitiesIn, final int screenWidthIn,
			final int screenHeightIn)
	{
		if (capabilitiesIn.currentExtent().width() != 0xffffffff) // UINT32_MAX = 0xffffffff
		{
			return capabilitiesIn.currentExtent();
		}

		final var extent = VkExtent2D.calloc();
		extent.width(screenWidthIn);
		extent.height(screenHeightIn);

		if (extent.width() > capabilitiesIn.maxImageExtent().width())
		{
			extent.width(capabilitiesIn.maxImageExtent().width());
		}
		if (extent.width() < capabilitiesIn.minImageExtent().width())
		{
			extent.width(capabilitiesIn.minImageExtent().width());
		}
		if (extent.height() > capabilitiesIn.maxImageExtent().height())
		{
			extent.height(capabilitiesIn.maxImageExtent().height());
		}
		if (extent.height() < capabilitiesIn.minImageExtent().height())
		{
			extent.height(capabilitiesIn.minImageExtent().height());
		}

		return extent;
	}

	// Getter | Setter

	long getVulkanWindowSurface()
	{
		return this.vulkanWindowSurface;
	}

	private void setVulkanWindowSurface(final long vulkanWindowSurfaceIn)
	{
		this.vulkanWindowSurface = vulkanWindowSurfaceIn;
	}

	public long getSwapchain()
	{
		return this.swapchain;
	}

	public void setSwapchain(final long swapchainIn)
	{
		this.swapchain = swapchainIn;
	}
}