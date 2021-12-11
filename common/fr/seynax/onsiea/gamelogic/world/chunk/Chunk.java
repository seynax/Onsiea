package fr.seynax.onsiea.gamelogic.world.chunk;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import fr.seynax.onsiea.game.block.Block;
import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.shader.BaseColourShader;
import fr.seynax.onsiea.utils.data.Serialization;

public class Chunk
{
	// Constructor variables

	private Vector3f				position;

	private Map<Vector3f, Block>	blocks;

	private Mesh					mesh;

	// private IGenerator generator;

	// Constructor

	public Chunk(final Vector3f positionIn)
	{
		this.setPosition(positionIn);

		this.setBlocks(new HashMap<>());
	}

	public Chunk(final float xIn, final float yIn, final float zIn)
	{
		this.setPosition(new Vector3f(xIn, yIn, zIn));

		this.setBlocks(new HashMap<>());
	}

	private Chunk(final float xIn, final float yIn, final float zIn, final Map<Vector3f, Block> blocksIn)
	{
		this.setPosition(new Vector3f(xIn, yIn, zIn));

		this.setBlocks(new HashMap<>());

		final var iterator = this.blocks.entrySet().iterator();

		while (iterator.hasNext())
		{
			final var entry = iterator.next();

			this.getBlocks().put(entry.getKey(), entry.getValue());
		}
	}

	// Methods

	public void initialization()
	{
		// Cubes * vertices * components (3D)

		final var	verticesBuffer	= BufferUtils.createFloatBuffer(this.getBlocks().size() * 8 * 3);
		final var	texCoordsBuffer	= BufferUtils.createFloatBuffer(this.getBlocks().size() * 8 * 2);

		// Cubes * faces * triangles * vertices (3D)

		final var	indicesBuffer	= BufferUtils.createIntBuffer(this.getBlocks().size() * 6 * 2 * 3);

		final var	cube			= Shapes.getShapeCube();

		final var	iterator		= this.getBlocks().entrySet().iterator();

		while (iterator.hasNext())
		{
			final var	entry		= iterator.next();

			final var	vertices	= cube.getTranslated((int) entry.getKey().x(), (int) entry.getKey().y(),
					(int) entry.getKey().z());

			verticesBuffer.put(vertices);
			texCoordsBuffer.put(cube.getTextureCoordinates());
			indicesBuffer.put(cube.getIndices());
		}

		this.setMesh(new Mesh(verticesBuffer, texCoordsBuffer, indicesBuffer));

		/**
		 * MemoryUtil.memFree(verticesBuffer); MemoryUtil.memFree(texCoordsBuffer);
		 * MemoryUtil.memFree(indicesBuffer);
		 **/
	}

	public void update()
	{

	}

	public void draw(final BaseColourShader baseColourShaderIn)
	{
		this.getMesh().render();
	}

	public void cleanup(final String filePathIn)
	{
		this.save(filePathIn);

		this.getBlocks().clear();
	}

	void setPosition(final int xIn, final int yIn, final int zIn)
	{
		this.getPosition().x	= xIn;
		this.getPosition().y	= yIn;
		this.getPosition().z	= zIn;
	}

	public boolean save(final String filePathIn)
	{
		try
		{
			final var save = new Save(this);

			Serialization.serialization(filePathIn, save);
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public final static Chunk load(final String filePathIn)
	{
		final var file = new File(filePathIn);

		if (!file.exists())
		{
			return null;
		}

		try
		{
			final var save = (Save) Serialization.deSerialization(filePathIn);

			if (save == null)
			{
				return null;
			}

			return new Chunk(save.getPosition().x(), save.getPosition().y(), save.getPosition().z(), save.getBlocks());
		}
		catch (final ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// Getter | Setter

	public Vector3f getPosition()
	{
		return this.position;
	}

	private void setPosition(final Vector3f positionIn)
	{
		this.position = positionIn;
	}

	Map<Vector3f, Block> getBlocks()
	{
		return this.blocks;
	}

	private void setBlocks(final Map<Vector3f, Block> blocksIn)
	{
		this.blocks = blocksIn;
	}

	private Mesh getMesh()
	{
		return this.mesh;
	}

	private void setMesh(final Mesh meshIn)
	{
		this.mesh = meshIn;
	}

	final static class Save implements Serializable
	{
		/**
		 *
		 */
		private static final long		serialVersionUID	= 1L;

		private Vector3f				position;

		private Map<Vector3f, Block>	blocks;

		public Save(final Chunk chunkIn)
		{
			this.setPosition(
					new Vector3f(chunkIn.getPosition().x(), chunkIn.getPosition().y(), chunkIn.getPosition().z()));

			this.setBlocks(new HashMap<>());

			final var iterator = this.getBlocks().entrySet().iterator();

			while (iterator.hasNext())
			{
				final var entry = iterator.next();

				this.getBlocks().put(entry.getKey(), entry.getValue());
			}

		}

		Vector3f getPosition()
		{
			return this.position;
		}

		private void setPosition(final Vector3f positionIn)
		{
			this.position = positionIn;
		}

		Map<Vector3f, Block> getBlocks()
		{
			return this.blocks;
		}

		private void setBlocks(final Map<Vector3f, Block> blocksIn)
		{
			this.blocks = blocksIn;
		}
	}
}