package fr.seynax.onsiea.utils.performances.profiling;

import java.util.ArrayList;
import java.util.Collections;
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

		Collections.addAll(this.getMeasurers(), measurersIn);
	}

	// Methods

	@Override
	public IProfiler add(final IMeasurer... measurersIn)
	{
		Collections.addAll(this.getMeasurers(), measurersIn);

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
		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// measurer.reset();
		//}
	}

	@Override
	public String shortReport()
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + measurer.shortReport(" ");
		//}

		return "Iterations : " + this.getIterations();
	}

	@Override
	public String shortReport(final String startIn)
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + startIn + measurer.shortReport(" ");
		//}

		return startIn + "Iterations : " + this.getIterations();
	}

	@Override
	public String report()
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + measurer.report(" ");
		//}

		return "Iterations : " + this.getIterations();
	}

	@Override
	public String report(final String startIn)
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + startIn + measurer.report(" ");
		//}

		return startIn + "Iterations : " + this.getIterations();
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