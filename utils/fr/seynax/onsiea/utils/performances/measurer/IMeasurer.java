package fr.seynax.onsiea.utils.performances.measurer;

public interface IMeasurer
{
	long start();

	long stop();

	void reset();

	String shortReport();

	String shortReport(String startIn);

	String report();

	String report(String startIn);
}
