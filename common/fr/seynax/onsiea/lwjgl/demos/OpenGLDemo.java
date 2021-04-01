package fr.seynax.onsiea.lwjgl.demos;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fr.seynax.onsiea.utils.performances.measurer.MeasurerFPS;

/**
 * Rendering a simple GL_TRIANGLE_STRIP grid.
 *
 * @author Kai Burjack
 */

public class OpenGLDemo
{

	private int width = 800, height = 600;

	private void run()
	{
		// Initialize GLFW and create window
		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		@SuppressWarnings("unused")
		GLFWKeyCallback				keyCallback;
		@SuppressWarnings("unused")
		GLFWFramebufferSizeCallback	fbCallback;
		final var					window	= GLFW.glfwCreateWindow(this.width, this.height,
				"Hello, triangle strip grid!", MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		GLFW.glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(final long window, final int key, final int scancode, final int action, final int mods)
			{
				if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
				{
					GLFW.glfwSetWindowShouldClose(window, true);
				}
			}
		});
		GLFW.glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(final long window, final int w, final int h)
			{
				if (w > 0 && h > 0)
				{
					OpenGLDemo.this.width	= w;
					OpenGLDemo.this.height	= h;
				}
			}
		});
		GLFW.glfwMakeContextCurrent(window);
		// HiDPI fix:
		try (var frame = MemoryStack.stackPush())
		{
			final var framebufferSize = frame.mallocInt(2);
			GLFW.nglfwGetFramebufferSize(window, MemoryUtil.memAddress(framebufferSize),
					MemoryUtil.memAddress(framebufferSize) + 4);
			this.width	= framebufferSize.get(0);
			this.height	= framebufferSize.get(1);
		}
		GLFW.glfwSwapInterval(0);

		// Tell LWJGL about an active OpenGL context:
		GL.createCapabilities();

		// Initialize some state
		GL11.glClearColor(0.3f, 0.45f, 0.72f, 1.0f);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glLoadIdentity();

		final int columns = 100, rows = 100;
		GL11.glOrtho(0, columns - 1, rows - 1, 0, -1, 1);

		// Build vertices
		final var	vertices	= new float[columns * rows * 2];
		var			i			= 0;
		for (var y = 0; y < rows; y++)
		{
			for (var x = 0; x < columns; x++)
			{
				vertices[i++]	= x;
				vertices[i++]	= y;
			}
		}

		// Build indices
		i = 0;
		final var indices = new int[(rows - 1) * (columns + 1) * 2];
		for (var y = 0; y < rows - 1; y++)
		{
			for (var x = 0; x < columns; x++)
			{
				indices[i++]	= y * columns + x;
				indices[i++]	= (y + 1) * columns + x;
			}
			if (y < this.height - 1)
			{
				indices[i++]	= (y + 2) * columns - 1;
				indices[i++]	= (y + 1) * columns;
			}
		}

		// Upload to buffer objects
		final int vbo = GL15.glGenBuffers(), ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0L);

		final var fpsUtils = new MeasurerFPS();
		fpsUtils.start();

		while (!GLFW.glfwWindowShouldClose(window))
		{
			GL11.glViewport(0, 0, this.width, this.height);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, indices.length, GL11.GL_UNSIGNED_INT, 0L);
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();

			final var FPS = fpsUtils.stop();

			if (FPS > 0)
			{
				GLFW.glfwSetWindowTitle(window, "FPS : " + FPS);
			}
		}
	}

	public static void main(final String[] args)
	{
		new OpenGLDemo().run();
	}
}
