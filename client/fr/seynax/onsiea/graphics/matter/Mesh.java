package fr.seynax.onsiea.graphics.matter;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh
{
	// Variables

	private int	vaoId;

	private int	vboId;

	private int	colourVboId;

	private int	textureCoordinatesId;

	private int	indicesId;

	private int	vertexCount;

	// Constructor

	public Mesh(final float[] positionsIn)
	{
		FloatBuffer verticesBuffer = null;
		try
		{
			this.setVaoId(GL30.glGenVertexArrays());
			GL30.glBindVertexArray(this.getVaoId());

			verticesBuffer = MemoryUtil.memAllocFloat(positionsIn.length);
			this.setVertexCount(positionsIn.length / 3);
			verticesBuffer.put(positionsIn).flip();
			this.setVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			this.setIndicesId(-1);

			GL30.glBindVertexArray(0);
		}
		finally
		{
			if (verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	public Mesh(final float[] positionsIn, final int[] indicesIn)
	{
		FloatBuffer verticesBuffer = null;
		try
		{
			this.setVaoId(GL30.glGenVertexArrays());
			GL30.glBindVertexArray(this.getVaoId());

			verticesBuffer = MemoryUtil.memAllocFloat(positionsIn.length);
			this.setVertexCount(positionsIn.length / 3);
			verticesBuffer.put(positionsIn).flip();
			this.setVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			final var indicesBuffer = MemoryUtil.memAllocInt(indicesIn.length);
			indicesBuffer.put(indicesIn).flip();
			this.setIndicesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getIndicesId());
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(indicesBuffer);

			this.setVertexCount(indicesIn.length);

			GL30.glBindVertexArray(0);
		}
		finally
		{
			if (verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	public Mesh(final float[] positionsIn, final float[] coloursIn, final int[] indicesIn)
	{
		FloatBuffer verticesBuffer = null;

		try
		{
			this.setVaoId(GL30.glGenVertexArrays());
			GL30.glBindVertexArray(this.getVaoId());

			verticesBuffer = MemoryUtil.memAllocFloat(positionsIn.length);
			verticesBuffer.put(positionsIn).flip();
			this.setVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// Colour VBO
			final var colourBuffer = MemoryUtil.memAllocFloat(coloursIn.length);
			colourBuffer.put(coloursIn).flip();
			this.setColourVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getColourVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colourBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(colourBuffer);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);

			final var indicesBuffer = MemoryUtil.memAllocInt(indicesIn.length);
			indicesBuffer.put(indicesIn).flip();
			this.setIndicesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getIndicesId());
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(indicesBuffer);

			this.setVertexCount(indicesIn.length);

			GL30.glBindVertexArray(0);
		}
		finally
		{
			if (verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	public Mesh(final float[] positionsIn, final float[] coloursIn, final float[] textureCoordinatessIn,
			final int[] indicesIn)
	{
		FloatBuffer verticesBuffer = null;
		try
		{
			this.setVaoId(GL30.glGenVertexArrays());
			GL30.glBindVertexArray(this.getVaoId());

			// Vertices

			verticesBuffer = MemoryUtil.memAllocFloat(positionsIn.length);
			verticesBuffer.put(positionsIn).flip();
			this.setVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// Texture Coordinates VBO

			final var textureCoordinatesBuffer = MemoryUtil.memAllocFloat(textureCoordinatessIn.length);
			textureCoordinatesBuffer.put(textureCoordinatessIn).flip();
			this.setTextureCoordinatesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getTextureCoordinatesId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(textureCoordinatesBuffer);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

			// Colour VBO

			final var colourBuffer = MemoryUtil.memAllocFloat(coloursIn.length);
			colourBuffer.put(coloursIn).flip();
			this.setColourVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getColourVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colourBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(colourBuffer);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);

			// Indices

			final var indicesBuffer = MemoryUtil.memAllocInt(indicesIn.length);
			indicesBuffer.put(indicesIn).flip();
			this.setIndicesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getIndicesId());
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(indicesBuffer);

			this.setVertexCount(indicesIn.length);

			GL30.glBindVertexArray(0);
		}
		finally
		{
			if (verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	// Methods

	public Mesh(final FloatBuffer verticesBufferIn, final FloatBuffer texCoordsBufferIn,
			final IntBuffer indicesBufferIn)
	{
		final FloatBuffer verticesBuffer = null;
		try
		{
			this.setVaoId(GL30.glGenVertexArrays());
			GL30.glBindVertexArray(this.getVaoId());

			// Vertices

			this.setVboId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBufferIn, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// Texture Coordinates VBO

			this.setTextureCoordinatesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getTextureCoordinatesId());
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordsBufferIn, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

			// Indices

			this.setIndicesId(GL15.glGenBuffers());
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getIndicesId());
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferIn, GL15.GL_STATIC_DRAW);
			this.setVertexCount(indicesBufferIn.remaining());

			GL30.glBindVertexArray(0);
		}
		finally
		{
			if (verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	public void render()
	{
		// Bind to the VAO

		GL30.glBindVertexArray(this.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		if (this.getIndicesId() > 0)
		{
			GL11.glDrawElements(GL11.GL_TRIANGLES, this.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		else
		{
			// Draw the vertices

			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.getVertexCount());
		}

		// Restore state

		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void cleanup()
	{
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);

		// Delete the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.getVboId());

		GL15.glDeleteBuffers(this.getColourVboId());
		GL15.glDeleteBuffers(this.getTextureCoordinatesId());
		GL15.glDeleteBuffers(this.getVboId());
		GL15.glDeleteBuffers(this.getIndicesId());

		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(this.getVaoId());
	}

	// Getter | Setter

	public int getVaoId()
	{
		return this.vaoId;
	}

	public void setVaoId(final int vaoIdIn)
	{
		this.vaoId = vaoIdIn;
	}

	public int getVboId()
	{
		return this.vboId;
	}

	public void setVboId(final int vboIdIn)
	{
		this.vboId = vboIdIn;
	}

	public int getColourVboId()
	{
		return this.colourVboId;
	}

	public void setColourVboId(final int colourVboIdIn)
	{
		this.colourVboId = colourVboIdIn;
	}

	public int getTextureCoordinatesId()
	{
		return this.textureCoordinatesId;
	}

	public void setTextureCoordinatesId(final int textureCoordinatesIdIn)
	{
		this.textureCoordinatesId = textureCoordinatesIdIn;
	}

	public int getIndicesId()
	{
		return this.indicesId;
	}

	public void setIndicesId(final int indicesIdIn)
	{
		this.indicesId = indicesIdIn;
	}

	public int getVertexCount()
	{
		return this.vertexCount;
	}

	public void setVertexCount(final int vertexCountIn)
	{
		this.vertexCount = vertexCountIn;
	}
}