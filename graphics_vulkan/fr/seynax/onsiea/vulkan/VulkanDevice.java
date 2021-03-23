package fr.seynax.onsiea.vulkan;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanDevice
{
	// Variables

	private VkDevice				device;

	private VulkanInstance			instance;
	private VulkanPhysicalDevice	physicalDevice;

	// Constructor

	VulkanDevice(final VulkanPhysicalDevice vulkanPhysicalDeviceIn, final VulkanInstance instanceIn)
	{
		this.setPhysicalDevice(vulkanPhysicalDeviceIn);
		this.setInstance(instanceIn);

		final var queueCreateInfo = VkDeviceQueueCreateInfo.calloc();

		queueCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
		queueCreateInfo.queueFamilyIndex(this.getPhysicalDevice().getQueueFamilyIndex());

		final var priorities = BufferUtils.createFloatBuffer(1);
		priorities.put(1.0f);
		priorities.flip();

		queueCreateInfo.pQueuePriorities(priorities);

		final var buffer = VkDeviceQueueCreateInfo.calloc(1);
		buffer.put(queueCreateInfo);
		buffer.flip();

		final var deviceCreateInfo = VkDeviceCreateInfo.calloc();
		deviceCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
		deviceCreateInfo.pQueueCreateInfos(buffer);
		deviceCreateInfo.pEnabledFeatures(this.getPhysicalDevice().getDeviceFeatures());

		final var	passDevicePointer	= MemoryUtil.memAllocPointer(1);

		final var	err					= VK10.vkCreateDevice(this.getPhysicalDevice().getDevice(), deviceCreateInfo,
				null, passDevicePointer);

		this.setDevice(new VkDevice(passDevicePointer.get(0), this.getPhysicalDevice().getDevice(), deviceCreateInfo));

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create device : " + VKUtil.translateVulkanResult(err));
		}

		MemoryUtil.memFree(passDevicePointer);
		MemoryUtil.memFree(priorities);
		buffer.free();
	}

	// Methods

	public VulkanCommandPool createCommandPool()
	{
		return new VulkanCommandPool(this.getPhysicalDevice(), this);
	}

	public VulkanBuffer createBuffer(final int[] dataIn)
	{
		return VulkanBuffer.createBuffer(this.getPhysicalDevice(), this, dataIn);
	}

	public VulkanWindowSurface createVulkanWindowSurface(final IWindow windowIn)
	{
		return new VulkanWindowSurface(this.getInstance(), this.getPhysicalDevice(), this, windowIn);
	}

	public void cleanup()
	{
		VK10.vkDestroyDevice(this.getDevice(), null);
	}

	// Device

	public VkDevice getDevice()
	{
		return this.device;
	}

	private void setDevice(final VkDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public VulkanPhysicalDevice getPhysicalDevice()
	{
		return this.physicalDevice;
	}

	private void setPhysicalDevice(final VulkanPhysicalDevice physicalDeviceIn)
	{
		this.physicalDevice = physicalDeviceIn;
	}

	public VulkanInstance getInstance()
	{
		return this.instance;
	}

	public void setInstance(final VulkanInstance instanceIn)
	{
		this.instance = instanceIn;
	}
}
