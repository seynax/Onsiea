package fr.seynax.onsiea.graphics.callbacks;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.input.Cursor;
import fr.seynax.onsiea.graphics.input.ICursorExtension;

public class CursorPosCallback implements GLFWCursorPosCallbackI
{
	// Constructor variables

	private IWindow	window;

	private double	interval;

	// Variables

	private Cursor	cursor;

	// Constructor

	public CursorPosCallback(final IWindow windowIn, final double intevalIn, final ICursorExtension cursorExtensionIn)
	{
		this.setWindow(windowIn);
		this.setInterval(intevalIn);
		this.setCursor(new Cursor(cursorExtensionIn));
	}

	// Methods

	public void initialization()
	{
		this.getCursor().initialization(this.getWindow(), this.getInterval());
	}

	// Interface methods

	@Override
	public void invoke(final long windowIn, final double xposIn, final double yposIn)
	{
		this.getCursor().update(this.getWindow(), windowIn, xposIn, yposIn, this.getInterval());
	}

	// Getter | Setter

	private IWindow getWindow()
	{
		return this.window;
	}

	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	private double getInterval()
	{
		return this.interval;
	}

	private void setInterval(final double intervalIn)
	{
		this.interval = intervalIn;
	}

	public Cursor getCursor()
	{
		return this.cursor;
	}

	private void setCursor(final Cursor cursorIn)
	{
		this.cursor = cursorIn;
	}
}