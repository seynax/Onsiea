package fr.seynax.onsiea;

import fr.seynax.onsiea.game.VulkanEngine;

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
		}
	}
}