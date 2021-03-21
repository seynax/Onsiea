package fr.seynax.onsiea.graphics.callbacks;

import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.system.MemoryUtil;

public class DropCallback implements GLFWDropCallbackI
{
	// Constructor

	public DropCallback()
	{
	}

	// Interface methods

	@Override
	public void invoke(final long windowIn, final int countIn, final long namesIn)
	{
		System.out.println("drop " + countIn + (countIn <= 1 ? " file" : "files"));

		final var nameBuffer = MemoryUtil.memPointerBuffer(namesIn, countIn);
		for (var i = 0; i < countIn; i++)
		{
			System.out.format("\t%d: %s%n", i + 1, MemoryUtil.memUTF8(MemoryUtil.memByteBufferNT1(nameBuffer.get(i))));
		}
	}
}
