package fr.seynax.onsiea.opengl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.utils.Date;

public class OpenGLScreenshot
{
	private static ByteBuffer		pixels;
	private static BufferedImage	bufferedImage;
	private final static int		bpp	= 4;

	public final static void resize(final IWindow windowIn)
	{
		OpenGLScreenshot.setPixels(MemoryUtil.memAlloc(windowIn.getWidth() * windowIn.getHeight() * 4));
		OpenGLScreenshot.setBufferedImage(
				new BufferedImage(windowIn.getWidth(), windowIn.getHeight(), BufferedImage.TYPE_INT_RGB));
	}

	public final static ByteBuffer getPixels(final IWindow windowIn)
	{
		GL11.glReadBuffer(GL11.GL_FRONT);

		OpenGLScreenshot.getPixels().clear();

		GL11.glReadPixels(0, 0, windowIn.getWidth(), windowIn.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
				OpenGLScreenshot.getPixels());

		return OpenGLScreenshot.getPixels();
	}

	public final static Texture getIntoTexture(final IWindow windowIn)
	{
		return Texture.loadTexture(OpenGLScreenshot.getPixels(windowIn), windowIn.getWidth(), windowIn.getHeight());
	}

	public final static Texture getIntoTexture(final ByteBuffer pixelsIn, final IWindow windowIn)
	{
		return Texture.loadTexture(pixelsIn, windowIn.getWidth(), windowIn.getHeight());
	}

	public static boolean write(final BufferedImage bufferedImageIn)
	{
		final var saveDirectory = new File("resources/screenshots/");

		if (!saveDirectory.exists())
		{
			if (!saveDirectory.mkdir())
			{
				System.err.println("The screenshot directory could not be created.");
			}
		}

		final var	file	= new File(saveDirectory + "/" + Date.getDate() + ".png");	// The file to save the pixels
																						// too.
		final var	format	= "png";													// "PNG" or "JPG".

		// Tries to create image.
		try
		{
			ImageIO.write(bufferedImageIn, format, file);
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public static BufferedImage getImage(final IWindow windowIn)
	{
		final var pixels = OpenGLScreenshot.getPixels(windowIn);

		for (var x = OpenGLScreenshot.getBufferedImage().getWidth() - 1; x >= 0; x--)
		{
			for (var y = OpenGLScreenshot.getBufferedImage().getHeight() - 1; y >= 0; y--)
			{
				final var i = (x + windowIn.getWidth() * y) * 4;
				OpenGLScreenshot.getBufferedImage().setRGB(x, OpenGLScreenshot.getBufferedImage().getHeight() - 1 - y,
						(pixels.get(i) & 0xFF & 0x0ff) << 16 | (pixels.get(i + 1) & 0xFF & 0x0ff) << 8
								| pixels.get(i + 2) & 0xFF & 0x0ff);
			}
		}

		return OpenGLScreenshot.getBufferedImage();
	}

	public static BufferedImage getImage(final ByteBuffer pixelsIn, final IWindow windowIn)
	{
		final var bufferedImage = new BufferedImage(windowIn.getWidth(), windowIn.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		for (var x = bufferedImage.getWidth() - 1; x >= 0; x--)
		{
			for (var y = bufferedImage.getHeight() - 1; y >= 0; y--)
			{
				final var i = (x + windowIn.getWidth() * y) * 4;
				bufferedImage.setRGB(x, bufferedImage.getHeight() - 1 - y, (pixelsIn.get(i) & 0xFF & 0x0ff) << 16
						| (pixelsIn.get(i + 1) & 0xFF & 0x0ff) << 8 | pixelsIn.get(i + 2) & 0xFF & 0x0ff);
			}
		}

		return bufferedImage;
	}

	public final static void cleanup()
	{
		MemoryUtil.memFree(OpenGLScreenshot.getPixels());
		OpenGLScreenshot.setBufferedImage(null);
	}

	public final static ByteBuffer getPixels()
	{
		return OpenGLScreenshot.pixels;
	}

	public final static void setPixels(final ByteBuffer pixelsIn)
	{
		OpenGLScreenshot.pixels = pixelsIn;
	}

	public final static int getBPP()
	{
		return OpenGLScreenshot.bpp;
	}

	public final static BufferedImage getBufferedImage()
	{
		return OpenGLScreenshot.bufferedImage;
	}

	public final static void setBufferedImage(final BufferedImage bufferedImageIn)
	{
		OpenGLScreenshot.bufferedImage = bufferedImageIn;
	}
}
