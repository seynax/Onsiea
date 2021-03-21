package fr.seynax.onsiea.graphics.callbacks;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.input.CursorExtensionFPS;
import fr.seynax.onsiea.graphics.input.CursorExtensionMenu;

public class CallbacksManager
{
	// Constuctor variables

	private IWindow						window;

	// Variables

	private CursorExtensionFPS			cursorExtensionFPS;
	private CursorExtensionMenu			cursorExtensionMenu;

	// Variables ; glfw callbacks

	private CharCallback				charCallback;
	private CharModsCallback			charModsCallback;
	private CursorEnterCallback			cursorEnterCallback;
	private CursorPosCallback			cursorPosCallback;
	private DropCallback				dropCallback;
	private ErrorCallback				errorCallback;
	private FramebufferSizeCallback		framebufferSizeCallback;
	private JoystickCallback			joystickCallback;
	private KeyCallback					keyCallback;
	private MonitorCallback				monitorCallback;
	private MouseButtonCallback			mouseButtonCallback;
	private ScrollCallback				scrollCallback;
	private WindowCloseCallback			windowCloseCallback;
	private WindowContentScaleCallback	windowContentScaleCallback;
	private WindowFocusCallback			windowFocusCallback;
	private WindowIconifyCallback		windowIconifyCallback;
	private WindowMaximizeCallback		windowMaximizeCallback;
	private WindowPosCallback			windowPosCallback;
	private WindowRefreshCallback		windowRefreshCallback;
	private WindowSizeCallback			windowSizeCallback;

	// Constructor

	public CallbacksManager(final IWindow windowIn)
	{
		this.setWindow(windowIn);
	}

	// methods

	public void errorCallbackInitialization()
	{
		final var errorCallback = new ErrorCallback();
		GLFW.glfwSetErrorCallback(errorCallback);

		// GLFWErrorCallback.createPrint(System.err).set();
	}

	public void initialization(final double intervalIn)
	{
		this.setCursorExtensionFPS(new CursorExtensionFPS(
				new Vector2d(this.getWindow().getWidth() / 2.0D, this.getWindow().getHeight() / 2.0D)));

		this.setCursorExtensionMenu(new CursorExtensionMenu());

		// Callbacks creation

		this.setCharCallback(new CharCallback());
		this.setCharModsCallback(new CharModsCallback());
		this.setCursorEnterCallback(new CursorEnterCallback());
		this.setCursorPosCallback(new CursorPosCallback(this.getWindow(), intervalIn, this.getCursorExtensionFPS()));
		this.setDropCallback(new DropCallback());
		this.setErrorCallback(new ErrorCallback());
		this.setFramebufferSizeCallback(new FramebufferSizeCallback(this.getWindow()));
		this.setJoystickCallback(new JoystickCallback());
		this.setKeyCallback(new KeyCallback());
		this.setMonitorCallback(new MonitorCallback());
		this.setMouseButtonCallback(new MouseButtonCallback());
		this.setScrollCallback(new ScrollCallback());
		this.setWindowCloseCallback(new WindowCloseCallback());
		this.setWindowContentScaleCallback(new WindowContentScaleCallback());
		this.setWindowFocusCallback(new WindowFocusCallback());
		this.setWindowIconifyCallback(new WindowIconifyCallback());
		this.setWindowMaximizeCallback(new WindowMaximizeCallback());
		this.setWindowPosCallback(new WindowPosCallback());
		this.setWindowRefreshCallback(new WindowRefreshCallback());
		this.setWindowSizeCallback(new WindowSizeCallback());

		// Callbacks initialization

		this.getCursorPosCallback().initialization();

		// Callbacks link with GLFW

		GLFW.glfwSetCharCallback(this.getWindow().getWindowHandle(), this.getCharCallback());
		GLFW.glfwSetCharModsCallback(this.getWindow().getWindowHandle(), this.getCharModsCallback());
		GLFW.glfwSetCursorEnterCallback(this.getWindow().getWindowHandle(), this.getCursorEnterCallback());
		GLFW.glfwSetCursorPosCallback(this.getWindow().getWindowHandle(), this.getCursorPosCallback());
		GLFW.glfwSetDropCallback(this.getWindow().getWindowHandle(), this.getDropCallback());

		GLFW.glfwSetFramebufferSizeCallback(this.getWindow().getWindowHandle(), this.getFramebufferSizeCallback());
		GLFW.glfwSetJoystickCallback(this.getJoystickCallback());
		GLFW.glfwSetKeyCallback(this.getWindow().getWindowHandle(), this.getKeyCallback());
		GLFW.glfwSetMonitorCallback(this.getMonitorCallback());
		GLFW.glfwSetMouseButtonCallback(this.getWindow().getWindowHandle(), this.getMouseButtonCallback());
		GLFW.glfwSetScrollCallback(this.getWindow().getWindowHandle(), this.getScrollCallback());
		GLFW.glfwSetWindowCloseCallback(this.getWindow().getWindowHandle(), this.getWindowCloseCallback());
		GLFW.glfwSetWindowContentScaleCallback(this.getWindow().getWindowHandle(),
				this.getWindowContentScaleCallback());
		GLFW.glfwSetWindowFocusCallback(this.getWindow().getWindowHandle(), this.getWindowFocusCallback());
		GLFW.glfwSetWindowIconifyCallback(this.getWindow().getWindowHandle(), this.getWindowIconifyCallback());
		GLFW.glfwSetWindowMaximizeCallback(this.getWindow().getWindowHandle(), this.getWindowMaximizeCallback());
		GLFW.glfwSetWindowPosCallback(this.getWindow().getWindowHandle(), this.getWindowPosCallback());
		GLFW.glfwSetWindowRefreshCallback(this.getWindow().getWindowHandle(), this.getWindowRefreshCallback());
		GLFW.glfwSetWindowSizeCallback(this.getWindow().getWindowHandle(), this.getWindowSizeCallback());
	}

	public void FPSView()
	{
		this.getCursorPosCallback().getCursor().setCursorExtension(this.getCursorExtensionFPS());
	}

	public void menuView()
	{
		this.getCursorPosCallback().getCursor().setCursorExtension(this.getCursorExtensionMenu());
	}

	public void reset()
	{
		this.getMouseButtonCallback().reset();
	}

	// Getter | Setter

	// Constructor variables

	private IWindow getWindow()
	{
		return this.window;
	}

	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	// Variables

	private CursorExtensionFPS getCursorExtensionFPS()
	{
		return this.cursorExtensionFPS;
	}

	private void setCursorExtensionFPS(final CursorExtensionFPS cursorExtensionFPSIn)
	{
		this.cursorExtensionFPS = cursorExtensionFPSIn;
	}

	private CursorExtensionMenu getCursorExtensionMenu()
	{
		return this.cursorExtensionMenu;
	}

	private void setCursorExtensionMenu(final CursorExtensionMenu cursorExtensionMenuIn)
	{
		this.cursorExtensionMenu = cursorExtensionMenuIn;
	}

	// Variables ; callbacks

	CharCallback getCharCallback()
	{
		return this.charCallback;
	}

	private void setCharCallback(final CharCallback charCallbackIn)
	{
		this.charCallback = charCallbackIn;
	}

	CharModsCallback getCharModsCallback()
	{
		return this.charModsCallback;
	}

	private void setCharModsCallback(final CharModsCallback charModsCallbackIn)
	{
		this.charModsCallback = charModsCallbackIn;
	}

	CursorEnterCallback getCursorEnterCallback()
	{
		return this.cursorEnterCallback;
	}

	private void setCursorEnterCallback(final CursorEnterCallback cursorEnterCallbackIn)
	{
		this.cursorEnterCallback = cursorEnterCallbackIn;
	}

	public CursorPosCallback getCursorPosCallback()
	{
		return this.cursorPosCallback;
	}

	private void setCursorPosCallback(final CursorPosCallback cursorPosCallbackIn)
	{
		this.cursorPosCallback = cursorPosCallbackIn;
	}

	DropCallback getDropCallback()
	{
		return this.dropCallback;
	}

	private void setDropCallback(final DropCallback dropCallbackIn)
	{
		this.dropCallback = dropCallbackIn;
	}

	ErrorCallback getErrorCallback()
	{
		return this.errorCallback;
	}

	private void setErrorCallback(final ErrorCallback errorCallbackIn)
	{
		this.errorCallback = errorCallbackIn;
	}

	public FramebufferSizeCallback getFramebufferSizeCallback()
	{
		return this.framebufferSizeCallback;
	}

	private void setFramebufferSizeCallback(final FramebufferSizeCallback framebufferSizeCallbackIn)
	{
		this.framebufferSizeCallback = framebufferSizeCallbackIn;
	}

	JoystickCallback getJoystickCallback()
	{
		return this.joystickCallback;
	}

	private void setJoystickCallback(final JoystickCallback joystickCallbackIn)
	{
		this.joystickCallback = joystickCallbackIn;
	}

	KeyCallback getKeyCallback()
	{
		return this.keyCallback;
	}

	private void setKeyCallback(final KeyCallback keyCallbackIn)
	{
		this.keyCallback = keyCallbackIn;
	}

	MonitorCallback getMonitorCallback()
	{
		return this.monitorCallback;
	}

	private void setMonitorCallback(final MonitorCallback monitorCallbackIn)
	{
		this.monitorCallback = monitorCallbackIn;
	}

	public MouseButtonCallback getMouseButtonCallback()
	{
		return this.mouseButtonCallback;
	}

	private void setMouseButtonCallback(final MouseButtonCallback mouseButtonCallbackIn)
	{
		this.mouseButtonCallback = mouseButtonCallbackIn;
	}

	ScrollCallback getScrollCallback()
	{
		return this.scrollCallback;
	}

	private void setScrollCallback(final ScrollCallback scrollCallbackIn)
	{
		this.scrollCallback = scrollCallbackIn;
	}

	WindowCloseCallback getWindowCloseCallback()
	{
		return this.windowCloseCallback;
	}

	private void setWindowCloseCallback(final WindowCloseCallback windowCloseCallbackIn)
	{
		this.windowCloseCallback = windowCloseCallbackIn;
	}

	WindowContentScaleCallback getWindowContentScaleCallback()
	{
		return this.windowContentScaleCallback;
	}

	private void setWindowContentScaleCallback(final WindowContentScaleCallback windowContentScaleCallbackIn)
	{
		this.windowContentScaleCallback = windowContentScaleCallbackIn;
	}

	WindowFocusCallback getWindowFocusCallback()
	{
		return this.windowFocusCallback;
	}

	private void setWindowFocusCallback(final WindowFocusCallback windowFocusCallbackIn)
	{
		this.windowFocusCallback = windowFocusCallbackIn;
	}

	WindowIconifyCallback getWindowIconifyCallback()
	{
		return this.windowIconifyCallback;
	}

	private void setWindowIconifyCallback(final WindowIconifyCallback windowIconifyCallbackIn)
	{
		this.windowIconifyCallback = windowIconifyCallbackIn;
	}

	WindowMaximizeCallback getWindowMaximizeCallback()
	{
		return this.windowMaximizeCallback;
	}

	private void setWindowMaximizeCallback(final WindowMaximizeCallback windowMaximizeCallbackIn)
	{
		this.windowMaximizeCallback = windowMaximizeCallbackIn;
	}

	WindowPosCallback getWindowPosCallback()
	{
		return this.windowPosCallback;
	}

	private void setWindowPosCallback(final WindowPosCallback windowPosCallbackIn)
	{
		this.windowPosCallback = windowPosCallbackIn;
	}

	WindowRefreshCallback getWindowRefreshCallback()
	{
		return this.windowRefreshCallback;
	}

	private void setWindowRefreshCallback(final WindowRefreshCallback windowRefreshCallbackIn)
	{
		this.windowRefreshCallback = windowRefreshCallbackIn;
	}

	WindowSizeCallback getWindowSizeCallback()
	{
		return this.windowSizeCallback;
	}

	private void setWindowSizeCallback(final WindowSizeCallback windowSizeCallbackIn)
	{
		this.windowSizeCallback = windowSizeCallbackIn;
	}
}