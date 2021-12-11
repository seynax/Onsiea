package fr.seynax.onsiea.vulkan;

import org.lwjgl.vulkan.KHRSwapchain;

public class VulkanManager
{
	// Variables

	private VulkanInstance			instance;
	private VulkanPhysicalDevice	physicalDevice;
	private VulkanDevice			device;
	private VulkanCommandPool		commandPool;
	private VulkanBuffer			vulkanBuffer;
	private VulkanDescriptor		vulkanDescriptor;
	private VulkanWindowSurface		vulkanWindowSurface;
	private VulkanSwapchain			vulkanSwapChain;

	public VulkanManager(final long windowHandleIn, final int windowWidthIn, final int windowHeightIn)
	{
		// Instance

		this.setInstance(new VulkanInstance("Aeison", "Onsiea"));

		// Physical device

		final var requiredExtensions = new String[] { KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME, };

		this.setPhysicalDevice(this.getInstance().createPhysicalDevice(requiredExtensions));

		// Device

		this.setDevice(this.getPhysicalDevice().createLogicalDevice());

		// Command pool

		this.setCommandPool(this.getDevice().createCommandPool());

		// Buffer

		/**
		 * Example Data
		 */

		final var data = new int[64];

		for (var i = 0; i < data.length; i++)
		{
			data[i] = i;
		}

		this.setVulkanBuffer(this.getDevice().createBuffer(data));

		/**
		 *
		 */

		// Descriptor

		this.setVulkanDescriptor(this.getVulkanBuffer().createDescriptor());

		// Window surface

		this.setVulkanWindowSurface(this.getDevice().createVulkanWindowSurface(windowHandleIn));

		// Swapchain

		this.setVulkanSwapChain(
				this.getVulkanWindowSurface().createSwapchain(windowHandleIn, windowWidthIn, windowHeightIn));
	}

	public void cleanup()
	{
		// Swapchain

		if (this.getVulkanSwapChain() != null)
		{
			this.getVulkanSwapChain().cleanup();
		}

		// Window surface

		if (this.getVulkanWindowSurface() != null)
		{
			this.getVulkanWindowSurface().cleanup();
		}

		// Descriptor

		if (this.getVulkanDescriptor() != null)
		{
			this.getVulkanDescriptor().cleanup(this.getDevice());
		}

		// Buffer

		if (this.getVulkanBuffer() != null)
		{
			this.getVulkanBuffer().cleanup(this.getDevice());
		}

		// Command pool
		if (this.getCommandPool() != null)
		{
			this.getCommandPool().cleanup(this.getDevice());
		}

		// Device

		if (this.getDevice() != null)
		{
			this.getDevice().cleanup();
		}

		// Instance

		if (this.getInstance() != null)
		{
			this.getInstance().cleanup();
		}
	}

	// Getter | Setter

	public VulkanInstance getInstance()
	{
		return this.instance;
	}

	private void setInstance(final VulkanInstance instanceIn)
	{
		this.instance = instanceIn;
	}

	public VulkanPhysicalDevice getPhysicalDevice()
	{
		return this.physicalDevice;
	}

	private void setPhysicalDevice(final VulkanPhysicalDevice physicalDeviceIn)
	{
		this.physicalDevice = physicalDeviceIn;
	}

	public VulkanDevice getDevice()
	{
		return this.device;
	}

	private void setDevice(final VulkanDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public VulkanCommandPool getCommandPool()
	{
		return this.commandPool;
	}

	private void setCommandPool(final VulkanCommandPool commandPoolIn)
	{
		this.commandPool = commandPoolIn;
	}

	public VulkanBuffer getVulkanBuffer()
	{
		return this.vulkanBuffer;
	}

	private void setVulkanBuffer(final VulkanBuffer vulkanBufferIn)
	{
		this.vulkanBuffer = vulkanBufferIn;
	}

	public VulkanDescriptor getVulkanDescriptor()
	{
		return this.vulkanDescriptor;
	}

	private void setVulkanDescriptor(final VulkanDescriptor vulkanDescriptorIn)
	{
		this.vulkanDescriptor = vulkanDescriptorIn;
	}

	public VulkanWindowSurface getVulkanWindowSurface()
	{
		return this.vulkanWindowSurface;
	}

	private void setVulkanWindowSurface(final VulkanWindowSurface vulkanWindowSurfaceIn)
	{
		this.vulkanWindowSurface = vulkanWindowSurfaceIn;
	}

	public VulkanSwapchain getVulkanSwapChain()
	{
		return this.vulkanSwapChain;
	}

	private void setVulkanSwapChain(final VulkanSwapchain vulkanSwapChainIn)
	{
		this.vulkanSwapChain = vulkanSwapChainIn;
	}
}