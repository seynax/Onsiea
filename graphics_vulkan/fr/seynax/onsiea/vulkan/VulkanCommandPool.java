package fr.seynax.onsiea.vulkan;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;

import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanCommandPool
{
	// Variables

	private long commandPool;
	// Constructor

	private VulkanCommandPool(final VulkanPhysicalDevice physicalDeviceIn, final VulkanDevice vulkanDeviceIn)
	{
		final var commandPoolCreateInfo = VkCommandPoolCreateInfo.calloc();

		commandPoolCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO);
		commandPoolCreateInfo.queueFamilyIndex(physicalDeviceIn.getQueueFamilyIndex());
		commandPoolCreateInfo.flags(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT);

		final var	passCommandPoolPointer	= MemoryUtil.memAllocLong(1);

		final var	err						= VK10.vkCreateCommandPool(vulkanDeviceIn.getDevice(),
				commandPoolCreateInfo, null, passCommandPoolPointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create command pool : " + VKUtil.translateVulkanResult(err));
		}
		this.setCommandPool(passCommandPoolPointer.get(0));
		MemoryUtil.memFree(passCommandPoolPointer);
	}

	// Static methods

	public final static VulkanCommandPool createCommandPool(final VulkanPhysicalDevice physicalDeviceIn,
			final VulkanDevice vulkanDeviceIn)
	{
		return new VulkanCommandPool(physicalDeviceIn, vulkanDeviceIn);
	}

	// Methods

	public void cleanup(final VulkanDevice deviceIn)
	{
		VK10.vkDestroyCommandPool(deviceIn.getDevice(), this.getCommandPool(), null);
	}

	// Getter | Setter

	public long getCommandPool()
	{
		return this.commandPool;
	}

	private void setCommandPool(final long commandPoolIn)
	{
		this.commandPool = commandPoolIn;
	}
}