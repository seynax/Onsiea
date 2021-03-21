package fr.seynax.onsiea.vulkan;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;

import fr.seynax.onsiea.vulkan.utils.DisallowVKUtil;

public class VulkanInstance
{
	private String	applicationName;
	private String	engineName;

	public VulkanInstance(final String applicationNameIn, final String engineNameIn)
	{
		this.setApplicationName(applicationNameIn);
		this.setEngineName(engineNameIn);
	}

	public VkInstance initialization()
	{
		// Enabled layers

		final Set<String>	availableLayers			= new HashSet<>();
		final var			availableLayersCount	= new int[1];

		VK10.vkEnumerateInstanceLayerProperties(availableLayersCount, null);
		final var count = availableLayersCount[0];

		try (final var stack = MemoryStack.stackPush())
		{
			if (count > 0)
			{
				final var instanceLayers = VkLayerProperties.mallocStack(count, stack);
				VK10.vkEnumerateInstanceLayerProperties(availableLayersCount, instanceLayers);
				for (var i = 0; i < count; i++)
				{
					final var layerName = instanceLayers.get(i).layerNameString();
					availableLayers.add(layerName);
				}
			}
		}

		final var	layers				= new String[] { "VK_LAYER_LUNARG_standard_validation",
				"VK_LAYER_KHRONOS_validation" };

		final var	ppEnabledLayerNames	= MemoryUtil.memAllocPointer(layers.length);

		for (final String layer : layers)
		{
			if (availableLayers.contains(layer))
			{
				ppEnabledLayerNames.put(MemoryUtil.memUTF8(layer));
			}
		}
		ppEnabledLayerNames.flip();

		// Enabled extensions

		final var	requiredExtension		= GLFWVulkan.glfwGetRequiredInstanceExtensions();

		final var	ppEnabledExtensionNames	= MemoryUtil.memAllocPointer(requiredExtension.remaining() + 1);
		ppEnabledExtensionNames.put(requiredExtension);
		final var VK_EXT_DEBUG_REPORT_EXTENSION = MemoryUtil.memUTF8(EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME);
		ppEnabledExtensionNames.flip();

		// ApplicationInfo

		final var applicationInfo = VkApplicationInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO)
				.apiVersion(VK10.VK_API_VERSION_1_0);

		applicationInfo.pApplicationName(MemoryUtil.memUTF8(this.getApplicationName()));
		applicationInfo.pEngineName(MemoryUtil.memUTF8(this.getEngineName()));
		applicationInfo.engineVersion(1);
		applicationInfo.applicationVersion(1);

		// CreateInfo

		final var instanceCreateInfo = VkInstanceCreateInfo.calloc().sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);

		instanceCreateInfo.pApplicationInfo(applicationInfo);
		instanceCreateInfo.ppEnabledExtensionNames(ppEnabledExtensionNames);
		instanceCreateInfo.ppEnabledLayerNames(ppEnabledLayerNames);

		// Instance

		final var	pInstance		= MemoryUtil.memAllocPointer(1);

		final var	err				= VK10.vkCreateInstance(instanceCreateInfo, null, pInstance);

		final var	instancePointer	= pInstance.get(0);

		MemoryUtil.memFree(pInstance);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create VkInstance: " + DisallowVKUtil.translateVulkanResult(err));
		}

		final var instance = new VkInstance(instancePointer, instanceCreateInfo);

		// Cleanup and memfree

		instanceCreateInfo.free();
		if (ppEnabledLayerNames != null)
		{
			MemoryUtil.memFree(ppEnabledLayerNames);
		}
		MemoryUtil.memFree(VK_EXT_DEBUG_REPORT_EXTENSION);
		MemoryUtil.memFree(ppEnabledExtensionNames);
		MemoryUtil.memFree(applicationInfo.pApplicationName());
		MemoryUtil.memFree(applicationInfo.pEngineName());
		applicationInfo.free();

		return instance;
	}

	// Getter | Setter

	private String getApplicationName()
	{
		return this.applicationName;
	}

	private void setApplicationName(final String applicationNameIn)
	{
		this.applicationName = applicationNameIn;
	}

	private String getEngineName()
	{
		return this.engineName;
	}

	private void setEngineName(final String engineNameIn)
	{
		this.engineName = engineNameIn;
	}
}