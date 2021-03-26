package fr.seynax.onsiea.utils;

import org.joml.Random;

public class Randomizer
{
	// Variables

	private final static Random random = new Random(Random.newSeed());

	// Methods

	public int nextInt(final int maxIn)
	{
		return Randomizer.random.nextInt(maxIn);
	}

	public int rand(final int minIn, final int maxIn)
	{
		return minIn + (int) (Math.random() * (maxIn - minIn + 1));
	}

	public float nextFloat(final int maxIn)
	{
		return Randomizer.random.nextFloat();
	}

	// Getter | Setter

	public static Random getRandom()
	{
		return Randomizer.random;
	}
}