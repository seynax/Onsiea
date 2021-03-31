package fr.seynax.onsiea.utils.performances.measurer;

public class MeasurerFactory
{
	// Constructor variables

	private IMeasurerFunction measurerFunction;

	// Constructor

	public MeasurerFactory(final IMeasurerFunction measurerFunctionIn)
	{
		this.setMeasurerFunction(measurerFunctionIn);
	}

	// Methods

	public Measurer create()
	{
		return new Measurer(this.getMeasurerFunction());
	}

	public Measurer create(final long timeIntervalIn)
	{
		return new Measurer(this.getMeasurerFunction(), timeIntervalIn);
	}

	// Getter | Setter

	private IMeasurerFunction getMeasurerFunction()
	{
		return this.measurerFunction;
	}

	private void setMeasurerFunction(final IMeasurerFunction measurerFunctionIn)
	{
		this.measurerFunction = measurerFunctionIn;
	}
}