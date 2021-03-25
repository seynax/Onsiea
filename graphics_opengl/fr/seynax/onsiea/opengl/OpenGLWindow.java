package fr.seynax.onsiea.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import fr.seynax.onsiea.graphics.GenericWindow;
import fr.seynax.onsiea.graphics.callbacks.GLFWEventManager;
import fr.seynax.onsiea.opengl.render.Renderer;

public class OpenGLWindow extends GenericWindow
{
	// Constructor

	public OpenGLWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn)
	{
		super(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, true);
	}

	public OpenGLWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn, final boolean fullscreenIn)
	{
		super(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, fullscreenIn);
	}

	// Methods
	@Override
	public GLFWEventManager initialization(final double intervalIn)
	{
		this.setGlfwEventManager(new GLFWEventManager(this));

		this.getGlfwEventManager().getCallbacksManager().errorCallbackInitialization();

		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// WindowHint

		this.initializeWindowHintsFirstPhase(false);

		// Fullscreen

		final var	primaryMonitor	= GLFW.glfwGetPrimaryMonitor();
		final var	vidmode			= GLFW.glfwGetVideoMode(primaryMonitor);

		this.initializeWindowHintsSecondPhase(vidmode);

		if (this.isFullscreen())
		{

			this.setWindowHandle(GLFW.glfwCreateWindow(this.getWidth(), this.getHeight(), this.getTitle(),
					primaryMonitor, MemoryUtil.NULL));
		}
		else
		{
			this.setWindowHandle(GLFW.glfwCreateWindow(this.getWidth(), this.getHeight(), this.getTitle(),
					MemoryUtil.NULL, MemoryUtil.NULL));
		}

		if (this.getWindowHandle() == MemoryUtil.NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window !");
		}

		// Center our window
		GLFW.glfwSetWindowPos(this.getWindowHandle(), (vidmode.width() - this.getWidth()) / 2,
				(vidmode.height() - this.getHeight()) / 2);

		if (this.isSynchronized())
		{
			// Enable v-sync

			GLFW.glfwSwapInterval(1);
		}

		GLFW.glfwMakeContextCurrent(this.getWindowHandle());

		Renderer.openglInitialization();

		this.getGlfwEventManager().initialization(intervalIn);

		GLFW.glfwShowWindow(this.getWindowHandle());

		return this.getGlfwEventManager();
	}
}
