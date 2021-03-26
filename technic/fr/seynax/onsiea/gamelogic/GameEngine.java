package fr.seynax.onsiea.gamelogic;

import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.OpenGLScreenshot;
import fr.seynax.onsiea.opengl.OpenGLWindow;
import fr.seynax.onsiea.utils.OS;
import fr.seynax.onsiea.utils.Timer;

public class GameEngine implements Runnable
{
	// Static constants

	private static final int	TARGET_FPS	= 60;

	private static final int	TARGET_UPS	= 60;

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
		this.setWindow(new OpenGLWindow(widthIn, heightIn, windowTitleIn, framerateIn, vSyncIn, syncIn,
				GraphicsConstants.isFullscreen()));

		this.setGameLogic(gameLogicIn);

		this.setGameLoopThread(new Thread(this, "GAME_LOOP_THREAD"));
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
		if (OS.getOsName().contains("Mac"))
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
		this.setTimer(new Timer());

		this.getWindow().initialization(1.0D / GameEngine.getTargetUps());
		OpenGLScreenshot.resize(this.getWindow());

		this.getTimer().start();

		Shapes.initialization();

		this.getGameLogic().initialization(this.getWindow());
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
			elpasedTime	= this.getTimer().getElapsedTimeToSecondsAndRestart();

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

			this.endInput();
		}
	}

	protected void handleInput()
	{
		this.getWindow().getGlfwEventManager().pollEvents();

		this.getGameLogic().input(this.getWindow());
	}

	protected void endInput()
	{
		this.getWindow().getGlfwEventManager().reset();
	}

	protected void updateGameState(final double intervalIn)
	{
		this.getGameLogic().update(intervalIn, this.getWindow());
	}

	protected void render()
	{
		this.getGameLogic().render(this.getWindow());

		this.getWindow().updateRender();
	}

	protected void cleanup()
	{
		this.getGameLogic().cleanup();

		this.getWindow().cleanup();
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
		final var endTime = Timer.convertToSeconds(this.getTimer().getLastTime()) + secsPerFrameIn;

		while (Timer.getTimeToSeconds() < endTime)
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
