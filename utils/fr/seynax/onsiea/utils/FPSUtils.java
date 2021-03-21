package fr.seynax.onsiea.utils;

public class FPSUtils
{
	private long	lastFrameTime;
	private int		fps;
	private long	lastFPS;

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
		final var	delta	= (int) (time - this.lastFrameTime);
		this.lastFrameTime = time;

		return delta;
	}

	public void start()
	{
		// some startup code
		this.lastFPS = this.getTime(); // set lastFPS to current Time
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public String updateFPS()
	{
		if (this.getTime() - this.lastFPS > 1000)
		{
			final var title = "FPS: " + this.fps;

			this.fps		= 0;		// reset the FPS counter
			this.lastFPS	+= 1000;	// add one second

			return title;
		}

		this.fps++;

		return null;
	}
}
