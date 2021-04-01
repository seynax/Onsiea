package fr.seynax.onsiea.utils.performances.profiling;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public interface IProfiler
{
	void start();

	void add(IMeasurer... measurerIn);

	void stop();

	void reset();
}
