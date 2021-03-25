package fr.seynax.onsiea.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import fr.seynax.onsiea.graphics.callbacks.GLFWEventManager;

public class GenericWindow implements IWindow
{
	// Construtor variables

	private int					width;
	private int					height;

	private String				title;

	private int					framerate;

	private boolean				isSynchronized;
	private int					sync;

	private long				windowHandle;

	private boolean				isFullscreen;

	// Variables

	private GLFWEventManager	glfwEventManager;

	// Construtor

	public GenericWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn)
	{
		this.verifyAndSetValue(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, true);
	}

	public GenericWindow(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn, final boolean fullscreenIn)
	{
		this.verifyAndSetValue(widthIn, heightIn, titleIn, framerateIn, isSynchronizedIn, syncIn, fullscreenIn);
	}

	// Methods

	public void verifyAndSetValue(final int widthIn, final int heightIn, final String titleIn, final int framerateIn,
			final boolean isSynchronizedIn, final int syncIn, final boolean isFullscreenIn)
	{
		// Width

		var width = widthIn;

		if (width > 0 && width < GraphicsConstants.getMinWidth())
		{
			width = GraphicsConstants.getMinWidth();
		}
		else if (width > GraphicsConstants.getMaxWidth())
		{
			width = GraphicsConstants.getMaxWidth();
		}
		else if (width <= 0)
		{
			width = GraphicsConstants.getDefaultWidth();
		}

		this.setWidth(width);

		// Height

		var height = heightIn;

		if (height > 0 && height < GraphicsConstants.getMinHeight())
		{
			height = GraphicsConstants.getMinHeight();
		}
		else if (height > GraphicsConstants.getMaxHeight())
		{
			height = GraphicsConstants.getMaxHeight();
		}
		else if (height <= 0)
		{
			height = GraphicsConstants.getDefaultHeight();
		}

		this.setHeight(height);

		// Title

		var title = titleIn;

		if (title == null ? true : title.matches("\\s+") || title.isEmpty()) // || titleIn.isBlank())
		{
			title = GraphicsConstants.getDefaultTitle();
		}

		this.setTitle(title);

		// FrameRate

		var framerate = framerateIn;

		if (framerate > 0 && framerate < GraphicsConstants.getMinFramerate())
		{
			framerate = GraphicsConstants.getMinFramerate();
		}
		else if (framerate > GraphicsConstants.getMaxFramerate())
		{
			framerate = GraphicsConstants.getMaxFramerate();
		}
		else if (framerate <= 0)
		{
			framerate = GraphicsConstants.getDefaultFramerate();
		}

		this.setFramerate(framerate);

		// IsSync

		this.setSynchronized(isSynchronizedIn);

		// Sync

		var sync = syncIn;

		if (sync > 0 && sync < GraphicsConstants.getMinSync())
		{
			sync = GraphicsConstants.getMinSync();
		}
		else if (sync > GraphicsConstants.getMaxSync())
		{
			sync = GraphicsConstants.getMaxSync();
		}
		else if (sync <= 0)
		{
			sync = GraphicsConstants.getDefaultSync();
		}

		this.setSync(sync);

		// isFullscreen

		this.setFullscreen(isFullscreenIn);
	}

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

		this.getGlfwEventManager().initialization(intervalIn);

		GLFW.glfwShowWindow(this.getWindowHandle());

		return this.getGlfwEventManager();
	}

	public void initializeWindowHintsFirstPhase(final boolean debugIsEnableIn)
	{
		GLFW.glfwDefaultWindowHints();
		if (debugIsEnableIn)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
		}
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
	}

	public void initializeWindowHintsSecondPhase(final GLFWVidMode videoModeIn)
	{
		if (this.isFullscreen())
		{
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, videoModeIn.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, videoModeIn.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, videoModeIn.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, videoModeIn.refreshRate());
		}
	}

	@Override
	public void updateRender()
	{
		GLFW.glfwSwapBuffers(this.getWindowHandle());
	}

	@Override
	public void updateWidth(final int widthIn)
	{
	}

	@Override
	public void updateHeight(final int heightIn)
	{
	}

	@Override
	public void updateTitle(final String titleIn)
	{
	}

	@Override
	public void updateFramerate(final int framerateIn)
	{
	}

	@Override
	public void updateSynchronized(final boolean isSynchronizedIn)
	{
	}

	@Override
	public void updateSync(final int syncIn)
	{
	}

	@Override
	public void updateFullscreen(final boolean isFullscreenIn)
	{
	}

	@Override
	public boolean windowShouldClose()
	{
		return GLFW.glfwWindowShouldClose(this.getWindowHandle());
	}

	// Getter | Setter

	// Constructor variables

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public void setWidth(final int widthIn)
	{
		this.width = widthIn;
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public void setHeight(final int heightIn)
	{
		this.height = heightIn;
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(final String titleIn)
	{
		this.title = titleIn;
	}

	@Override
	public int getFramerate()
	{
		return this.framerate;
	}

	public void setFramerate(final int framerateIn)
	{
		this.framerate = framerateIn;
	}

	@Override
	public boolean isSynchronized()
	{
		return this.isSynchronized;
	}

	public void setSynchronized(final boolean isSynchronizedIn)
	{
		this.isSynchronized = isSynchronizedIn;
	}

	@Override
	public int getSync()
	{
		return this.sync;
	}

	public void setSync(final int syncIn)
	{
		this.sync = syncIn;
	}

	@Override
	public long getWindowHandle()
	{
		return this.windowHandle;
	}

	public void setWindowHandle(final long windowHandleIn)
	{
		this.windowHandle = windowHandleIn;
	}

	@Override
	public boolean isFullscreen()
	{
		return this.isFullscreen;
	}

	private void setFullscreen(final boolean isFullscreenIn)
	{
		this.isFullscreen = isFullscreenIn;
	}

	// Variables

	@Override
	public GLFWEventManager getGlfwEventManager()
	{
		return this.glfwEventManager;
	}

	protected void setGlfwEventManager(final GLFWEventManager glfwEventManagerIn)
	{
		this.glfwEventManager = glfwEventManagerIn;
	}
}
