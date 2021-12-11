package fr.seynax.onsiea.gamelogic.world.generator;

import java.util.List;

import fr.seynax.onsiea.gamelogic.world.elements.ElementGroup;

public interface IGenerator
{
	void initialization();

	List<ElementGroup> generate(float xIn, float yIn, float zIn);

	void cleanup();
}