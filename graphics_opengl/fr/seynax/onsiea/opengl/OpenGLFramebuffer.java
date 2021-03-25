package fr.seynax.onsiea.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

public class OpenGLFramebuffer
{
	// Variables

	private int	id;
	private int	textureId;
	private int	depthBufferId;

	// Constructor

	public OpenGLFramebuffer(final int widthIn, final int heightIn)
	{
		// FrameBuffer

		this.setId(GL30.glGenFramebuffers());

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.getId());

		this.setDepthBufferId(GL30.glGenRenderbuffers());

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.getDepthBufferId());

		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, 1920, 1080);

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				this.getDepthBufferId());

		// LoadTexture

		this.setTextureId(GL11.glGenTextures());

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.getTextureId());
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
				GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		// End loadTexture

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D,
				this.getTextureId(), 0);

		if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Frame buffer créer avec succés !");
		}
		else
		{
			System.err.println("La création du frame buffer a échoué !");
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	// Static methods

	public final static void unbind()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	// Methods

	public void clear()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.getId());

		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public void bind()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.getId());
	}

	public void start(final int xIn, final int yIn, final int widthIn, final int heightIn)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.getId());

		GL11.glViewport(xIn, yIn, widthIn, heightIn);
	}

	// Getter | Setter

	public int getId()
	{
		return this.id;
	}

	public void setId(final int idIn)
	{
		this.id = idIn;
	}

	public int getTextureId()
	{
		return this.textureId;
	}

	public void setTextureId(final int textureIdIn)
	{
		this.textureId = textureIdIn;
	}

	public int getDepthBufferId()
	{
		return this.depthBufferId;
	}

	public void setDepthBufferId(final int depthBufferIdIn)
	{
		this.depthBufferId = depthBufferIdIn;
	}
}
