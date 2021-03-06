package fr.seynax.onsiea.utils.performances.helper;

import java.lang.management.MemoryUsage;

import fr.seynax.onsiea.utils.performances.beans.Beans;
import fr.seynax.onsiea.utils.performances.beans.ExtendedBeans;

public class MemoryHelper
{
	/**
	 * @return used memory by the application
	 */
	public static long usedMemory()
	{
		return MemoryHelper.totalMemory() - MemoryHelper.freeMemory();
	}

	/**
	 * @return the total amount of memory in the Java virtual machine
	 */
	public static final long totalMemory()
	{
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * maximum usable memory by the virtual machine
	 *
	 * @return
	 */
	public static final long maxMemory()
	{
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * free usable memory by the virtual machine
	 *
	 * @return
	 */
	public static final long freeMemory()
	{
		return Runtime.getRuntime().freeMemory();
	}

	/**
	 *
	 * @return
	 */
	public static final long committedVirtualMemorySize()
	{
		return ExtendedBeans.getOperatingSystem().getCommittedVirtualMemorySize();
	}

	/**
	 * free system memory size
	 *
	 * @return
	 */
	public static final long freeMemorySize()
	{
		return ExtendedBeans.getOperatingSystem().getFreeMemorySize();
	}

	/**
	 *
	 * @return
	 */
	public static final long freeSwapSpaceSize()
	{
		return ExtendedBeans.getOperatingSystem().getFreeSwapSpaceSize();
	}

	/**
	 * Memory size on system
	 *
	 * @return
	 */
	public static final long totalMemorySize()
	{
		return ExtendedBeans.getOperatingSystem().getTotalMemorySize();
	}

	/**
	 *
	 * @return
	 */
	public static final long totalSwapSpaceSize()
	{
		return ExtendedBeans.getOperatingSystem().getTotalSwapSpaceSize();
	}

	public static final MemoryUsage heapMemoryUsage()
	{
		return Beans.getMemory().getHeapMemoryUsage();
	}

	public static final MemoryUsage nonHeapMemoryUsage()
	{
		return Beans.getMemory().getNonHeapMemoryUsage();
	}

	public static final int getObjectPendingFinalizationCount()
	{
		return Beans.getMemory().getObjectPendingFinalizationCount();
	}

	public static final boolean isVerbose()
	{
		return Beans.getMemory().isVerbose();
	}
}
