package fr.onsiea.graphics.gui.inventory;

import fr.seynax.onsiea.gamelogic.item.Items;
import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.renderer.RendererGuiInventory;
import fr.seynax.onsiea.graphics.renderer.RendererGuiSlot;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiInventory implements IRenderable<ShaderGui, GuiInventory, RendererGuiInventory>
{
	// Constructor variables

	private RendererGuiInventory	renderer;

	// Variables

	private GuiSlot					guiSlot0;

	private GuiSlot					guiSlot1;

	private boolean					isOpen;

	// Constructor

	public GuiInventory(final RendererGuiInventory rendererIn)
	{
		this.setRenderer(rendererIn);
	}

	// Interface methods

	public void initialization(final RendererGuiSlot rendererGuiSlotIn)
	{
		// GuiSlot0

		this.setGuiSlot0(new GuiSlot(rendererGuiSlotIn, Items.getItemTest(),
				new Vector2f(1 - 0.5f * 0.5f, -1 + 0.5f * 0.5f / 9 * 16), new Vector2f(0.25f, 0.25f / 9 * 16),
				new Vector2f(0.015f, 0.0f), new Vector2f(0.125f, 0.125f / 9 * 16)));
		this.getGuiSlot0().initialization();

		// GuiSlot1

		this.setGuiSlot1(new GuiSlot(rendererGuiSlotIn, null,
				new Vector2f(-1 + 0.25f * 0.5f, -1 + 0.25f * 0.5f / 9 * 16), new Vector2f(0.25f, 0.25f / 9 * 16),
				new Vector2f(-1 + 0.125f * 0.5f, -1 + 0.125f * 0.5f), new Vector2f(0.125f, 0.125f / 9 * 16)));
		this.getGuiSlot1().initialization();
	}

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

	public void cleanup()
	{
		this.getGuiSlot0().cleanup();
		this.getGuiSlot1().cleanup();
	}

	// Interface getter | setter

	@Override
	public RendererGuiInventory getRenderer()
	{
		return this.renderer;
	}

	// Getter | Setter

	public GuiSlot getGuiSlot0()
	{
		return this.guiSlot0;
	}

	private void setGuiSlot0(final GuiSlot guiSlot0In)
	{
		this.guiSlot0 = guiSlot0In;
	}

	public GuiSlot getGuiSlot1()
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

	public void setRenderer(final RendererGuiInventory rendererIn)
	{
		this.renderer = rendererIn;
	}
}
