package fr.seynax.onsiea.utils.performances.helper;

import fr.seynax.onsiea.utils.performances.beans.Beans;
import fr.seynax.onsiea.utils.performances.beans.ExtendedBeans;

public class CPUHelper
{
	public final static int availableProcessors()
	{
		return Beans.getOperatingSystem().getAvailableProcessors();
	}

	public final static double systemCpuLoad()
	{
		return Beans.getOperatingSystem().getSystemLoadAverage();
	}

	public final static double systemLoadAverage()
	{
		return ExtendedBeans.getOperatingSystem().getSystemLoadAverage();
	}

	public final static double cpuLoad()
	{
		return ExtendedBeans.getOperatingSystem().getCpuLoad();
	}

	public final static double processCpuLoad()
	{
		return ExtendedBeans.getOperatingSystem().getProcessCpuLoad();
	}

	public final static long processCpuTime()
	{
		return ExtendedBeans.getOperatingSystem().getProcessCpuTime();
	}
}
