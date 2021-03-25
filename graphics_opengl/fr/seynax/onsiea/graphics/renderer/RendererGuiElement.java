package fr.seynax.onsiea.graphics.renderer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.graphics.IRenderer;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.graphics.shader.ShaderProgram;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;

public class RendererGuiElement extends RendererBase<ShaderGui, IGuiElementData>
		implements IRenderer<ShaderGui, IGuiElementData>
{
	@Override
	public void startDrawing(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		shaderGuiIn.start();
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn, final IGuiElementData guiElementDataIn)
	{
		Texture.bind(guiElementDataIn.getTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(guiElementDataIn.getRectangle().getPosition().getX(),
						guiElementDataIn.getRectangle().getPosition().getY(), 0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(guiElementDataIn.getRectangle().getSize().getX(),
						guiElementDataIn.getRectangle().getSize().getY(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);

	}

	@Override
	public void endDrawing(final ShaderGui shaderProgramIn)
	{
		ShaderProgram.stop();

		GL30.glBindVertexArray(0);
	}
}