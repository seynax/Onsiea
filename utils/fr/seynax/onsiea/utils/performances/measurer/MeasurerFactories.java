package fr.seynax.onsiea.utils.performances.measurer;

import fr.seynax.onsiea.utils.performances.helper.CPUHelper;
import fr.seynax.onsiea.utils.performances.helper.MemoryHelper;

public class MeasurerFactories
{
	private final static Factory	FREE_MEMORY	= new Factory(() ->
												{
													MemoryHelper.freeMemory();

													return 0L;
												});

	private final static Factory	USED_MEMORY	= new Factory(() ->
												{
													MemoryHelper.usedMemory();

													return 0L;
												});

	private final static Factory	CPU_USAGE	= new Factory(() ->
												{
													CPUHelper.processCpuLoad();

													return 0L;
												});

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

	final static class Factory
	{
		// Variables

		private IMeasurerFunction measurerFunction;

		// Constructor

		public Factory(final IMeasurerFunction measurerFunctionIn)
		{
			this.setMeasurerFunction(measurerFunctionIn);
		}

		// Methods

		public IMeasurer create()
		{
			return new MeasurerAverage(this.getMeasurerFunction());
		}

		public IMeasurer create(final long timeIntervalIn)
		{
			return new MeasurerAverage(this.getMeasurerFunction(), timeIntervalIn);
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
	}
}