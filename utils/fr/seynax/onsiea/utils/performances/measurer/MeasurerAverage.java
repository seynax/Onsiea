package fr.seynax.onsiea.utils.performances.measurer;

public class MeasurerAverage extends MeasurerGraph implements IMeasurer, Runnable
{
	// Variables

	private double	average;

	private double	total;

	private boolean	oneIteration;

	// Constructor

	public MeasurerAverage()
	{

	}

	// Methods

	@Override
	public long runtime()
	{
		final var value = super.runtime();

		if (this.isOneIteration())
		{
			this.setAverage((this.getAverage() + value) / 2.0f);
		}
		else
		{
			this.setAverage(value * 1.0D);

			this.setOneIteration(true);
		}

		this.setTotal(this.getTotal() + value);

		return value;
	}

	// Getter | Setter

	public double getAverage()
	{
		return this.average;
	}

	public void setAverage(final double averageIn)
	{
		this.average = averageIn;
	}

	public boolean isOneIteration()
	{
		return this.oneIteration;
	}

	public void setOneIteration(final boolean oneIterationIn)
	{
		this.oneIteration = oneIterationIn;
	}

	public double getTotal()
	{
		return this.total;
	}

	public void setTotal(final double totalIn)
	{
		this.total = totalIn;
	}
}