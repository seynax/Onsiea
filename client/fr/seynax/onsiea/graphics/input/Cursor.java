package fr.seynax.onsiea.graphics.input;

import org.joml.Vector2d;

import fr.seynax.onsiea.graphics.IWindow;

public class Cursor
{
	// Constructor variables

	private ICursorExtension	cursorExtension;

	// Variables

	private Vector2d			position;

	private Vector2d			translation;

	// Constructor

	/**
	 * private Cursor() { this.setPosition(new Vector2d()); this.setTranslation(new
	 * Vector2d()); }
	 **/

	public Cursor(final ICursorExtension cursorExtensionIn)
	{
		this.setCursorExtension(cursorExtensionIn);

		this.setPosition(new Vector2d());
		this.setTranslation(new Vector2d());
	}

	// Methods

	public void setPosition(final double xIn, final double yIn)
	{
		this.getPosition().x	= xIn;
		this.getPosition().y	= yIn;
	}

	public void setTranslation(final double xIn, final double yIn)
	{
		this.getTranslation().x	= yIn;
		this.getTranslation().y	= xIn;
	}

	// Methods

	public void initialization(final IWindow windowIn, final double intervalIn)
	{
		this.getCursorExtension().initialization(this);
	}

	public void update(final IWindow windowIn, final long windowPointerIn, final double xposIn, final double yposIn,
			final double intervalIn)
	{
		this.getCursorExtension().update(this, windowIn, windowPointerIn, xposIn, yposIn, intervalIn);
	}

	// Getter | Setter

	public Vector2d getPosition()
	{
		return this.position;
	}

	private void setPosition(final Vector2d positionIn)
	{
		this.position = positionIn;
	}

	public Vector2d getTranslation()
	{
		return this.translation;
	}

	private void setTranslation(final Vector2d translationIn)
	{
		this.translation = translationIn;
	}

	public ICursorExtension getCursorExtension()
	{
		return this.cursorExtension;
	}

	public void setCursorExtension(final ICursorExtension cursorExtensionIn)
	{
		this.cursorExtension = cursorExtensionIn;
	}
}
