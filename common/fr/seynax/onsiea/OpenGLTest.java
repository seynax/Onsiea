package fr.seynax.onsiea;

import fr.seynax.onsiea.game.DummyGame;
import fr.seynax.onsiea.game.GameEngine;

public class OpenGLTest
{
	// Main method

	public static void main(final String[] argsIn)
	{
		/**
		 * final var functionsThread = new FunctionsThread(null); functionsThread.add(()
		 * -> { System.out.println(System.nanoTime() / 1_000_000_000D + " : A");
		 * 
		 * return true; }, "a", 0); functionsThread.add(() -> {
		 * System.out.println(System.nanoTime() / 1_000_000_000D + " : B");
		 * 
		 * return true; }, "b", 2); functionsThread.add(() -> {
		 * System.out.println(System.nanoTime() / 1_000_000_000D + " : B");
		 * 
		 * return true; }, "c", 1); functionsThread.add(() -> {
		 * System.out.println(System.nanoTime() / 1_000_000_000D + " : B");
		 * 
		 * return true; }, "d", 0);
		 * 
		 * final var iterator = functionsThread.getFunctions().keyIterator(); while
		 * (iterator.hasNext()) { final var functionName = iterator.next();
		 * 
		 * System.out.println("Function name : " + functionName); }
		 **/

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
