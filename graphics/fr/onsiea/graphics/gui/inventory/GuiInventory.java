package fr.onsiea.graphics.gui.inventory;

import fr.seynax.onsiea.gamelogic.item.Items;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.gui.IGui;
import fr.seynax.onsiea.graphics.gui.inventory.GuiSlot;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiInventory implements IGui
{
	// Variables

	private GuiSlot	guiSlot0;

	private GuiSlot	guiSlot1;

	private boolean	isOpen;

	// Constructor

	public GuiInventory()
	{

	}

	// Interface methods

	@Override
	public void initialization()
	{
		// GuiSlot0

		this.setGuiSlot0(new GuiSlot(Items.getItemTest(), new Vector2f(1 - 0.5f * 0.5f, -1 + 0.5f * 0.5f / 9 * 16),
				new Vector2f(0.25f, 0.25f / 9 * 16), new Vector2f(0.015f, 0.0f),
				new Vector2f(0.125f, 0.125f / 9 * 16)));
		this.getGuiSlot0().initialization();

		// GuiSlot1

		this.setGuiSlot1(new GuiSlot(null, new Vector2f(-1 + 0.25f * 0.5f, -1 + 0.25f * 0.5f / 9 * 16),
				new Vector2f(0.25f, 0.25f / 9 * 16), new Vector2f(-1 + 0.125f * 0.5f, -1 + 0.125f * 0.5f),
				new Vector2f(0.125f, 0.125f / 9 * 16)));
		this.getGuiSlot1().initialization();
	}

	@Override
	public void update(final IWindow windowIn)
	{
		// Position

		this.getGuiSlot0().getItemRectangle().getPosition().x	= 0.0f;
		this.getGuiSlot0().getItemRectangle().getPosition().y	= 0.0f;

		// Size

		this.getGuiSlot0().getItemRectangle().getSize().x		= 0.125f;
		this.getGuiSlot0().getItemRectangle().getSize().y		= 0.125f / 9 * 16;

		this.getGuiSlot0().update(windowIn);
		this.getGuiSlot1().update(windowIn);
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn)
	{
		this.getGuiSlot0().draw(shaderGuiIn);
		this.getGuiSlot1().draw(shaderGuiIn);
	}

	@Override
	public void cleanup()
	{
		this.getGuiSlot0().cleanup();
		this.getGuiSlot1().cleanup();
	}

	// Getter | Setter

	private GuiSlot getGuiSlot0()
	{
		return this.guiSlot0;
	}

	private void setGuiSlot0(final GuiSlot guiSlot0In)
	{
		this.guiSlot0 = guiSlot0In;
	}

	private GuiSlot getGuiSlot1()
	{
		return this.guiSlot1;
	}

	private void setGuiSlot1(final GuiSlot guiSlot1In)
	{
		this.guiSlot1 = guiSlot1In;
	}

	public boolean isOpen()
	{
		return this.isOpen;
	}

	public void setOpen(final boolean isOpenIn)
	{
		this.isOpen = isOpenIn;
	}
}
