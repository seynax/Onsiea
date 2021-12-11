package fr.seynax.onsiea.graphics.gui;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.opengl.IGuiElementData;
import fr.seynax.onsiea.opengl.renderer.RendererGuiElement;
import fr.seynax.onsiea.opengl.shader.ShaderGui;

public class GuiComplexeButton extends GuiButton
		implements IGuiElementData, IRenderable<ShaderGui, IGuiElementData, RendererGuiElement>
{
	// Constructor

	public GuiComplexeButton(final RendererGuiElement rendererIn)
	{
		super(rendererIn);
	}

	public GuiComplexeButton(final RendererGuiElement rendererIn, final Vector2f positionIn, final Vector2f sizeIn)
	{
		super(rendererIn, positionIn, sizeIn);
	}

	// Methods

	@Override
	public void initialization()
	{
		this.putTexture("default", Texture.loadTexture("gui").getTextureId());
		this.putTexture("colide", Texture.loadTexture("gui0").getTextureId());
		this.putTexture("press", Texture.loadTexture("gui1").getTextureId());
		this.putTexture("repeat", Texture.loadTexture("gui2").getTextureId());
		this.putTexture("release", Texture.loadTexture("gui3").getTextureId());
	}

	private long	last;
	private long	latence	= 0L;
	private String	textureName;

	boolean			colide	= false;
	boolean			disable	= false;

	@Override
	public void update(final IWindow windowIn)
	{
		if (this.textureName != null)
		{
			if (this.disable)
			{
				if (System.nanoTime() - this.last >= this.latence)
				{
					this.textureName	= null;

					this.disable		= false;
				}
			}
			else
			{
				this.enableTexture(this.textureName);

				this.disable = true;
			}
		}
		else if (this.getRectangle()
				.isIn(windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getPosition().x() / 1920.0D * 2.0D - 1.0D,
						1.0D - windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
								.getPosition().y() / 1080.0D * 2.0D))
		{
			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_MOUSE_BUTTON_1))
			{
				if (this.colide)
				{
					this.textureName	= "repeat";

					this.latence		= 0L;

					this.last			= System.nanoTime();
				}
				else
				{
					this.textureName	= "press";

					this.latence		= 1_000_000_000L;

					this.colide			= true;

					this.last			= System.nanoTime();
				}
			}
			else
			{
				if (this.colide)
				{
					this.colide			= false;

					this.textureName	= "release";

					this.latence		= 1_000_000_000L;

					this.last			= System.nanoTime();
				}
				else
				{
					this.textureName = "colide";
				}
			}
		}
		else
		{
			this.textureName	= "default";

			this.latence		= 0L;

			this.last			= System.nanoTime();
		}
	}
}
