package fr.seynax.onsiea.graphics.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.opengl.renderer.RendererGuiElement;
import fr.seynax.onsiea.opengl.renderer.RendererGuiScreenshots;
import fr.seynax.onsiea.opengl.shader.ShaderGui;

public class GuiScreenshots implements IRenderable<ShaderGui, GuiScreenshots, RendererGuiScreenshots>
{
	// Constructor variables

	private RendererGuiScreenshots	renderer;

	// Variables

	private GuiButton				previousButton;

	private GuiButton				nextButton;

	private TexturedRectangle		screenshotsSurface;

	private List<Integer>			screenshots;

	private int						selectedScreenshot	= 0;

	private long					last;

	private boolean					isOpen;

	// Constructor

	public GuiScreenshots(final RendererGuiScreenshots RendererIn, final RendererGuiElement rendererGuiIn)
	{
		this.setRenderer(RendererIn);

		this.setPreviousButton(new GuiButton(rendererGuiIn, new Vector2f(-1 + 0.5f * 0.25f, -1 + 0.125f * 0.5f),
				new Vector2f(0.25f, 0.125f)));
		this.setNextButton(new GuiButton(rendererGuiIn, new Vector2f(1 - 0.5f * 0.25f, -1 + 0.125f * 0.5f),
				new Vector2f(0.25f, 0.125f)));
		this.setScreenshotsSurface(new TexturedRectangle(new Vector2f(0.0f, 0.0f), new Vector2f(1.0f, 1.0f),
				Texture.loadTexture("gui").getTextureId()));

		this.setScreenshots(new ArrayList<>());
	}

	// Methods

	public void addScreenshot(final int screenshotTextureId)
	{
		this.getScreenshots().add(screenshotTextureId);
	}

	public void initialization()
	{
		this.getPreviousButton().initialization();

		this.getNextButton().initialization();
	}

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

	@Override
	public RendererGuiScreenshots getRenderer()
	{
		return this.renderer;
	}

	private void setRenderer(final RendererGuiScreenshots rendererIn)
	{
		this.renderer = rendererIn;
	}
}