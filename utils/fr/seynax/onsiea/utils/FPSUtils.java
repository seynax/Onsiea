package fr.seynax.onsiea.utils;

public class FPSUtils
{
	// Variables

	private long	lastFrameTime;
	private int		fps;
	private long	lastFPS;

	// Constructor

	public FPSUtils()
	{
		this.start();
	}

	// Methods

	/**
	 * Get the time in milliseconds
	 *
	 * @return The system time in milliseconds
	 */
	public long getTime()
	{
		return System.nanoTime() / 1_000_000L;
	}

	public int getDelta()
	{
		final var	time	= this.getTime();
		final var	delta	= (int) (time - this.getLastFrameTime());
		this.setLastFrameTime(time);

		return delta;
	}

	public void start()
	{
		// some startup code
		this.setLastFPS(this.getTime()); // set lastFPS to current Time
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public String updateFPS()
	{
		if (this.getTime() - this.getLastFPS() > 1000)
		{
			final var title = "FPS: " + this.getFPS();

			this.setFPS(0); // reset the FPS counter
			this.setLastFPS(this.getLastFPS() + 1000); // add one second

			return title;
		}

		this.setFPS(this.getFPS() + 1); // add one second

		return null;
	}

	// Getter | Setter

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
