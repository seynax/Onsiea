package fr.seynax.onsiea.utils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
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

	public static IntBuffer createIntBuffer(final int... contentsIn)
	{
		return MemoryUtil.memAllocInt(contentsIn.length).put(contentsIn).flip();
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

	public final static PointerBuffer createPointerBuffer(final long... pointersIn)
	{
		return MemoryUtil.memAllocPointer(pointersIn.length).put(pointersIn).flip();
	}

	public final static PointerBuffer createPointerBuffer(final PointerBuffer pointerBufferIn)
	{
		return MemoryUtil.memAllocPointer(pointerBufferIn.remaining()).put(pointerBufferIn).flip();
	}

	public final static PointerBuffer createPointerBuffer(final PointerBuffer pointerBufferIn,
			final long... otherPointersIn)
	{
		return MemoryUtil.memAllocPointer(pointerBufferIn.remaining() + otherPointersIn.length).put(pointerBufferIn)
				.put(otherPointersIn).flip();
	}
}