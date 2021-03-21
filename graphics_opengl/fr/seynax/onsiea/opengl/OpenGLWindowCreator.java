package fr.seynax.onsiea.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import fr.seynax.onsiea.graphics.IWindowCreator;
import fr.seynax.onsiea.graphics.render.Renderer;

public class OpenGLWindowCreator implements IWindowCreator
{
	// Variables ; opengl callbacks

	private Callback debugProc;

	// Constructor

	public OpenGLWindowCreator()
	{

	}

	// Interface methods

	@Override
	public boolean isSupported()
	{
		return true;
	}

	@Override
	public long create(final int widthIn, final int heightIn, final String titleIn, final long monitorIn,
			final long shareIn)
	{
		return GLFW.glfwCreateWindow(widthIn, heightIn, titleIn, monitorIn, shareIn);
	}

	@Override
	public boolean initialization()
	{
		Renderer.openglInitialization();

		return true;
	}

	@Override
	public boolean enableDebug()
	{
		this.setDebugProc(GLUtil.setupDebugMessageCallback());

		return true;
	}

	@Override
	public boolean disableDebug()
	{
		return false;
	}

	// Getter | Setter

	Callback getDebugProc()
	{
		return this.debugProc;
	}

	private void setDebugProc(final Callback debugProcIn)
	{
		this.debugProc = debugProcIn;
	}
}