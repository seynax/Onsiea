package fr.seynax.onsiea.gamelogic;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.utils.Timer;

public class TechnicEngine implements Runnable
{
	// Variables

	private Camera	camera;

	private boolean	isRunning;

	private IWindow	window;

	private Thread	thread;

	private Timer	timer;

	// Constructor

	public TechnicEngine(final Camera cameraIn, final IWindow windowIn)
	{
		this.setCamera(cameraIn);
		this.setWindow(windowIn);
		this.setTimer(new Timer());
	}

	// Interface methods

	@Override
	public void run()
	{
		double		elpasedTime;
		var			accumulator		= 0.0D;

		final var	secsPerUpdate	= 1.0D / GameEngine.getTargetUps();	// Interval

		while (this.isRunning())
		{
			elpasedTime	= this.getTimer().getElapsedTimeToSecondsAndRestart();

			accumulator	+= elpasedTime;

			while (accumulator >= secsPerUpdate)
			{
				this.update(secsPerUpdate);

				accumulator -= secsPerUpdate;
			}
		}
	}

	// Methods

	public Thread start()
	{
		this.initialization();

		final var thread = new Thread(this);

		this.setThread(thread);

		this.setRunning(true);

		this.getTimer().start();

		thread.start();

		return thread;
	}

	public void initialization()
	{

	}

	public void update(final double intervalIn)
	{
		// Camera update

		this.getCamera().update(this.getWindow(), intervalIn);
	}

	public void cleanup()
	{
	}

	// Getter | Setter

	public boolean isRunning()
	{
		return this.isRunning;
	}

	public void setRunning(final boolean isRunningIn)
	{
		this.isRunning = isRunningIn;
	}

	private Camera getCamera()
	{
		return this.camera;
	}

	private void setCamera(final Camera cameraIn)
	{
		this.camera = cameraIn;
	}

	private IWindow getWindow()
	{
		return this.window;
	}

	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	public Thread getThread()
	{
		return this.thread;
	}

	private void setThread(final Thread threadIn)
	{
		this.thread = threadIn;
	}

	private Timer getTimer()
	{
		return this.timer;
	}

	private void setTimer(final Timer timerIn)
	{
		this.timer = timerIn;
	}
}