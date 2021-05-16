package fr.seynax.onsiea.gamelogic.world;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.gamelogic.logic.LogicConstants;
import fr.seynax.onsiea.gamelogic.world.chunk.Chunk;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.opengl.shader.BaseColourShader;

public class World
{
	// Methods

	private Map<Vector3f, Chunk>	chunks;
	private Floor					floor;

	private Texture					texture;

	// Constructor

	public World(final Mesh surfaceIn)
	{
		this.setChunks(new HashMap<>());

		this.setFloor(
				new Floor(LogicConstants.RenderDistance.getChooseRenderDistance() * LogicConstants.Chunk.getDistanceX(),
						LogicConstants.RenderDistance.getChooseRenderDistance() * LogicConstants.Chunk.getDistanceY(),
						LogicConstants.RenderDistance.getChooseRenderDistance() * LogicConstants.Chunk.getDistanceZ(),
						surfaceIn));

		this.setTexture(Texture.loadTexture("resources/textures/blocks/a"));
	}

	public void initialization(final Camera cameraIn)
	{
		for (var x = -LogicConstants.RenderDistance.getChooseRenderDistance(); x < LogicConstants.RenderDistance
				.getChooseRenderDistance(); x++)
		{
			for (var y = -LogicConstants.RenderDistance.getChooseRenderDistance(); y < LogicConstants.RenderDistance
					.getChooseRenderDistance(); y++)
			{
				for (var z = -LogicConstants.RenderDistance.getChooseRenderDistance(); z < LogicConstants.RenderDistance
						.getChooseRenderDistance(); z++)
				{
					final var	finalX	= (int) cameraIn.getPosition().x() + x;
					final var	finalY	= (int) cameraIn.getPosition().y() + y;
					final var	finalZ	= (int) cameraIn.getPosition().z() + z;

					final var	chunk	= this.loadChunkIfNotExist(finalX, finalY, finalZ);

					if (chunk == null)
					{
						continue;
					}
					chunk.initialization();
				}
			}
		}
	}

	public void update(final Camera cameraIn)
	{
		if (cameraIn.isHasMoveChunk())
		{
			this.getFloor().setPosition((int) cameraIn.getChunkPosition().x() * LogicConstants.Chunk.getMaxX(), 0,
					(int) cameraIn.getChunkPosition().z() * LogicConstants.Chunk.getMaxZ());

			this.updateChunks(cameraIn);
		}
	}

	private void updateChunks(final Camera cameraIn)
	{
		final var iterator = this.getChunks().entrySet().iterator();

		while (iterator.hasNext())
		{
			final var	entry		= iterator.next();

			final var	distanceX	= cameraIn.getPosition().x() - entry.getKey().x();
			final var	distanceY	= cameraIn.getPosition().y() - entry.getKey().y();
			final var	distanceZ	= cameraIn.getPosition().z() - entry.getKey().z();

			final var	distance	= distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;

			if (distance >= LogicConstants.RenderDistance.getDistance())
			{
				entry.getValue().cleanup(World.getFilePath(entry.getValue()));

				iterator.remove();
			}
		}

		for (var x = -LogicConstants.RenderDistance.getChooseRenderDistance(); x < LogicConstants.RenderDistance
				.getChooseRenderDistance(); x++)
		{
			for (var y = -LogicConstants.RenderDistance.getChooseRenderDistance(); y < LogicConstants.RenderDistance
					.getChooseRenderDistance(); y++)
			{
				for (var z = -LogicConstants.RenderDistance.getChooseRenderDistance(); z < LogicConstants.RenderDistance
						.getChooseRenderDistance(); z++)
				{
					final var	finalX	= (int) cameraIn.getPosition().x() + x;
					final var	finalY	= (int) cameraIn.getPosition().y() + y;
					final var	finalZ	= (int) cameraIn.getPosition().z() + z;

					final var	chunk	= this.loadChunkIfNotExist(finalX, finalY, finalZ);
					chunk.initialization();
				}
			}
		}
	}

	public void draw(final BaseColourShader baseColourShaderIn)
	{
		Texture.bind(this.getTexture().getTextureId());

		this.getFloor().draw(baseColourShaderIn);

		for (final Chunk chunk : this.getChunks().values())
		{
			/**
			 * final var distanceX = cameraIn.getPosition().x() - chunk.getPosition().x();
			 * final var distanceY = cameraIn.getPosition().y() - chunk.getPosition().y();
			 * final var distanceZ = cameraIn.getPosition().z() - chunk.getPosition().z();
			 *
			 * final var distance = distanceX * distanceX + distanceY * distanceY +
			 * distanceZ * distanceZ;
			 *
			 * if (distance >= LogicConstants.RenderDistance.getDistance() &&
			 * Frustum.isIn(chunk) {
			 **/

			chunk.draw(baseColourShaderIn);
		}
	}

	private Chunk loadChunkIfNotExist(final int xIn, final int yIn, final int zIn)
	{
		var chunk = this.getChunks().get(new Vector3f(xIn, yIn, zIn));

		if (chunk != null)
		{
			return chunk;
		}

		chunk = Chunk.load(World.getFilePath(xIn, yIn, zIn));

		if (chunk == null)
		{
			chunk = new Chunk(xIn, yIn, zIn);
		}

		this.getChunks().put(chunk.getPosition(), chunk);

		return chunk;
	}

	private final static String getFilePath(final int xIn, final int yIn, final int zIn)
	{
		return "resources/maps/test/chunks/" + xIn / LogicConstants.Region.getLengthX() + " "
				+ yIn / LogicConstants.Region.getLengthY() + " " + zIn / LogicConstants.Region.getLengthZ() + "/" + xIn
				+ " " + yIn + " " + zIn;
	}

	private final static String getFilePath(final Chunk chunkIn)
	{
		return "resources/maps/test/chunks/" + chunkIn.getPosition().x() / LogicConstants.Region.getLengthX() + " "
				+ chunkIn.getPosition().y() / LogicConstants.Region.getLengthY() + " "
				+ chunkIn.getPosition().z() / LogicConstants.Region.getLengthZ() + "/" + chunkIn.getPosition().x() + " "
				+ chunkIn.getPosition().y() + " " + chunkIn.getPosition().z();
	}

	public void cleanup()
	{
		for (final Chunk chunk : this.getChunks().values())
		{
			chunk.cleanup(World.getFilePath(chunk));
		}

		this.getChunks().clear();
	}

	// Getter | Setter

	private Map<Vector3f, Chunk> getChunks()
	{
		return this.chunks;
	}

	private void setChunks(final Map<Vector3f, Chunk> chunksIn)
	{
		this.chunks = chunksIn;
	}

	private Floor getFloor()
	{
		return this.floor;
	}

	private void setFloor(final Floor floorIn)
	{
		this.floor = floorIn;
	}

	private Texture getTexture()
	{
		return this.texture;
	}

	private void setTexture(final Texture textureIn)
	{
		this.texture = textureIn;
	}
}