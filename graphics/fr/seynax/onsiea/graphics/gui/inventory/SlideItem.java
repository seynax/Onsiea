package fr.seynax.onsiea.graphics.gui.inventory;

import fr.seynax.onsiea.gamelogic.item.Item;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.input.Cursor;
import fr.seynax.onsiea.graphics.input.ICursorElement;
import fr.seynax.onsiea.graphics.input.ICursorExtension;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class SlideItem implements ICursorElement
{
	// Variables

	private Vector2f	startPosition;

	private Vector2f	position;

	private Vector2f	rotation;

	private Vector2f	size;

	private Item		item;

	// Constructor

	public SlideItem()
	{
		this.setStartPosition(new Vector2f());
		this.setPosition(new Vector2f());
		this.setRotation(new Vector2f());
		this.setSize(new Vector2f(1.0f, 1.0f));
	}

	// Methods

	public void setStartPosition(final float xIn, final float yIn)
	{
		this.getStartPosition().x	= xIn;
		this.getStartPosition().y	= yIn;
	}

	// Inetrface methods

	@Override
	public void update(final Cursor cursorIn, final ICursorExtension cursorExtensionIn, final IWindow windowIn)
	{
		final var	normX	= (float) (windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback()
				.getCursor().getPosition().x() / 1920.0D * 2.0D - 1.0D);
		final var	normY	= (float) (1.0D - windowIn.getGlfwEventManager().getCallbacksManager()
				.getCursorPosCallback().getCursor().getPosition().y() / 1080.0D * 2.0D);

		this.setPosition(normX - this.getStartPosition().getX(), normY - this.getStartPosition().getY());
	}

	// Position

	@Override
	public void setPosition(final float xIn, final float yIn)
	{
		this.getPosition().x	= xIn;
		this.getPosition().y	= yIn;
	}

	@Override
	public void move(final Vector2f deltaIn)
	{
		this.getPosition().x	+= deltaIn.getX();
		this.getPosition().y	+= deltaIn.getY();
	}

	@Override
	public void move(final float deltaXIn, final float deltaYIn)
	{
		this.getPosition().x	+= deltaXIn;
		this.getPosition().y	+= deltaYIn;
	}

	// Rotation

	@Override
	public void setRotation(final float xIn, final float yIn)
	{
		this.getRotation().x	= xIn;
		this.getRotation().y	= yIn;
	}

	@Override
	public void rotate(final Vector2f deltaIn)
	{
		this.getRotation().x	+= deltaIn.getX();
		this.getRotation().y	+= deltaIn.getY();
	}

	@Override
	public void rotate(final float deltaXIn, final float deltaYIn)
	{
		this.getRotation().x	+= deltaXIn;
		this.getRotation().y	+= deltaYIn;
	}

	// Size

	@Override
	public void setSize(final float xIn, final float yIn)
	{
		this.getSize().x	= xIn;
		this.getSize().y	= xIn;
	}

	@Override
	public void resize(final Vector2f deltaIn)
	{
		this.getSize().x	+= deltaIn.getX();
		this.getSize().y	+= deltaIn.getY();
	}

	@Override
	public void resize(final float deltaXIn, final float deltaYIn)
	{
		this.getSize().x	+= deltaXIn;
		this.getSize().y	+= deltaYIn;
	}

	// Getter | Setter

	@Override
	public Vector2f getPosition()
	{
		return this.position;
	}

	@Override
	public void setPosition(final Vector2f positionIn)
	{
		this.position = positionIn;
	}

	@Override
	public void setRotation(final Vector2f rotationIn)
	{
		this.rotation = rotationIn;
	}

	@Override
	public Vector2f getRotation()
	{
		return this.rotation;
	}

	@Override
	public Vector2f getSize()
	{
		return this.size;
	}

	@Override
	public void setSize(final Vector2f sizeIn)
	{
		this.size = sizeIn;
	}

	public Item getItem()
	{
		return this.item;
	}

	public void setItem(final Item itemIn)
	{
		this.item = itemIn;
	}

	private Vector2f getStartPosition()
	{
		return this.startPosition;
	}

	private void setStartPosition(final Vector2f startPositionIn)
	{
		this.startPosition = startPositionIn;
	}
}