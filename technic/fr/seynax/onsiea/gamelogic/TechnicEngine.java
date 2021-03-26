package fr.seynax.onsiea.gamelogic;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.utils.Timer;
import fr.seynax.onsiea.utils.thread.StoppableThread;

public class TechnicEngine extends StoppableThread
{
	// Variables

	private Camera	camera;

	private IWindow	window;

	private Timer	timer;

	private double	elapsedTime;
	private double	accumulator;
	private double	secsPerUpdate;

	// Constructor

	public TechnicEngine(final Camera cameraIn, final IWindow windowIn)
	{
		super("Technic-Engine");

		this.setCamera(cameraIn);
		this.setWindow(windowIn);
		this.setTimer(new Timer());

		this.setSecsPerUpdate(1.0D / GameEngine.getTargetUps());
	}

	// Methods

	public void execute(final double intervalIn)
	{
		// Camera update

		this.setElapsedTime(this.getTimer().getElapsedTimeToSecondsAndRestart());

		this.setAccumulator(this.getAccumulator() + this.getElapsedTime());

		while (this.getAccumulator() >= this.getSecsPerUpdate())
		{
			this.getCamera().update(this.getWindow(), intervalIn);

			this.setAccumulator(this.getAccumulator() - this.getSecsPerUpdate());
		}
	}

	// Getter | Setter

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

	private Timer getTimer()
	{
		return this.timer;
	}

	private void setTimer(final Timer timerIn)
	{
		this.timer = timerIn;
	}

	public double getElapsedTime()
	{
		return this.elapsedTime;
	}

	public void setElapsedTime(final double elapsedTimeIn)
	{
		this.elapsedTime = elapsedTimeIn;
	}

	public double getAccumulator()
	{
		return this.accumulator;
	}

	public void setAccumulator(final double accumulatorIn)
	{
		this.accumulator = accumulatorIn;
	}

	public double getSecsPerUpdate()
	{
		return this.secsPerUpdate;
	}

	public void setSecsPerUpdate(final double secsPerUpdateIn)
	{
		this.secsPerUpdate = secsPerUpdateIn;
	}
}