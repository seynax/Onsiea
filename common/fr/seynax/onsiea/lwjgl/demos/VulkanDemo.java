package fr.seynax.onsiea.lwjgl.demos;

import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkClearValue;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkDebugReportCallbackCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugReportCallbackEXT;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineLayoutCreateInfo;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkRenderPassBeginInfo;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.lwjgl.vulkan.VkViewport;

import fr.seynax.onsiea.utils.FPSUtils;
import fr.seynax.onsiea.vulkan.utils.DisallowVKUtil;

/**
 * Renders a simple triangle on a cornflower blue background on a GLFW window
 * with Vulkan.
 *
 * @author Kai Burjack
 */
public class VulkanDemo
{
	private static boolean		debug		= System.getProperty("NDEBUG") == null;

	private static String[]		layers		= { "VK_LAYER_LUNARG_standard_validation", "VK_LAYER_KHRONOS_validation", };

	/**
	 * This is just -1L, but it is nicer as a symbolic constant.
	 */
	private static final long	UINT64_MAX	= 0xFFFFFFFFFFFFFFFFL;

	/**
	 * Create a Vulkan instance using LWJGL 3.
	 *
	 * @return the VkInstance handle
	 */
	private static VkInstance createInstance(final PointerBuffer requiredExtensions)
	{
		final var	appInfo					= VkApplicationInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO)
				.apiVersion(VK10.VK_API_VERSION_1_0);
		final var	ppEnabledExtensionNames	= MemoryUtil.memAllocPointer(requiredExtensions.remaining() + 1);
		ppEnabledExtensionNames.put(requiredExtensions);
		final var VK_EXT_DEBUG_REPORT_EXTENSION = MemoryUtil.memUTF8(EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME);
		ppEnabledExtensionNames.put(VK_EXT_DEBUG_REPORT_EXTENSION);
		ppEnabledExtensionNames.flip();
		final var	ppEnabledLayerNames	= VulkanDemo.debug ? DisallowVKUtil.allocateLayerBuffer(VulkanDemo.layers) : null;
		final var	pCreateInfo			= VkInstanceCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO).pApplicationInfo(appInfo)
				.ppEnabledExtensionNames(ppEnabledExtensionNames).ppEnabledLayerNames(ppEnabledLayerNames);
		final var	pInstance			= MemoryUtil.memAllocPointer(1);
		final var	err					= VK10.vkCreateInstance(pCreateInfo, null, pInstance);
		final var	instance			= pInstance.get(0);
		MemoryUtil.memFree(pInstance);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create VkInstance: " + DisallowVKUtil.translateVulkanResult(err));
		}
		final var ret = new VkInstance(instance, pCreateInfo);
		pCreateInfo.free();
		if (ppEnabledLayerNames != null)
		{
			MemoryUtil.memFree(ppEnabledLayerNames);
		}
		MemoryUtil.memFree(VK_EXT_DEBUG_REPORT_EXTENSION);
		MemoryUtil.memFree(ppEnabledExtensionNames);
		MemoryUtil.memFree(appInfo.pApplicationName());
		MemoryUtil.memFree(appInfo.pEngineName());
		appInfo.free();
		return ret;
	}

	private static long setupDebugging(final VkInstance instance, final int flags,
			final VkDebugReportCallbackEXT callback)
	{
		final var	dbgCreateInfo	= VkDebugReportCallbackCreateInfoEXT.calloc()
				.sType(EXTDebugReport.VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT).pfnCallback(callback)
				.flags(flags);
		final var	pCallback		= MemoryUtil.memAllocLong(1);
		final var	err				= EXTDebugReport.vkCreateDebugReportCallbackEXT(instance, dbgCreateInfo, null,
				pCallback);
		final var	callbackHandle	= pCallback.get(0);
		MemoryUtil.memFree(pCallback);
		dbgCreateInfo.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create VkInstance: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return callbackHandle;
	}

	private static VkPhysicalDevice getFirstPhysicalDevice(final VkInstance instance)
	{
		final var	pPhysicalDeviceCount	= MemoryUtil.memAllocInt(1);
		var			err						= VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, null);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get number of physical devices: " + DisallowVKUtil.translateVulkanResult(err));
		}
		final var pPhysicalDevices = MemoryUtil.memAllocPointer(pPhysicalDeviceCount.get(0));
		err = VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, pPhysicalDevices);
		final var physicalDevice = pPhysicalDevices.get(0);
		MemoryUtil.memFree(pPhysicalDeviceCount);
		MemoryUtil.memFree(pPhysicalDevices);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get physical devices: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return new VkPhysicalDevice(physicalDevice, instance);
	}

	private static class DeviceAndGraphicsQueueFamily
	{
		VkDevice							device;
		int									queueFamilyIndex;
		VkPhysicalDeviceMemoryProperties	memoryProperties;
	}

	private static DeviceAndGraphicsQueueFamily createDeviceAndGetGraphicsQueueFamily(
			final VkPhysicalDevice physicalDevice)
	{
		final var pQueueFamilyPropertyCount = MemoryUtil.memAllocInt(1);
		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, null);
		final var	queueCount	= pQueueFamilyPropertyCount.get(0);
		final var	queueProps	= VkQueueFamilyProperties.calloc(queueCount);
		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, queueProps);
		MemoryUtil.memFree(pQueueFamilyPropertyCount);
		int graphicsQueueFamilyIndex;
		for (graphicsQueueFamilyIndex = 0; graphicsQueueFamilyIndex < queueCount; graphicsQueueFamilyIndex++)
		{
			if ((queueProps.get(graphicsQueueFamilyIndex).queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0)
			{
				break;
			}
		}
		queueProps.free();
		final var pQueuePriorities = MemoryUtil.memAllocFloat(1).put(0.0f);
		pQueuePriorities.flip();
		final var	queueCreateInfo				= VkDeviceQueueCreateInfo.calloc(1)
				.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO).queueFamilyIndex(graphicsQueueFamilyIndex)
				.pQueuePriorities(pQueuePriorities);

		final var	extensions					= MemoryUtil.memAllocPointer(1);
		final var	VK_KHR_SWAPCHAIN_EXTENSION	= MemoryUtil.memUTF8(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME);
		extensions.put(VK_KHR_SWAPCHAIN_EXTENSION);
		extensions.flip();

		final var	deviceCreateInfo	= VkDeviceCreateInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
				.pQueueCreateInfos(queueCreateInfo).ppEnabledExtensionNames(extensions);

		final var	pDevice				= MemoryUtil.memAllocPointer(1);
		final var	err					= VK10.vkCreateDevice(physicalDevice, deviceCreateInfo, null, pDevice);
		final var	device				= pDevice.get(0);
		MemoryUtil.memFree(pDevice);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create device: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var memoryProperties = VkPhysicalDeviceMemoryProperties.calloc();
		VK10.vkGetPhysicalDeviceMemoryProperties(physicalDevice, memoryProperties);

		final var ret = new DeviceAndGraphicsQueueFamily();
		ret.device				= new VkDevice(device, physicalDevice, deviceCreateInfo);
		ret.queueFamilyIndex	= graphicsQueueFamilyIndex;
		ret.memoryProperties	= memoryProperties;

		deviceCreateInfo.free();
		MemoryUtil.memFree(VK_KHR_SWAPCHAIN_EXTENSION);
		MemoryUtil.memFree(extensions);
		MemoryUtil.memFree(pQueuePriorities);
		return ret;
	}

	private static class ColorFormatAndSpace
	{
		int	colorFormat;
		int	colorSpace;
	}

	private static ColorFormatAndSpace getColorFormatAndSpace(final VkPhysicalDevice physicalDevice, final long surface)
	{
		final var pQueueFamilyPropertyCount = MemoryUtil.memAllocInt(1);
		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, null);
		final var	queueCount	= pQueueFamilyPropertyCount.get(0);
		final var	queueProps	= VkQueueFamilyProperties.calloc(queueCount);
		VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, queueProps);
		MemoryUtil.memFree(pQueueFamilyPropertyCount);

		// Iterate over each queue to learn whether it supports presenting:
		final var supportsPresent = MemoryUtil.memAllocInt(queueCount);
		for (var i = 0; i < queueCount; i++)
		{
			supportsPresent.position(i);
			final var err = KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i, surface,
					supportsPresent);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failed to physical device surface support: " + DisallowVKUtil.translateVulkanResult(err));
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

		// Generate error if could not find both a graphics and a present queue
		if (graphicsQueueNodeIndex == Integer.MAX_VALUE)
		{
			throw new AssertionError("No graphics queue found");
		}
		if (presentQueueNodeIndex == Integer.MAX_VALUE)
		{
			throw new AssertionError("No presentation queue found");
		}
		if (graphicsQueueNodeIndex != presentQueueNodeIndex)
		{
			throw new AssertionError("Presentation queue != graphics queue");
		}

		// Get list of supported formats
		final var	pFormatCount	= MemoryUtil.memAllocInt(1);
		var			err				= KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface,
				pFormatCount, null);
		final var	formatCount		= pFormatCount.get(0);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to query number of physical device surface formats: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var surfFormats = VkSurfaceFormatKHR.calloc(formatCount);
		err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface, pFormatCount, surfFormats);
		MemoryUtil.memFree(pFormatCount);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to query physical device surface formats: " + DisallowVKUtil.translateVulkanResult(err));
		}

		int colorFormat;
		if (formatCount == 1 && surfFormats.get(0).format() == VK10.VK_FORMAT_UNDEFINED)
		{
			colorFormat = VK10.VK_FORMAT_B8G8R8A8_UNORM;
		}
		else
		{
			colorFormat = surfFormats.get(0).format();
		}
		final var colorSpace = surfFormats.get(0).colorSpace();
		surfFormats.free();

		final var ret = new ColorFormatAndSpace();
		ret.colorFormat	= colorFormat;
		ret.colorSpace	= colorSpace;
		return ret;
	}

	private static long createCommandPool(final VkDevice device, final int queueNodeIndex)
	{
		final var	cmdPoolInfo	= VkCommandPoolCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO).queueFamilyIndex(queueNodeIndex)
				.flags(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT);
		final var	pCmdPool	= MemoryUtil.memAllocLong(1);
		final var	err			= VK10.vkCreateCommandPool(device, cmdPoolInfo, null, pCmdPool);
		final var	commandPool	= pCmdPool.get(0);
		cmdPoolInfo.free();
		MemoryUtil.memFree(pCmdPool);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create command pool: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return commandPool;
	}

	private static VkQueue createDeviceQueue(final VkDevice device, final int queueFamilyIndex)
	{
		final var pQueue = MemoryUtil.memAllocPointer(1);
		VK10.vkGetDeviceQueue(device, queueFamilyIndex, 0, pQueue);
		final var queue = pQueue.get(0);
		MemoryUtil.memFree(pQueue);
		return new VkQueue(queue, device);
	}

	private static VkCommandBuffer createCommandBuffer(final VkDevice device, final long commandPool)
	{
		final var	cmdBufAllocateInfo	= VkCommandBufferAllocateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO).commandPool(commandPool)
				.level(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY).commandBufferCount(1);
		final var	pCommandBuffer		= MemoryUtil.memAllocPointer(1);
		final var	err					= VK10.vkAllocateCommandBuffers(device, cmdBufAllocateInfo, pCommandBuffer);
		cmdBufAllocateInfo.free();
		final var commandBuffer = pCommandBuffer.get(0);
		MemoryUtil.memFree(pCommandBuffer);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to allocate command buffer: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return new VkCommandBuffer(commandBuffer, device);
	}

	private static class Swapchain
	{
		long	swapchainHandle;
		long[]	images;
		long[]	imageViews;
	}

	private static Swapchain createSwapChain(final VkDevice device, final VkPhysicalDevice physicalDevice,
			final long surface, final long oldSwapChain, final VkCommandBuffer commandBuffer, final int newWidth,
			final int newHeight, final int colorFormat, final int colorSpace)
	{
		int			err;
		// Get physical device surface properties and formats
		final var	surfCaps	= VkSurfaceCapabilitiesKHR.calloc();
		err = KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, surface, surfCaps);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface capabilities: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var pPresentModeCount = MemoryUtil.memAllocInt(1);
		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, surface, pPresentModeCount, null);
		final var presentModeCount = pPresentModeCount.get(0);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get number of physical device surface presentation modes: "
					+ DisallowVKUtil.translateVulkanResult(err));
		}

		final var pPresentModes = MemoryUtil.memAllocInt(presentModeCount);
		err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, surface, pPresentModeCount,
				pPresentModes);
		MemoryUtil.memFree(pPresentModeCount);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError(
					"Failed to get physical device surface presentation modes: " + DisallowVKUtil.translateVulkanResult(err));
		}

		// Try to use mailbox mode. Low latency and non-tearing
		var swapchainPresentMode = KHRSurface.VK_PRESENT_MODE_FIFO_KHR;
		for (var i = 0; i < presentModeCount; i++)
		{
			if (pPresentModes.get(i) == KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR)
			{
				swapchainPresentMode = KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR;
				break;
			}
			if (swapchainPresentMode != KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR
					&& pPresentModes.get(i) == KHRSurface.VK_PRESENT_MODE_IMMEDIATE_KHR)
			{
				swapchainPresentMode = KHRSurface.VK_PRESENT_MODE_IMMEDIATE_KHR;
			}
		}
		MemoryUtil.memFree(pPresentModes);

		// Determine the number of images
		var desiredNumberOfSwapchainImages = surfCaps.minImageCount() + 1;
		if (surfCaps.maxImageCount() > 0 && desiredNumberOfSwapchainImages > surfCaps.maxImageCount())
		{
			desiredNumberOfSwapchainImages = surfCaps.maxImageCount();
		}

		final var	currentExtent	= surfCaps.currentExtent();
		final var	currentWidth	= currentExtent.width();
		final var	currentHeight	= currentExtent.height();
		if (currentWidth != -1 && currentHeight != -1)
		{
			VulkanDemo.width	= currentWidth;
			VulkanDemo.height	= currentHeight;
		}
		else
		{
			VulkanDemo.width	= newWidth;
			VulkanDemo.height	= newHeight;
		}

		int preTransform;
		if ((surfCaps.supportedTransforms() & KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR) != 0)
		{
			preTransform = KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR;
		}
		else
		{
			preTransform = surfCaps.currentTransform();
		}
		surfCaps.free();

		final var swapchainCI = VkSwapchainCreateInfoKHR.calloc()
				.sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR).surface(surface)
				.minImageCount(desiredNumberOfSwapchainImages).imageFormat(colorFormat).imageColorSpace(colorSpace)
				.imageUsage(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT).preTransform(preTransform).imageArrayLayers(1)
				.imageSharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE).presentMode(swapchainPresentMode)
				.oldSwapchain(oldSwapChain).clipped(true).compositeAlpha(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);
		swapchainCI.imageExtent().width(VulkanDemo.width).height(VulkanDemo.height);
		final var pSwapChain = MemoryUtil.memAllocLong(1);
		err = KHRSwapchain.vkCreateSwapchainKHR(device, swapchainCI, null, pSwapChain);
		swapchainCI.free();
		final var swapChain = pSwapChain.get(0);
		MemoryUtil.memFree(pSwapChain);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create swap chain: " + DisallowVKUtil.translateVulkanResult(err));
		}

		// If we just re-created an existing swapchain, we should destroy the old
		// swapchain at this point.
		// Note: destroying the swapchain also cleans up all its associated presentable
		// images once the platform is done with them.
		if (oldSwapChain != VK10.VK_NULL_HANDLE)
		{
			KHRSwapchain.vkDestroySwapchainKHR(device, oldSwapChain, null);
		}

		final var pImageCount = MemoryUtil.memAllocInt(1);
		err = KHRSwapchain.vkGetSwapchainImagesKHR(device, swapChain, pImageCount, null);
		final var imageCount = pImageCount.get(0);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get number of swapchain images: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var pSwapchainImages = MemoryUtil.memAllocLong(imageCount);
		err = KHRSwapchain.vkGetSwapchainImagesKHR(device, swapChain, pImageCount, pSwapchainImages);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to get swapchain images: " + DisallowVKUtil.translateVulkanResult(err));
		}
		MemoryUtil.memFree(pImageCount);

		final var	images				= new long[imageCount];
		final var	imageViews			= new long[imageCount];
		final var	pBufferView			= MemoryUtil.memAllocLong(1);
		final var	colorAttachmentView	= VkImageViewCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO).format(colorFormat)
				.viewType(VK10.VK_IMAGE_VIEW_TYPE_2D);
		colorAttachmentView.subresourceRange().aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT).levelCount(1).layerCount(1);
		for (var i = 0; i < imageCount; i++)
		{
			images[i] = pSwapchainImages.get(i);
			colorAttachmentView.image(images[i]);
			err				= VK10.vkCreateImageView(device, colorAttachmentView, null, pBufferView);
			imageViews[i]	= pBufferView.get(0);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to create image view: " + DisallowVKUtil.translateVulkanResult(err));
			}
		}
		colorAttachmentView.free();
		MemoryUtil.memFree(pBufferView);
		MemoryUtil.memFree(pSwapchainImages);

		final var ret = new Swapchain();
		ret.images			= images;
		ret.imageViews		= imageViews;
		ret.swapchainHandle	= swapChain;
		return ret;
	}

	private static long createRenderPass(final VkDevice device, final int colorFormat)
	{
		final var	attachments		= VkAttachmentDescription.calloc(1).format(colorFormat)
				.samples(VK10.VK_SAMPLE_COUNT_1_BIT).loadOp(VK10.VK_ATTACHMENT_LOAD_OP_CLEAR)
				.storeOp(VK10.VK_ATTACHMENT_STORE_OP_STORE).stencilLoadOp(VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE)
				.stencilStoreOp(VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE).initialLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)
				.finalLayout(KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);

		final var	colorReference	= VkAttachmentReference.calloc(1).attachment(0)
				.layout(VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

		final var	subpass			= VkSubpassDescription.calloc(1)
				.pipelineBindPoint(VK10.VK_PIPELINE_BIND_POINT_GRAPHICS)
				.colorAttachmentCount(colorReference.remaining()).pColorAttachments(colorReference)																																								// <-
																																																																// only
																																																																// color
																																																																// attachment
		;

		final var	renderPassInfo	= VkRenderPassCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO).pAttachments(attachments).pSubpasses(subpass);

		final var	pRenderPass		= MemoryUtil.memAllocLong(1);
		final var	err				= VK10.vkCreateRenderPass(device, renderPassInfo, null, pRenderPass);
		final var	renderPass		= pRenderPass.get(0);
		MemoryUtil.memFree(pRenderPass);
		renderPassInfo.free();
		colorReference.free();
		subpass.free();
		attachments.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create clear render pass: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return renderPass;
	}

	private static long[] createFramebuffers(final VkDevice device, final Swapchain swapchain, final long renderPass,
			final int width, final int height)
	{
		final var	attachments		= MemoryUtil.memAllocLong(1);
		final var	fci				= VkFramebufferCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO).pAttachments(attachments).height(height)
				.width(width).layers(1).renderPass(renderPass);
		// Create a framebuffer for each swapchain image
		final var	framebuffers	= new long[swapchain.images.length];
		final var	pFramebuffer	= MemoryUtil.memAllocLong(1);
		for (var i = 0; i < swapchain.images.length; i++)
		{
			attachments.put(0, swapchain.imageViews[i]);
			final var	err			= VK10.vkCreateFramebuffer(device, fci, null, pFramebuffer);
			final var	framebuffer	= pFramebuffer.get(0);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to create framebuffer: " + DisallowVKUtil.translateVulkanResult(err));
			}
			framebuffers[i] = framebuffer;
		}
		MemoryUtil.memFree(attachments);
		MemoryUtil.memFree(pFramebuffer);
		fci.free();
		return framebuffers;
	}

	private static void submitCommandBuffer(final VkQueue queue, final VkCommandBuffer commandBuffer)
	{
		if (commandBuffer == null || commandBuffer.address() == MemoryUtil.NULL)
		{
			return;
		}
		final var	submitInfo		= VkSubmitInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO);
		final var	pCommandBuffers	= MemoryUtil.memAllocPointer(1).put(commandBuffer).flip();
		submitInfo.pCommandBuffers(pCommandBuffers);
		final var err = VK10.vkQueueSubmit(queue, submitInfo, VK10.VK_NULL_HANDLE);
		MemoryUtil.memFree(pCommandBuffers);
		submitInfo.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to submit command buffer: " + DisallowVKUtil.translateVulkanResult(err));
		}
	}

	private static long loadShader(final String classPath, final VkDevice device, final int stage) throws IOException
	{
		final var	shaderCode			= DisallowVKUtil.glslToSpirv(classPath, stage);
		int			err;
		final var	moduleCreateInfo	= VkShaderModuleCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO).pCode(shaderCode);
		final var	pShaderModule		= MemoryUtil.memAllocLong(1);
		err = VK10.vkCreateShaderModule(device, moduleCreateInfo, null, pShaderModule);
		final var shaderModule = pShaderModule.get(0);
		MemoryUtil.memFree(pShaderModule);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create shader module: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return shaderModule;
	}

	private static VkPipelineShaderStageCreateInfo loadShader(final VkDevice device, final String classPath,
			final int stage) throws IOException
	{
		final var shaderStage = VkPipelineShaderStageCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO).stage(stage)
				.module(VulkanDemo.loadShader(classPath, device, stage)).pName(MemoryUtil.memUTF8("main"));
		return shaderStage;
	}

	private static boolean getMemoryType(final VkPhysicalDeviceMemoryProperties deviceMemoryProperties,
			final int typeBits, final int properties, final IntBuffer typeIndex)
	{
		var bits = typeBits;
		for (var i = 0; i < 32; i++)
		{
			if ((bits & 1) == 1)
			{
				if ((deviceMemoryProperties.memoryTypes(i).propertyFlags() & properties) == properties)
				{
					typeIndex.put(0, i);
					return true;
				}
			}
			bits >>= 1;
		}
		return false;
	}

	private static class Vertices
	{
		long									verticesBuf;
		VkPipelineVertexInputStateCreateInfo	createInfo;
	}

	private static Vertices createVertices(final VkPhysicalDeviceMemoryProperties deviceMemoryProperties,
			final VkDevice device)
	{
		final var	vertexBuffer	= MemoryUtil.memAlloc(3 * 2 * 4);
		final var	fb				= vertexBuffer.asFloatBuffer();
		// The triangle will showup upside-down, because Vulkan does not do proper
		// viewport transformation to
		// account for inverted Y axis between the window coordinate system and clip
		// space/NDC
		fb.put(-0.5f).put(-0.5f);
		fb.put(0.5f).put(-0.5f);
		fb.put(0.0f).put(0.5f);

		final var	memAlloc	= VkMemoryAllocateInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
		final var	memReqs		= VkMemoryRequirements.calloc();

		int			err;

		// Generate vertex buffer
		// Setup
		final var	bufInfo		= VkBufferCreateInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
				.size(vertexBuffer.remaining()).usage(VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT);
		final var	pBuffer		= MemoryUtil.memAllocLong(1);
		err = VK10.vkCreateBuffer(device, bufInfo, null, pBuffer);
		final var verticesBuf = pBuffer.get(0);
		MemoryUtil.memFree(pBuffer);
		bufInfo.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create vertex buffer: " + DisallowVKUtil.translateVulkanResult(err));
		}

		VK10.vkGetBufferMemoryRequirements(device, verticesBuf, memReqs);
		memAlloc.allocationSize(memReqs.size());
		final var memoryTypeIndex = MemoryUtil.memAllocInt(1);
		VulkanDemo.getMemoryType(deviceMemoryProperties, memReqs.memoryTypeBits(),
				VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT, memoryTypeIndex);
		memAlloc.memoryTypeIndex(memoryTypeIndex.get(0));
		MemoryUtil.memFree(memoryTypeIndex);
		memReqs.free();

		final var pMemory = MemoryUtil.memAllocLong(1);
		err = VK10.vkAllocateMemory(device, memAlloc, null, pMemory);
		final var verticesMem = pMemory.get(0);
		MemoryUtil.memFree(pMemory);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to allocate vertex memory: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var pData = MemoryUtil.memAllocPointer(1);
		err = VK10.vkMapMemory(device, verticesMem, 0, memAlloc.allocationSize(), 0, pData);
		memAlloc.free();
		final var data = pData.get(0);
		MemoryUtil.memFree(pData);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to map vertex memory: " + DisallowVKUtil.translateVulkanResult(err));
		}

		MemoryUtil.memCopy(MemoryUtil.memAddress(vertexBuffer), data, vertexBuffer.remaining());
		MemoryUtil.memFree(vertexBuffer);
		VK10.vkUnmapMemory(device, verticesMem);
		err = VK10.vkBindBufferMemory(device, verticesBuf, verticesMem, 0);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to bind memory to vertex buffer: " + DisallowVKUtil.translateVulkanResult(err));
		}

		// Binding description
		final var	bindingDescriptor		= VkVertexInputBindingDescription.calloc(1).binding(0)													// <-
																																					// we
																																					// bind
																																					// our
																																					// vertex
																																					// buffer
																																					// to
																																					// point
																																					// 0
				.stride(2 * 4).inputRate(VK10.VK_VERTEX_INPUT_RATE_VERTEX);

		// Attribute descriptions
		// Describes memory layout and shader attribute locations
		final var	attributeDescriptions	= VkVertexInputAttributeDescription.calloc(1);
		// Location 0 : Position
		attributeDescriptions.get(0).binding(0) // <- binding point used in the VkVertexInputBindingDescription
				.location(0) // <- location in the shader's attribute layout (inside the shader source)
				.format(VK10.VK_FORMAT_R32G32_SFLOAT).offset(0);

		// Assign to vertex buffer
		final var	vi	= VkPipelineVertexInputStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
				.pVertexBindingDescriptions(bindingDescriptor).pVertexAttributeDescriptions(attributeDescriptions);

		final var	ret	= new Vertices();
		ret.createInfo	= vi;
		ret.verticesBuf	= verticesBuf;
		return ret;
	}

	private static long createPipeline(final VkDevice device, final long renderPass,
			final VkPipelineVertexInputStateCreateInfo vi) throws IOException
	{
		int			err;
		// Vertex input state
		// Describes the topoloy used with this pipeline
		final var	inputAssemblyState	= VkPipelineInputAssemblyStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
				.topology(VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST);

		// Rasterization state
		final var	rasterizationState	= VkPipelineRasterizationStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
				.polygonMode(VK10.VK_POLYGON_MODE_FILL).cullMode(VK10.VK_CULL_MODE_NONE)
				.frontFace(VK10.VK_FRONT_FACE_COUNTER_CLOCKWISE).lineWidth(1.0f);

		// Color blend state
		// Describes blend modes and color masks
		final var	colorWriteMask		= VkPipelineColorBlendAttachmentState.calloc(1).colorWriteMask(0xF);																													// <-
																																																								// RGBA
		final var	colorBlendState		= VkPipelineColorBlendStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO).pAttachments(colorWriteMask);

		// Viewport state
		final var	viewportState		= VkPipelineViewportStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO).viewportCount(1)																																// <-
																																																								// one
																																																								// viewport
				.scissorCount(1);																																																// <-
																																																								// one
																																																								// scissor
																																																								// rectangle

		// Enable dynamic states
		// Describes the dynamic states to be used with this pipeline
		// Dynamic states can be set even after the pipeline has been created
		// So there is no need to create new pipelines just for changing
		// a viewport's dimensions or a scissor box
		final var	pDynamicStates		= MemoryUtil.memAllocInt(2);
		pDynamicStates.put(VK10.VK_DYNAMIC_STATE_VIEWPORT).put(VK10.VK_DYNAMIC_STATE_SCISSOR).flip();
		final var	dynamicState		= VkPipelineDynamicStateCreateInfo.calloc()
				// The dynamic state properties themselves are stored in the command buffer
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO).pDynamicStates(pDynamicStates);

		// Depth and stencil state
		// Describes depth and stenctil test and compare ops
		final var	depthStencilState	= VkPipelineDepthStencilStateCreateInfo.calloc()
				// No depth test/write and no stencil used
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO)
				.depthCompareOp(VK10.VK_COMPARE_OP_ALWAYS);
		depthStencilState.back().failOp(VK10.VK_STENCIL_OP_KEEP).passOp(VK10.VK_STENCIL_OP_KEEP)
				.compareOp(VK10.VK_COMPARE_OP_ALWAYS);
		depthStencilState.front(depthStencilState.back());

		// Multi sampling state
		// No multi sampling used in this example
		final var	multisampleState	= VkPipelineMultisampleStateCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
				.rasterizationSamples(VK10.VK_SAMPLE_COUNT_1_BIT);

		// Load shaders
		final var	shaderStages		= VkPipelineShaderStageCreateInfo.calloc(2);
		shaderStages.get(0)
				.set(VulkanDemo.loadShader(device, "resources/shaders/triangle.vert", VK10.VK_SHADER_STAGE_VERTEX_BIT));
		shaderStages.get(1).set(
				VulkanDemo.loadShader(device, "resources/shaders/triangle.frag", VK10.VK_SHADER_STAGE_FRAGMENT_BIT));

		// Create the pipeline layout that is used to generate the rendering pipelines
		// that
		// are based on this descriptor set layout
		final var	pPipelineLayoutCreateInfo	= VkPipelineLayoutCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO);

		final var	pPipelineLayout				= MemoryUtil.memAllocLong(1);
		err = VK10.vkCreatePipelineLayout(device, pPipelineLayoutCreateInfo, null, pPipelineLayout);
		final var layout = pPipelineLayout.get(0);
		MemoryUtil.memFree(pPipelineLayout);
		pPipelineLayoutCreateInfo.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create pipeline layout: " + DisallowVKUtil.translateVulkanResult(err));
		}

		// Assign states
		final var	pipelineCreateInfo	= VkGraphicsPipelineCreateInfo.calloc(1)
				.sType(VK10.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO).layout(layout)																																																// <-
																																																																						// the
																																																																						// layout
																																																																						// used
																																																																						// for
																																																																						// this
																																																																						// pipeline
																																																																						// (NEEDS
																																																																						// TO
																																																																						// BE
																																																																						// SET!
																																																																						// even
																																																																						// though
																																																																						// it
																																																																						// is
																																																																						// basically
																																																																						// empty)
				.renderPass(renderPass)																																																													// <-
																																																																						// renderpass
																																																																						// this
																																																																						// pipeline
																																																																						// is
																																																																						// attached
																																																																						// to
				.pVertexInputState(vi).pInputAssemblyState(inputAssemblyState).pRasterizationState(rasterizationState)
				.pColorBlendState(colorBlendState).pMultisampleState(multisampleState).pViewportState(viewportState)
				.pDepthStencilState(depthStencilState).pStages(shaderStages).pDynamicState(dynamicState);

		// Create rendering pipeline
		final var	pPipelines			= MemoryUtil.memAllocLong(1);
		err = VK10.vkCreateGraphicsPipelines(device, VK10.VK_NULL_HANDLE, pipelineCreateInfo, null, pPipelines);
		final var pipeline = pPipelines.get(0);
		shaderStages.free();
		multisampleState.free();
		depthStencilState.free();
		dynamicState.free();
		MemoryUtil.memFree(pDynamicStates);
		viewportState.free();
		colorBlendState.free();
		colorWriteMask.free();
		rasterizationState.free();
		inputAssemblyState.free();
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create pipeline: " + DisallowVKUtil.translateVulkanResult(err));
		}
		return pipeline;
	}

	private static VkCommandBuffer[] createRenderCommandBuffers(final VkDevice device, final long commandPool,
			final long[] framebuffers, final long renderPass, final int width, final int height, final long pipeline,
			final long verticesBuf)
	{
		// Create the render command buffers (one command buffer per framebuffer image)
		final var	cmdBufAllocateInfo	= VkCommandBufferAllocateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO).commandPool(commandPool)
				.level(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY).commandBufferCount(framebuffers.length);
		final var	pCommandBuffer		= MemoryUtil.memAllocPointer(framebuffers.length);
		var			err					= VK10.vkAllocateCommandBuffers(device, cmdBufAllocateInfo, pCommandBuffer);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to allocate render command buffer: " + DisallowVKUtil.translateVulkanResult(err));
		}
		final var renderCommandBuffers = new VkCommandBuffer[framebuffers.length];
		for (var i = 0; i < framebuffers.length; i++)
		{
			renderCommandBuffers[i] = new VkCommandBuffer(pCommandBuffer.get(i), device);
		}
		MemoryUtil.memFree(pCommandBuffer);
		cmdBufAllocateInfo.free();

		// Create the command buffer begin structure
		final var	cmdBufInfo	= VkCommandBufferBeginInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO);

		// Specify clear color (cornflower blue)
		final var	clearValues	= VkClearValue.calloc(1);
		clearValues.color().float32(0, 100 / 255.0f).float32(1, 149 / 255.0f).float32(2, 237 / 255.0f).float32(3, 1.0f);

		// Specify everything to begin a render pass
		final var	renderPassBeginInfo	= VkRenderPassBeginInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO).renderPass(renderPass).pClearValues(clearValues);
		final var	renderArea			= renderPassBeginInfo.renderArea();
		renderArea.offset().set(0, 0);
		renderArea.extent().set(width, height);

		for (var i = 0; i < renderCommandBuffers.length; ++i)
		{
			// Set target frame buffer
			renderPassBeginInfo.framebuffer(framebuffers[i]);

			err = VK10.vkBeginCommandBuffer(renderCommandBuffers[i], cmdBufInfo);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to begin render command buffer: " + DisallowVKUtil.translateVulkanResult(err));
			}

			VK10.vkCmdBeginRenderPass(renderCommandBuffers[i], renderPassBeginInfo, VK10.VK_SUBPASS_CONTENTS_INLINE);

			// Update dynamic viewport state
			final var viewport = VkViewport.calloc(1).height(height).width(width).minDepth(0.0f).maxDepth(1.0f);
			VK10.vkCmdSetViewport(renderCommandBuffers[i], 0, viewport);
			viewport.free();

			// Update dynamic scissor state
			final var scissor = VkRect2D.calloc(1);
			scissor.extent().set(width, height);
			scissor.offset().set(0, 0);
			VK10.vkCmdSetScissor(renderCommandBuffers[i], 0, scissor);
			scissor.free();

			// Bind the rendering pipeline (including the shaders)
			VK10.vkCmdBindPipeline(renderCommandBuffers[i], VK10.VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);

			// Bind triangle vertices
			final var offsets = MemoryUtil.memAllocLong(1);
			offsets.put(0, 0L);
			final var pBuffers = MemoryUtil.memAllocLong(1);
			pBuffers.put(0, verticesBuf);
			VK10.vkCmdBindVertexBuffers(renderCommandBuffers[i], 0, pBuffers, offsets);
			MemoryUtil.memFree(pBuffers);
			MemoryUtil.memFree(offsets);

			// Draw triangle
			VK10.vkCmdDraw(renderCommandBuffers[i], 3, 1, 0, 0);

			VK10.vkCmdEndRenderPass(renderCommandBuffers[i]);

			err = VK10.vkEndCommandBuffer(renderCommandBuffers[i]);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to begin render command buffer: " + DisallowVKUtil.translateVulkanResult(err));
			}
		}
		renderPassBeginInfo.free();
		clearValues.free();
		cmdBufInfo.free();
		return renderCommandBuffers;
	}

	/*
	 * All resources that must be reallocated on window resize.
	 */
	private static Swapchain			swapchain;
	private static long[]				framebuffers;
	private static int					width, height;
	private static VkCommandBuffer[]	renderCommandBuffers;

	public static void main(final String[] args) throws IOException
	{
		if (!GLFW.glfwInit())
		{
			throw new RuntimeException("Failed to initialize GLFW");
		}
		if (!GLFWVulkan.glfwVulkanSupported())
		{
			throw new AssertionError("GLFW failed to find the Vulkan loader");
		}

		/* Look for instance extensions */
		final var requiredExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();
		if (requiredExtensions == null)
		{
			throw new AssertionError("Failed to find list of required Vulkan extensions");
		}

		// Create the Vulkan instance
		final var						instance						= VulkanDemo.createInstance(requiredExtensions);
		final VkDebugReportCallbackEXT	debugCallback					= new VkDebugReportCallbackEXT() {
																			@Override
																			public int invoke(final int flags,
																					final int objectType,
																					final long object,
																					final long location,
																					final int messageCode,
																					final long pLayerPrefix,
																					final long pMessage,
																					final long pUserData)
																			{
																				System.err.println("ERROR OCCURED: "
																						+ VkDebugReportCallbackEXT
																								.getString(pMessage));
																				return 0;
																			}
																		};
		final var						debugCallbackHandle				= VulkanDemo.setupDebugging(instance,
				EXTDebugReport.VK_DEBUG_REPORT_ERROR_BIT_EXT | EXTDebugReport.VK_DEBUG_REPORT_WARNING_BIT_EXT,
				debugCallback);
		final var						physicalDevice					= VulkanDemo.getFirstPhysicalDevice(instance);
		final var						deviceAndGraphicsQueueFamily	= VulkanDemo
				.createDeviceAndGetGraphicsQueueFamily(physicalDevice);
		final var						device							= deviceAndGraphicsQueueFamily.device;
		final var						queueFamilyIndex				= deviceAndGraphicsQueueFamily.queueFamilyIndex;
		final var						memoryProperties				= deviceAndGraphicsQueueFamily.memoryProperties;

		// Create GLFW window
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		final var		window	= GLFW.glfwCreateWindow(800, 600, "GLFW Vulkan Demo", MemoryUtil.NULL, MemoryUtil.NULL);
		GLFWKeyCallback	keyCallback;
		GLFW.glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(final long window, final int key, final int scancode, final int action, final int mods)
			{
				if (action != GLFW.GLFW_RELEASE)
				{
					return;
				}
				if (key == GLFW.GLFW_KEY_ESCAPE)
				{
					GLFW.glfwSetWindowShouldClose(window, true);
				}
			}
		});
		final var	pSurface	= MemoryUtil.memAllocLong(1);
		var			err			= GLFWVulkan.glfwCreateWindowSurface(instance, window, null, pSurface);
		final var	surface		= pSurface.get(0);
		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create surface: " + DisallowVKUtil.translateVulkanResult(err));
		}

		// Create static Vulkan resources
		final var	colorFormatAndSpace	= VulkanDemo.getColorFormatAndSpace(physicalDevice, surface);
		final var	commandPool			= VulkanDemo.createCommandPool(device, queueFamilyIndex);
		final var	setupCommandBuffer	= VulkanDemo.createCommandBuffer(device, commandPool);
		final var	queue				= VulkanDemo.createDeviceQueue(device, queueFamilyIndex);
		final var	renderPass			= VulkanDemo.createRenderPass(device, colorFormatAndSpace.colorFormat);
		final var	renderCommandPool	= VulkanDemo.createCommandPool(device, queueFamilyIndex);
		final var	vertices			= VulkanDemo.createVertices(memoryProperties, device);
		final var	pipeline			= VulkanDemo.createPipeline(device, renderPass, vertices.createInfo);

		final class SwapchainRecreator
		{
			boolean mustRecreate = true;

			void recreate()
			{
				// Begin the setup command buffer (the one we will use for swapchain/framebuffer
				// creation)
				final var	cmdBufInfo	= VkCommandBufferBeginInfo.calloc()
						.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO);
				var			err			= VK10.vkBeginCommandBuffer(setupCommandBuffer, cmdBufInfo);
				cmdBufInfo.free();
				if (err != VK10.VK_SUCCESS)
				{
					throw new AssertionError(
							"Failed to begin setup command buffer: " + DisallowVKUtil.translateVulkanResult(err));
				}
				final var oldChain = VulkanDemo.swapchain != null ? VulkanDemo.swapchain.swapchainHandle
						: VK10.VK_NULL_HANDLE;
				// Create the swapchain (this will also add a memory barrier to initialize the
				// framebuffer images)
				VulkanDemo.swapchain	= VulkanDemo.createSwapChain(device, physicalDevice, surface, oldChain,
						setupCommandBuffer, VulkanDemo.width, VulkanDemo.height, colorFormatAndSpace.colorFormat,
						colorFormatAndSpace.colorSpace);
				err						= VK10.vkEndCommandBuffer(setupCommandBuffer);
				if (err != VK10.VK_SUCCESS)
				{
					throw new AssertionError(
							"Failed to end setup command buffer: " + DisallowVKUtil.translateVulkanResult(err));
				}
				VulkanDemo.submitCommandBuffer(queue, setupCommandBuffer);
				VK10.vkQueueWaitIdle(queue);

				if (VulkanDemo.framebuffers != null)
				{
					for (final long framebuffer : VulkanDemo.framebuffers)
					{
						VK10.vkDestroyFramebuffer(device, framebuffer, null);
					}
				}
				VulkanDemo.framebuffers = VulkanDemo.createFramebuffers(device, VulkanDemo.swapchain, renderPass,
						VulkanDemo.width, VulkanDemo.height);
				// Create render command buffers
				if (VulkanDemo.renderCommandBuffers != null)
				{
					VK10.vkResetCommandPool(device, renderCommandPool, DisallowVKUtil.VK_FLAGS_NONE);
				}
				VulkanDemo.renderCommandBuffers	= VulkanDemo.createRenderCommandBuffers(device, renderCommandPool,
						VulkanDemo.framebuffers, renderPass, VulkanDemo.width, VulkanDemo.height, pipeline,
						vertices.verticesBuf);

				this.mustRecreate				= false;
			}
		}
		final var						swapchainRecreator	= new SwapchainRecreator();

		// Handle canvas resize
		final GLFWWindowSizeCallback	windowSizeCallback	= new GLFWWindowSizeCallback() {
																@Override
																public void invoke(final long window, final int width,
																		final int height)
																{
																	if (width <= 0 || height <= 0)
																	{
																		return;
																	}
																	VulkanDemo.width				= width;
																	VulkanDemo.height				= height;
																	swapchainRecreator.mustRecreate	= true;
																}
															};
		GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
		GLFW.glfwShowWindow(window);

		// Pre-allocate everything needed in the render loop

		final var	pImageIndex					= MemoryUtil.memAllocInt(1);
		var			currentBuffer				= 0;
		final var	pCommandBuffers				= MemoryUtil.memAllocPointer(1);
		final var	pSwapchains					= MemoryUtil.memAllocLong(1);
		final var	pImageAcquiredSemaphore		= MemoryUtil.memAllocLong(1);
		final var	pRenderCompleteSemaphore	= MemoryUtil.memAllocLong(1);

		// Info struct to create a semaphore
		final var	semaphoreCreateInfo			= VkSemaphoreCreateInfo.calloc()
				.sType(VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);

		// Info struct to submit a command buffer which will wait on the semaphore
		final var	pWaitDstStageMask			= MemoryUtil.memAllocInt(1);
		pWaitDstStageMask.put(0, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
		final var	submitInfo	= VkSubmitInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO)
				.waitSemaphoreCount(pImageAcquiredSemaphore.remaining()).pWaitSemaphores(pImageAcquiredSemaphore)
				.pWaitDstStageMask(pWaitDstStageMask).pCommandBuffers(pCommandBuffers)
				.pSignalSemaphores(pRenderCompleteSemaphore);

		// Info struct to present the current swapchain image to the display
		final var	presentInfo	= VkPresentInfoKHR.calloc().sType(KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
				.pWaitSemaphores(pRenderCompleteSemaphore).swapchainCount(pSwapchains.remaining())
				.pSwapchains(pSwapchains).pImageIndices(pImageIndex);

		final var	fpsUtils	= new FPSUtils();
		fpsUtils.start();

		// The render loop
		while (!GLFW.glfwWindowShouldClose(window))
		{
			// Handle window messages. Resize events happen exactly here.
			// So it is safe to use the new swapchain images and framebuffers afterwards.
			GLFW.glfwPollEvents();
			if (swapchainRecreator.mustRecreate)
			{
				swapchainRecreator.recreate();
			}

			// Create a semaphore to wait for the swapchain to acquire the next image
			err = VK10.vkCreateSemaphore(device, semaphoreCreateInfo, null, pImageAcquiredSemaphore);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failed to create image acquired semaphore: " + DisallowVKUtil.translateVulkanResult(err));
			}

			// Create a semaphore to wait for the render to complete, before presenting
			err = VK10.vkCreateSemaphore(device, semaphoreCreateInfo, null, pRenderCompleteSemaphore);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failed to create render complete semaphore: " + DisallowVKUtil.translateVulkanResult(err));
			}

			// Get next image from the swap chain (back/front buffer).
			// This will setup the imageAquiredSemaphore to be signalled when the operation
			// is complete
			err				= KHRSwapchain.vkAcquireNextImageKHR(device, VulkanDemo.swapchain.swapchainHandle,
					VulkanDemo.UINT64_MAX, pImageAcquiredSemaphore.get(0), VK10.VK_NULL_HANDLE, pImageIndex);
			currentBuffer	= pImageIndex.get(0);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError(
						"Failed to acquire next swapchain image: " + DisallowVKUtil.translateVulkanResult(err));
			}

			// Select the command buffer for the current framebuffer image/attachment
			pCommandBuffers.put(0, VulkanDemo.renderCommandBuffers[currentBuffer]);

			// Submit to the graphics queue
			err = VK10.vkQueueSubmit(queue, submitInfo, VK10.VK_NULL_HANDLE);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to submit render queue: " + DisallowVKUtil.translateVulkanResult(err));
			}

			// Present the current buffer to the swap chain
			// This will display the image
			pSwapchains.put(0, VulkanDemo.swapchain.swapchainHandle);
			err = KHRSwapchain.vkQueuePresentKHR(queue, presentInfo);
			if (err != VK10.VK_SUCCESS)
			{
				throw new AssertionError("Failed to present the swapchain image: " + DisallowVKUtil.translateVulkanResult(err));
			}
			// Create and submit post present barrier
			VK10.vkQueueWaitIdle(queue);

			// Destroy this semaphore (we will create a new one in the next frame)
			VK10.vkDestroySemaphore(device, pImageAcquiredSemaphore.get(0), null);
			VK10.vkDestroySemaphore(device, pRenderCompleteSemaphore.get(0), null);

			final var title = fpsUtils.updateFPS();
			if (title != null)
			{
				GLFW.glfwSetWindowTitle(window, title);
			}
		}
		presentInfo.free();
		MemoryUtil.memFree(pWaitDstStageMask);
		submitInfo.free();
		MemoryUtil.memFree(pImageAcquiredSemaphore);
		MemoryUtil.memFree(pRenderCompleteSemaphore);
		semaphoreCreateInfo.free();
		MemoryUtil.memFree(pSwapchains);
		MemoryUtil.memFree(pCommandBuffers);

		EXTDebugReport.vkDestroyDebugReportCallbackEXT(instance, debugCallbackHandle, null);

		windowSizeCallback.free();
		keyCallback.free();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();

		// We don't bother disposing of all Vulkan resources.
		// Let the OS process manager take care of it.
	}

}