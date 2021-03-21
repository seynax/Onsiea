package fr.seynax.onsiea.gamelogic.item;

import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class TexturedRectangle
{
	// Constructor variables

	private int			textureId;

	// Variables

	private Rectangle	rectangle;

	// Constructor

	public TexturedRectangle(final Vector2f positionIn, final Vector2f sizeIn, final int textureIdIn)
	{
		this.setRectangle(new Rectangle(positionIn, sizeIn));

		this.setTextureId(textureIdIn);
	}

	// Delegated Methods of Rectangle

	public boolean isIn(final double mouseXIn, final double mouseYIn)
	{
		return this.rectangle.isIn(mouseXIn, mouseYIn);
	}

	public boolean isIn(final Vector2f mousePositionIn)
	{
		return this.rectangle.isIn(mousePositionIn);
	}

	public Vector2f getPosition()
	{
		return this.rectangle.getPosition();
	}

	public Vector2f getSize()
	{
		return this.rectangle.getSize();
	}

	public Vector2f getStart()
	{
		return this.rectangle.getStart();
	}

	public Vector2f getEnd()
	{
		return this.rectangle.getEnd();
	}

	// Getter | Setter

	// Constructor variables

	public int getTextureId()
	{
		return this.textureId;
	}

	public void setTextureId(final int textureIdIn)
	{
		this.textureId = textureIdIn;
	}

	// Variables

	public Rectangle getRectangle()
	{
		return this.rectangle;
	}

	public void setRectangle(final Rectangle rectangleIn)
	{
		this.rectangle = rectangleIn;
	}
}