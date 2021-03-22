# Onsiea

## About Onsiea

Onsiea is the name I gave to my **2D** and **3D game engine** created from scratch.

## Base of Onsiea

- under the "Java" language.
- using the LWJGL
- mainly supports GLFW, OpenGL, OpenAL and Vulkan.

## Architecture

The architecture might seem very busy at first glance but it makes sense and is done with the aim of separating the elements correctly, in order to better understand the code.

### Technic

The "technical" part manages, for example;
- physics,
- collisions,
- movements,
- data structures
- ...

In short, everything that is not graphic.

### Graphics

##### Common graphics
The graphics part focuses on the display of data on the screen, the management of optimizations and graphics parameters.

However, the engine must be able to handle OpenGL and Vulkan without repeating the code twice.

##### Specialied graphics

Thus graphics_opengl and graphics_vulkan contain all source code using in order OpenGL and Vulkan.

### Common

The common part is there to bring everything together, to allow communication between the technical part and the graphics part, this part will be very useful in the long term when multi-threading is implemented

### Utils


the utils part, contains all the tools not being dependent on the elements of the other parts of the engine.

That is to say that this part is completely independent and could be moved from one project to another without difficulty (by adding the LWJGL, if the desired tool needs it).

Here are some examples of tools:
- BufferHelper
 (help for creating and filling buffers under LWJGL)
- FileUtils (read the content of a file and receive a String, or a List <String>)
- ...

### Game

the "game" part is there to test the engine, this part is not part of the engine. It presents some examples of engine use. There can be multiple games.
