package fr.seynax.onsiea.utils;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

public class BufferHelper
{
	public final static FloatBuffer toFloatBUffer(final float... floatsIn)
	{
		final var floatBuffer = MemoryUtil.memAllocFloat(floatsIn.length);

		floatBuffer.put(floatsIn).flip();

		return floatBuffer;
	}
}