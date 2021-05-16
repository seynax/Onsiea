package fr.seynax.onsiea.utils.performances.measurer;

import fr.seynax.onsiea.utils.OS;

public class Measurer implements IMeasurer, Runnable
{
	// Variables

	private IMeasurerFunction	measurerFunction;

	private Thread				thread;

	private boolean				isRunning;

	// Constructor

	public Measurer()
	{

	}

	// Interface methods

	@Override
	public void start()
	{
		this.setThread(new Thread(this));

		this.setRunning(true);

		if (OS.getOsName().contains("Mac"))
		{
			this.getThread().run();
		}
		else
		{
			this.getThread().start();
		}
	}

	@Override
	public void run()
	{
		while (this.isRunning())
		{
			this.runtime();
		}
	}

	@Override
	public void stop()
	{
		this.setRunning(false);
	}

	// Methods

	public long runtime()
	{
		return this.getMeasurerFunction().measure();
	}

	// Getter | Setter

	public IMeasurerFunction getMeasurerFunction()
	{
		return this.measurerFunction;
	}

	public void setMeasurerFunction(final IMeasurerFunction measurerFunctionIn)
	{
		this.measurerFunction = measurerFunctionIn;
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