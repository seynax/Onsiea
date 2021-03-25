package fr.seynax.onsiea.opengl.render;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import fr.seynax.onsiea.gamelogic.item.AnimatedItem;
import fr.seynax.onsiea.gamelogic.item.GameItem;
import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.opengl.shader.BaseColourShader;
import fr.seynax.onsiea.utils.maths.Maths;

public class Renderer
{
	// Variables

	private BaseColourShader shaderProgram;

	// Constants

	// Constructor

	public Renderer()
	{
	}

	// Methods

	public void initialization(final IWindow windowIn)
	{
		Maths.initialization(windowIn);

		this.setShaderProgram(new BaseColourShader());

		this.getShaderProgram().start();

		this.getShaderProgram().sendProjectionMatrix(Maths.getProjectionMatrix());

		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	public static void openglInitialization()
	{
		GL.createCapabilities();

		// Set the clear color

		GL11.glClearColor(GraphicsConstants.getDefaultColorR(), GraphicsConstants.getDefaultColorG(),
				GraphicsConstants.getDefaultColorB(), GraphicsConstants.getDefaultColorA());
	}

	public void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public final static void clearColor(final float rIn, final float gIn, final float bIn, final float aIn)
	{
		GL11.glClearColor(rIn, gIn, bIn, aIn);
	}

	public final static void clearColorByDefault()
	{
		GL11.glClearColor(GraphicsConstants.getDefaultColorR(), GraphicsConstants.getDefaultColorG(),
				GraphicsConstants.getDefaultColorB(), GraphicsConstants.getDefaultColorA());
	}

	public void startRendering(final IWindow windowIn)
	{
		this.clear();

		if (windowIn.getGlfwEventManager().getCallbacksManager().getFramebufferSizeCallback().isResized())
		{
			GL11.glViewport(0, 0, windowIn.getWidth(), windowIn.getHeight());

			windowIn.getGlfwEventManager().getCallbacksManager().getFramebufferSizeCallback().setResized(false);
		}
	}

	public void render(final IWindow window, final GameItem[] gameItems)
	{
		this.getShaderProgram().start();

		// Render each gameItem
		for (final GameItem gameItem : gameItems)
		{
			// Set world matrix for this item
			final var worldMatrix = Maths.getWorldMatrix(gameItem.getPosition(), gameItem.getRotation(),
					gameItem.getScale());

			this.getShaderProgram().sendTransformationMatrix(worldMatrix);

			// Render the mes for this game item
			gameItem.getMesh().render();
		}
	}

	public void render(final IWindow window, final AnimatedItem[] gameItems)
	{
		this.getShaderProgram().start();

		// Render each gameItem
		for (final AnimatedItem animatedItem : gameItems)
		{
			final var	gameItem	= animatedItem.getGameItem();

			// Set world matrix for this item

			final var	worldMatrix	= Maths.getWorldMatrix(gameItem.getPosition(), gameItem.getRotation(),
					gameItem.getScale());

			this.getShaderProgram().sendTransformationMatrix(worldMatrix);

			// Render the mes for this game item

			gameItem.getMesh().render();
		}
	}

	public void render(final IWindow windowIn, final GameItem gameItem)
	{
		this.getShaderProgram().start();

		// Set world matrix for this item

		final var worldMatrix = Maths.getWorldMatrix(gameItem.getPosition(), gameItem.getRotation(),
				gameItem.getScale());

		this.getShaderProgram().sendTransformationMatrix(worldMatrix);

		// Render the mes for this game item

		gameItem.getMesh().render();
	}

	public void cleanup()
	{
		if (this.getShaderProgram() != null)
		{
			this.getShaderProgram().cleanup();
		}
	}

	// Getter | Setter

	public BaseColourShader getShaderProgram()
	{
		return this.shaderProgram;
	}

	public void setShaderProgram(final BaseColourShader shaderProgramIn)
	{
		this.shaderProgram = shaderProgramIn;
	}

	// Constants getter

}