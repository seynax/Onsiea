package fr.seynax.onsiea.graphics.callbacks;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;

public class GLFWEventManager
{
	// Constructor variables

	private IWindow				window;

	// Variables

	private CallbacksManager	callbacksManager;

	// Constructor

	public GLFWEventManager(final IWindow windowIn)
	{
		this.setWindow(windowIn);
		this.setCallbacksManager(new CallbacksManager(windowIn));
	}

	// Methods

	public void initialization(final double intervalIn)
	{
		this.getCallbacksManager().initialization(intervalIn);
	}

	public void pollEvents()
	{
		GLFW.glfwPollEvents();
	}

	public void waitEvents()
	{
		GLFW.glfwWaitEvents();
	}

	public void waitTimeoutEvents(final double timeoutIn)
	{
		GLFW.glfwWaitEventsTimeout(timeoutIn);
	}

	public void postEmptyEvent()
	{
		GLFW.glfwPostEmptyEvent();
	}

	// Key methods

	// glfwSetInputMode (fenêtre, GLFW_STICKY_KEYS , GLFW_TRUE );
	// glfwSetInputMode (fenêtre, GLFW_LOCK_KEY_MODS , GLFW_TRUE );

	public int getKeyScancode(final int glfwKeyIn)
	{
		return GLFW.glfwGetKeyScancode(glfwKeyIn);
	}

	public int getKey(final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(this.getWindow().getWindowHandle(), glfwKeyIn);
	}

	public boolean keyIsHasPress(final int glfwKeyOIn)
	{
		return this.getCallbacksManager().getKeyCallback().isHasPress(glfwKeyOIn);
	}

	public boolean keyIsPress(final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(this.getWindow().getWindowHandle(), glfwKeyIn) == GLFW.GLFW_PRESS;
	}

	public boolean keyIsRepeat(final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(this.getWindow().getWindowHandle(), glfwKeyIn) == GLFW.GLFW_REPEAT;
	}

	public boolean keyIsRelease(final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(this.getWindow().getWindowHandle(), glfwKeyIn) == GLFW.GLFW_RELEASE;
	}

	public String getKeyName(final int glfwKeyIn)
	{
		return GLFW.glfwGetKeyName(glfwKeyIn, 0);
	}

	public String getGamepadKeyName(final int gamepadKeyIn)
	{
		return GLFW.glfwGetGamepadName(gamepadKeyIn);
	}

	public String getJoystickKeyName(final int joystickKeyIn)
	{
		return GLFW.glfwGetJoystickName(joystickKeyIn);
	}

	public String getJoystickKeyGUID(final int joystickKeyIn)
	{
		return GLFW.glfwGetJoystickGUID(joystickKeyIn);
	}

	public String getClipboardContent()
	{
		return GLFW.glfwGetClipboardString(this.getWindow().getWindowHandle());
	}

	// Cursor

	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

	/**
	 * unsigned char pixels[16 * 16 * 4]; memset(pixels, 0xff, sizeof(pixels));
	 *
	 * GLFWimage image; image.width = 16; image.height = 16; image.pixels = pixels;
	 *
	 * GLFWcursor* cursor = glfwCreateCursor(&image, 0, 0);
	 *
	 */

	// GLFWcursor* cursor = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
	// glfwDestroyCursor(cursor);
	// glfwSetCursor(IWindow, NULL);

	// glfwSetInputMode (fenêtre, GLFW_STICKY_MOUSE_BUTTONS , GLFW_TRUE );

	public boolean enableCursorRawMotion()
	{
		if (GLFW.glfwRawMouseMotionSupported())
		{
			GLFW.glfwSetInputMode(this.getWindow().getWindowHandle(), GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);

			return true;
		}

		return false;
	}

	public boolean disableCursorRawMotion()
	{
		if (GLFW.glfwRawMouseMotionSupported())
		{
			GLFW.glfwSetInputMode(this.getWindow().getWindowHandle(), GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);

			return true;
		}

		return false;
	}

	public void reset()
	{
		this.getCallbacksManager().reset();
	}

	// Getter | Setter

	public CallbacksManager getCallbacksManager()
	{
		return this.callbacksManager;
	}

	private IWindow getWindow()
	{
		return this.window;
	}

	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	private void setCallbacksManager(final CallbacksManager callbacksManagerIn)
	{
		this.callbacksManager = callbacksManagerIn;
	}
}