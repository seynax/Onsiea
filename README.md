# Onsiea

## About Onsiea

Onsiea is the name I gave to my **2D** and **3D game engine** created from scratch.

## Base of Onsiea

- under the "Java" language.
- using the LWJGL
- mainly supports GLFW, OpenGL, OpenAL (and Vulkan [in progress]).

## Details

- The Vulkan management part of this engine is under development (and since everything takes longer with Vulkan ...), so without adding additional source code it would be impossible to make vulkan work in depth. (more or less further than a simple rectangle), do not hesitate to collaborate, it would be with great pleasure!

## Architecture

- The architecture might seem very busy at first glance but it makes sense and is done with the aim of separating the elements correctly, in order to better understand the code.

### Core

- The "core" is the heart of everything. The nerve center of the engine. This is made up of the “centralizing” part of the engine, ie the link between the “server / client” part and the more technical “physical, colisions, entities, movements” part, the "centralizer" will be very useful when you want to set up multithreading by separating these parts.

- It is also the entry point of the engine, where we can launch the game, for that it is necessary to transmit to the engine a game logic (game / fr.seynax.onsiea.game.DummyGame.java in the source code), Here is typically the launch code below which can be used in the main method of the main class with the following parameters in order: window width, height, framerate, vertical synchronization, sync number, instance of game logic


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

### Client

- Client part contains all sources about "resources, graphics, sounds ..."

##### Common graphics (client/ fr.seynax.onsiea.graphics)
- The graphics part focuses on the display of data on the screen, the management of optimizations and graphics parameters.

- However, the engine must be able to handle OpenGL and Vulkan without repeating the code twice.

##### Specialied graphics (client/ fr.seynax.onsiea.graphics.opengl or fr.seynax.onsiea.graphics.vulkan)

- Thus opengl and vulkan sub package contain all source code to use them.

### Utils

- the utils part, contains all the tools not being dependent on the elements of the other parts of the engine.

- That is to say that this part is completely independent and could be moved from one project to another without difficulty (by adding the LWJGL, if the desired tool needs it).

Here are some examples of tools:
- BufferHelper
 (help for creating and filling buffers under LWJGL)
- FileUtils (read the content of a file and receive a String, or a List <String>)
- ...

### Game

- the "game" part is there to test the engine, this part is not part of the engine. It presents some examples of engine use. There can be multiple games.
