package fr.seynax.onsiea.graphics.renderer;

import org.lwjgl.opengl.GL30;

import fr.onsiea.graphics.gui.inventory.GuiInventory;
import fr.seynax.onsiea.graphics.IRenderer;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.graphics.shader.ShaderProgram;

public class RendererGuiInventory extends RendererBase<ShaderGui, GuiInventory>
		implements IRenderer<ShaderGui, GuiInventory>
{
	@Override
	public void startDrawing(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		shaderGuiIn.start();
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn, final GuiInventory guiInventoryIn)
	{
		guiInventoryIn.getGuiSlot0().getRenderer().draw(shaderGuiIn, guiInventoryIn.getGuiSlot0());
		guiInventoryIn.getGuiSlot1().getRenderer().draw(shaderGuiIn, guiInventoryIn.getGuiSlot1());
	}

	@Override
	public void endDrawing(final ShaderGui shaderProgramIn)
	{
		ShaderProgram.stop();

		GL30.glBindVertexArray(0);
	}
}