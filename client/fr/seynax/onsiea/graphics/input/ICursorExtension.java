package fr.seynax.onsiea.graphics.input;

import fr.seynax.onsiea.graphics.IWindow;

public interface ICursorExtension
{
	// Update

	void initialization(final Cursor cursorIn);

	void update(Cursor cursorIn, IWindow windowIn, final long windowPointerIn, final double xposIn, final double yposIn,
			double intervalIn);

	// Type

	String getType();
}