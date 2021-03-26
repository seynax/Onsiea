package fr.seynax.onsiea.opengl;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;

public class OpenGLInitializer
{
	public final static void initialize(final IWindow windowIn)
	{
		GLFW.glfwMakeContextCurrent(windowIn.getWindowHandle());
	}
}