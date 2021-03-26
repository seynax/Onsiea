package fr.seynax.onsiea.graphics;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import fr.seynax.onsiea.utils.Pair;

public class TextureMerger
{
	public final static Image getImage(final String filePathNameIn)
	{
		int			width;
		int			height;

		ByteBuffer	buffer;

		try (var stack = MemoryStack.stackPush())
		{
			final var	w			= stack.mallocInt(1);
			final var	h			= stack.mallocInt(1);
			final var	channels	= stack.mallocInt(1);

			final var	file		= new File(filePathNameIn);

			final var	filePath	= file.getAbsolutePath();

			buffer = STBImage.stbi_load(filePath, w, h, channels, 4);

			if (buffer == null)
			{
				throw new Exception("Can't load file " + filePathNameIn + " " + STBImage.stbi_failure_reason());
			}

			width	= w.get();
			height	= h.get();

			return new Image(buffer, width, height);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private final static Pair<Vector2i, Map<Vector2i, List<Image>>> getImages(final String... filesPathNameIn)
	{
		final var							maxResolution	= new Vector2i();
		final Map<Vector2i, List<Image>>	resolutedImages	= new HashMap<>();

		for (final String filePathNameIn : filesPathNameIn)
		{
			int			width;
			int			height;

			ByteBuffer	buffer;

			try (var stack = MemoryStack.stackPush())
			{
				final var	w			= stack.mallocInt(1);
				final var	h			= stack.mallocInt(1);
				final var	channels	= stack.mallocInt(1);

				final var	file		= new File(filePathNameIn);

				final var	filePath	= file.getAbsolutePath();

				buffer = STBImage.stbi_load(filePath, w, h, channels, 4);

				if (buffer == null)
				{
					throw new Exception("Can't load file " + filePathNameIn + " " + STBImage.stbi_failure_reason());
				}

				width			= w.get();
				height			= h.get();

				maxResolution.x	+= width;
				maxResolution.y	+= height;

				final var	resolution	= new Vector2i(width, height);

				var			images		= resolutedImages.get(resolution);

				if (images == null)
				{
					images = new ArrayList<>();

					resolutedImages.put(resolution, images);
				}

				images.add(new Image(buffer, width, height));
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

		return new Pair<>(maxResolution, resolutedImages);
	}

	// Gauche vers droite, bas vers haut
	public final static void merge(final String... filesPathNamesIn)
	{
		final var	pair			= TextureMerger.getImages(filesPathNamesIn);

		final var	maxResolution	= pair.getOne();
		final var	resolutedImages	= pair.getTwo();

		final var	byteBuffer		= BufferUtils.createByteBuffer(maxResolution.x() * maxResolution.y());

		final var	iterator		= resolutedImages.entrySet().iterator();

		@SuppressWarnings("unused")
		final var	currentWidth	= 0;
		@SuppressWarnings("unused")
		final var	currentHeight	= 0;

		while (iterator.hasNext())
		{
			final var	entry		= iterator.next();

			final var	resolution	= entry.getKey();
			final var	images		= entry.getValue();

			for (var x = 0; x < resolution.x() * Math.sqrt(images.size()); x++)
			{
				for (var y = 0; y < resolution.y() * Math.sqrt(images.size()); y++)
				{
					byteBuffer.put((byte) 0);
				}
			}

		}
	}

	public final static void mergeSameResolution(final String... filesPathNameIn)
	{
		final List<Image> images = new ArrayList<>();

		for (final String filePathName : filesPathNameIn)
		{
			images.add(TextureMerger.getImage(filePathName));
		}

		final var	column	= (int) Math.round(Math.sqrt(images.size()));
		final var	row		= images.size() / column;

		final var	rest	= images.size() - row * column;

		System.out.println(column + " * " + row + " = " + rest);

		final var	width	= column * images.get(0).getWidth();
		final var	height	= (row + rest) * images.get(0).getHeight();

		System.out.println(width + " " + height);
	}

	public static class Image
	{

		// Variables

		private ByteBuffer	buffer;

		private int			width;
		private int			height;

		// Constructor

		public Image(final ByteBuffer bufferIn, final int widthIn, final int heightIn)
		{
			this.setBuffer(bufferIn);
			this.setWidth(widthIn);
			this.setHeight(heightIn);
		}

		// Fetter | Setter

		public ByteBuffer getBuffer()
		{
			return this.buffer;
		}

		private void setBuffer(final ByteBuffer bufferIn)
		{
			this.buffer = bufferIn;
		}

		int getWidth()
		{
			return this.width;
		}

		private void setWidth(final int widthIn)
		{
			this.width = widthIn;
		}

		int getHeight()
		{
			return this.height;
		}

		private void setHeight(final int heightIn)
		{
			this.height = heightIn;
		}
	}
}
