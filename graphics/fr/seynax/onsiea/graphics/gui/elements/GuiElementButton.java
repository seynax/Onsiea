package fr.seynax.onsiea.graphics.gui.elements;

import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.gamelogic.item.Rectangle;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiElementButton implements IGuiElement
{
	// Variables

	private Rectangle				rectangle;

	private Map<String, Integer>	textures;

	private String					activeTextureName;

	private int						activeTextureId;

	// Constructor

	public GuiElementButton()
	{
		this.setRectangle(new Rectangle(new Vector2f(0.0f, 0.0f), new Vector2f(1.0f, 1.0f)));

		this.setTextures(new HashMap<>());
	}

	public GuiElementButton(final Vector2f positionIn, final Vector2f sizeIn)
	{
		this.setRectangle(new Rectangle(positionIn, sizeIn));

		this.setTextures(new HashMap<>());
	}

	// Methods

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

	// Interface methods

	@Override
	public String getActiveTextureName()
	{
		return this.activeTextureName;
	}

	@Override
	public int getActiveTextureId()
	{
		return this.activeTextureId;
	}

	@Override
	public Map<String, Integer> getTextures()
	{
		return this.textures;
	}

	@Override
	public Rectangle getRectangle()
	{
		return this.rectangle;
	}

	// Getter | Setter

	public void setRectangle(final Rectangle rectangleIn)
	{
		this.rectangle = rectangleIn;
	}

	public void setTextures(final Map<String, Integer> texturesIn)
	{
		this.textures = texturesIn;
	}

	public void setActiveTextureName(final String activeTextureNameIn)
	{
		this.activeTextureName = activeTextureNameIn;
	}

	public void setActiveTextureId(final int activeTextureIdIn)
	{
		this.activeTextureId = activeTextureIdIn;
	}
}