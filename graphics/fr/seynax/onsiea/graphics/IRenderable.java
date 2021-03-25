package fr.seynax.onsiea.graphics;

import fr.seynax.onsiea.graphics.shader.ShaderProgram;

public interface IRenderable<S extends ShaderProgram, O, R extends IRenderer<S, O>>
{
	R getRenderer();
}
