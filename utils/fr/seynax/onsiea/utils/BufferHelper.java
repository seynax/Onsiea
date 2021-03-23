package fr.seynax.onsiea.utils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

public class BufferHelper
{
	public final static ByteBuffer createByteBuffer(final byte... contentsIn)
	{
		return BufferUtils.createByteBuffer(contentsIn.length).put(contentsIn).flip();
	}

	public final static ShortBuffer createShortBuffer(final short... contentsIn)
	{
		return MemoryUtil.memAllocShort(contentsIn.length).put(contentsIn).flip();
	}

	public final static FloatBuffer createFloatBuffer(final float... contentsIn)
	{
		return MemoryUtil.memAllocFloat(contentsIn.length).put(contentsIn).flip();
	}

	public final static DoubleBuffer createDoubleBuffer(final double... contentsIn)
	{
		return MemoryUtil.memAllocDouble(contentsIn.length).put(contentsIn).flip();
	}

	public final static LongBuffer createFloatBuffer(final long... contentsIn)
	{
		return MemoryUtil.memAllocLong(contentsIn.length).put(contentsIn).flip();
	}
}