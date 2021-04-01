package fr.seynax.onsiea.utils.performances.measurer;

public class MeasurerTime implements IMeasurer
{
	// Variables

	private long	start;
	private long	end;

	private double	time;

	private long	totalTime;

	// Interface methods

	@Override
	public long start()
	{
		this.setStart(System.nanoTime());

		return this.getStart();
	}

	/**
	 * @return currentTime
	 */
	@Override
	public long stop()
	{
		this.setEnd(System.nanoTime());

		final var currentTime = this.getEnd() - this.getStart();

		this.setTotalTime(this.getTotalTime() + currentTime);

		this.setTime((this.getTime() + currentTime) / 2.0D);

		return currentTime;
	}

	@Override
	public void reset()
	{
		this.setStart(System.nanoTime());
		this.setEnd(System.nanoTime());

		this.setTime(0.0D);
		this.setTotalTime(0L);
	}

	@Override
	public String getMeasureName()
	{
		return "time";
	}

	@Override
	public String shortReport()
	{
		return "Time : " + this.getTime();
	}

	@Override
	public String shortReport(final String startIn)
	{
		return startIn + "Time : " + this.getTime();
	}

	@Override
	public String report()
	{
		return this.getEnd() + " - " + this.getStart() + ", Time : " + this.getTime();
	}

	@Override
	public String report(final String startIn)
	{
		return startIn + this.getEnd() + " - " + this.getStart() + ", Time : " + this.getTime();
	}

	@Override
	public String toString()
	{
		return this.getEnd() + " - " + this.getStart() + ", Time : " + this.getTime();
	}

	// Getter | Setter

	public long getStart()
	{
		return this.start;
	}

	public void setStart(final long startIn)
	{
		this.start = startIn;
	}

	public long getEnd()
	{
		return this.end;
	}

	public void setEnd(final long endIn)
	{
		this.end = endIn;
	}

	public double getTime()
	{
		return this.time;
	}

	public void setTime(final double timeIn)
	{
		this.time = timeIn;
	}

	public long getTotalTime()
	{
		return this.totalTime;
	}

	public void setTotalTime(final long totalTimeIn)
	{
		this.totalTime = totalTimeIn;
	}
}