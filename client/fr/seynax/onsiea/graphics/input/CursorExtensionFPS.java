package fr.seynax.onsiea.graphics.input;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;

public class CursorExtensionFPS implements ICursorExtension
{
	// Constructor variable

	private Vector2d	toReset;

	private boolean		canReset;

	// Constructor

	public CursorExtensionFPS()
	{
		// Constructor variables

		this.setToReset(new Vector2d());
		this.setCanReset(true);
	}

	public CursorExtensionFPS(final Vector2d toResetIn)
	{
		// Constructor variables

		this.setToReset(toResetIn);
		this.setCanReset(true);
	}

	public CursorExtensionFPS(final Vector2d toResetIn, final boolean isCanResetIn)
	{
		// Constructor variables

		this.setToReset(toResetIn);
		this.setCanReset(isCanResetIn);
	}

	// Methods

	// Interface methods

	@Override
	public void initialization(final Cursor cursorIn)
	{
		cursorIn.setPosition(this.getToReset().x(), this.getToReset().y());
	}

	@Override
	public void update(final Cursor cursorIn, final IWindow windowIn, final long windowPointerIn, final double xposIn,
			final double yposIn, final double intervalIn)
	{
		cursorIn.setTranslation(xposIn - cursorIn.getPosition().x(), yposIn - cursorIn.getPosition().y());

		if (this.isCanReset())
		{
			cursorIn.setPosition(this.getToReset().x(), this.getToReset().y());

			GLFW.glfwSetCursorPos(windowIn.getWindowHandle(), this.getToReset().x(), this.getToReset().y());
		}
		else
		{
			cursorIn.setPosition(xposIn, yposIn);
		}
	}

	@Override
	public String getType()
	{
		return "CURSOR-FPS";
	}

	// Getter | Setter

	public Vector2d getToReset()
	{
		return this.toReset;
	}

	public void setToReset(final Vector2d toResetIn)
	{
		this.toReset = toResetIn;
	}

	public boolean isCanReset()
	{
		return this.canReset;
	}

	public void setCanReset(final boolean canResetIn)
	{
		this.canReset = canResetIn;
	}
}