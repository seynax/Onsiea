package fr.seynax.onsiea.utils.performances.measurer;

import fr.seynax.onsiea.utils.performances.helper.CPUHelper;
import fr.seynax.onsiea.utils.performances.helper.MemoryHelper;

public class MeasurerFactories
{
	private final static Factory	FREE_MEMORY	= new Factory("freeMemory", MemoryHelper::freeMemory);

	private final static Factory	USED_MEMORY	= new Factory("usedMemory", MemoryHelper::usedMemory);

	private final static Factory	CPU_USAGE	= new Factory("cpuUsage", () -> ((long) CPUHelper.processCpuLoad()));

	// Getter

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (Factory) freeMemory measurer
	 */
	public static Factory getFreeMemory()
	{
		return MeasurerFactories.FREE_MEMORY;
	}

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (Factory) usedMemory measurer
	 */
	public static Factory getUsedMemory()
	{
		return MeasurerFactories.USED_MEMORY;
	}

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (Factory) cpuUsage measurer
	 */
	public static Factory getCpuUsage()
	{
		return MeasurerFactories.CPU_USAGE;
	}

	public final static class Factory
	{
		// Variables

		private IMeasurerFunction	measurerFunction;

		private String				measureName;

		// Constructor

		public Factory(final String measureNameIn, final IMeasurerFunction measurerFunctionIn)
		{
			this.setMeasureName(measureNameIn);
			this.setMeasurerFunction(measurerFunctionIn);
		}

		// Methods

		public IMeasurer create()
		{
			return new MeasurerAverage(this.getMeasureName(), this.getMeasurerFunction());
		}

		public IMeasurer create(final long timeIntervalIn)
		{
			return new MeasurerAverage(this.getMeasureName(), this.getMeasurerFunction(), timeIntervalIn);
		}

		// Getter | Setter

		public IMeasurerFunction getMeasurerFunction()
		{
			return this.measurerFunction;
		}

		public void setMeasurerFunction(final IMeasurerFunction measurerFunctionIn)
		{
			this.measurerFunction = measurerFunctionIn;
		}

		public String getMeasureName()
		{
			return this.measureName;
		}

		public void setMeasureName(final String measureNameIn)
		{
			this.measureName = measureNameIn;
		}
	}
}