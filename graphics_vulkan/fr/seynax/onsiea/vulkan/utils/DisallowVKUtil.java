/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package fr.seynax.onsiea.vulkan.utils;

import static org.lwjgl.vulkan.EXTDebugReport.VK_ERROR_VALIDATION_FAILED_EXT;
import static org.lwjgl.vulkan.KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_ERROR_NATIVE_WINDOW_IN_USE_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_ERROR_SURFACE_LOST_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_SUBOPTIMAL_KHR;
import static org.lwjgl.vulkan.NVRayTracing.VK_SHADER_STAGE_ANY_HIT_BIT_NV;
import static org.lwjgl.vulkan.NVRayTracing.VK_SHADER_STAGE_CLOSEST_HIT_BIT_NV;
import static org.lwjgl.vulkan.NVRayTracing.VK_SHADER_STAGE_MISS_BIT_NV;
import static org.lwjgl.vulkan.NVRayTracing.VK_SHADER_STAGE_RAYGEN_BIT_NV;
import static org.lwjgl.vulkan.VK10.VK_ERROR_DEVICE_LOST;
import static org.lwjgl.vulkan.VK10.VK_ERROR_EXTENSION_NOT_PRESENT;
import static org.lwjgl.vulkan.VK10.VK_ERROR_FEATURE_NOT_PRESENT;
import static org.lwjgl.vulkan.VK10.VK_ERROR_FORMAT_NOT_SUPPORTED;
import static org.lwjgl.vulkan.VK10.VK_ERROR_INCOMPATIBLE_DRIVER;
import static org.lwjgl.vulkan.VK10.VK_ERROR_INITIALIZATION_FAILED;
import static org.lwjgl.vulkan.VK10.VK_ERROR_LAYER_NOT_PRESENT;
import static org.lwjgl.vulkan.VK10.VK_ERROR_MEMORY_MAP_FAILED;
import static org.lwjgl.vulkan.VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY;
import static org.lwjgl.vulkan.VK10.VK_ERROR_OUT_OF_HOST_MEMORY;
import static org.lwjgl.vulkan.VK10.VK_ERROR_TOO_MANY_OBJECTS;
import static org.lwjgl.vulkan.VK10.VK_EVENT_RESET;
import static org.lwjgl.vulkan.VK10.VK_EVENT_SET;
import static org.lwjgl.vulkan.VK10.VK_INCOMPLETE;
import static org.lwjgl.vulkan.VK10.VK_NOT_READY;
import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_FRAGMENT_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHADER_STAGE_VERTEX_BIT;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.VK_TIMEOUT;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.shaderc.Shaderc;
import org.lwjgl.util.shaderc.ShadercIncludeResolve;
import org.lwjgl.util.shaderc.ShadercIncludeResult;
import org.lwjgl.util.shaderc.ShadercIncludeResultRelease;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkLayerProperties;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.lwjgl.vulkan.VkSpecializationInfo;

import fr.seynax.onsiea.utils.file.IOUtils;

/**
 * Utility functions for Vulkan.
 *
 * @author Kai Burjack
 */
public class DisallowVKUtil
{
	public static final int VK_FLAGS_NONE = 0;

	private static int vulkanStageToShadercKind(final int stage)
	{
		switch (stage)
		{
			case VK_SHADER_STAGE_VERTEX_BIT:
				return Shaderc.shaderc_vertex_shader;
			case VK_SHADER_STAGE_FRAGMENT_BIT:
				return Shaderc.shaderc_fragment_shader;
			case VK_SHADER_STAGE_RAYGEN_BIT_NV:
				return Shaderc.shaderc_raygen_shader;
			case VK_SHADER_STAGE_CLOSEST_HIT_BIT_NV:
				return Shaderc.shaderc_closesthit_shader;
			case VK_SHADER_STAGE_MISS_BIT_NV:
				return Shaderc.shaderc_miss_shader;
			case VK_SHADER_STAGE_ANY_HIT_BIT_NV:
				return Shaderc.shaderc_anyhit_shader;
			default:
				throw new IllegalArgumentException("Stage: " + stage);
		}
	}

	public static ByteBuffer glslToSpirv(final String classPath, final int vulkanStage) throws IOException
	{
		final var			src			= IOUtils.ioResourceToByteBuffer(classPath, 1024);
		final var					compiler	= Shaderc.shaderc_compiler_initialize();
		final var					options		= Shaderc.shaderc_compile_options_initialize();
		ShadercIncludeResolve		resolver;
		ShadercIncludeResultRelease	releaser;
		Shaderc.shaderc_compile_options_set_target_env(options, Shaderc.shaderc_target_env_vulkan,
				Shaderc.shaderc_env_version_vulkan_1_2);
		Shaderc.shaderc_compile_options_set_target_spirv(options, Shaderc.shaderc_spirv_version_1_4);
		Shaderc.shaderc_compile_options_set_optimization_level(options, Shaderc.shaderc_optimization_level_performance);
		Shaderc.shaderc_compile_options_set_include_callbacks(options, resolver = new ShadercIncludeResolve() {
			@Override
			public long invoke(final long user_data, final long requested_source, final int type,
					final long requesting_source, final long include_depth)
			{
				final var res = ShadercIncludeResult.calloc();
				try
				{
					final var src = classPath.substring(0, classPath.lastIndexOf('/')) + "/"
							+ MemoryUtil.memUTF8(requested_source);
					res.content(IOUtils.ioResourceToByteBuffer(src, 1024));
					res.source_name(MemoryUtil.memUTF8(src));
					return res.address();
				}
				catch (final IOException e)
				{
					throw new AssertionError("Failed to resolve include: " + src);
				}
			}
		}, releaser = new ShadercIncludeResultRelease() {
			@Override
			public void invoke(final long user_data, final long include_result)
			{
				final var result = ShadercIncludeResult.create(include_result);
				MemoryUtil.memFree(result.source_name());
				result.free();
			}
		}, 0L);
		long res;
		try (var stack = MemoryStack.stackPush())
		{
			res = Shaderc.shaderc_compile_into_spv(compiler, src, DisallowVKUtil.vulkanStageToShadercKind(vulkanStage),
					stack.UTF8(classPath), stack.UTF8("main"), options);
			if (res == 0L)
			{
				throw new AssertionError("Internal error during compilation!");
			}
		}
		if (Shaderc.shaderc_result_get_compilation_status(res) != Shaderc.shaderc_compilation_status_success)
		{
			throw new AssertionError("Shader compilation failed: " + Shaderc.shaderc_result_get_error_message(res));
		}
		final var	size		= (int) Shaderc.shaderc_result_get_length(res);
		final var	resultBytes	= BufferUtils.createByteBuffer(size);
		resultBytes.put(Shaderc.shaderc_result_get_bytes(res));
		resultBytes.flip();
		Shaderc.shaderc_result_release(res);
		Shaderc.shaderc_compiler_release(compiler);
		releaser.free();
		resolver.free();
		return resultBytes;
	}

	public static void _CHECK_(final int ret, final String msg)
	{
		if (ret != VK10.VK_SUCCESS)
		{
			throw new AssertionError(msg + ": " + DisallowVKUtil.translateVulkanResult(ret));
		}
	}

	public static void loadShader(final VkPipelineShaderStageCreateInfo info, final VkSpecializationInfo specInfo,
			final MemoryStack stack, final VkDevice device, final String classPath, final int stage) throws IOException
	{
		final var	shaderCode		= DisallowVKUtil.glslToSpirv(classPath, stage);
		final var	pShaderModule	= stack.mallocLong(1);
		DisallowVKUtil._CHECK_(VK10.vkCreateShaderModule(device,
				VKFactory.VkShaderModuleCreateInfo(stack).pCode(shaderCode).flags(0), null, pShaderModule),
				"Failed to create shader module");
		info.stage(stage).pSpecializationInfo(specInfo).module(pShaderModule.get(0)).pName(stack.UTF8("main"));
	}

	/**
	 * Translates a Vulkan {@code VkResult} value to a String describing the result.
	 *
	 * @param result the {@code VkResult} value
	 *
	 * @return the result description
	 */
	public static String translateVulkanResult(final int result)
	{
		switch (result)
		{
			// Success codes
			case VK_SUCCESS:
				return "Command successfully completed.";
			case VK_NOT_READY:
				return "A fence or query has not yet completed.";
			case VK_TIMEOUT:
				return "A wait operation has not completed in the specified time.";
			case VK_EVENT_SET:
				return "An event is signaled.";
			case VK_EVENT_RESET:
				return "An event is unsignaled.";
			case VK_INCOMPLETE:
				return "A return array was too small for the result.";
			case VK_SUBOPTIMAL_KHR:
				return "A swapchain no longer matches the surface properties exactly, but can still be used to present to the surface successfully.";

			// Error codes
			case VK_ERROR_OUT_OF_HOST_MEMORY:
				return "A host memory allocation has failed.";
			case VK_ERROR_OUT_OF_DEVICE_MEMORY:
				return "A device memory allocation has failed.";
			case VK_ERROR_INITIALIZATION_FAILED:
				return "Initialization of an object could not be completed for implementation-specific reasons.";
			case VK_ERROR_DEVICE_LOST:
				return "The logical or physical device has been lost.";
			case VK_ERROR_MEMORY_MAP_FAILED:
				return "Mapping of a memory object has failed.";
			case VK_ERROR_LAYER_NOT_PRESENT:
				return "A requested layer is not present or could not be loaded.";
			case VK_ERROR_EXTENSION_NOT_PRESENT:
				return "A requested extension is not supported.";
			case VK_ERROR_FEATURE_NOT_PRESENT:
				return "A requested feature is not supported.";
			case VK_ERROR_INCOMPATIBLE_DRIVER:
				return "The requested version of Vulkan is not supported by the driver or is otherwise incompatible for implementation-specific reasons.";
			case VK_ERROR_TOO_MANY_OBJECTS:
				return "Too many objects of the type have already been created.";
			case VK_ERROR_FORMAT_NOT_SUPPORTED:
				return "A requested format is not supported on this device.";
			case VK_ERROR_SURFACE_LOST_KHR:
				return "A surface is no longer available.";
			case VK_ERROR_NATIVE_WINDOW_IN_USE_KHR:
				return "The requested window is already connected to a VkSurfaceKHR, or to some other non-Vulkan API.";
			case VK_ERROR_OUT_OF_DATE_KHR:
				return "A surface has changed in such a way that it is no longer compatible with the swapchain, and further presentation requests using the "
						+ "swapchain will fail. Applications must query the new surface properties and recreate their swapchain if they wish to continue"
						+ "presenting to the surface.";
			case VK_ERROR_INCOMPATIBLE_DISPLAY_KHR:
				return "The display used by a swapchain does not use the same presentable image layout, or is incompatible in a way that prevents sharing an"
						+ " image.";
			case VK_ERROR_VALIDATION_FAILED_EXT:
				return "A validation layer found an error.";
			default:
				return String.format("%s [%d]", "Unknown", Integer.valueOf(result));
		}
	}

	public static final PointerBuffer allocateLayerBuffer(final String[] layers)
	{
		final var	availableLayers		= DisallowVKUtil.getAvailableLayers();

		final var	ppEnabledLayerNames	= MemoryUtil.memAllocPointer(layers.length);
		System.out.println("Using layers:");
		for (final String layer : layers)
		{
			if (availableLayers.contains(layer))
			{
				System.out.println("\t" + layer);
				ppEnabledLayerNames.put(MemoryUtil.memUTF8(layer));
			}
		}
		ppEnabledLayerNames.flip();
		return ppEnabledLayerNames;
	}

	private static final Set<String> getAvailableLayers()
	{
		final Set<String>	res	= new HashSet<>();
		final var			ip	= new int[1];
		VK10.vkEnumerateInstanceLayerProperties(ip, null);
		final var count = ip[0];

		try (final var stack = MemoryStack.stackPush())
		{
			if (count > 0)
			{
				final var instanceLayers = VkLayerProperties.mallocStack(count, stack);
				VK10.vkEnumerateInstanceLayerProperties(ip, instanceLayers);
				for (var i = 0; i < count; i++)
				{
					final var layerName = instanceLayers.get(i).layerNameString();
					res.add(layerName);
				}
			}
		}

		return res;
	}
}