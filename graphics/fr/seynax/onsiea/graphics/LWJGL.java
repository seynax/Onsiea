package fr.seynax.onsiea.graphics;

import org.lwjgl.system.Configuration;

public class LWJGL
{
	public static void enableDebugging()
	{
		Configuration.DISABLE_CHECKS.set(false);
		Configuration.DISABLE_FUNCTION_CHECKS.set(false);
		Configuration.GLFW_CHECK_THREAD0.set(true);
		Configuration.DEBUG.set(true);
		Configuration.DEBUG_FUNCTIONS.set(true);
		Configuration.DEBUG_LOADER.set(true);
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
		Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.set(true);
		Configuration.DEBUG_STREAM.set(true);
	}

	public final static void enableDebugStack()
	{
		Configuration.DEBUG_STACK.set(true);
	}

	public final static void disableDebugStack()
	{
		Configuration.DEBUG_STACK.set(false);
	}

	public static void disableDebugging()
	{
		Configuration.DISABLE_CHECKS.set(true);
		Configuration.DISABLE_FUNCTION_CHECKS.set(true);
		Configuration.GLFW_CHECK_THREAD0.set(false);
		Configuration.DEBUG.set(false);
		Configuration.DEBUG_FUNCTIONS.set(false);
		Configuration.DEBUG_LOADER.set(false);
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(false);
		Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.set(false);
		Configuration.DEBUG_STACK.set(false);
		Configuration.DEBUG_STREAM.set(false);
	}
}