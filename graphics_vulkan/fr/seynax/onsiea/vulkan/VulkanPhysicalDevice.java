package fr.seynax.onsiea.vulkan;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkQueueFamilyProperties;

import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanPhysicalDevice
{
	// Constructor variables

	private VulkanInstance						instance;

	// Variables

	private VkPhysicalDevice					device;
	private VkPhysicalDeviceProperties			deviceProperties;
	private VkPhysicalDeviceFeatures			deviceFeatures;
	private VkPhysicalDeviceMemoryProperties	deviceMemoryProperties;
	private int									queueFamilyIndex;
	private int									enabledExtensionCount;
	private String[]							enabledExtensionNames;

	// Constructor

	VulkanPhysicalDevice(final VulkanInstance vulkanInstanceIn)
	{
		this.setInstance(vulkanInstanceIn);

		// Device

		final var	instance				= vulkanInstanceIn.getInstance();

		final var	pPhysicalDeviceCount	= MemoryUtil.memAllocInt(1);
		var			err						= VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get number of physical devices: " + VKUtil.translateVulkanResult(err));
		}

		final var	physicalDeviceCount	= pPhysicalDeviceCount.get(0);

		final var	pPhysicalDevices	= MemoryUtil.memAllocPointer(physicalDeviceCount);

		err = VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, pPhysicalDevices);

		MemoryUtil.memFree(pPhysicalDeviceCount);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get physical devices: " + VKUtil.translateVulkanResult(err));
		}

		// Device extensions and choose the GPU

		final var							requiredExtensions						= new String[] {
				KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME };

		VkPhysicalDevice					chosenPhysicalDevice					= null;
		VkPhysicalDeviceProperties			chosenPhysicalDeviceProperties			= null;
		VkPhysicalDeviceFeatures			chosenPhysicalDeviceFeatures			= null;
		VkPhysicalDeviceMemoryProperties	chosenPhysicalDeviceMemoryProperties	= null;
		var									chosenQueueFamilyIndex					= -1;

		for (var i = 0; i < physicalDeviceCount; i++)
		{
			final var physicalDevice = new VkPhysicalDevice(pPhysicalDevices.get(0), instance);

			if (this.hasRequiredExtensions(physicalDevice, requiredExtensions, requiredExtensions.length))
			{
				final var queueFamilyIndex = this.getQueueFamilly(physicalDevice, VK10.VK_QUEUE_GRAPHICS_BIT);

				if (queueFamilyIndex >= 0)
				{
					final var physicalDeviceProperties = VkPhysicalDeviceProperties.mallocStack();

					VK10.vkGetPhysicalDeviceProperties(physicalDevice, physicalDeviceProperties);

					final var physicalDeviceFeatures = VkPhysicalDeviceFeatures.mallocStack();

					VK10.vkGetPhysicalDeviceFeatures(physicalDevice, physicalDeviceFeatures);

					final var physicalDeviceMemoryProperties = VkPhysicalDeviceMemoryProperties.mallocStack();

					VK10.vkGetPhysicalDeviceMemoryProperties(physicalDevice, physicalDeviceMemoryProperties);

					if (chosenPhysicalDevice == null
							|| physicalDeviceProperties.deviceType() == VK10.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU)
					{
						chosenPhysicalDevice					= physicalDevice;
						chosenPhysicalDeviceProperties			= physicalDeviceProperties;
						chosenPhysicalDeviceFeatures			= physicalDeviceFeatures;
						chosenPhysicalDeviceMemoryProperties	= physicalDeviceMemoryProperties;
						chosenQueueFamilyIndex					= queueFamilyIndex;
					}
				}
			}
		}
		MemoryUtil.memFree(pPhysicalDevices);

		if (chosenPhysicalDevice == null)
		{
			throw new RuntimeException("Failed to choose physical device !");
		}

		this.setDevice(chosenPhysicalDevice);
		this.setDeviceProperties(chosenPhysicalDeviceProperties);
		this.setDeviceFeatures(chosenPhysicalDeviceFeatures);
		this.setDeviceMemoryProperties(chosenPhysicalDeviceMemoryProperties);
		this.setQueueFamilyIndex(chosenQueueFamilyIndex);
		this.setEnabledExtensionCount(requiredExtensions.length);
		this.setEnabledExtensionNames(requiredExtensions);
	}

	// Methods

	private boolean hasRequiredExtensions(final VkPhysicalDevice physicalDeviceIn, final String[] requiredExtensionsIn,
			final int extensionCountIn)
	{
		final var	passDeviceExtensionCount	= MemoryUtil.memAllocInt(1);

		var			err							= VK10.vkEnumerateDeviceExtensionProperties(physicalDeviceIn,
				(ByteBuffer) null, passDeviceExtensionCount, (VkExtensionProperties.Buffer) null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get device extensions properties count : " + VKUtil.translateVulkanResult(err));
		}

		final var						deviceExtensionCount	= passDeviceExtensionCount.get(0);

		VkExtensionProperties.Buffer	buffer					= null;

		try (var stack = MemoryStack.stackPush())
		{
			buffer	= VkExtensionProperties.mallocStack(deviceExtensionCount, stack);

			err		= VK10.vkEnumerateDeviceExtensionProperties(physicalDeviceIn, (ByteBuffer) null,
					passDeviceExtensionCount, buffer);

			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failed to get device extensions properties : " + VKUtil.translateVulkanResult(err));
			}

			for (var i = 0; i < extensionCountIn; i++)
			{
				var extensionFound = false;

				for (var j = 0; j < deviceExtensionCount; j++)
				{
					buffer.position(j);

					if (buffer.extensionNameString().contentEquals(requiredExtensionsIn[i]))
					{
						extensionFound = true;

						break;
					}
				}

				if (!extensionFound)
				{
					return false;
				}
			}
		}
		finally
		{
			MemoryUtil.memFree(passDeviceExtensionCount);

			if (buffer != null)
			{
				buffer.free();
			}
		}

		return true;
	}

	private int getQueueFamilly(final VkPhysicalDevice physicalDeviceIn, final int flagsIn)
	{
		final var passQueueFamilyCount = MemoryUtil.memAllocInt(1);

		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDeviceIn, passQueueFamilyCount,
				(VkQueueFamilyProperties.Buffer) null);

		final var queueFamilyCount = passQueueFamilyCount.get(0);
		MemoryUtil.memFree(passQueueFamilyCount);

		VkQueueFamilyProperties.Buffer buffer = null;

		try (var stack = MemoryStack.stackPush())
		{
			buffer = VkQueueFamilyProperties.mallocStack(queueFamilyCount, stack);

			VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDeviceIn, passQueueFamilyCount, buffer);

			for (var i = 0; i < queueFamilyCount; i++)
			{
				buffer.position(i);

				final var queueFamilyProperties = buffer.get();

				if (queueFamilyProperties.queueCount() > 0)
				{
					if ((queueFamilyProperties.queueFlags() & flagsIn) == flagsIn)
					{
						return i;
					}
				}
			}
		}
		finally
		{
			if (buffer != null)
			{
				buffer.free();
			}
		}

		return -1;
	}

	public VulkanDevice createLogicalDevice()
	{
		return new VulkanDevice(this, this.getInstance());
	}

	// Getter | Setter

	VulkanInstance getInstance()
	{
		return this.instance;
	}

	private void setInstance(final VulkanInstance instanceIn)
	{
		this.instance = instanceIn;
	}

	public VkPhysicalDevice getDevice()
	{
		return this.device;
	}

	public void setDevice(final VkPhysicalDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public VkPhysicalDeviceProperties getDeviceProperties()
	{
		return this.deviceProperties;
	}

	public void setDeviceProperties(final VkPhysicalDeviceProperties devicePropertiesIn)
	{
		this.deviceProperties = devicePropertiesIn;
	}

	public VkPhysicalDeviceFeatures getDeviceFeatures()
	{
		return this.deviceFeatures;
	}

	public void setDeviceFeatures(final VkPhysicalDeviceFeatures deviceFeaturesIn)
	{
		this.deviceFeatures = deviceFeaturesIn;
	}

	public VkPhysicalDeviceMemoryProperties getDeviceMemoryProperties()
	{
		return this.deviceMemoryProperties;
	}

	public void setDeviceMemoryProperties(final VkPhysicalDeviceMemoryProperties deviceMemoryPropertiesIn)
	{
		this.deviceMemoryProperties = deviceMemoryPropertiesIn;
	}

	int getQueueFamilyIndex()
	{
		return this.queueFamilyIndex;
	}

	private void setQueueFamilyIndex(final int queueFamilyIndexIn)
	{
		this.queueFamilyIndex = queueFamilyIndexIn;
	}

	int getEnabledExtensionCount()
	{
		return this.enabledExtensionCount;
	}

	private void setEnabledExtensionCount(final int enabledExtensionCountIn)
	{
		this.enabledExtensionCount = enabledExtensionCountIn;
	}

	String[] getEnabledExtensionNames()
	{
		return this.enabledExtensionNames;
	}

	private void setEnabledExtensionNames(final String[] extensionNamesIn)
	{
		this.enabledExtensionNames = extensionNamesIn;
	}
}
