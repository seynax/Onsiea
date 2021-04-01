package fr.seynax.onsiea.utils.performances.profiling;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class NamedProfiler implements IProfiler
{
	// Static variables

	private static int		namedProfilerNumber;

	// Variables

	private List<IMeasurer>	measurers;

	private int				iterations;

	private String			name;

	// Constructor

	public NamedProfiler()
	{
		this.setName("PROFILER-" + NamedProfiler.getNamedProfilerNumber());

		NamedProfiler.addNamedProfiler();

		this.setMeasurers(new ArrayList<>());
	}

	public NamedProfiler(final String nameIn)
	{
		this.setName(nameIn);

		NamedProfiler.addNamedProfiler();

		this.setMeasurers(new ArrayList<>());
	}

	public NamedProfiler(final IMeasurer... measurersIn)
	{
		this.setName("PROFILER-" + NamedProfiler.getNamedProfilerNumber());

		NamedProfiler.addNamedProfiler();

		this.setMeasurers(new ArrayList<>());

		for (final IMeasurer measurer : measurersIn)
		{
			this.getMeasurers().add(measurer);
		}
	}

	public NamedProfiler(final String nameIn, final IMeasurer... measurersIn)
	{
		this.setName(nameIn);

		NamedProfiler.addNamedProfiler();

		this.setMeasurers(new ArrayList<>());

		for (final IMeasurer measurer : measurersIn)
		{
			this.getMeasurers().add(measurer);
		}
	}
	// Static methods

	public final static int addNamedProfiler()
	{
		NamedProfiler.setNamedProfilerNumber(NamedProfiler.getNamedProfilerNumber() + 1);

		return NamedProfiler.getNamedProfilerNumber();
	}

	// Methods

	@Override
	public IProfiler add(final IMeasurer... measurersIn)
	{
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

	// Static variables

	public static int getNamedProfilerNumber()
	{
		return NamedProfiler.namedProfilerNumber;
	}

	public static void setNamedProfilerNumber(final int namedProfilerNumberIn)
	{
		NamedProfiler.namedProfilerNumber = namedProfilerNumberIn;
	}

	// Variables

	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}

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