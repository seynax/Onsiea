package fr.seynax.onsiea.utils.performances.measurer;

import fr.seynax.onsiea.utils.performances.helper.CPUHelper;
import fr.seynax.onsiea.utils.performances.helper.MemoryHelper;

public class Measurers
{
	private final static MeasurerFactory	FREE_MEMORY	= new MeasurerFactory(() ->
														{
															MemoryHelper.freeMemory();

															return 0L;
														});

	private final static MeasurerFactory	USED_MEMORY	= new MeasurerFactory(() ->
														{
															MemoryHelper.usedMemory();

															return 0L;
														});

	private final static MeasurerFactory	CPU_USAGE	= new MeasurerFactory(() ->
														{
															CPUHelper.processCpuLoad();

															return 0L;
														});

	// Getter

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (MeasurerFactory) freeMemory measurer
	 */
	public static MeasurerFactory getFreeMemory()
	{
		return Measurers.FREE_MEMORY;
	}

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (MeasurerFactory) usedMemory measurer
	 */
	public static MeasurerFactory getUsedMemory()
	{
		return Measurers.USED_MEMORY;
	}

	/**
	 * Uses this to create new measurer with "create()" or "create(long
	 * timeIntervalIn)" methods
	 *
	 * @return (MeasurerFactory) cpuUsage measurer
	 */
	public static MeasurerFactory getCpuUsage()
	{
		return Measurers.CPU_USAGE;
	}
}