package fr.seynax.onsiea.vulkan;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetAllocateInfo;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkDescriptorSetLayoutCreateInfo;
import org.lwjgl.vulkan.VkWriteDescriptorSet;

import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanDescriptor
{
	// Variables

	private long	descriptorPool;
	private long	descriptorSetLayout;
	private long	descriptorSets;

	// Constructor

	private VulkanDescriptor(final VulkanDevice vulkanDeviceIn, final long bufferPointerIn, final long bufferSizeIn)
	{
		// Descriptor pool

		{
			final var descriptorPoolSize = VkDescriptorPoolSize.calloc();
			descriptorPoolSize.type(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
			descriptorPoolSize.descriptorCount(1);

			final var passDescriptorPoolSize = VkDescriptorPoolSize.calloc(1);
			passDescriptorPoolSize.put(descriptorPoolSize);
			passDescriptorPoolSize.flip();

			final var descriptorPoolCreateInfo = VkDescriptorPoolCreateInfo.calloc();
			descriptorPoolCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO);
			descriptorPoolCreateInfo.pPoolSizes(passDescriptorPoolSize);
			descriptorPoolCreateInfo.maxSets(1);

			final var	passDescriptorPoolPointer	= MemoryUtil.memAllocLong(1);

			final var	err							= VK10.vkCreateDescriptorPool(vulkanDeviceIn.getDevice(),
					descriptorPoolCreateInfo, null, passDescriptorPoolPointer);

			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failted to create descriptor pool : " + VKUtil.translateVulkanResult(err));
			}

			this.setDescriptorPool(passDescriptorPoolPointer.get(0));
			MemoryUtil.memFree(passDescriptorPoolSize);
			MemoryUtil.memFree(passDescriptorPoolPointer);
		}

		// Descriptor set layout

		{
			final var descriptorSetLayoutBinding = VkDescriptorSetLayoutBinding.calloc();
			descriptorSetLayoutBinding.binding(0);
			descriptorSetLayoutBinding.descriptorType(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
			descriptorSetLayoutBinding.descriptorCount(1);
			descriptorSetLayoutBinding.stageFlags(VK10.VK_SHADER_STAGE_VERTEX_BIT);

			final var passDescriptorSetLayoutBinding = VkDescriptorSetLayoutBinding.calloc(1);
			passDescriptorSetLayoutBinding.put(descriptorSetLayoutBinding);
			passDescriptorSetLayoutBinding.flip();

			final var descriptorSetLayoutCreateInfo = VkDescriptorSetLayoutCreateInfo.calloc();
			descriptorSetLayoutCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO);
			// descriptorSetLayoutCreateInfo.bindingCount(1);
			descriptorSetLayoutCreateInfo.pBindings(passDescriptorSetLayoutBinding);

			final var	passDescriptorSetLayoutPointer	= MemoryUtil.memAllocLong(1);

			var			err								= VK10.vkCreateDescriptorSetLayout(vulkanDeviceIn.getDevice(),
					descriptorSetLayoutCreateInfo, null, passDescriptorSetLayoutPointer);

			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failted to create descriptor set layout : " + VKUtil.translateVulkanResult(err));
			}

			this.setDescriptorSetLayout(passDescriptorSetLayoutPointer.get(0));
			MemoryUtil.memFree(passDescriptorSetLayoutBinding);

			// Descriptor allocate info

			final var descriptorSetAllocateInfo = VkDescriptorSetAllocateInfo.calloc();
			descriptorSetAllocateInfo.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO);
			descriptorSetAllocateInfo.descriptorPool(this.getDescriptorPool());
			// descriptorSetAllocateInfo.descriptorSetCount(1);
			descriptorSetAllocateInfo.pSetLayouts(passDescriptorSetLayoutPointer);
			MemoryUtil.memFree(passDescriptorSetLayoutPointer);

			final var passDescriptorSetsPointer = MemoryUtil.memAllocLong(1);

			err = VK10.vkAllocateDescriptorSets(vulkanDeviceIn.getDevice(), descriptorSetAllocateInfo,
					passDescriptorSetsPointer);

			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failted to create descriptor sets : " + VKUtil.translateVulkanResult(err));
			}

			this.setDescriptorSets(passDescriptorSetsPointer.get(0));
			MemoryUtil.memFree(passDescriptorSetsPointer);
		}

		{
			final var descriptorBufferInfo = VkDescriptorBufferInfo.calloc();
			descriptorBufferInfo.buffer(bufferPointerIn);
			descriptorBufferInfo.offset(0);
			descriptorBufferInfo.range(bufferSizeIn);

			final var passDescriptorBufferInfo = VkDescriptorBufferInfo.calloc(1);
			passDescriptorBufferInfo.put(descriptorBufferInfo);
			passDescriptorBufferInfo.flip();

			final var writeDescriptorSet = VkWriteDescriptorSet.calloc();
			writeDescriptorSet.sType(VK10.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
			writeDescriptorSet.dstSet(this.getDescriptorSets());
			writeDescriptorSet.dstBinding(0);
			writeDescriptorSet.dstArrayElement(0);
			writeDescriptorSet.descriptorType(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
			writeDescriptorSet.descriptorCount(1);
			writeDescriptorSet.pBufferInfo(passDescriptorBufferInfo);

			final var passWriteDescriptorSet = VkWriteDescriptorSet.calloc(1);
			passWriteDescriptorSet.put(writeDescriptorSet);
			passWriteDescriptorSet.flip();

			VK10.vkUpdateDescriptorSets(vulkanDeviceIn.getDevice(), passWriteDescriptorSet, null);

			MemoryUtil.memFree(passDescriptorBufferInfo);
			MemoryUtil.memFree(passWriteDescriptorSet);
		}
	}

	// Static methods

	public final static VulkanDescriptor create(final VulkanDevice vulkanDeviceIn, final long bufferPointerIn,
			final long bufferSizeIn)
	{
		return new VulkanDescriptor(vulkanDeviceIn, bufferPointerIn, bufferSizeIn);
	}

	// Methods

	public void cleanup(final VulkanDevice vulkanDeviceIn)
	{
		VK10.vkDestroyDescriptorSetLayout(vulkanDeviceIn.getDevice(), this.getDescriptorSetLayout(), null);
		VK10.vkDestroyDescriptorPool(vulkanDeviceIn.getDevice(), this.getDescriptorPool(), null);
	}

	// Getter | Setter

	public long getDescriptorPool()
	{
		return this.descriptorPool;
	}

	public void setDescriptorPool(final long descriptorPoolIn)
	{
		this.descriptorPool = descriptorPoolIn;
	}

	public long getDescriptorSetLayout()
	{
		return this.descriptorSetLayout;
	}

	public void setDescriptorSetLayout(final long descriptorSetLayoutIn)
	{
		this.descriptorSetLayout = descriptorSetLayoutIn;
	}

	public long getDescriptorSets()
	{
		return this.descriptorSets;
	}

	public void setDescriptorSets(final long descriptorSetsIn)
	{
		this.descriptorSets = descriptorSetsIn;
	}
}
