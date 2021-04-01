package fr.seynax.onsiea.utils.performances.profiling;

import java.util.HashMap;
import java.util.Map;

public class ProfilingSystem
{
	// Variables

	private Map<String, IProfiler> profilers;

	// Constructor

	public ProfilingSystem(final NamedProfiler... namedProfilersIn)
	{
		this.setProfilers(new HashMap<>());

		for (final NamedProfiler namedProfiler : namedProfilersIn)
		{
			this.getProfilers().put(namedProfiler.getName(), namedProfiler);
		}
	}

	// Methods

	public ProfilingSystem add(final String nameIn, final Profiler profilerIn)
	{
		this.getProfilers().put(nameIn, profilerIn);

		return this;
	}

	public ProfilingSystem add(final NamedProfiler namedProfilerIn)
	{
		this.getProfilers().put(namedProfilerIn.getName(), namedProfilerIn);

		return this;
	}

	public boolean remove(final String profilerNmeIn)
	{
		return this.getProfilers().remove(profilerNmeIn) != null;
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

	public boolean reset(final String profilerNameIn)
	{
		final var profiler = this.getProfilers().get(profilerNameIn);

		if (profiler == null)
		{
			return false;
		}

		profiler.reset();

		return true;
	}

	// Getter | Setter

	public Map<String, IProfiler> getProfilers()
	{
		return this.profilers;
	}

	public void setProfilers(final Map<String, IProfiler> profilersIn)
	{
		this.profilers = profilersIn;
	}
}