package fr.onsiea.graphics.gui.inventory;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.gamelogic.item.Item;
import fr.seynax.onsiea.gamelogic.item.Rectangle;
import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.input.CursorExtensionMenu;
import fr.seynax.onsiea.graphics.renderer.RendererGuiSlot;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiSlot implements IRenderable<ShaderGui, GuiSlot, RendererGuiSlot>
{
	// Constructor variables

	private RendererGuiSlot		renderer;

	private Item				item;

	// Variables

	private final static int	SLOT_DEFAULT_TEXTURE_ID	= Texture.loadTexture("slot").getTextureId();
	private final static int	SLOT_HOVERED_TEXTURE_ID	= Texture.loadTexture("slot_hovered").getTextureId();
	private final static int	SLOT_CLICK_TEXTURE_ID	= Texture.loadTexture("slot_click").getTextureId();

	private int					slotActiveTextureId;

	private Rectangle			slotRectangle;
	private Rectangle			itemRectangle;

	private long				lastClickTime;

	// Constructor

	public GuiSlot(final RendererGuiSlot rendererIn)
	{
		this.setRenderer(rendererIn);

		this.setSlotRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
		this.setItemRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
	}

	public GuiSlot(final RendererGuiSlot rendererIn, final Item itemIn)
	{
		this.setRenderer(rendererIn);

		this.setItem(itemIn);
		this.setSlotRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
		this.setItemRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
	}

	public GuiSlot(final RendererGuiSlot rendererIn, final Item itemIn, final Vector2f slotPositionIn,
			final Vector2f slotSizeIn, final Vector2f itemPositionIn, final Vector2f itemSizeIn)
	{
		this.setRenderer(rendererIn);

		this.setItem(itemIn);

		this.setSlotRectangle(new Rectangle(slotPositionIn, slotSizeIn));
		this.setItemRectangle(new Rectangle(itemPositionIn, itemSizeIn));
	}

	// Interface methods

	public void initialization()
	{
		this.setSlotActiveTextureId(GuiSlot.SLOT_DEFAULT_TEXTURE_ID);
	}

	public void update(final IWindow windowIn)
	{
		final var	cursor	= windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor();
		final var	normX	= windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
				.getPosition().x() / 1920.0D * 2.0D - 1.0D;
		final var	normY	= 1.0D - windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback()
				.getCursor().getPosition().y() / 1080.0D * 2.0D;

		if (this.getSlotRectangle().isIn(normX, normY))
		{
			this.setSlotActiveTextureId(GuiSlot.getSlotHoveredTextureId());

			var timeHasClick = windowIn.getGlfwEventManager().getCallbacksManager().getMouseButtonCallback()
					.getTimeIsPress(GLFW.GLFW_MOUSE_BUTTON_1);

			if (timeHasClick < 0)
			{
				timeHasClick = windowIn.getGlfwEventManager().getCallbacksManager().getMouseButtonCallback()
						.getTimeIsHasPress(GLFW.GLFW_MOUSE_BUTTON_1);
			}

			if (timeHasClick > 0 && timeHasClick != this.getLastClickTime())
			{
				this.setLastClickTime(timeHasClick);

				this.setSlotActiveTextureId(GuiSlot.getSlotClickTextureId());

				final var cursorExtension = cursor.getCursorExtension();

				if (cursorExtension instanceof CursorExtensionMenu)
				{
					final var cursorExtensionMenu = (CursorExtensionMenu) cursorExtension;
					if (cursorExtensionMenu.getMainCursorElement() != null)
					{
						final var mainCursorElement = cursorExtensionMenu.getMainCursorElement();

						if (mainCursorElement instanceof SlideItem)
						{
							final var	slideItem	= (SlideItem) mainCursorElement;

							final var	item		= slideItem.getItem();

							if (item != null)
							{
								this.setItem(item);

								cursorExtensionMenu.setMainCursorElement(null);
							}
						}
					}
					else
					{
						if (this.getItem() != null)
						{
							final var slideItem = new SlideItem();

							/**
							 * slideItem.setStartPosition((float) normX -
							 * this.getItemRectangle().getPosition().getX(), (float) (normY -
							 * this.getItemRectangle().getPosition().getY()));
							 *
							 * slideItem.setPosition(this.getItemRectangle().getPosition().getX(),
							 * this.getItemRectangle().getPosition().getY());
							 **/

							slideItem.setStartPosition(0.0f, 0.0f);

							slideItem.setPosition((float) normX, (float) normY);

							slideItem.setSize(this.getItemRectangle().getSize().getX(),
									this.getItemRectangle().getSize().getY());

							slideItem.setItem(this.getItem());

							cursorExtensionMenu.setMainCursorElement(slideItem);

							this.setItem(null);
						}
					}
				}
			}
		}
		else
		{
			this.setSlotActiveTextureId(GuiSlot.getSlotDefaultTextureId());
		}
	}

	public void cleanup()
	{
	}

	// Interface getter | setter

	@Override
	public RendererGuiSlot getRenderer()
	{
		return this.renderer;
	}

	// Getter | Setter

	public Item getItem()
	{
		return this.item;
	}

	public void setItem(final Item itemIn)
	{
		this.item = itemIn;
	}

	public Rectangle getSlotRectangle()
	{
		return this.slotRectangle;
	}

	public void setSlotRectangle(final Rectangle slotRectangleIn)
	{
		this.slotRectangle = slotRectangleIn;
	}

	public Rectangle getItemRectangle()
	{
		return this.itemRectangle;
	}

	public void setItemRectangle(final Rectangle itemRectangleIn)
	{
		this.itemRectangle = itemRectangleIn;
	}

	public int getSlotActiveTextureId()
	{
		return this.slotActiveTextureId;
	}

	private void setSlotActiveTextureId(final int slotActiveTextureIdIn)
	{
		this.slotActiveTextureId = slotActiveTextureIdIn;
	}

	// Static getter | setter

	private static int getSlotDefaultTextureId()
	{
		return GuiSlot.SLOT_DEFAULT_TEXTURE_ID;
	}

	private static int getSlotHoveredTextureId()
	{
		return GuiSlot.SLOT_HOVERED_TEXTURE_ID;
	}

	private static int getSlotClickTextureId()
	{
		return GuiSlot.SLOT_CLICK_TEXTURE_ID;
	}

	private void setRenderer(final RendererGuiSlot rendererIn)
	{
		this.renderer = rendererIn;
	}

	public long getLastClickTime()
	{
		return this.lastClickTime;
	}

	public void setLastClickTime(final long lastClickTimeIn)
	{
		this.lastClickTime = lastClickTimeIn;
	}
}