package fr.seynax.onsiea.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import fr.seynax.onsiea.graphics.IWindow;

public class OpenGL
{
	// Static variables

	private static Callback debugProc;

	// Static methods

	public final static void initialization(final IWindow windowIn)
	{
		GLFW.glfwMakeContextCurrent(windowIn.getWindowHandle());

		GL.createCapabilities();
	}

	public final static void showAllError()
	{
		var error = 0;
		while ((error = OpenGL.getError()) != GL11.GL_NO_ERROR)
		{
			System.err.println(OpenGL.translate(error));
		}
	}

	public final static int getError()
	{
		return GL11.glGetError();
	}

	public final static String translate(final int errorCodeIn)
	{
		switch (errorCodeIn)
		{
			case GL11.GL_NO_ERROR:
				return "No Error";

			case GL11.GL_INVALID_ENUM:
				return "Invalid Enum";

			case GL11.GL_INVALID_VALUE:
				return "Invalid Value";

			case GL11.GL_INVALID_OPERATION:
				return "Invalid Operation";

			case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
				return "Invalid Framebuffer Operation";

			case GL11.GL_OUT_OF_MEMORY:
				return "Out of Memory";

			case GL11.GL_STACK_UNDERFLOW:
				return "Stack Underflow";

			case GL11.GL_STACK_OVERFLOW:
				return "Stack Overflow";

			case GL45.GL_CONTEXT_LOST:
				return "Context Lost";

			default:
				return "Unknown Error";
		}
	}

	public final static void enableDebugging()
	{
		// GLFW

		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);

		// Callback

		OpenGL.setDebugProc(GLUtil.setupDebugMessageCallback());

		// GL

		GL11.glEnable(GL43.GL_DEBUG_OUTPUT);
		GL11.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
	}

	public final static void disableDebugging()
	{
		// GL

		GL11.glDisable(GL43.GL_DEBUG_OUTPUT);
		GL11.glDisable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);

		// Callback

		if (OpenGL.getDebugProc() != null)
		{
			OpenGL.getDebugProc().free();
			OpenGL.setDebugProc(null);
		}

		// GLFW

		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_FALSE);
	}

	public final static void cleanup()
	{
		OpenGLScreenshot.cleanup();

		OpenGL.disableDebugging();

		GL.setCapabilities(null);

		GL.destroy();
	}

	// Static getter | setter

	public static Callback getDebugProc()
	{
		return OpenGL.debugProc;
	}

	public static void setDebugProc(final Callback debugProcIn)
	{
		OpenGL.debugProc = debugProcIn;
	}
}