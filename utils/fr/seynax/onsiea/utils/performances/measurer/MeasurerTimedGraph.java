package fr.seynax.onsiea.utils.performances.measurer;

import java.util.Map;

public class MeasurerTimedGraph extends Measurer implements IMeasurer, Runnable
{
	// Variables

	private Map<Long, Long> graph;

	// Constructor

	public MeasurerTimedGraph()
	{

	}

	// Methods

	@Override
	public long runtime()
	{
		final var value = super.runtime();

		this.getGraph().put(System.nanoTime(), value);

		return value;
	}

	// Getter | Setter

	public Map<Long, Long> getGraph()
	{
		return this.graph;
	}

	public void setGraph(final Map<Long, Long> graphIn)
	{
		this.graph = graphIn;
	}
}