package fr.seynax.onsiea.graphics.callbacks;

import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

import fr.seynax.onsiea.graphics.IWindow;

public class FramebufferSizeCallback implements GLFWFramebufferSizeCallbackI
{
	// Constructor variables

	private IWindow	window;

	// Variables

	private boolean	isResized;

	// Constructor

	public FramebufferSizeCallback(final IWindow windowIn)
	{
		this.setWindow(windowIn);
	}

	// Interface methods

	@Override
	public void invoke(final long windowIn, final int widthIn, final int heightIn)
	{
		this.getWindow().setWidth(widthIn);
		this.getWindow().setHeight(heightIn);
		this.setResized(true);
	}

	// Getter | Setter

	private IWindow getWindow()
	{
		return this.window;
	}

	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	public boolean isResized()
	{
		return this.isResized;
	}

	public void setResized(final boolean isResizedIn)
	{
		this.isResized = isResizedIn;
	}
}