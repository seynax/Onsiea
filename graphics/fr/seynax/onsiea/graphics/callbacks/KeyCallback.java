package fr.seynax.onsiea.graphics.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyCallback implements GLFWKeyCallbackI
{
	// Constructor

	public KeyCallback()
	{
	}

	// Interface methods

	@Override
	public void invoke(final long windowIn, final int keyIn, final int scancodeIn, final int actionIn, final int modsIn)
	{
		if (keyIn == GLFW.GLFW_KEY_ESCAPE && actionIn == GLFW.GLFW_RELEASE)
		{
			GLFW.glfwSetWindowShouldClose(windowIn, true); // We will detect this in the rendering loop
		}
	}
}