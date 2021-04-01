package fr.seynax.onsiea.utils.performances;

public class FPSCalculator
{
	// Variables

	private long	lastFrameTime;
	private int		fps;
	private long	lastFPS;

	// Constructor

	public FPSCalculator()
	{
		this.start();
	}

	// Methods

	public int getDelta()
	{
		final var	time	= System.nanoTime();
		final var	delta	= (int) (time - this.getLastFrameTime());
		this.setLastFrameTime(time);

		return delta;
	}

	// Methods

	public long start()
	{
		this.setLastFPS(System.nanoTime());

		return this.getLastFPS();
	}

	public long stop()
	{
		if (System.nanoTime() - this.getLastFPS() > 1_000_000_000L)
		{
			final var FPS = this.getFPS();

			this.setFPS(0);
			this.setLastFPS(this.getLastFPS() + 1_000_000_000L);

			return FPS;
		}

		this.setFPS(this.getFPS() + 1);

		return -1L;
	}

	public void reset()
	{
		this.setFPS(0);
		this.setLastFPS(0L);
		this.setLastFrameTime(0L);
	}

	public long getLastFrameTime()
	{
		return this.lastFrameTime;
	}

	public void setLastFrameTime(final long lastFrameTimeIn)
	{
		this.lastFrameTime = lastFrameTimeIn;
	}

	public int getFPS()
	{
		return this.fps;
	}

	public void setFPS(final int fpsIn)
	{
		this.fps = fpsIn;
	}

	public long getLastFPS()
	{
		return this.lastFPS;
	}

	public void setLastFPS(final long lastFPSIn)
	{
		this.lastFPS = lastFPSIn;
	}
}
