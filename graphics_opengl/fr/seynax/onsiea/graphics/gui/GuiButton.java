package fr.seynax.onsiea.graphics.gui;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.gui.elements.GuiElementButton;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiButton implements IGui
{
	// Variables

	private GuiElementButton	button;

	private boolean				isPress;

	// Constructor

	public GuiButton()
	{
		this.setButton(new GuiElementButton());
	}

	public GuiButton(final Vector2f positionIn, final Vector2f sizeIn)
	{
		this.setButton(new GuiElementButton(positionIn, sizeIn));
	}

	// Methods

	@Override
	public void initialization()
	{
		this.getButton().putTexture("default", Texture.loadTexture("gui").getTextureId());
		this.getButton().putTexture("colide", Texture.loadTexture("gui0").getTextureId());
		this.getButton().putTexture("press", Texture.loadTexture("gui1").getTextureId());
	}

	@Override
	public void update(final IWindow windowIn)
	{
		this.setPress(false);

		if (this.getButton().getRectangle()
				.isIn(windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getPosition().x() / 1920.0D * 2.0D - 1.0D,
						1.0D - windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
								.getPosition().y() / 1080.0D * 2.0D))
		{
			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_MOUSE_BUTTON_1))
			{
				this.getButton().enableTexture("press");

				this.setPress(true);
			}
			else
			{
				this.getButton().enableTexture("colide");
			}
		}
		else
		{
			this.getButton().enableTexture("default");
		}
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn)
	{
		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		Texture.bind(this.getButton().getActiveTextureId());

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new Vector3f(this.getButton().getRectangle().getPosition().getX(),
						this.getButton().getRectangle().getPosition().getY(), 0.0F),
				new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(this.getButton().getRectangle().getSize().getX(),
						this.getButton().getRectangle().getSize().getY(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);

		GL30.glBindVertexArray(0);
	}

	@Override
	public void cleanup()
	{

	}

	// Getter | Setter

	public GuiElementButton getButton()
	{
		return this.button;
	}

	public void setButton(final GuiElementButton buttonIn)
	{
		this.button = buttonIn;
	}

	public boolean isPress()
	{
		return this.isPress;
	}

	public void setPress(final boolean isPressIn)
	{
		this.isPress = isPressIn;
	}
}
