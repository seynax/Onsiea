package fr.seynax.onsiea.utils.performances.measurer;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.utils.OS;
import fr.seynax.onsiea.utils.Timer;

public class MeasurerGraph implements IMeasurer, Runnable
{
	// Variables

	private IMeasurerFunction	measurer;

	private List<Long>			values;

	private double				average;

	private long				total;

	private long				timeInterval;

	private Timer				timer;

	private Thread				thread;

	private boolean				isRunning;

	// Constructor

	public MeasurerGraph(final IMeasurerFunction measurerIn)
	{
		this.setMeasurer(measurerIn);

		this.setValues(new ArrayList<>());

		this.setTimer(new Timer());

		this.setTimeInterval(1_000_000_000L);

	}

	public MeasurerGraph(final IMeasurerFunction measurerIn, final long timeIntervalIn)
	{
		this.setMeasurer(measurerIn);

		this.setTimeInterval(timeIntervalIn);

		this.setValues(new ArrayList<>());

		this.setTimer(new Timer());

	}

	// Methods

	private boolean execute()
	{
		if (this.getTimer().getElapsedTime() > this.getTimeInterval())
		{
			this.getTimer().start();

			final var measure = this.getMeasurer().measure();
			this.getValues().add(measure);
			this.setAverage((this.getAverage() + measure) / 2.0D);
			this.setTotal(this.getTotal() + measure);

			return true;
		}

		return false;
	}

	// Interface methods

	@Override
	public long start()
	{
		this.setThread(new Thread(this));

		if (OS.getOsName().contains("mac"))
		{
			this.getThread().run();
		}
		else
		{
			this.getThread().start();
		}

		return -1L;
	}

	@Override
	public long stop()
	{
		this.setRunning(false);

		return -1L;
	}

	@Override
	public void reset()
	{
		this.setAverage(0.0D);
		this.setTotal(0);
	}

	@Override
	public void run()
	{
		while (this.isRunning())
		{
			this.execute();
		}
	}

	@Override
	public String shortReport()
	{
		return this.getAverage() + " [" + this.getTotal() + "]";
	}

	@Override
	public String shortReport(final String startIn)
	{
		return startIn + this.getAverage() + " [" + this.getTotal() + "]";
	}

	@Override
	public String report()
	{

		var output = this.getAverage() + " [" + this.getTotal() + "] :\n{";

		for (final long value : this.getValues())
		{
			output += "	" + value + "\n";
		}

		output += "}";

		return output;
	}

	@Override
	public String report(final String startIn)
	{
		var output = startIn + this.getAverage() + " [" + this.getTotal() + "] :\n" + startIn + "{";

		for (final long value : this.getValues())
		{
			output += startIn + "	" + value + "\n";
		}

		output += startIn + "}";

		return output;
	}

	@Override
	public String toString()
	{
		return this.getAverage() + " [" + this.getTotal() + "]";
	}

	// Getter | Setter

	public IMeasurerFunction getMeasurer()
	{
		return this.measurer;
	}

	public void setMeasurer(final IMeasurerFunction measurerIn)
	{
		this.measurer = measurerIn;
	}

	public List<Long> getValues()
	{
		return this.values;
	}

	public void setValues(final List<Long> valuesIn)
	{
		this.values = valuesIn;
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

	public long getTimeInterval()
	{
		return this.timeInterval;
	}

	public void setTimeInterval(final long timeIntervalIn)
	{
		this.timeInterval = timeIntervalIn;
	}

	public Timer getTimer()
	{
		return this.timer;
	}

	public void setTimer(final Timer timerIn)
	{
		this.timer = timerIn;
	}

	public Thread getThread()
	{
		return this.thread;
	}

	public void setThread(final Thread threadIn)
	{
		this.thread = threadIn;
	}

	public boolean isRunning()
	{
		return this.isRunning;
	}

	public void setRunning(final boolean isRunningIn)
	{
		this.isRunning = isRunningIn;
	}
}