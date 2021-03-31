package fr.seynax.onsiea.opengl.renderer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.graphics.IRenderer;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.graphics.gui.inventory.GuiSlot;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.shader.ShaderGui;
import fr.seynax.onsiea.opengl.shader.ShaderProgram;
import fr.seynax.onsiea.utils.maths.Maths;

public class RendererGuiSlot extends RendererBase<ShaderGui, GuiSlot> implements IRenderer<ShaderGui, GuiSlot>
{
	@Override
	public void startDrawing(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		shaderGuiIn.start();
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn, final GuiSlot guiSlotIn)
	{
		if (guiSlotIn.getItem() != null)
		{
			Texture.bind(guiSlotIn.getItem().getTexture().getTextureId());

			shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(new Vector3f(
					guiSlotIn.getSlotRectangle().getPosition().x() + guiSlotIn.getItemRectangle().getPosition().x(),
					guiSlotIn.getSlotRectangle().getPosition().y() + guiSlotIn.getItemRectangle().getPosition().y(),
					0.0F), new Vector3f(0.0F, 0.0F, 0.0F),
					new Vector3f(guiSlotIn.getItemRectangle().getSize().x(), guiSlotIn.getItemRectangle().getSize().y(),
							1.0F)));

			GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
		}

		Texture.bind(guiSlotIn.getSlotActiveTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(guiSlotIn.getSlotRectangle().getPosition().x(),
						guiSlotIn.getSlotRectangle().getPosition().y(), 0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(guiSlotIn.getSlotRectangle().getSize().x(),
						guiSlotIn.getSlotRectangle().getSize().y(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
	}

	@Override
	public void endDrawing(final ShaderGui shaderProgramIn)
	{
		ShaderProgram.stop();

		GL30.glBindVertexArray(0);
	}
}