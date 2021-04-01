package fr.seynax.onsiea.utils.performances.profiling;

import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public interface IProfiler
{
	void start();

	IProfiler add(IMeasurer... measurerIn);

	void stop();

	void reset();

	String shortReport();

	String shortReport(String startIn);

	String report();

	String report(String startIn);
}
