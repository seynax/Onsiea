package fr.seynax.onsiea.utils.performances.profiling;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class Profiler implements IProfiler
{
	// Variables

	private List<IMeasurer>	measurers;

	private int				iterations;

	// Constructor

	public Profiler()
	{
		this.setMeasurers(new ArrayList<>());
	}

	public Profiler(final IMeasurer... measurersIn)
	{
		this.setMeasurers(new ArrayList<>());

		for (final IMeasurer measurer : measurersIn)
		{
			this.getMeasurers().add(measurer);
		}
	}

	// Methods

	@Override
	public IProfiler add(final IMeasurer... measurersIn)
	{
		this.setMeasurers(new ArrayList<>());

		for (final IMeasurer measurer : measurersIn)
		{
			this.getMeasurers().add(measurer);
		}

		return this;
	}

	@Override
	public void start()
	{
		for (final IMeasurer measurer : this.getMeasurers())
		{
			measurer.start();
		}

		this.setIterations(this.getIterations() + 1);
	}

	@Override
	public void stop()
	{
		for (final IMeasurer measurer : this.getMeasurers())
		{
			measurer.stop();
		}
	}

	@Override
	public void reset()
	{
		for (final IMeasurer measurer : this.getMeasurers())
		{
			measurer.reset();
		}
	}

	// Variables

	public List<IMeasurer> getMeasurers()
	{
		return this.measurers;
	}

	public void setMeasurers(final List<IMeasurer> measurersIn)
	{
		this.measurers = measurersIn;
	}

	public int getIterations()
	{
		return this.iterations;
	}

	public void setIterations(final int iterationsIn)
	{
		this.iterations = iterationsIn;
	}
}