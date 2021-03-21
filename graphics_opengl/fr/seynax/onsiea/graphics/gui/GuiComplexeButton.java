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

public class GuiComplexeButton implements IGui
{
	// Variables

	private GuiElementButton button;

	// Constructor

	public GuiComplexeButton()
	{
	}

	// Methods

	@Override
	public void initialization()
	{
		this.setButton(new GuiElementButton());

		this.getButton().putTexture("default", Texture.loadTexture("gui").getTextureId());
		this.getButton().putTexture("colide", Texture.loadTexture("gui0").getTextureId());
		this.getButton().putTexture("press", Texture.loadTexture("gui1").getTextureId());
		this.getButton().putTexture("repeat", Texture.loadTexture("gui2").getTextureId());
		this.getButton().putTexture("release", Texture.loadTexture("gui3").getTextureId());
	}

	private long	last;
	private long	latence	= 0L;
	private String	textureName;

	boolean			colide	= false;
	boolean			disable	= false;

	@Override
	public void update(final IWindow windowIn)
	{
		if (this.textureName != null)
		{
			if (this.disable)
			{
				if (System.nanoTime() - this.last >= this.latence)
				{
					this.textureName	= null;

					this.disable		= false;
				}
			}
			else
			{
				this.getButton().enableTexture(this.textureName);

				this.disable = true;
			}
		}
		else if (this.getButton().getRectangle()
				.isIn(windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getPosition().x() / 1920.0D * 2.0D - 1.0D,
						1.0D - windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
								.getPosition().y() / 1080.0D * 2.0D))
		{
			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_MOUSE_BUTTON_1))
			{
				if (this.colide)
				{
					this.textureName	= "repeat";

					this.latence		= 0L;

					this.last			= System.nanoTime();
				}
				else
				{
					this.textureName	= "press";

					this.latence		= 1_000_000_000L;

					this.colide			= true;

					this.last			= System.nanoTime();
				}
			}
			else
			{
				if (this.colide)
				{
					this.colide			= false;

					this.textureName	= "release";

					this.latence		= 1_000_000_000L;

					this.last			= System.nanoTime();
				}
				else
				{
					this.textureName = "colide";
				}
			}
		}
		else
		{
			this.textureName	= "default";

			this.latence		= 0L;

			this.last			= System.nanoTime();
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
}
