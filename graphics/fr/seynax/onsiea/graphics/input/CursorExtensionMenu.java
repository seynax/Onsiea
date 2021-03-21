package fr.seynax.onsiea.graphics.input;

import fr.seynax.onsiea.graphics.IWindow;

public class CursorExtensionMenu implements ICursorExtension
{
	// Variables

	private ICursorElement mainCursorElement;

	// Constructor

	public CursorExtensionMenu()
	{
	}

	// Methods

	// Interface methods

	@Override
	public void initialization(final Cursor cursorIn)
	{
	}

	@Override
	public void update(final Cursor cursorIn, final IWindow windowIn, final long windowPointerIn, final double xposIn,
			final double yposIn, final double intervalIn)
	{
		cursorIn.setTranslation(xposIn - cursorIn.getPosition().x(), yposIn - cursorIn.getPosition().y());
		cursorIn.setPosition(xposIn, yposIn);

		if (this.getMainCursorElement() != null)
		{
			this.getMainCursorElement().update(cursorIn, this, windowIn);
		}
	}

	@Override
	public String getType()
	{
		return "CURSOR-MENU";
	}

	// Getter | Setter

	public void setMainCursorElement(final ICursorElement mainCursorElementIn)
	{
		this.mainCursorElement = mainCursorElementIn;
	}

	public ICursorElement getMainCursorElement()
	{
		return this.mainCursorElement;
	}
}