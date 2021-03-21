package fr.seynax.onsiea.graphics.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.gamelogic.item.TexturedRectangle;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;

public class Gui implements IGui
{
	// Variables

	private List<TexturedRectangle> texturedRectangles;

	// Constructor

	public Gui()
	{
		this.setTexturedRectangles(new ArrayList<>());
	}

	public Gui(final TexturedRectangle... texturedRectanglesIn)
	{
		this.setTexturedRectangles(new ArrayList<>());

		for (final TexturedRectangle texturedRectangle : texturedRectanglesIn)
		{
			this.getTexturedRectangles().add(texturedRectangle);
		}
	}

	// Methods

	@Override
	public void initialization()
	{

	}

	public void addTexturedRectangle(final TexturedRectangle... texturedRectanglesIn)
	{
		for (final TexturedRectangle texturedRectangle : texturedRectanglesIn)
		{
			this.getTexturedRectangles().add(texturedRectangle);
		}
	}

	@Override
	public void update(final IWindow windowIn)
	{
		final var	start	= this.getTexturedRectangles().get(0).getStart();
		final var	end		= this.getTexturedRectangles().get(0).getEnd();

		final var	mouseX	= (float) (windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback()
				.getCursor().getPosition().x() * 2.0D / 1920.0D - 1.0D);
		final var	mouseY	= (float) (1.0D - windowIn.getGlfwEventManager().getCallbacksManager()
				.getCursorPosCallback().getCursor().getPosition().y() * 2.0D / 1080.0D);

		if (mouseX >= start.getX() && mouseY >= start.getY() && mouseX <= end.getX() && mouseY <= end.getY())
		{
			System.out.println("Colide !");
		}
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		for (final TexturedRectangle texturedRectangle : this.getTexturedRectangles())
		{
			Texture.bind(texturedRectangle.getTextureId());

			shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
					new Vector3f(texturedRectangle.getPosition().getX(), texturedRectangle.getPosition().getY(), 0.0F),
					new Vector3f(0.0F, 0.0F, 0.0F),
					new Vector3f(texturedRectangle.getSize().getX(), texturedRectangle.getSize().getY(), 1.0F)));

			GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
		}

		GL30.glBindVertexArray(0);
	}

	@Override
	public void cleanup()
	{

	}

	// Getter | Setter

	public List<TexturedRectangle> getTexturedRectangles()
	{
		return this.texturedRectangles;
	}

	public void setTexturedRectangles(final List<TexturedRectangle> texturedRectanglesIn)
	{
		this.texturedRectangles = texturedRectanglesIn;
	}
}