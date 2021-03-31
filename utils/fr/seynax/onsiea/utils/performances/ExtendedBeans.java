package fr.seynax.onsiea.utils.performances;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;

public class ExtendedBeans
{
	// Static variables

	private final static ClassLoadingMXBean		CLASS_LOADING	= ManagementFactory
			.getPlatformMXBean(ClassLoadingMXBean.class);
	private final static CompilationMXBean		COMPILATION		= ManagementFactory
			.getPlatformMXBean(CompilationMXBean.class);
	private final static MemoryMXBean			MEMORY			= ManagementFactory
			.getPlatformMXBean(MemoryMXBean.class);
	private final static OperatingSystemMXBean	OPERATING_SYSTEM	= ManagementFactory
			.getPlatformMXBean(OperatingSystemMXBean.class);
	private final static RuntimeMXBean			RUNTIME			= ManagementFactory
			.getPlatformMXBean(RuntimeMXBean.class);
	private final static ThreadMXBean			THREAD			= ManagementFactory
			.getPlatformMXBean(ThreadMXBean.class);

	// Static getter

	public static ClassLoadingMXBean getClassloading()
	{
		return ExtendedBeans.CLASS_LOADING;
	}

	public static CompilationMXBean getCompilation()
	{
		return ExtendedBeans.COMPILATION;
	}

	public static MemoryMXBean getMemory()
	{
		return ExtendedBeans.MEMORY;
	}

	public static OperatingSystemMXBean getOperatingSystem()
	{
		return ExtendedBeans.OPERATING_SYSTEM;
	}

	public static RuntimeMXBean getRuntime()
	{
		return ExtendedBeans.RUNTIME;
	}

	public static ThreadMXBean getThread()
	{
		return ExtendedBeans.THREAD;
	}
}
