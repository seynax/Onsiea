package fr.seynax.onsiea.utils.performances.measurer;

public interface IMeasurer
{
	long start();

	long stop();

	void reset();
}
