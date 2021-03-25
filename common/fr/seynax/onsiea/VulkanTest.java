package fr.seynax.onsiea;

import fr.seynax.onsiea.gamelogic.VulkanEngine;

public class VulkanTest
{
	// Main method

	public static void main(final String[] argsIn)
	{
		try
		{
			final var gameEng = new VulkanEngine();

			gameEng.start();
		}
		catch (final Exception exception)
		{
			exception.printStackTrace();

			System.exit(-1);
		}
	}
}