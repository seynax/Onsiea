package fr.seynax.onsiea.utils.performances;

import java.util.HashMap;
import java.util.Map;

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