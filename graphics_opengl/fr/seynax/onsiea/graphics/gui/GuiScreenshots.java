package fr.seynax.onsiea.graphics.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.gamelogic.item.TexturedRectangle;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.graphics.shader.ShaderScreenshot;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public class GuiScreenshots implements IGui
{
	// Variables

	private GuiButton			previousButton;

	private GuiButton			nextButton;

	private TexturedRectangle	screenshotsSurface;

	private List<Integer>		screenshots;

	private int					selectedScreenshot	= 0;

	private long				last;

	private boolean				isOpen;

	// Constructor

	public GuiScreenshots()
	{
		this.setPreviousButton(
				new GuiButton(new Vector2f(-1 + 0.5f * 0.25f, -1 + 0.125f * 0.5f), new Vector2f(0.25f, 0.125f)));
		this.setNextButton(
				new GuiButton(new Vector2f(1 - 0.5f * 0.25f, -1 + 0.125f * 0.5f), new Vector2f(0.25f, 0.125f)));
		this.setScreenshotsSurface(new TexturedRectangle(new Vector2f(0.0f, 0.0f), new Vector2f(1.0f, 1.0f),
				Texture.loadTexture("gui").getTextureId()));

		this.setScreenshots(new ArrayList<>());
	}

	// Methods

	public void addScreenshot(final int screenshotTextureId)
	{
		this.getScreenshots().add(screenshotTextureId);
	}

	@Override
	public void initialization()
	{
		this.getPreviousButton().initialization();

		this.getNextButton().initialization();
	}

	@Override
	public void update(final IWindow windowIn)
	{
		if (System.nanoTime() - this.getLast() > 8_500_000_0L)
		{
			this.setLast(System.nanoTime());

			this.getPreviousButton().update(windowIn);

			this.getNextButton().update(windowIn);

			if (this.getPreviousButton().isPress())
			{
				this.getPreviousButton().setPress(false);

				this.setSelectedScreenshot(this.getSelectedScreenshot() - 1);

				if (this.getSelectedScreenshot() < 0)
				{
					if (this.getScreenshots().size() > 0)
					{
						this.setSelectedScreenshot(this.getScreenshots().size() - 1);
					}
					else
					{
						this.setSelectedScreenshot(0);
					}
				}

				System.out.println(this.getSelectedScreenshot());
			}

			if (this.getNextButton().isPress())
			{
				this.getNextButton().setPress(false);

				this.setSelectedScreenshot(this.getSelectedScreenshot() + 1);

				if (this.getSelectedScreenshot() >= this.getScreenshots().size())
				{
					this.setSelectedScreenshot(0);
				}

				System.out.println(this.getSelectedScreenshot());
			}
		}
	}

	@Override
	public void draw(final ShaderGui shaderGuiIn)
	{
		// PreviousButton

		this.getPreviousButton().draw(shaderGuiIn);

		// NextButton

		this.getNextButton().draw(shaderGuiIn);
	}

	public void draw(final ShaderGui shaderGuiIn, final ShaderScreenshot shaderScreenshotIn)
	{
		// Screenshots surfaces

		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		if (this.getScreenshots().size() <= 0)
		{
			shaderGuiIn.start();

			this.draw(shaderGuiIn, this.getScreenshotsSurface().getPosition(), this.getScreenshotsSurface().getSize(),
					this.getScreenshotsSurface().getTextureId());
		}
		else
		{
			shaderScreenshotIn.start();

			final int textureId = this.getScreenshots().get(this.getSelectedScreenshot());

			this.draw(shaderScreenshotIn, this.getScreenshotsSurface().getPosition(),
					this.getScreenshotsSurface().getSize(), textureId);
		}

		GL30.glBindVertexArray(0);
	}

	private void draw(final ShaderGui shaderGuiIn, final Vector2f positionIn, final Vector2f sizeIn,
			final int textureIdIn)
	{
		Texture.bind(textureIdIn);

		shaderGuiIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new org.joml.Vector3f(positionIn.getX(), positionIn.getY(), 0.0F),
				new org.joml.Vector3f(0.0F, 0.0F, 0.0F), new org.joml.Vector3f(sizeIn.getX(), sizeIn.getY(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
	}

	private void draw(final ShaderScreenshot shaderScreenshotIn, final Vector2f positionIn, final Vector2f sizeIn,
			final int textureIdIn)
	{
		Texture.bind(textureIdIn);

		shaderScreenshotIn.sendTransformationMatrix(Maths.getWorldMatrix(
				new org.joml.Vector3f(positionIn.getX(), positionIn.getY(), 0.0F),
				new org.joml.Vector3f(0.0F, 0.0F, 0.0F), new org.joml.Vector3f(sizeIn.getX(), sizeIn.getY(), 1.0F)));

		GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT, 0);
	}

	@Override
	public void cleanup()
	{
		this.getPreviousButton().cleanup();

		this.getNextButton().cleanup();
	}

	// Getter | Setter

	public GuiButton getPreviousButton()
	{
		return this.previousButton;
	}

	public void setPreviousButton(final GuiButton previousButtonIn)
	{
		this.previousButton = previousButtonIn;
	}

	public GuiButton getNextButton()
	{
		return this.nextButton;
	}

	public void setNextButton(final GuiButton nextButtonIn)
	{
		this.nextButton = nextButtonIn;
	}

	public TexturedRectangle getScreenshotsSurface()
	{
		return this.screenshotsSurface;
	}

	public void setScreenshotsSurface(final TexturedRectangle screenshotsSurfaceIn)
	{
		this.screenshotsSurface = screenshotsSurfaceIn;
	}

	public List<Integer> getScreenshots()
	{
		return this.screenshots;
	}

	public void setScreenshots(final List<Integer> screenshotsIn)
	{
		this.screenshots = screenshotsIn;
	}

	public int getSelectedScreenshot()
	{
		return this.selectedScreenshot;
	}

	public void setSelectedScreenshot(final int selectedScreenshotIn)
	{
		this.selectedScreenshot = selectedScreenshotIn;
	}

	public long getLast()
	{
		return this.last;
	}

	public void setLast(final long lastIn)
	{
		this.last = lastIn;
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