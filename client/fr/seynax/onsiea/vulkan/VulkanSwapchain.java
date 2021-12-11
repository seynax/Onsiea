package fr.seynax.onsiea.vulkan;

import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;

import fr.seynax.onsiea.vulkan.utils.VKUtil;

public class VulkanSwapchain
{
	// Constructor variables

	private VulkanDevice		device;

	private long				vulkanWindowSurfaceHandle;

	// Variables

	private long				handle;

	private int					imagesCount;
	private long				imagesHandle;

	private VkSurfaceFormatKHR	surfaceFormat;

	private long[]				imageViews;

	// Constructor

	public VulkanSwapchain(final VulkanDevice deviceIn, final VulkanPhysicalDevice physicalDeviceIn,
			final long vulkanWindowSurfaceHandleIn, final long windowHandleIn, final int windowWidthIn,
			final int windowHeightIn, final VkSurfaceCapabilitiesKHR surfaceCapabilitiesIn,
			final VkSurfaceFormatKHR.Buffer passSurfaceFormatBufferIn, final int surfaceFormatCountIn,
			final IntBuffer passSurfacePresentModeBufferIn, final int surfacePresentModeCountIn)
	{
		this.setDevice(deviceIn);
		this.setVulkanWindowSurfaceHandle(vulkanWindowSurfaceHandleIn);

		// Swapchain creation
		{
			final var hasSupport = this.checkSwapchainSupport(physicalDeviceIn.getDevice(), deviceIn.getDevice(),
					windowWidthIn, windowHeightIn, this.getVulkanWindowSurfaceHandle(), surfaceCapabilitiesIn,
					passSurfaceFormatBufferIn, surfaceFormatCountIn, passSurfacePresentModeBufferIn,
					surfacePresentModeCountIn);

			if (!hasSupport)
			{
				throw new AssertionError("Failed to check swapchain support !");
			}
		}

		final var	passSwapchainImageCountBuffer	= MemoryUtil.memAllocInt(1);

		var			err								= KHRSwapchain.vkGetSwapchainImagesKHR(this.getDevice().getDevice(),
				this.getHandle(), passSwapchainImageCountBuffer, null);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failted to get swapchain images count : " + VKUtil.translateVulkanResult(err));
		}

		this.setImagesCount(passSwapchainImageCountBuffer.get(0));

		final var passSwapchainImages = MemoryUtil.memAllocLong(this.getImagesCount());

		err = KHRSwapchain.vkGetSwapchainImagesKHR(this.getDevice().getDevice(), this.getHandle(),
				passSwapchainImageCountBuffer, passSwapchainImages);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failted to get swapchain images : " + VKUtil.translateVulkanResult(err));
		}

		MemoryUtil.memFree(passSwapchainImageCountBuffer);

		this.setImageViews(new long[this.getImagesCount()]);
		final var passImageView = MemoryUtil.memAllocLong(1);

		for (var i = 0; i < this.getImagesCount(); i++)
		{
			final var imageViewCreateInfo = VkImageViewCreateInfo.calloc();
			imageViewCreateInfo.sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO);
			imageViewCreateInfo.image(passSwapchainImages.get(i));
			imageViewCreateInfo.viewType(VK10.VK_IMAGE_VIEW_TYPE_2D);
			imageViewCreateInfo.format(this.getSurfaceFormat().format());
			imageViewCreateInfo.components().r(VK10.VK_COMPONENT_SWIZZLE_R);
			imageViewCreateInfo.components().g(VK10.VK_COMPONENT_SWIZZLE_G);
			imageViewCreateInfo.components().b(VK10.VK_COMPONENT_SWIZZLE_B);
			imageViewCreateInfo.components().a(VK10.VK_COMPONENT_SWIZZLE_A);
			imageViewCreateInfo.subresourceRange().aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT);
			imageViewCreateInfo.subresourceRange().baseMipLevel(0);
			imageViewCreateInfo.subresourceRange().levelCount(1);
			imageViewCreateInfo.subresourceRange().baseArrayLayer(0);
			imageViewCreateInfo.subresourceRange().layerCount(1);

			err						= VK10.vkCreateImageView(this.getDevice().getDevice(), imageViewCreateInfo, null,
					passImageView);

			this.getImageViews()[i]	= passImageView.get(0);

			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failted to create image view : " + VKUtil.translateVulkanResult(err));
			}
		}

		MemoryUtil.memFree(passSwapchainImageCountBuffer);
		MemoryUtil.memFree(passSwapchainImages);
	}

	// Static methods

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

		this.setSurfaceFormat(choosenSurfaceFormat);

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
		swapchainCreateInfo.surface(this.getVulkanWindowSurfaceHandle());
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
			throw new AssertionError("Failted to create swapchain khr : " + VKUtil.translateVulkanResult(err));
		}

		this.setHandle(passSwapchainPointer.get(0));
		MemoryUtil.memFree(passSwapchainPointer);

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

	public void cleanup()
	{
		for (var i = 0; i < this.getImagesCount(); i++)
		{
			VK10.vkDestroyImageView(this.getDevice().getDevice(), this.getImageViews()[i], null);
		}

		KHRSwapchain.vkDestroySwapchainKHR(this.getDevice().getDevice(), this.getHandle(), null);
	}

	// Getter | Setter

	public VulkanDevice getDevice()
	{
		return this.device;
	}

	public void setDevice(final VulkanDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public long getVulkanWindowSurfaceHandle()
	{
		return this.vulkanWindowSurfaceHandle;
	}

	public void setVulkanWindowSurfaceHandle(final long vulkanWindowSurfaceHandleIn)
	{
		this.vulkanWindowSurfaceHandle = vulkanWindowSurfaceHandleIn;
	}

	public long getHandle()
	{
		return this.handle;
	}

	public void setHandle(final long handleIn)
	{
		this.handle = handleIn;
	}

	public int getImagesCount()
	{
		return this.imagesCount;
	}

	public void setImagesCount(final int imagesCountIn)
	{
		this.imagesCount = imagesCountIn;
	}

	public long getImagesHandle()
	{
		return this.imagesHandle;
	}

	public void setImagesHandle(final long imagesHandleIn)
	{
		this.imagesHandle = imagesHandleIn;
	}

	public VkSurfaceFormatKHR getSurfaceFormat()
	{
		return this.surfaceFormat;
	}

	public void setSurfaceFormat(final VkSurfaceFormatKHR surfaceFormatIn)
	{
		this.surfaceFormat = surfaceFormatIn;
	}

	public long[] getImageViews()
	{
		return this.imageViews;
	}

	public void setImageViews(final long[] imageViewsIn)
	{
		this.imageViews = imageViewsIn;
	}
}