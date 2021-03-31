package fr.seynax.onsiea.utils.performances.helper;

import java.lang.management.ManagementFactory;

public class ThreadHelper
{
	public final static long currentThreadCpuTime()
	{
		return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
	}

	public final static long currentThreadUserTime()
	{
		return ManagementFactory.getThreadMXBean().getCurrentThreadUserTime();
	}

	public final static int daemonThreadCount()
	{
		return ManagementFactory.getThreadMXBean().getDaemonThreadCount();
	}

	public final static int peakThreadCount()
	{
		return ManagementFactory.getThreadMXBean().getPeakThreadCount();
	}

	public final static int threadCount()
	{
		return ManagementFactory.getThreadMXBean().getThreadCount();
	}

	public final static long totalStartedThreadCount()
	{
		return ManagementFactory.getThreadMXBean().getTotalStartedThreadCount();
	}
}
