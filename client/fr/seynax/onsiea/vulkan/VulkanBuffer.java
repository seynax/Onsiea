package fr.seynax.onsiea.vulkan;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;

import fr.seynax.onsiea.utils.BufferHelper;
import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanBuffer
{
	// Variables

	private long					buffer;
	private long					bufferSize;

	private long					allocateMemory;

	private VulkanPhysicalDevice	vulkanPhysicalDevice;
	private VulkanDevice			vulkanDevice;

	// Constructor

	private VulkanBuffer(final VulkanPhysicalDevice physicalDeviceIn, final VulkanDevice deviceIn, final int[] dataIn)
	{
		this.setVulkanPhysicalDevice(physicalDeviceIn);
		this.setVulkanDevice(deviceIn);

		this.setBufferSize(Integer.BYTES * dataIn.length);

		final var	bufferMemoryProperties	= VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;

		final var	bufferInfo				= VkBufferCreateInfo.calloc();
		bufferInfo.sType(VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO);
		bufferInfo.size(this.getBufferSize());
		bufferInfo.usage(VK10.VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT);
		bufferInfo.sharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE);

		final var	passBufferPointer	= MemoryUtil.memAllocLong(1);

		var			err					= VK10.vkCreateBuffer(deviceIn.getDevice(), bufferInfo, null,
				passBufferPointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create buffer : " + VKUtil.translateVulkanResult(err));
		}

		this.setBuffer(passBufferPointer.get(0));
		MemoryUtil.memFree(passBufferPointer);
		bufferInfo.free();

		final var memoryRequirements = VkMemoryRequirements.calloc();
		VK10.vkGetBufferMemoryRequirements(deviceIn.getDevice(), this.getBuffer(), memoryRequirements);

		final var memoryType = this.findMemoryType(memoryRequirements.memoryTypeBits(), bufferMemoryProperties,
				physicalDeviceIn);

		memoryRequirements.free();

		final var memoryAllocateInfo = VkMemoryAllocateInfo.calloc();
		memoryAllocateInfo.sType(VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
		memoryAllocateInfo.allocationSize(this.getBufferSize());
		memoryAllocateInfo.memoryTypeIndex(memoryType);

		final var passAllocateMemoryPointer = MemoryUtil.memAllocLong(1);

		err = VK10.vkAllocateMemory(deviceIn.getDevice(), memoryAllocateInfo, null, passAllocateMemoryPointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create allocate memory : " + VKUtil.translateVulkanResult(err));
		}

		this.setAllocateMemory(passAllocateMemoryPointer.get(0));
		MemoryUtil.memFree(passAllocateMemoryPointer);

		err = VK10.vkBindBufferMemory(deviceIn.getDevice(), this.getBuffer(), this.getAllocateMemory(), 0);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to bind buffer memory : " + VKUtil.translateVulkanResult(err));
		}

		final var pointerBuffer = MemoryUtil.memAllocPointer(1);

		err = VK10.vkMapMemory(deviceIn.getDevice(), this.getAllocateMemory(), 0, memoryAllocateInfo.allocationSize(),
				0, pointerBuffer);
		memoryAllocateInfo.free();

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to map memory : " + VKUtil.translateVulkanResult(err));
		}

		final var mappedMemory = pointerBuffer.get(0);
		MemoryUtil.memFree(pointerBuffer);

		final var dataBuffer = BufferHelper.createIntBuffer(dataIn);

		MemoryUtil.memCopy(MemoryUtil.memAddress(dataBuffer), mappedMemory, dataBuffer.remaining());
		MemoryUtil.memFree(dataBuffer);
		VK10.vkUnmapMemory(deviceIn.getDevice(), this.getAllocateMemory());
	}

	// Static methods

	public final static VulkanBuffer createBuffer(final VulkanPhysicalDevice physicalDeviceIn,
			final VulkanDevice vulkanDeviceIn, final int[] dataIn)
	{
		return new VulkanBuffer(physicalDeviceIn, vulkanDeviceIn, dataIn);
	}

	// Methods

	public int findMemoryType(final int typeFilterIn, final int memoryPropertyFlagsIn,
			final VulkanPhysicalDevice physicalDeviceIn)
	{
		for (var i = 0; i < physicalDeviceIn.getDeviceMemoryProperties().memoryTypeCount(); i++)
		{
			// � v�rifier

			if ((typeFilterIn & 1 << i) == 1 << i
					&& (physicalDeviceIn.getDeviceMemoryProperties().memoryTypes(i).propertyFlags()
							& memoryPropertyFlagsIn) == memoryPropertyFlagsIn)
			{
				return i;
			}
		}

		return -1;
	}

	public VulkanDescriptor createDescriptor()
	{
		return new VulkanDescriptor(this.getVulkanDevice(), this.getBuffer(), this.getBufferSize());
	}

	public void cleanup(final VulkanDevice deviceIn)
	{
		// VK10.vkUnmapMemory(deviceIn.getDevice(), this.getAllocateMemory());
		VK10.vkDestroyBuffer(deviceIn.getDevice(), this.getBuffer(), null);
		VK10.vkFreeMemory(deviceIn.getDevice(), this.getAllocateMemory(), null);
	}

	// Getter | Setter

	public long getBuffer()
	{
		return this.buffer;
	}

	private void setBuffer(final long bufferIn)
	{
		this.buffer = bufferIn;
	}

	public long getBufferSize()
	{
		return this.bufferSize;
	}

	public void setBufferSize(final long bufferSizeIn)
	{
		this.bufferSize = bufferSizeIn;
	}

	private long getAllocateMemory()
	{
		return this.allocateMemory;
	}

	private void setAllocateMemory(final long allocateMemoryIn)
	{
		this.allocateMemory = allocateMemoryIn;
	}

	public VulkanPhysicalDevice getVulkanPhysicalDevice()
	{
		return this.vulkanPhysicalDevice;
	}

	public void setVulkanPhysicalDevice(final VulkanPhysicalDevice vulkanPhysicalDeviceIn)
	{
		this.vulkanPhysicalDevice = vulkanPhysicalDeviceIn;
	}

	public VulkanDevice getVulkanDevice()
	{
		return this.vulkanDevice;
	}

	public void setVulkanDevice(final VulkanDevice vulkanDeviceIn)
	{
		this.vulkanDevice = vulkanDeviceIn;
	}
}