package fr.seynax.onsiea.vulkan;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.GenericWindow;
import fr.seynax.onsiea.graphics.IWindow;

public class VulkanWindow extends GenericWindow implements IWindow
{
	// Constructor

	public VulkanWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn)
	{
		super(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, true);
	}

	public VulkanWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn, final boolean fullscreenIn)
	{
		super(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, fullscreenIn);
	}

	// Methods

	@Override
	public void initializeWindowHintsFirstPhase(final boolean debugIsEnableIn)
	{
		GLFW.glfwDefaultWindowHints();

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);
	}
}
