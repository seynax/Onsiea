package fr.seynax.onsiea.vulkan.utils;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.util.vma.VmaAllocationInfo;
import org.lwjgl.util.vma.VmaAllocatorCreateInfo;
import org.lwjgl.util.vma.VmaVulkanFunctions;
import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.EXTDebugUtils;
import org.lwjgl.vulkan.KHR8bitStorage;
import org.lwjgl.vulkan.KHRGetMemoryRequirements2;
import org.lwjgl.vulkan.KHRGetPhysicalDeviceProperties2;
import org.lwjgl.vulkan.KHRShaderFloat16Int8;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.KHRTimelineSemaphore;
import org.lwjgl.vulkan.NVRayTracing;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkAccelerationStructureCreateInfoNV;
import org.lwjgl.vulkan.VkAccelerationStructureInfoNV;
import org.lwjgl.vulkan.VkAccelerationStructureMemoryRequirementsInfoNV;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkBindAccelerationStructureMemoryInfoNV;
import org.lwjgl.vulkan.VkBufferCopy;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.lwjgl.vulkan.VkClearValue;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkComponentMapping;
import org.lwjgl.vulkan.VkDebugReportCallbackCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorImageInfo;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetAllocateInfo;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkDescriptorSetLayoutCreateInfo;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkFormatProperties;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;
import org.lwjgl.vulkan.VkGeometryAABBNV;
import org.lwjgl.vulkan.VkGeometryNV;
import org.lwjgl.vulkan.VkGeometryTrianglesNV;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkImageBlit;
import org.lwjgl.vulkan.VkImageCopy;
import org.lwjgl.vulkan.VkImageCreateInfo;
import org.lwjgl.vulkan.VkImageMemoryBarrier;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryBarrier;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkMemoryRequirements2KHR;
import org.lwjgl.vulkan.VkOffset3D;
import org.lwjgl.vulkan.VkPhysicalDevice8BitStorageFeaturesKHR;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures2;
import org.lwjgl.vulkan.VkPhysicalDeviceFloat16Int8FeaturesKHR;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties2;
import org.lwjgl.vulkan.VkPhysicalDeviceRayTracingPropertiesNV;
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
import org.lwjgl.vulkan.VkQueryPoolCreateInfo;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkRayTracingPipelineCreateInfoNV;
import org.lwjgl.vulkan.VkRayTracingShaderGroupCreateInfoNV;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkRenderPassBeginInfo;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSamplerCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreTypeCreateInfoKHR;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.lwjgl.vulkan.VkSpecializationInfo;
import org.lwjgl.vulkan.VkSpecializationMapEntry;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.lwjgl.vulkan.VkViewport;
import org.lwjgl.vulkan.VkWriteDescriptorSet;
import org.lwjgl.vulkan.VkWriteDescriptorSetAccelerationStructureNV;

/**
 * Factory methods to allocate various Vulkan structs with their propert sType.
 *
 * @author Kai Burjack
 */
@SuppressWarnings("deprecation")
public class VKFactory
{
	static VmaVulkanFunctions VmaVulkanFunctions(final MemoryStack stack)
	{
		return VmaVulkanFunctions.callocStack(stack);
	}

	static VmaAllocatorCreateInfo VmaAllocatorCreateInfo(final MemoryStack stack)
	{
		return VmaAllocatorCreateInfo.callocStack(stack);
	}

	static VkInstanceCreateInfo VkInstanceCreateInfo(final MemoryStack stack)
	{
		return VkInstanceCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
	}

	static VkApplicationInfo VkApplicationInfo(final MemoryStack stack)
	{
		return VkApplicationInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO);
	}

	static VkDebugReportCallbackCreateInfoEXT VkDebugReportCallbackCreateInfoEXT(final MemoryStack stack)
	{
		return VkDebugReportCallbackCreateInfoEXT.callocStack(stack)
				.sType(EXTDebugReport.VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT);
	}

	static VkDebugUtilsMessengerCreateInfoEXT VkDebugUtilsMessengerCreateInfoEXT(final MemoryStack stack)
	{
		return VkDebugUtilsMessengerCreateInfoEXT.callocStack(stack)
				.sType(EXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT);
	}

	static VkDeviceCreateInfo VkDeviceCreateInfo(final MemoryStack stack)
	{
		return VkDeviceCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
	}

	static VkDeviceQueueCreateInfo.Buffer VkDeviceQueueCreateInfo(final MemoryStack stack)
	{
		return VkDeviceQueueCreateInfo.callocStack(1, stack).sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
	}

	static VkPhysicalDevice8BitStorageFeaturesKHR VkPhysicalDevice8BitStorageFeaturesKHR(final MemoryStack stack)
	{
		return VkPhysicalDevice8BitStorageFeaturesKHR.callocStack(stack)
				.sType(KHR8bitStorage.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_8BIT_STORAGE_FEATURES_KHR);
	}

	static VkPhysicalDeviceFloat16Int8FeaturesKHR VkPhysicalDeviceFloat16Int8FeaturesKHR(final MemoryStack stack)
	{
		return VkPhysicalDeviceFloat16Int8FeaturesKHR.callocStack(stack)
				.sType(KHRShaderFloat16Int8.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FLOAT16_INT8_FEATURES_KHR);
	}

	static VkPhysicalDeviceProperties2 VkPhysicalDeviceProperties2(final MemoryStack stack)
	{
		return VkPhysicalDeviceProperties2.callocStack(stack)
				.sType(KHRGetPhysicalDeviceProperties2.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2_KHR);
	}

	static VkPhysicalDeviceRayTracingPropertiesNV VkPhysicalDeviceRayTracingPropertiesNV(final MemoryStack stack)
	{
		return VkPhysicalDeviceRayTracingPropertiesNV.callocStack(stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_RAY_TRACING_PROPERTIES_NV);
	}

	static VkSwapchainCreateInfoKHR VkSwapchainCreateInfoKHR(final MemoryStack stack)
	{
		return VkSwapchainCreateInfoKHR.callocStack(stack)
				.sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR);
	}

	static VkImageViewCreateInfo VkImageViewCreateInfo(final MemoryStack stack)
	{
		return VkImageViewCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO);
	}

	static VkCommandPoolCreateInfo VkCommandPoolCreateInfo(final MemoryStack stack)
	{
		return VkCommandPoolCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO);
	}

	static VkMemoryRequirements VkMemoryRequirements(final MemoryStack stack)
	{
		return VkMemoryRequirements.callocStack(stack);
	}

	static VkImageCreateInfo VkImageCreateInfo(final MemoryStack stack)
	{
		return VkImageCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO);
	}

	static VkImageMemoryBarrier.Buffer VkImageMemoryBarrier(final MemoryStack stack)
	{
		return VkImageMemoryBarrier.callocStack(1, stack).sType(VK10.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER);
	}

	static VkFenceCreateInfo VkFenceCreateInfo(final MemoryStack stack)
	{
		return VkFenceCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO);
	}

	static VkSubmitInfo VkSubmitInfo(final MemoryStack stack)
	{
		return VkSubmitInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO);
	}

	static VkCommandBufferBeginInfo VkCommandBufferBeginInfo(final MemoryStack stack)
	{
		return VkCommandBufferBeginInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO);
	}

	static VkCommandBufferAllocateInfo VkCommandBufferAllocateInfo(final MemoryStack stack)
	{
		return VkCommandBufferAllocateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO);
	}

	static VkMemoryAllocateInfo VkMemoryAllocateInfo(final MemoryStack stack)
	{
		return VkMemoryAllocateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO);
	}

	static VkBufferCreateInfo VkBufferCreateInfo(final MemoryStack stack)
	{
		return VkBufferCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO);
	}

	static VkGeometryAABBNV VkGeometryAABBNV(final VkGeometryAABBNV geometry)
	{
		return geometry.sType(NVRayTracing.VK_STRUCTURE_TYPE_GEOMETRY_AABB_NV);
	}

	static VkGeometryTrianglesNV VkGeometryTrianglesNV(final VkGeometryTrianglesNV geometry)
	{
		return geometry.sType(NVRayTracing.VK_STRUCTURE_TYPE_GEOMETRY_TRIANGLES_NV);
	}

	static VkGeometryNV VkGeometryNV(final MemoryStack stack)
	{
		return VkGeometryNV.callocStack(stack).sType(NVRayTracing.VK_STRUCTURE_TYPE_GEOMETRY_NV);
	}

	static VkMemoryBarrier.Buffer VkMemoryBarrier(final MemoryStack stack)
	{
		return VkMemoryBarrier.callocStack(1, stack).sType(VK10.VK_STRUCTURE_TYPE_MEMORY_BARRIER);
	}

	static VkBindAccelerationStructureMemoryInfoNV.Buffer VkBindAccelerationStructureMemoryInfoNV(
			final MemoryStack stack)
	{
		return VkBindAccelerationStructureMemoryInfoNV.callocStack(1, stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_BIND_ACCELERATION_STRUCTURE_MEMORY_INFO_NV);
	}

	static VkAccelerationStructureInfoNV VkAccelerationStructureInfoNV(final MemoryStack stack)
	{
		return VkAccelerationStructureInfoNV.callocStack(stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_ACCELERATION_STRUCTURE_INFO_NV);
	}

	static VkMemoryRequirements2KHR VkMemoryRequirements2KHR(final MemoryStack stack)
	{
		return VkMemoryRequirements2KHR.callocStack(stack)
				.sType(KHRGetMemoryRequirements2.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2_KHR);
	}

	static VkAccelerationStructureMemoryRequirementsInfoNV VkAccelerationStructureMemoryRequirementsInfoNV(
			final MemoryStack stack)
	{
		return VkAccelerationStructureMemoryRequirementsInfoNV.callocStack(stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_ACCELERATION_STRUCTURE_MEMORY_REQUIREMENTS_INFO_NV);
	}

	static VkAccelerationStructureCreateInfoNV VkAccelerationStructureCreateInfoNV(final MemoryStack stack)
	{
		return VkAccelerationStructureCreateInfoNV.callocStack(stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_ACCELERATION_STRUCTURE_CREATE_INFO_NV);
	}

	static VkPipelineShaderStageCreateInfo.Buffer VkPipelineShaderStageCreateInfo(final MemoryStack stack,
			final int count)
	{
		final var ret = VkPipelineShaderStageCreateInfo.callocStack(count, stack);
		ret.forEach(sci -> sci.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO));
		return ret;
	}

	static VkDescriptorSetLayoutBinding.Buffer VkDescriptorSetLayoutBinding(final MemoryStack stack, final int count)
	{
		return VkDescriptorSetLayoutBinding.callocStack(count, stack);
	}

	static VkDescriptorSetLayoutBinding VkDescriptorSetLayoutBinding(final MemoryStack stack)
	{
		return VkDescriptorSetLayoutBinding.callocStack(stack);
	}

	static VkRayTracingPipelineCreateInfoNV.Buffer VkRayTracingPipelineCreateInfoNV(final MemoryStack stack)
	{
		return VkRayTracingPipelineCreateInfoNV.callocStack(1, stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_RAY_TRACING_PIPELINE_CREATE_INFO_NV);
	}

	static VkRayTracingShaderGroupCreateInfoNV.Buffer VkRayTracingShaderGroupCreateInfoNV(final int size,
			final MemoryStack stack)
	{
		final var buf = VkRayTracingShaderGroupCreateInfoNV.callocStack(size, stack);
		buf.forEach(info -> info.sType(NVRayTracing.VK_STRUCTURE_TYPE_RAY_TRACING_SHADER_GROUP_CREATE_INFO_NV)
				.anyHitShader(NVRayTracing.VK_SHADER_UNUSED_NV).closestHitShader(NVRayTracing.VK_SHADER_UNUSED_NV)
				.generalShader(NVRayTracing.VK_SHADER_UNUSED_NV).intersectionShader(NVRayTracing.VK_SHADER_UNUSED_NV));
		return buf;
	}

	static VkPipelineLayoutCreateInfo VkPipelineLayoutCreateInfo(final MemoryStack stack)
	{
		return VkPipelineLayoutCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO);
	}

	static VkDescriptorSetLayoutCreateInfo VkDescriptorSetLayoutCreateInfo(final MemoryStack stack)
	{
		return VkDescriptorSetLayoutCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO);
	}

	static VkDescriptorBufferInfo.Buffer VkDescriptorBufferInfo(final MemoryStack stack, final int count)
	{
		return VkDescriptorBufferInfo.callocStack(count, stack);
	}

	static VkDescriptorImageInfo.Buffer VkDescriptorImageInfo(final MemoryStack stack, final int count)
	{
		return VkDescriptorImageInfo.callocStack(count, stack);
	}

	static VkDescriptorPoolSize.Buffer VkDescriptorPoolSize(final MemoryStack stack, final int count)
	{
		return VkDescriptorPoolSize.callocStack(count, stack);
	}

	static VkWriteDescriptorSetAccelerationStructureNV VkWriteDescriptorSetAccelerationStructureNV(
			final MemoryStack stack)
	{
		return VkWriteDescriptorSetAccelerationStructureNV.callocStack(stack)
				.sType(NVRayTracing.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET_ACCELERATION_STRUCTURE_NV);
	}

	static VkWriteDescriptorSet VkWriteDescriptorSet(final MemoryStack stack)
	{
		return VkWriteDescriptorSet.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
	}

	static VkDescriptorSetAllocateInfo VkDescriptorSetAllocateInfo(final MemoryStack stack)
	{
		return VkDescriptorSetAllocateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO);
	}

	static VkDescriptorPoolCreateInfo VkDescriptorPoolCreateInfo(final MemoryStack stack)
	{
		return VkDescriptorPoolCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO);
	}

	static VkPresentInfoKHR VkPresentInfoKHR(final MemoryStack stack)
	{
		return VkPresentInfoKHR.callocStack(stack).sType(KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR);
	}

	static VkSemaphoreCreateInfo VkSemaphoreCreateInfo(final MemoryStack stack)
	{
		return VkSemaphoreCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);
	}

	static VkSemaphoreTypeCreateInfoKHR VkSemaphoreTypeCreateInfo(final MemoryStack stack)
	{
		return VkSemaphoreTypeCreateInfoKHR.calloc(stack)
				.sType(KHRTimelineSemaphore.VK_STRUCTURE_TYPE_SEMAPHORE_TYPE_CREATE_INFO_KHR);
	}

	static VkQueueFamilyProperties.Buffer VkQueueFamilyProperties(final int count)
	{
		return VkQueueFamilyProperties.callocStack(count);
	}

	static VkPhysicalDeviceFeatures VkPhysicalDeviceFeatures(final MemoryStack stack)
	{
		return VkPhysicalDeviceFeatures.callocStack(stack);
	}

	static VkPhysicalDeviceFeatures2 VkPhysicalDeviceFeatures2(final MemoryStack stack)
	{
		return VkPhysicalDeviceFeatures2.callocStack(stack)
				.sType(KHRGetPhysicalDeviceProperties2.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2_KHR);
	}

	static VkPhysicalDeviceProperties VkPhysicalDeviceProperties(final MemoryStack stack)
	{
		return VkPhysicalDeviceProperties.callocStack(stack);
	}

	static VkGeometryNV.Buffer VkGeometryNV(final MemoryStack stack, final int count)
	{
		final var buf = VkGeometryNV.callocStack(count, stack);
		buf.forEach(info -> info.sType(NVRayTracing.VK_STRUCTURE_TYPE_GEOMETRY_NV));
		return buf;
	}

	static VkWriteDescriptorSet.Buffer VkWriteDescriptorSet(final MemoryStack stack, final int count)
	{
		final var ret = VkWriteDescriptorSet.callocStack(count, stack);
		ret.forEach(wds -> wds.sType(VK10.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET));
		return ret;
	}

	static VkPipelineShaderStageCreateInfo VkPipelineShaderStageCreateInfo(final MemoryStack stack)
	{
		return VkPipelineShaderStageCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO);
	}

	static VkShaderModuleCreateInfo VkShaderModuleCreateInfo(final MemoryStack stack)
	{
		return VkShaderModuleCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO);
	}

	static VkSurfaceCapabilitiesKHR VkSurfaceCapabilitiesKHR(final MemoryStack stack)
	{
		return VkSurfaceCapabilitiesKHR.callocStack(stack);
	}

	static VkSurfaceFormatKHR.Buffer VkSurfaceFormatKHR(final MemoryStack stack, final int count)
	{
		return VkSurfaceFormatKHR.callocStack(count, stack);
	}

	static VmaAllocationCreateInfo VmaAllocationCreateInfo(final MemoryStack stack)
	{
		return VmaAllocationCreateInfo.callocStack(stack);
	}

	static VmaAllocationInfo VmaAllocationInfo(final MemoryStack stack)
	{
		return VmaAllocationInfo.callocStack(stack);
	}

	static VkBufferCopy.Buffer VkBufferCopy(final MemoryStack stack, final int count)
	{
		return VkBufferCopy.callocStack(count, stack);
	}

	static VkSamplerCreateInfo VkSamplerCreateInfo(final MemoryStack stack)
	{
		return VkSamplerCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO);
	}

	static VkBufferImageCopy.Buffer VkBufferImageCopy(final MemoryStack stack)
	{
		return VkBufferImageCopy.callocStack(1, stack);
	}

	static VkImageSubresourceRange VkImageSubresourceRange(final MemoryStack stack)
	{
		return VkImageSubresourceRange.callocStack(stack);
	}

	static VkComponentMapping VkComponentMapping(final MemoryStack stack)
	{
		return VkComponentMapping.callocStack(stack);
	}

	static VkAttachmentReference VkAttachmentReference(final MemoryStack stack)
	{
		return VkAttachmentReference.callocStack(stack);
	}

	static VkAttachmentReference.Buffer VkAttachmentReference(final MemoryStack stack, final int count)
	{
		return VkAttachmentReference.callocStack(count, stack);
	}

	static VkSubpassDescription.Buffer VkSubpassDescription(final MemoryStack stack, final int count)
	{
		return VkSubpassDescription.callocStack(count, stack);
	}

	static VkAttachmentDescription.Buffer VkAttachmentDescription(final MemoryStack stack, final int count)
	{
		return VkAttachmentDescription.callocStack(count, stack);
	}

	static VkRenderPassCreateInfo VkRenderPassCreateInfo(final MemoryStack stack)
	{
		return VkRenderPassCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO);
	}

	static VkOffset3D VkOffset3D(final MemoryStack stack)
	{
		return VkOffset3D.callocStack(stack);
	}

	static VkImageBlit.Buffer VkImageBlit(final MemoryStack stack, final int count)
	{
		return VkImageBlit.callocStack(count, stack);
	}

	static VkSpecializationMapEntry.Buffer VkSpecializationMapEntry(final MemoryStack stack, final int count)
	{
		return VkSpecializationMapEntry.callocStack(count, stack);
	}

	static VkSpecializationInfo VkSpecializationInfo(final MemoryStack stack)
	{
		return VkSpecializationInfo.callocStack(stack);
	}

	static VkQueryPoolCreateInfo VkQueryPoolCreateInfo(final MemoryStack stack)
	{
		return VkQueryPoolCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO);
	}

	static VkGeometryNV.Buffer VkGeometryNV(final int count)
	{
		return VkGeometryNV.calloc(count).sType(NVRayTracing.VK_STRUCTURE_TYPE_GEOMETRY_NV);
	}

	static VkFramebufferCreateInfo VkFramebufferCreateInfo(final MemoryStack stack)
	{
		return VkFramebufferCreateInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO);
	}

	static VkVertexInputBindingDescription.Buffer VkVertexInputBindingDescription(final MemoryStack stack,
			final int count)
	{
		return VkVertexInputBindingDescription.callocStack(count, stack);
	}

	static VkVertexInputAttributeDescription.Buffer VkVertexInputAttributeDescription(final MemoryStack stack,
			final int count)
	{
		return VkVertexInputAttributeDescription.callocStack(count, stack);
	}

	static VkPipelineVertexInputStateCreateInfo VkPipelineVertexInputStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineVertexInputStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO);
	}

	static VkPipelineInputAssemblyStateCreateInfo VkPipelineInputAssemblyStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineInputAssemblyStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO);
	}

	static VkPipelineRasterizationStateCreateInfo VkPipelineRasterizationStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineRasterizationStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO);
	}

	static VkPipelineColorBlendAttachmentState.Buffer VkPipelineColorBlendAttachmentState(final MemoryStack stack,
			final int count)
	{
		return VkPipelineColorBlendAttachmentState.callocStack(count, stack);
	}

	static VkPipelineColorBlendStateCreateInfo VkPipelineColorBlendStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineColorBlendStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO);
	}

	static VkPipelineViewportStateCreateInfo VkPipelineViewportStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineViewportStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO);
	}

	static VkPipelineDynamicStateCreateInfo VkPipelineDynamicStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineDynamicStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO);
	}

	static VkPipelineDepthStencilStateCreateInfo VkPipelineDepthStencilStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineDepthStencilStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO);
	}

	static VkPipelineMultisampleStateCreateInfo VkPipelineMultisampleStateCreateInfo(final MemoryStack stack)
	{
		return VkPipelineMultisampleStateCreateInfo.callocStack(stack)
				.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO);
	}

	static VkGraphicsPipelineCreateInfo.Buffer VkGraphicsPipelineCreateInfo(final MemoryStack stack, final int count)
	{
		final var ret = VkGraphicsPipelineCreateInfo.callocStack(count, stack);
		ret.forEach(pci -> pci.sType(VK10.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO));
		return ret;
	}

	static VkClearValue.Buffer VkClearValue(final MemoryStack stack, final int count)
	{
		return VkClearValue.callocStack(count, stack);
	}

	static VkRenderPassBeginInfo VkRenderPassBeginInfo(final MemoryStack stack)
	{
		return VkRenderPassBeginInfo.callocStack(stack).sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO);
	}

	static VkViewport.Buffer VkViewport(final MemoryStack stack, final int count)
	{
		return VkViewport.callocStack(count, stack);
	}

	static VkRect2D.Buffer VkRect2D(final MemoryStack stack, final int count)
	{
		return VkRect2D.callocStack(count, stack);
	}

	static VkFormatProperties VkFormatProperties(final MemoryStack stack)
	{
		return VkFormatProperties.callocStack(stack);
	}

	static VkSubpassDependency.Buffer VkSubpassDependency(final MemoryStack stack, final int count)
	{
		return VkSubpassDependency.callocStack(count, stack);
	}

	static VkImageCopy.Buffer VkImageCopy(final MemoryStack stack, final int count)
	{
		return VkImageCopy.callocStack(count, stack);
	}

}