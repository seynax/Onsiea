package fr.seynax.onsiea.graphics.gui;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.gamelogic.item.TexturedRectangle;
import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.renderer.RendererGui;
import fr.seynax.onsiea.graphics.shader.ShaderGui;

public class Gui implements IRenderable<ShaderGui, Gui, RendererGui>
{
	// Constructor variables

	private RendererGui				renderer;

	private List<TexturedRectangle>	texturedRectangles;

	// Constructor

	public Gui(final RendererGui rendererIn)
	{
		this.setRenderer(rendererIn);

		this.setTexturedRectangles(new ArrayList<>());
	}

	public Gui(final RendererGui rendererIn, final TexturedRectangle... texturedRectanglesIn)
	{
		this.setRenderer(rendererIn);

		this.setTexturedRectangles(new ArrayList<>());

		for (final TexturedRectangle texturedRectangle : texturedRectanglesIn)
		{
			this.getTexturedRectangles().add(texturedRectangle);
		}
	}

	// Methods

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

	@Override
	public RendererGui getRenderer()
	{
		return this.renderer;
	}

	private void setRenderer(final RendererGui rendererIn)
	{
		this.renderer = rendererIn;
	}

}