package fr.seynax.onsiea.utils.performances.profiling;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class NamedProfiler extends Profiler implements IProfiler
{
	// Static variables

	private static int	namedProfilerNumber;

	// Variables

	private String		name;

	// Constructor

	public NamedProfiler()
	{
		this.setName("PROFILER-" + NamedProfiler.getNamedProfilerNumber());

		NamedProfiler.addNamedProfiler();
	}

	public NamedProfiler(final String nameIn)
	{
		this.setName(nameIn);

		NamedProfiler.addNamedProfiler();
	}

	public NamedProfiler(final IMeasurer... measurersIn)
	{
		super(measurersIn);

		this.setName("PROFILER-" + NamedProfiler.getNamedProfilerNumber());

		NamedProfiler.addNamedProfiler();
	}

	public NamedProfiler(final String nameIn, final IMeasurer... measurersIn)
	{
		super(measurersIn);

		this.setName(nameIn);

		NamedProfiler.addNamedProfiler();
	}

	// Static methods

	public final static int addNamedProfiler()
	{
		NamedProfiler.setNamedProfilerNumber(NamedProfiler.getNamedProfilerNumber() + 1);

		return NamedProfiler.getNamedProfilerNumber();
	}

	// Interface methods

	@Override
	public String shortReport()
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + measurer.shortReport();
		//}

		return "";
	}

	@Override
	public String shortReport(final String startIn)
	{

		//for (final IMeasurer measurer : this.getMeasurers())
		//{
		// output += "\n" + startIn + "[" + measurer.shortReport();
		//}

		return "";
	}

	@Override
	public String report()
	{
		return "[ Name : " + this.getName() + " ]\n" + super.report();
	}

	@Override
	public String report(final String startIn)
	{
		return startIn + "[ Name : " + this.getName() + " ]\n" + super.report(startIn);
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
}