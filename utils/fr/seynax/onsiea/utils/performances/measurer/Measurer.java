package fr.seynax.onsiea.utils.performances.measurer;

import fr.seynax.onsiea.utils.Timer;
import fr.seynax.onsiea.utils.thread.StoppableThread;

public class Measurer extends StoppableThread implements Runnable
{
	// Variables

	private double		average;

	private long		total;

	private int			iterationNumber;

	private long		timeInterval;

	private String		name;

	private IMeasurerFunction	measurer;

	private Timer		timer	= new Timer();

	// Constructor

	public Measurer(final IMeasurerFunction measurerIn)
	{
		this.setMeasurer(measurerIn);

		this.setName("PROFILE-" + TimeMeasurer.addProfileNumber());

		this.setTimer(new Timer());

		this.setTimeInterval(1_000_000_000L);

	}

	public Measurer(final IMeasurerFunction measurerIn, final String nameIn)
	{
		this.setMeasurer(measurerIn);

		this.setName(nameIn);

		TimeMeasurer.addProfileNumber();

		this.setTimer(new Timer());

		this.setTimeInterval(1_000_000_000L);
	}

	public Measurer(final IMeasurerFunction measurerIn, final long timeIntervalIn)
	{
		this.setMeasurer(measurerIn);

		this.setTimeInterval(timeIntervalIn);

		this.setName("PROFILE-" + TimeMeasurer.addProfileNumber());

		this.setTimer(new Timer());

	}

	public Measurer(final IMeasurerFunction measurerIn, final String nameIn, final long timeIntervalIn)
	{
		this.setMeasurer(measurerIn);

		this.setName(nameIn);

		TimeMeasurer.addProfileNumber();

		this.setTimeInterval(timeIntervalIn);

		this.setTimer(new Timer());
	}

	// Methods

	public void reset()
	{
		this.setAverage(0.0D);
		this.setTotal(0);
		this.setIterationNumber(0);
	}

	public void exedcute()
	{
		while (this.isRunning())
		{
			if (this.getTimer().getElapsedTime() > this.getTimeInterval())
			{
				this.getTimer().start();

				final var measure = this.getMeasurer().measure();
				this.setAverage((this.getAverage() + measure) / 2.0D);
				this.setTotal(this.getTotal() + measure);
				this.setIterationNumber(this.getIterationNumber() + 1);
			}
		}
	}

	@Override
	public String toString()
	{
		return this.getName() + " -> [" + this.getAverage() + " - [" + this.getTotal() + "]";
	}

	// Getter | Setter

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

	public int getIterationNumber()
	{
		return this.iterationNumber;
	}

	public void setIterationNumber(final int iterationNumberIn)
	{
		this.iterationNumber = iterationNumberIn;
	}

	public long getTimeInterval()
	{
		return this.timeInterval;
	}

	public void setTimeInterval(final long timeIntervalIn)
	{
		this.timeInterval = timeIntervalIn;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}

	public Timer getTimer()
	{
		return this.timer;
	}

	public void setTimer(final Timer timerIn)
	{
		this.timer = timerIn;
	}

	public IMeasurerFunction getMeasurer()
	{
		return this.measurer;
	}

	public void setMeasurer(final IMeasurerFunction measurerIn)
	{
		this.measurer = measurerIn;
	}
}