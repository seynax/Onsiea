package fr.seynax.onsiea.graphics.renderer;

import fr.seynax.onsiea.graphics.IRenderable;
import fr.seynax.onsiea.graphics.IRenderer;
import fr.seynax.onsiea.graphics.shader.ShaderProgram;

public class RendererBase<S extends ShaderProgram, R> implements IRenderer<S, R>
{
	@Override
	public void startDrawing(final S shaderProgramIn, final IRenderable<S, R, IRenderer<S, R>> renderableIn)
	{
		renderableIn.getRenderer().startDrawing(shaderProgramIn, renderableIn);
	}

	@Override
	public void startDrawing(final S shaderProgramIn)
	{
		shaderProgramIn.start();
	}

	@Override
	public void draw(final S shaderProgramIn, final R renderablesIn)
	{

	}

	@Override
	public void draw(final S shaderProgramIn, final Iterable<R> renderablesIn)
	{
		for (final R renderable : renderablesIn)
		{
			this.draw(shaderProgramIn, renderable);
		}
	}

	@Override
	public void draw(final S shaderProgramIn, final R[] renderablesIn)
	{
		for (final R renderable : renderablesIn)
		{
			this.draw(shaderProgramIn, renderable);
		}
	}

	@Override
	public void draw(final S shaderProgramIn, final IRenderable<S, R, IRenderer<S, R>> renderableIn)
	{
		renderableIn.getRenderer().draw(shaderProgramIn, renderableIn);
	}

	@Override
	public void draw(final S shaderProgramIn, final IRenderable<S, R, IRenderer<S, R>>[] renderablesIn)
	{
		for (final var renderable : renderablesIn)
		{
			renderable.getRenderer().draw(shaderProgramIn, renderable);
		}
	}

	@Override
	public void endDrawing(final S shaderProgramIn)
	{
	}

	@Override
	public void endDrawing(final S shaderProgramIn, final IRenderable<S, R, IRenderer<S, R>> renderableIn)
	{
		renderableIn.getRenderer().endDrawing(shaderProgramIn, renderableIn);
	}
}