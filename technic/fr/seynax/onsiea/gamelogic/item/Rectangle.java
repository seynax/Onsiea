package fr.seynax.onsiea.gamelogic.item;

import org.joml.Vector2f;

public class Rectangle
{
	// Constructor variables

	private Vector2f	position;

	private Vector2f	size;

	// Variables

	private Vector2f	start;

	private Vector2f	end;

	// Constructor

	public Rectangle(final Vector2f positionIn, final Vector2f sizeIn)
	{
		// Constructor variable assigments

		this.setPosition(positionIn);
		this.setSize(sizeIn);

		// Variables assigments

		this.setStart(new Vector2f(positionIn.x() - sizeIn.x() * 0.5f, positionIn.y() - sizeIn.y() * 0.5f));
		this.setEnd(new Vector2f(positionIn.x() + sizeIn.x() * 0.5f, positionIn.y() + sizeIn.y() * 0.5f));
	}

	// Methods

	public boolean isIn(final double mouseXIn, final double mouseYIn)
	{
		return mouseXIn >= this.getStart().x() && mouseYIn >= this.getStart().y() && mouseXIn <= this.getEnd().x()
				&& mouseYIn <= this.getEnd().y();
	}

	public boolean isIn(final Vector2f mousePositionIn)
	{
		return mousePositionIn.x() >= this.getStart().x() && mousePositionIn.y() >= this.getStart().y()
				&& mousePositionIn.x() <= this.getEnd().x() && mousePositionIn.y() <= this.getEnd().y();
	}

	// Getter | Setter

	// Constructor variables

	public Vector2f getPosition()
	{
		return this.position;
	}

	public void setPosition(final Vector2f positionIn)
	{
		this.position = positionIn;
	}

	public Vector2f getSize()
	{
		return this.size;
	}

	public void setSize(final Vector2f sizeIn)
	{
		this.size = sizeIn;
	}

	// Variables

	public Vector2f getStart()
	{
		return this.start;
	}

	public void setStart(final Vector2f startIn)
	{
		this.start = startIn;
	}

	public Vector2f getEnd()
	{
		return this.end;
	}

	public void setEnd(final Vector2f endIn)
	{
		this.end = endIn;
	}
}