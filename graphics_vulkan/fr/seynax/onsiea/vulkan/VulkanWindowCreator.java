package fr.seynax.onsiea.vulkan;

import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryUtil;

import fr.seynax.onsiea.graphics.IWindowCreator;

public class VulkanWindowCreator implements IWindowCreator
{
	// Constructor

	public VulkanWindowCreator()
	{

	}

	// Interface methods

	@Override
	public boolean isSupported()
	{
		return GLFWVulkan.glfwVulkanSupported();
	}

	@Override
	public long create(final int widthIn, final int heightIn, final String titleIn, final long monitorIn,
			final long shareIn)
	{
		return MemoryUtil.NULL;
	}

	@Override
	public boolean initialization()
	{
		return false;
	}

	@Override
	public boolean enableDebug()
	{
		return false;
	}

	@Override
	public boolean disableDebug()
	{
		return false;
	}
}