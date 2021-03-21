package fr.seynax.onsiea.utils;

public class Timer
{
	// Static Constants

	private final static float	NANO_TO_SECONDS	= 1.0f / 1_000_000_000.0F;

	// Variables

	private double				lastLoopTime;

	// Constructor

	public Timer()
	{

	}

	// Methods

	public void initialization()
	{
		this.setLastLoopTime(Timer.getTime());
	}

	public final static double getTime()
	{
		return System.nanoTime() * Timer.getNanoToSeconds();
	}

	public float getElapsedTime()
	{
		final var	time		= Timer.getTime();

		final var	elapsedTime	= (float) (time - this.getLastLoopTime());

		this.setLastLoopTime(time);

		return elapsedTime;
	}

	// Getter | Setter

	public double getLastLoopTime()
	{
		return this.lastLoopTime;
	}

	public void setLastLoopTime(final double lastLoopTimeIn)
	{
		this.lastLoopTime = lastLoopTimeIn;
	}

	// Constants getter

	public static float getNanoToSeconds()
	{
		return Timer.NANO_TO_SECONDS;
	}
}