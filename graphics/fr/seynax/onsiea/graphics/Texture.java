package fr.seynax.onsiea.graphics;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Texture
{
	private final static Map<String, Texture>	textures		= new HashMap<>();

	private final static IntBuffer				intBuffer		= BufferUtils.createIntBuffer(1);

	private int									textureId;

	private int									width;
	private int									height;

	private final static String					DEFAULT_PATH	= "resources/textures/";

	public Texture(final int textureIdIn, final int widthIn, final int heightIn)
	{
		this.setTextureId(textureIdIn);
		this.setWidth(widthIn);
		this.setHeight(heightIn);
	}

	public final static String getFilePathOfTexture(final String filePathIn)
	{
		var filePath = filePathIn;

		if (!filePathIn.startsWith(Texture.DEFAULT_PATH))
		{
			filePath = Texture.DEFAULT_PATH + filePath;
		}

		if (!filePathIn.endsWith(".png"))
		{
			filePath = filePath + ".png";
		}

		return filePath;
	}

	public static Texture loadTexture(final String textureFilePathIn, final TextureData textureDataIn)
	{
		final var	textureFilePath	= Texture.getFilePathOfTexture(textureFilePathIn);

		Texture		texture			= null;

		if (Texture.getTextures().containsKey(textureFilePath))
		{
			texture = Texture.getTextures().get(textureFilePath);

			if (texture != null)
			{
				return texture;
			}
		}

		var			textureId	= 0;
		int			width;
		int			height;
		ByteBuffer	buffer;

		try (var stack = MemoryStack.stackPush())
		{
			final var	w			= stack.mallocInt(1);
			final var	h			= stack.mallocInt(1);
			final var	channels	= stack.mallocInt(1);

			final var	file		= new File(textureFilePath);
			final var	filePath	= file.getAbsolutePath();
			buffer = STBImage.stbi_load(filePath, w, h, channels, 4);

			if (buffer == null)
			{
				throw new Exception("Can't load file " + textureFilePath + " " + STBImage.stbi_failure_reason());
			}

			width		= w.get();
			height		= h.get();

			textureId	= GL11.glGenTextures();

			texture		= new Texture(textureId, width, height);

			Texture.getTextures().put(textureFilePath, texture);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

			// Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up
			// or down
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
			// range
			// Note: GL_CLAMP_TO_EDGE is part of GL12
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

			textureDataIn.setData(new byte[buffer.capacity()]);

			for (var i = 0; i < buffer.capacity(); i++)
			{
				textureDataIn.getData()[i] = buffer.get(i);
			}

			buffer.flip();

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, buffer);

			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

			STBImage.stbi_image_free(buffer);

			return texture;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static Texture loadTexture(final String textureFilePathIn)
	{
		final var	textureFilePath	= Texture.getFilePathOfTexture(textureFilePathIn);

		Texture		texture			= null;

		if (Texture.getTextures().containsKey(textureFilePath))
		{
			texture = Texture.getTextures().get(textureFilePath);
			if (texture != null)
			{
				return texture;
			}
		}

		var			textureId	= 0;
		int			width;
		int			height;
		ByteBuffer	buffer;
		try (var stack = MemoryStack.stackPush())
		{
			final var	w			= stack.mallocInt(1);
			final var	h			= stack.mallocInt(1);
			final var	channels	= stack.mallocInt(1);

			final var	file		= new File(textureFilePath);
			final var	filePath	= file.getAbsolutePath();

			STBImage.stbi_set_flip_vertically_on_load(true);
			buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
			if (buffer == null)
			{
				throw new Exception("Can't load file " + textureFilePath + " " + STBImage.stbi_failure_reason());
			}
			width		= w.get();
			height		= h.get();

			textureId	= GL11.glGenTextures();

			texture		= new Texture(textureId, width, height);

			Texture.getTextures().put(textureFilePath, texture);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

			// Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up
			// or down
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
			// range
			// Note: GL_CLAMP_TO_EDGE is part of GL12
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, buffer);

			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			STBImage.stbi_image_free(buffer);

			return texture;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static Texture loadTexture(final ByteBuffer bufferIn, final int widthIn, final int heightIn)
	{
		if (bufferIn == null)
		{
			return null;
		}

		var textureId = 0;

		textureId = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		// Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up
		// or down
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
		// range
		// Note: GL_CLAMP_TO_EDGE is part of GL12
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, widthIn, heightIn, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, bufferIn);

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		// STBImage.stbi_image_free(bufferIn);

		return new Texture(textureId, widthIn, heightIn);
	}

	public final static void send(final Texture textureIn, final byte[] dataIn)
	{
		final var byteBuffer = BufferUtils.createByteBuffer(dataIn.length);

		byteBuffer.put(dataIn);

		byteBuffer.flip();

		Texture.bind(textureIn.getTextureId());

		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, textureIn.getWidth(), textureIn.getHeight(), GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, byteBuffer);

		Texture.unbind();
	}

	public final static void bind(final int textureIdIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
	}

	public final static void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void active(final int idIn)
	{
		GL13.glActiveTexture(idIn);
	}

	public static void cleanUp()
	{
		final var texturesIdBuffer = MemoryUtil.memAllocInt(Texture.getTextures().size());
		for (final Texture texture : Texture.getTextures().values())
		{
			texturesIdBuffer.put(texture.getTextureId());
		}
		texturesIdBuffer.flip();

		GL11.glDeleteTextures(texturesIdBuffer);

		texturesIdBuffer.clear();

		MemoryUtil.memFree(texturesIdBuffer);

		Texture.getTextures().clear();
		Texture.getIntbuffer().clear();
	}

	public static void cleanUp(final int textureIdIn)
	{
		Texture.getIntbuffer().put(textureIdIn);
		Texture.getIntbuffer().flip();

		GL11.glDeleteTextures(Texture.getIntbuffer());

		Texture.getIntbuffer().clear();
	}

	public int getTextureId()
	{
		return this.textureId;
	}

	private void setTextureId(final int textureIdIn)
	{
		this.textureId = textureIdIn;
	}

	public int getWidth()
	{
		return this.width;
	}

	private void setWidth(final int widthIn)
	{
		this.width = widthIn;
	}

	public int getHeight()
	{
		return this.height;
	}

	private void setHeight(final int heightIn)
	{
		this.height = heightIn;
	}

	private static Map<String, Texture> getTextures()
	{
		return Texture.textures;
	}

	private static IntBuffer getIntbuffer()
	{
		return Texture.intBuffer;
	}
}
