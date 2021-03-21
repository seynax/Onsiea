package fr.seynax.onsiea.vulkan;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;

import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanDevice
{
	// Variables

	private VkDevice device;

	// Constructor

	public VulkanDevice()
	{
	}

	// Methods

	public void initiallization(final VulkanPhysicalDevice vulkanPhysicalDeviceIn)
	{
		final var queueCreateInfo = VkDeviceQueueCreateInfo.calloc();

		queueCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
		queueCreateInfo.queueFamilyIndex(vulkanPhysicalDeviceIn.getQueueFamilyIndex());

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
		deviceCreateInfo.pEnabledFeatures(vulkanPhysicalDeviceIn.getDeviceFeatures());

		final var	passDevicePointer	= MemoryUtil.memAllocPointer(1);

		final var	err					= VK10.vkCreateDevice(vulkanPhysicalDeviceIn.getDevice(), deviceCreateInfo,
				null, passDevicePointer);

		this.setDevice(new VkDevice(passDevicePointer.get(0), vulkanPhysicalDeviceIn.getDevice(), deviceCreateInfo));

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create device : " + VKUtil.translateVulkanResult(err));
		}

		MemoryUtil.memFree(passDevicePointer);
		MemoryUtil.memFree(priorities);
		buffer.free();
	}

	public void cleanup()
	{
		VK10.vkDestroyDevice(this.getDevice(), null);
	}

	// Device

	VkDevice getDevice()
	{
		return this.device;
	}

	private void setDevice(final VkDevice deviceIn)
	{
		this.device = deviceIn;
	}
}
