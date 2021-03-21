package fr.seynax.onsiea.graphics.gui.inventory;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.onsiea.graphics.gui.inventory.SlideItem;
import fr.seynax.onsiea.gamelogic.item.Item;
import fr.seynax.onsiea.gamelogic.item.Rectangle;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.gui.IGui;
import fr.seynax.onsiea.graphics.input.CursorExtensionMenu;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiSlot implements IGui
{
	// Variables

	private Item				item;

	private final static int	SLOT_DEFAULT_TEXTURE_ID	= Texture.loadTexture("slot").getTextureId();
	private final static int	SLOT_HOVERED_TEXTURE_ID	= Texture.loadTexture("slot_hovered").getTextureId();
	private final static int	SLOT_CLICK_TEXTURE_ID	= Texture.loadTexture("slot_click").getTextureId();

	private int					slotActiveTextureId;

	private Rectangle			slotRectangle;
	private Rectangle			itemRectangle;

	// Constructor

	public GuiSlot()
	{
		this.setSlotRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
		this.setItemRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
	}

	public GuiSlot(final Item itemIn)
	{
		this.setItem(itemIn);
		this.setSlotRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
		this.setItemRectangle(new Rectangle(new Vector2f(), new Vector2f(1.0f, 1.0f)));
	}

	public GuiSlot(final Item itemIn, final Vector2f slotPositionIn, final Vector2f slotSizeIn,
			final Vector2f itemPositionIn, final Vector2f itemSizeIn)
	{
		this.setItem(itemIn);
		this.setSlotRectangle(new Rectangle(slotPositionIn, slotSizeIn));
		this.setItemRectangle(new Rectangle(itemPositionIn, itemSizeIn));
	}

	// Interface methods

	@Override
	public void initialization()
	{
		this.setSlotActiveTextureId(GuiSlot.SLOT_DEFAULT_TEXTURE_ID);
	}

	@Override
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

			if (windowIn.getGlfwEventManager().getCallbacksManager().getMouseButtonCallback()
					.isPress(GLFW.GLFW_MOUSE_BUTTON_1))
			{
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

	@Override
	public void draw(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		if (this.getItem() != null)
		{
			Texture.bind(this.getItem().getTexture().getTextureId());

			shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(new Vector3f(
					this.getSlotRectangle().getPosition().getX() + this.getItemRectangle().getPosition().getX(),
					this.getSlotRectangle().getPosition().getY() + this.getItemRectangle().getPosition().getY(), 0.0F),
					new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(this.getItemRectangle().getSize().getX(),
							this.getItemRectangle().getSize().getY(), 1.0F)));

			GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
		}

		Texture.bind(this.getSlotActiveTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(this.getSlotRectangle().getPosition().getX(), this.getSlotRectangle().getPosition().getY(),
						0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(this.getSlotRectangle().getSize().getX(),
						this.getSlotRectangle().getSize().getY(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);

		GL30.glBindVertexArray(0);
	}

	@Override
	public void cleanup()
	{
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

	private int getSlotActiveTextureId()
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
}