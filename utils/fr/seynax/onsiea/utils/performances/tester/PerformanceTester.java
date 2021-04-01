package fr.seynax.onsiea.utils.performances.tester;

import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.utils.IFunction;
import fr.seynax.onsiea.utils.file.FileUtils;
import fr.seynax.onsiea.utils.performances.measurer.MeasurerTime;
import fr.seynax.onsiea.utils.performances.tester.PerformanceTypes.EnumTypes;

public class PerformanceTester
{
	// Variables

	private PerformanceTypes		performancesTypes;

	private Map<String, IFunction>	functions;

	private MeasurerTime			timingProfiler;

	private int						phases;
	private int						iterations;

	// Constructor

	public PerformanceTester(final PerformanceTypes performanceTypesIn, final int phasesIn, final int iterationsIn)
	{
		this.setPerformancesTypes(performanceTypesIn);
		this.setPhases(phasesIn);
		this.setIterations(iterationsIn);

		this.setFunctions(new HashMap<>());

		this.setTimingProfiler(new MeasurerTime());
	}

	// Methods

	public PerformanceTester add(final String functionNameIn, final IFunction functionIn)
	{
		this.getFunctions().put(functionNameIn, functionIn);

		return this;
	}

	public void check()
	{
		final var iterator = this.getFunctions().entrySet().iterator();

		while (iterator.hasNext())
		{
			final var	entry			= iterator.next();

			final var	filepath		= entry.getKey();
			final var	function		= entry.getValue();

			final var	finalFilepath	= "output/timing/" + filepath + ".log";

			FileUtils.write(finalFilepath, "", false);

			for (var i = 0; i < this.getPhases(); i++)
			{
				if (this.getPerformancesTypes().has(EnumTypes.TIMING))
				{
					PerformanceTester.checkTiming(function, this.getIterations(), this.getTimingProfiler(),
							finalFilepath);
				}
			}
		}
	}

	public final static String checkTiming(final IFunction functionIn, final int iterationsIn,
			final MeasurerTime timingProfilerIn, final String filepathIn)
	{
		timingProfilerIn.reset();

		for (var i = 0; i < iterationsIn; i++)
		{
			timingProfilerIn.start();

			functionIn.execute();

			timingProfilerIn.stop();
		}

		final var report = timingProfilerIn.shortReport();

		FileUtils.write(filepathIn, report + "\n", true);

		return report;
	}

	// Getter | Setter

	public PerformanceTypes getPerformancesTypes()
	{
		return this.performancesTypes;
	}

	public void setPerformancesTypes(final PerformanceTypes performancesTypesIn)
	{
		this.performancesTypes = performancesTypesIn;
	}

	public Map<String, IFunction> getFunctions()
	{
		return this.functions;
	}

	public void setFunctions(final Map<String, IFunction> functionsIn)
	{
		this.functions = functionsIn;
	}

	public MeasurerTime getTimingProfiler()
	{
		return this.timingProfiler;
	}

	public void setTimingProfiler(final MeasurerTime timingProfilerIn)
	{
		this.timingProfiler = timingProfilerIn;
	}

	public int getPhases()
	{
		return this.phases;
	}

	public void setPhases(final int phasesIn)
	{
		this.phases = phasesIn;
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