package fr.seynax.onsiea.gamelogic.world.generator;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.gamelogic.LogicConstants;
import fr.seynax.onsiea.gamelogic.world.Element;
import fr.seynax.onsiea.gamelogic.world.ElementGroup;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.utils.maths.vector.Vector3f;

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

		for (var x = LogicConstants.getChunkMinX(); x < LogicConstants.getChunkMaxX(); x++)
		{
			for (var y = LogicConstants.getChunkMinY(); y < LogicConstants.getChunkMaxY(); y++)
			{
				for (var z = LogicConstants.getChunkMinZ(); z < LogicConstants.getChunkMaxZ(); z++)
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