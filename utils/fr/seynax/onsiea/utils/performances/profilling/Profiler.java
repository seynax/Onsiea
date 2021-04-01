package fr.seynax.onsiea.utils.performances.profilling;

import java.util.List;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class Profiler
{
	// Variables

	private List<IMeasurer> measurers;

	// Constructor

	public Profiler()
	{
	}

	// Methods

	public void addMeasurer(final IMeasurer... measurersIn)
	{
		for (final IMeasurer measurer : measurersIn)
		{
			this.getMeasurers().add(measurer);
		}
	}

	public void start()
	{
		for (final IMeasurer measurer : this.getMeasurers())
		{
			measurer.start();
		}
	}

	public void stop()
	{
		for (final IMeasurer measurer : this.getMeasurers())
		{
			measurer.stop();
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
}