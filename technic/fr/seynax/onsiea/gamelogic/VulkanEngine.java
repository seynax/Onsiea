package fr.seynax.onsiea.gamelogic;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.VulkanManager;
import fr.seynax.onsiea.vulkan.VulkanWindow;

public class VulkanEngine implements Runnable
{
	// Variables

	private IWindow			window;
	private int				targetUps		= 0;
	private float			targetUpsTiming	= 0.0f;
	private Thread			gameLoopThread;
	private boolean			isRunning;

	// Variables ; Vulkan

	private VulkanManager	vulkanManager;

	// Constructor

	public VulkanEngine()
	{

	}

	// Methods

	private void initialization()
	{
		this.setWindow(new VulkanWindow(1920, 1080, "Vulkan !", 60, true, 1));

		this.setTargetUps(50);
		this.setTargetUpsTiming(1.0f / this.getTargetUps());

		this.getWindow().initialization(this.getTargetUps());

		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Vulkan
		{
			this.setVulkanManager(new VulkanManager(this.getWindow().getWindowHandle(), this.getWindow().getWidth(),
					this.getWindow().getHeight()));
		}
	}

	public void start()
	{
		this.setGameLoopThread(new Thread(this));

		final var osName = System.getProperty("os.name");

		if (osName.contains("Mac"))
		{
			this.getGameLoopThread().run();
		}
		else
		{
			this.getGameLoopThread().start();
		}
	}

	@Override
	public void run()
	{
		try
		{
			this.initialization();

			this.setRunning(true);

			// this.gameLoop();
		}
		catch (final Exception excp)
		{
			excp.printStackTrace();
		}
		finally
		{

			this.cleanup();
		}
	}

	@SuppressWarnings("unused")
	private void gameLoop()
	{
		while (this.isRunning() && !this.getWindow().windowShouldClose())
		{
			this.input();

			this.update();

			this.startRender();

			this.render();

			this.endRender();
		}
	}

	private void input()
	{
		this.getWindow().getGlfwEventManager().pollEvents();
	}

	private void update()
	{

	}

	private void startRender()
	{

	}

	private void render()
	{

	}

	private void endRender()
	{
		this.getWindow().updateRender();
	}

	private void cleanup()
	{
		// Vulkan
		{
			this.getVulkanManager().cleanup();
		}
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

	private int getTargetUps()
	{
		return this.targetUps;
	}

	private void setTargetUps(final int targetUpsIn)
	{
		this.targetUps = targetUpsIn;
	}

	float getTargetUpsTiming()
	{
		return this.targetUpsTiming;
	}

	private void setTargetUpsTiming(final float targetUpsTimingIn)
	{
		this.targetUpsTiming = targetUpsTimingIn;
	}

	private Thread getGameLoopThread()
	{
		return this.gameLoopThread;
	}

	private void setGameLoopThread(final Thread gameLoopThreadIn)
	{
		this.gameLoopThread = gameLoopThreadIn;
	}

	private boolean isRunning()
	{
		return this.isRunning;
	}

	private void setRunning(final boolean isRunningIn)
	{
		this.isRunning = isRunningIn;
	}

	// Getter | Setter ; Vulkan

	public VulkanManager getVulkanManager()
	{
		return this.vulkanManager;
	}

	public void setVulkanManager(final VulkanManager vulkanManagerIn)
	{
		this.vulkanManager = vulkanManagerIn;
	}
}