package fr.seynax.onsiea.graphics;

import fr.seynax.onsiea.opengl.shader.ShaderProgram;

public interface IRenderer<S extends ShaderProgram, R>
{
	void startDrawing(S shaderProgramIn, IRenderable<S, R, IRenderer<S, R>> renderableIn);

	void startDrawing(S shaderProgramIn);

	void draw(S shaderProgramIn, R renderablesIn);

	void draw(S shaderProgramIn, Iterable<R> renderablesIn);

	void draw(S shaderProgramIn, R[] renderablesIn);

	void draw(S shaderProgramIn, IRenderable<S, R, IRenderer<S, R>> renderableIn);

	void draw(S shaderProgramIn, IRenderable<S, R, IRenderer<S, R>>[] renderablesIn);

	void endDrawing(S shaderProgramIn);

	void endDrawing(S shaderProgramIn, IRenderable<S, R, IRenderer<S, R>> renderableIn);
}