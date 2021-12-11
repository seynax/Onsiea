package fr.seynax.onsiea.gamelogic.world.generator;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import fr.seynax.onsiea.gamelogic.logic.LogicConstants;
import fr.seynax.onsiea.gamelogic.world.elements.Element;
import fr.seynax.onsiea.gamelogic.world.elements.ElementGroup;
import fr.seynax.onsiea.graphics.Texture;

public class GeneratorPlane implements IGenerator
{
	@Override
	public void initialization()
	{

	}

	@Override
	public List<ElementGroup> generate(final float xIn, final float yIn, final float zIn)
	{
		final var	elementGroup	= new ElementGroup();
		final var	dirt			= new Element("dirt", Texture.loadTexture("dirt").getTextureId());

		for (var x = LogicConstants.Chunk.getMinX(); x < LogicConstants.Chunk.getMaxX(); x++)
		{
			for (var y = LogicConstants.Chunk.getMinY(); y < LogicConstants.Chunk.getMaxY(); y++)
			{
				for (var z = LogicConstants.Chunk.getMinZ(); z < LogicConstants.Chunk.getMaxZ(); z++)
				{
					elementGroup.add(new Vector3f(x + xIn, y + yIn, z + zIn), dirt);
				}
			}
		}

		final List<ElementGroup> elementGroups = new ArrayList<>();

		elementGroups.add(elementGroup);

		return elementGroups;
	}

	@Override
	public void cleanup()
	{
	}
}