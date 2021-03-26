package fr.seynax.onsiea.utils;

public class Timer
{
	// Static Constants

	private final static float	NANO_TO_SECONDS	= 1.0f / 1_000_000_000.0F;

	// Variables

	private long				lastTime;

	// Constructor

	public Timer()
	{
		this.start();
	}

	// Methods

	public void start()
	{
		this.setLastTime(Timer.getTime());
	}

	public final static long getTime()
	{
		return System.nanoTime();
	}

	public final static double convertToSeconds(final long timeIn)
	{
		return timeIn * Timer.getNanoToSeconds();
	}

	public final static double getTimeToSeconds()
	{
		return System.nanoTime() * Timer.getNanoToSeconds();
	}

	public long getElapsedTime()
	{
		final var	time		= Timer.getTime();

		final var	elapsedTime	= time - this.getLastTime();

		return elapsedTime;
	}

	public long getElapsedTimeAndRestart()
	{
		final var	time		= Timer.getTime();

		final var	elapsedTime	= time - this.getLastTime();

		this.setLastTime(time);

		return elapsedTime;
	}

	public double getElapsedTimeToSeconds()
	{
		final var	time		= Timer.getTime();

		final var	elapsedTime	= Timer.convertToSeconds(time) - Timer.convertToSeconds(this.getLastTime());

		return elapsedTime;
	}

	public double getElapsedTimeToSecondsAndRestart()
	{
		final var	time		= Timer.getTime();

		final var	elapsedTime	= Timer.convertToSeconds(time) - Timer.convertToSeconds(this.getLastTime());

		this.setLastTime(time);

		return elapsedTime;
	}

	// Getter | Setter

	public long getLastTime()
	{
		return this.lastTime;
	}

	public void setLastTime(final long lastTimeIn)
	{
		this.lastTime = lastTimeIn;
	}

	// Constants getter

	public static float getNanoToSeconds()
	{
		return Timer.NANO_TO_SECONDS;
	}
}