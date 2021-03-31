package fr.seynax.onsiea.utils.performances;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.PlatformManagedObject;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;

public class Beans
{
	// Static variables

	private final static ClassLoadingMXBean						CLASS_LOADING					= ManagementFactory
			.getClassLoadingMXBean();
	final static CompilationMXBean								COMPILATION						= ManagementFactory
			.getCompilationMXBean();
	final static List<GarbageCollectorMXBean>					GARBAGE_COLLECTOR				= ManagementFactory
			.getGarbageCollectorMXBeans();
	final static List<MemoryManagerMXBean>						MEMORY_MANAGER					= ManagementFactory
			.getMemoryManagerMXBeans();
	final static MemoryMXBean									MEMORY							= ManagementFactory
			.getMemoryMXBean();
	final static List<MemoryPoolMXBean>							MEMORY_POOL						= ManagementFactory
			.getMemoryPoolMXBeans();
	final static OperatingSystemMXBean							OPERATING_SYSTEM				= ManagementFactory
			.getOperatingSystemMXBean();
	final static Set<Class<? extends PlatformManagedObject>>	PLATFORM_MANAGEMENT_INTERFACES	= ManagementFactory
			.getPlatformManagementInterfaces();
	final static MBeanServer									SERVER_PLATFORM					= ManagementFactory
			.getPlatformMBeanServer();
	final static RuntimeMXBean									RUNTIME							= ManagementFactory
			.getRuntimeMXBean();
	final static ThreadMXBean									THREAD							= ManagementFactory
			.getThreadMXBean();

	// Static getter

	public static ClassLoadingMXBean getClassloading()
	{
		return Beans.CLASS_LOADING;
	}

	public static CompilationMXBean getCompilation()
	{
		return Beans.COMPILATION;
	}

	public static List<GarbageCollectorMXBean> getGarbagecollector()
	{
		return Beans.GARBAGE_COLLECTOR;
	}

	public static List<MemoryManagerMXBean> getMemorymanager()
	{
		return Beans.MEMORY_MANAGER;
	}

	public static MemoryMXBean getMemory()
	{
		return Beans.MEMORY;
	}

	public static List<MemoryPoolMXBean> getMemorypool()
	{
		return Beans.MEMORY_POOL;
	}

	public static OperatingSystemMXBean getOperatingSystem()
	{
		return Beans.OPERATING_SYSTEM;
	}

	public static Set<Class<? extends PlatformManagedObject>> getPlatformmanagementinterfaces()
	{
		return Beans.PLATFORM_MANAGEMENT_INTERFACES;
	}

	public static MBeanServer getServerplatform()
	{
		return Beans.SERVER_PLATFORM;
	}

	public static RuntimeMXBean getRuntime()
	{
		return Beans.RUNTIME;
	}

	public static ThreadMXBean getThread()
	{
		return Beans.THREAD;
	}
}