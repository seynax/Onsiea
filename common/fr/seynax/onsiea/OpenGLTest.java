package fr.seynax.onsiea;

import fr.seynax.onsiea.game.DummyGame;
import fr.seynax.onsiea.gamelogic.GameEngine;

public class OpenGLTest
{
	// Main method

	public static void main(final String[] argsIn)
	{
		try
		{
			final var gameEng = new GameEngine("Onsiea", 1920, 1080, 60, true, 1, new DummyGame());

			gameEng.start();
		}
		catch (final Exception exception)
		{
			exception.printStackTrace();

			System.exit(-1);
		}
	}
}
