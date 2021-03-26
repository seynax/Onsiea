package fr.seynax.onsiea.utils.performances;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.utils.maths.Maths;

public class ProfilerSystem
{
	// Variables

	private Map<String, Profiler> profilers;

	// Constructor

	public ProfilerSystem()
	{
		this.setProfiler(new HashMap<>());
	}

	// Methods

	public String shortReport()
	{
		var	totalTimeIterations	= 0L;

		var	reportOutput		= "";

		for (final Profiler profiler : this.getProfilers().values())
		{
			totalTimeIterations += profiler.getTotalTime();
		}

		reportOutput = "[TotalTime count each iteration = " + totalTimeIterations + "] :\n";

		for (final Profiler profiler : this.getProfilers().values())
		{
			reportOutput += "	" + profiler.toString() + " | "
					+ Maths.round((double) profiler.getTotalTime() / totalTimeIterations * 100.0D, 2) + " %] for "
					+ profiler.getIterationNumber() + " iterations !\n";
		}

		reportOutput += "\n}\n";

		return reportOutput;
	}

	public String report(final boolean roundPercentIn)
	{
		var	totalTimeIterations	= 0L;
		var	totalTime			= 0L;

		var	reportOutput		= "";

		for (final Profiler profiler : this.getProfilers().values())
		{
			totalTimeIterations	+= profiler.getTotalTime();
			totalTime			+= profiler.getTime();
		}

		reportOutput	= "[TotalTime count each iteration : " + totalTimeIterations + "] :\n";
		reportOutput	= "[TotalTime : " + totalTime + "]\n{\n";

		var i = 0;

		for (final Profiler profiler : this.getProfilers().values())
		{
			reportOutput += "	[" + i + "] : " + profiler.toString() + "\n	{\n";

			if (roundPercentIn)
			{
				reportOutput	+= "		Percent with iterations : "
						+ Maths.round((double) profiler.getTotalTime() / (double) totalTimeIterations * 100.0D, 2)
						+ " %\n";
				reportOutput	+= "		Percent : " + Maths.round(profiler.getTime() / totalTime * 100.0D, 2)
						+ " %\n";
			}
			else
			{
				reportOutput	+= "		Percent with iterations : "
						+ (double) profiler.getTotalTime() / (double) totalTimeIterations * 100.0D + " %\n";
				reportOutput	+= "		Percent : " + profiler.getTime() / totalTime * 100.0D + " %\n";
			}
			reportOutput	+= "		Iterations : " + profiler.getIterationNumber() + "\n";
			reportOutput	+= "	}";

			if (i < this.getProfilers().size() - 1)
			{
				reportOutput += ",";
			}

			reportOutput += "\n";

			i++;
		}

		reportOutput += "\n}\n";

		return reportOutput;
	}

	public String report()
	{
		return this.report(true);
	}

	public boolean start(final String profilerNameIn)
	{
		final var profiler = this.getProfilers().get(profilerNameIn);

		if (profiler == null)
		{
			return false;
		}

		profiler.start();

		return true;
	}

	public boolean remove(final String profilerNameIn)
	{
		return this.getProfilers().remove(profilerNameIn) != null;
	}

	public void add(final String... profilersNameIn)
	{
		for (final String profilerName : profilersNameIn)
		{
			this.getProfilers().put(profilerName, new Profiler(profilerName));
		}
	}

	public void add(final Profiler... profilersIn)
	{
		for (final Profiler profiler : profilersIn)
		{
			this.getProfilers().put(profiler.getName(), profiler);
		}
	}

	public Profiler get(final String profilerNameIn)
	{
		return this.getProfilers().get(profilerNameIn);
	}

	public boolean stop(final String profilerNameIn)
	{
		final var profiler = this.getProfilers().get(profilerNameIn);

		if (profiler == null)
		{
			return false;
		}

		profiler.stop();

		return true;
	}

	public Collection<String> names()
	{
		return this.getProfilers().keySet();
	}

	public Collection<Profiler> profilers()
	{
		return this.getProfilers().values();
	}

	// Getter | Setter

	private Map<String, Profiler> getProfilers()
	{
		return this.profilers;
	}

	private void setProfiler(final Map<String, Profiler> profilersIn)
	{
		this.profilers = profilersIn;
	}
}