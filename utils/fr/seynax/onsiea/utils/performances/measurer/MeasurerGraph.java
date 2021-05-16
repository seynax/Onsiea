package fr.seynax.onsiea.utils.performances.measurer;

import java.util.List;

public class MeasurerGraph extends Measurer implements IMeasurer, Runnable
{
	// Variables

	private List<Long> graph;

	// Constructor

	public MeasurerGraph()
	{

	}

	// Methods

	@Override
	public long runtime()
	{
		final var value = super.runtime();

		this.getGraph().add(value);

		return value;
	}

	// Getter | Setter

	public List<Long> getGraph()
	{
		return this.graph;
	}

	public void setGraph(final List<Long> graphIn)
	{
		this.graph = graphIn;
	}
}