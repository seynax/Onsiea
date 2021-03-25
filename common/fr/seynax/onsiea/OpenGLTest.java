package fr.seynax.onsiea;

import fr.seynax.onsiea.game.DummyGame;
import fr.seynax.onsiea.gamelogic.GameEngine;
import fr.seynax.onsiea.utils.Logger;

public class OpenGLTest
{
	// Main method

	public static void main(final String[] argsIn)
	{
		final var logger = new Logger("resources/test.log", "[Main] ");

		logger.logErrLn("[ERROR] : testA");
		logger.logLn("[INFO] : testA1");
		logger.logErrLn("[ERROR] : testB");
		logger.logLn("[INFO] : testB1");
		logger.logErrLn("[ERROR] : testC");
		logger.logLn("[INFO] : testC1");
		logger.logErrLn("[ERROR] : testD");

		logger.write();

		try
		{
			final var gameEng = new GameEngine("Onsiea", 1280, 720, 60, true, 1, new DummyGame());

			gameEng.start();
		}
		catch (final Exception exception)
		{
			exception.printStackTrace();

			System.exit(-1);
		}
	}
}
