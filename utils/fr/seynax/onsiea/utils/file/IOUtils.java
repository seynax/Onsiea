package fr.seynax.onsiea.utils.file;

/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.lwjgl.BufferUtils;

/**
 * @author Kai Burjack
 */
public class IOUtils
{
	private static ByteBuffer resizeBuffer(final ByteBuffer buffer, final int newCapacity)
	{
		final var newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	public static ByteBuffer ioResourceToByteBuffer(final String resource, final int bufferSize) throws IOException
	{
		ByteBuffer	buffer;
		/**
		 * final var url =
		 * Thread.currentThread().getContextClassLoader().getResource(resource); if (url
		 * == null) { throw new IOException("Classpath resource not found: " +
		 * resource); }
		 **/
		final var	file	= new File(resource);
		if (file.isFile())
		{
			final var	fis	= new FileInputStream(file);
			final var	fc	= fis.getChannel();
			buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			fc.close();
			fis.close();
		}
		else
		{
			buffer = BufferUtils.createByteBuffer(bufferSize);
			final var source = new FileInputStream(file); // .openStream();
			/**
			 * if (source == null) { throw new FileNotFoundException(resource); }
			 **/
			try
			{
				final var buf = new byte[8192];
				while (true)
				{
					final var bytes = source.read(buf, 0, buf.length);
					if (bytes == -1)
					{
						break;
					}
					if (buffer.remaining() < bytes)
					{
						buffer = IOUtils.resizeBuffer(buffer,
								Math.max(buffer.capacity() * 2, buffer.capacity() - buffer.remaining() + bytes));
					}
					buffer.put(buf, 0, bytes);
				}
				buffer.flip();
			}
			finally
			{
				source.close();
			}
		}
		return buffer;
	}
}