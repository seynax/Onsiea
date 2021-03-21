package fr.seynax.onsiea.gamelogic;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

import fr.seynax.onsiea.graphics.GenericWindow;
import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.OpenGLWindowCreator;
import fr.seynax.onsiea.utils.Timer;

public class GameEngine implements Runnable
{
	// Static constants

	private static final int	TARGET_FPS	= 75;

	private static final int	TARGET_UPS	= 30;

	// Constructor variables

	private Thread				gameLoopThread;

	private IWindow				window;

	private IGameLogic			gameLogic;

	private Timer				timer;

	// Variables

	private static boolean		keepOnRunning;

	// Constructor

	public GameEngine(final String windowTitleIn, final int widthIn, final int heightIn, final int framerateIn,
			final boolean vSyncIn, final int syncIn, final IGameLogic gameLogicIn) throws Exception
	{
		this.setGameLoopThread(new Thread(this, "GAME_LOOP_THREAD"));

		this.setWindow(new GenericWindow(widthIn, heightIn, windowTitleIn, framerateIn, vSyncIn, syncIn,
				GraphicsConstants.isFullscreen(), new OpenGLWindowCreator()));

		this.setGameLogic(gameLogicIn);

		this.setTimer(new Timer());
	}

	// Methods

	@Override
	public void run()
	{
		try
		{
			this.init();

			this.gameLoop();
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

	public void start()
	{
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

	public void init() throws Exception
	{
		this.getWindow().initialization(1.0D / GameEngine.getTargetUps());

		this.getTimer().initialization();

		Shapes.initialization();

		this.getGameLogic().initialization(this.getWindow());
		System.out.println(GL11.glGetInteger(GL31.GL_MAX_TEXTURE_BUFFER_SIZE));
		System.out.println(GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE));

		System.exit(0);
	}

	public void gameLoop()
	{
		GameEngine.setKeepOnRunning(true);

		double		elpasedTime;
		var			accumulator		= 0.0D;

		final var	secsPerUpdate	= 1.0D / GameEngine.getTargetUps();	// Interval
		final var	secsPerFrame	= 1.0D / GameEngine.getTargetFps();	// FPS

		while (GameEngine.isKeepOnRunning() && !this.getWindow().windowShouldClose())
		{
			elpasedTime	= this.getTimer().getElapsedTime();

			accumulator	+= elpasedTime;

			this.handleInput();

			while (accumulator >= secsPerUpdate)
			{
				this.updateGameState(secsPerUpdate);

				accumulator -= secsPerUpdate;
			}

			this.render();

			if (!this.getWindow().isSynchronized())
			{
				this.sync(secsPerFrame);
			}
		}
	}

	protected void handleInput()
	{
		this.getWindow().getGlfwEventManager().pollEvents();

		this.getGameLogic().input(this.getWindow());
	}

	protected void updateGameState(final double intervalIn)
	{
		this.getGameLogic().update(intervalIn, this.getWindow());
	}

	/**
	 * private long startRenderTime;
	 *
	 * private long endRenderTime;
	 *
	 * private long startDisplayTime; private long endDisplayTime;
	 **/

	protected void render()
	{
		// this.startRenderTime = System.nanoTime();
		this.getGameLogic().render(this.getWindow());
		// this.endRenderTime = System.nanoTime();

		/**
		 * System.out.println("[INFORMATION-GameEngine] Render time : " +
		 * (this.endRenderTime - this.startRenderTime) / 1_000_000_000D);
		 **/

		// this.startDisplayTime = System.nanoTime();
		this.getWindow().updateRender();
		// this.endDisplayTime = System.nanoTime();

		/**
		 * System.out.println("[INFORMATION-GameEngine] Display time : " +
		 * (this.endDisplayTime - this.startDisplayTime) / 1_000_000_000D);
		 **/

	}

	protected void cleanup()
	{
		this.getGameLogic().cleanup();
	}

	// Add

	public static void sleep(final long timeIn)
	{
		try
		{
			Thread.sleep(timeIn);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void sync(final double secsPerFrameIn)
	{
		final var endTime = this.getTimer().getLastLoopTime() + secsPerFrameIn;

		while (Timer.getTime() < endTime)
		{
			GameEngine.sleep(1L);
		}
	}

	// Constructor getter | Setter

	public Thread getGameLoopThread()
	{
		return this.gameLoopThread;
	}

	public void setGameLoopThread(final Thread gameLoopThreadIn)
	{
		this.gameLoopThread = gameLoopThreadIn;
	}

	public IWindow getWindow()
	{
		return this.window;
	}

	public void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	public IGameLogic getGameLogic()
	{
		return this.gameLogic;
	}

	public void setGameLogic(final IGameLogic gameLogicIn)
	{
		this.gameLogic = gameLogicIn;
	}

	public Timer getTimer()
	{
		return this.timer;
	}

	public void setTimer(final Timer timerIn)
	{
		this.timer = timerIn;
	}

	// Getter | Setter

	public static boolean isKeepOnRunning()
	{
		return GameEngine.keepOnRunning;
	}

	public static void setKeepOnRunning(final boolean keepOnRunningIn)
	{
		GameEngine.keepOnRunning = keepOnRunningIn;
	}

	// Static constants

	public static int getTargetFps()
	{
		return GameEngine.TARGET_FPS;
	}

	public static int getTargetUps()
	{
		return GameEngine.TARGET_UPS;
	}
}
