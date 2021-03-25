package fr.seynax.onsiea.graphics.gui;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.gamelogic.item.Rectangle;
import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.opengl.IGuiElementData;
import fr.seynax.onsiea.opengl.renderer.RendererGui;
import fr.seynax.onsiea.opengl.shader.ShaderGui;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiButton implements IGuiElementData, IRenderable<ShaderGui, Gui, RendererGui>
{
	// Constructor variables

	private RendererGui				renderer;

	// Variables

	private Rectangle				rectangle;

	private Map<String, Integer>	textures;

	private String					activeTextureName;

	private int						activeTextureId;

	private boolean					isPress;

	// Constructor

	public GuiButton(final RendererGui rendererIn)
	{
		this.setRenderer(rendererIn);

		this.setRectangle(new Rectangle(new Vector2f(0.0f, 0.0f), new Vector2f(1.0f, 1.0f)));

		this.setTextures(new HashMap<>());
	}

	public GuiButton(final RendererGui rendererIn, final Vector2f positionIn, final Vector2f sizeIn)
	{
		this.setRenderer(rendererIn);

		this.setRectangle(new Rectangle(positionIn, sizeIn));

		this.setTextures(new HashMap<>());
	}

	// Methods

	public void initialization()
	{
		this.putTexture("default", Texture.loadTexture("gui").getTextureId());
		this.putTexture("colide", Texture.loadTexture("gui0").getTextureId());
		this.putTexture("press", Texture.loadTexture("gui1").getTextureId());
	}

	public void update(final IWindow windowIn)
	{
		this.setPress(false);

		if (this.getRectangle()
				.isIn(windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getPosition().x() / 1920.0D * 2.0D - 1.0D,
						1.0D - windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
								.getPosition().y() / 1080.0D * 2.0D))
		{
			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_MOUSE_BUTTON_1))
			{
				this.enableTexture("press");

				this.setPress(true);
			}
			else
			{
				this.enableTexture("colide");
			}
		}
		else
		{
			this.enableTexture("default");
		}
	}

	public boolean putTexture(final String textureNameIn, final int textureIdIn)
	{
		if (this.getTextures().containsKey(textureNameIn))
		{
			return false;
		}

		this.getTextures().put(textureNameIn, textureIdIn);

		return true;
	}

	public void enableTexture(final String textureNameIn)
	{
		if (this.getTextures().containsKey(textureNameIn))
		{
			this.setActiveTextureName(textureNameIn);

			this.setActiveTextureId(this.getTextures().get(textureNameIn));
		}
	}

	public void cleanup()
	{

	}

	// Interface getter | setter

	@Override
	public int getTextureId()
	{
		return this.activeTextureId;
	}

	@Override
	public RendererGui getRenderer()
	{
		return this.renderer;
	}

	@Override
	public Rectangle getRectangle()
	{
		return this.rectangle;
	}

	// Getter | Setter

	public void setRenderer(final RendererGui rendererIn)
	{
		this.renderer = rendererIn;
	}

	public void setRectangle(final Rectangle rectangleIn)
	{
		this.rectangle = rectangleIn;
	}

	public Map<String, Integer> getTextures()
	{
		return this.textures;
	}

	public void setTextures(final Map<String, Integer> texturesIn)
	{
		this.textures = texturesIn;
	}

	public String getActiveTextureName()
	{
		return this.activeTextureName;
	}

	public void setActiveTextureName(final String activeTextureNameIn)
	{
		this.activeTextureName = activeTextureNameIn;
	}

	public void setActiveTextureId(final int activeTextureIdIn)
	{
		this.activeTextureId = activeTextureIdIn;
	}

	public int getActiveTextureId()
	{
		return this.activeTextureId;
	}

	public boolean isPress()
	{
		return this.isPress;
	}

	public void setPress(final boolean isPressIn)
	{
		this.isPress = isPressIn;
	}
}
