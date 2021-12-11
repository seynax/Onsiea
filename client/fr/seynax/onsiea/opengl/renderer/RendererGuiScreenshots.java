package fr.seynax.onsiea.opengl.renderer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.graphics.IRenderer;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.graphics.gui.GuiScreenshots;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.shader.ShaderGui;
import fr.seynax.onsiea.opengl.shader.ShaderProgram;
import fr.seynax.onsiea.utils.maths.Maths;

public class RendererGuiScreenshots extends RendererBase<ShaderGui, GuiScreenshots>
		implements IRenderer<ShaderGui, GuiScreenshots>
{
	@Override
	public void startDrawing(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		shaderGuiIn.start();
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn, final GuiScreenshots guiScreenshots)
	{
		// PreviousButton

		Texture.bind(guiScreenshots.getPreviousButton().getTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(guiScreenshots.getPreviousButton().getRectangle().getPosition().x(),
						guiScreenshots.getPreviousButton().getRectangle().getPosition().y(), 0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F),
				new Vector3f(guiScreenshots.getPreviousButton().getRectangle().getSize().x(),
						guiScreenshots.getPreviousButton().getRectangle().getSize().y(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);

		// NextButton

		Texture.bind(guiScreenshots.getNextButton().getTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(guiScreenshots.getNextButton().getRectangle().getPosition().x(),
						guiScreenshots.getNextButton().getRectangle().getPosition().y(), 0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F),
				new Vector3f(guiScreenshots.getNextButton().getRectangle().getSize().x(),
						guiScreenshots.getNextButton().getRectangle().getSize().y(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);

		// Screenshots

		var textureId = guiScreenshots.getScreenshotsSurface().getTextureId();

		if (guiScreenshots.getScreenshots().size() > 0)
		{
			// shaderScreenshotIn.start();

			shaderGuiIn.start();

			textureId = guiScreenshots.getScreenshots().get(guiScreenshots.getSelectedScreenshot());

		}

		Texture.bind(textureId);

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new org.joml.Vector3f(guiScreenshots.getScreenshotsSurface().getPosition().x(),
						guiScreenshots.getScreenshotsSurface().getPosition().y(), 0.0F),
				new org.joml.Vector3f(0.0F, 0.0F, 0.0F),
				new org.joml.Vector3f(guiScreenshots.getScreenshotsSurface().getSize().x(),
						guiScreenshots.getScreenshotsSurface().getSize().y(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
	}

	@Override
	public void endDrawing(final ShaderGui shaderProgramIn)
	{
		ShaderProgram.stop();

		GL30.glBindVertexArray(0);
	}
}