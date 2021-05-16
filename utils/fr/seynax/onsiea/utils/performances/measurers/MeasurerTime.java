package fr.seynax.onsiea.utils.performances.measurers;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class MeasurerTime implements IMeasurer
{
	private long	start;
	private long	end;

	private double	average;
	private long	total;

	private long	time;

	private boolean	isOneIteration;

	// Interface methods

	@Override
	public void start()
	{
		this.setStart(System.nanoTime());
	}

	/**
	 * @return currentTime
	 */
	@Override
	public void stop()
	{
		this.setEnd(System.nanoTime());

		this.setTime(this.getEnd() - this.getStart());

		this.setTotal(this.getTotal() + this.getTime());

		if (this.isOneIteration())
		{
			this.setAverage((this.getAverage() + this.getTime()) / 2.0D);
		}
		else
		{
			this.setAverage(this.getTime());
			this.setOneIteration(true);
		}

	}

	public void reset()
	{
		this.setStart(System.nanoTime());
		this.setEnd(System.nanoTime());

		this.setTime(0L);
		this.setTotal(0L);
		this.setAverage(0.0D);
	}

	public String getMeasureName()
	{
		return "time";
	}

	public String shortReport()
	{
		return "Time : " + this.getTime();
	}

	public String shortReport(final String startIn)
	{
		return startIn + "Time : " + this.getTime();
	}

	public String report()
	{
		return this.getEnd() + " - " + this.getStart() + ", Time : " + this.getTime();
	}

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

	public long getTime()
	{
		return this.time;
	}

	public void setTime(final long timeIn)
	{
		this.time = timeIn;
	}

	public long getEnd()
	{
		return this.end;
	}

	public void setEnd(final long endIn)
	{
		this.end = endIn;
	}

	public double getAverage()
	{
		return this.average;
	}

	public void setAverage(final double averageIn)
	{
		this.average = averageIn;
	}

	public long getTotal()
	{
		return this.total;
	}

	public void setTotal(final long totalIn)
	{
		this.total = totalIn;
	}

	public boolean isOneIteration()
	{
		return this.isOneIteration;
	}

	public void setOneIteration(final boolean isOneIterationIn)
	{
		this.isOneIteration = isOneIterationIn;
	}
}