package fr.seynax.onsiea.graphics.gui;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.shader.ShaderGui;

public interface IGui
{
	void initialization();

	void update(final IWindow windowIn);

	void draw(final ShaderGui shaderGuiIn);

	void cleanup();
}