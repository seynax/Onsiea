package fr.seynax.onsiea.graphics;

import fr.seynax.onsiea.graphics.callbacks.GLFWEventManager;

public interface IWindow
{
	GLFWEventManager initialization(final double intervalIn);

	void updateRender();

	boolean windowShouldClose();

	// Getter | Setter

	// Constructor variables

	int getWidth();

	void updateWidth(final int widthIn);

	void setWidth(int widthIn);

	int getHeight();

	void updateHeight(final int heightIn);

	void setHeight(int widthIn);

	String getTitle();

	void updateTitle(final String titleIn);

	int getFramerate();

	void updateFramerate(final int framerateIn);

	boolean isSynchronized();

	void updateSynchronized(final boolean isSynchronizedIn);

	int getSync();

	void updateSync(final int syncIn);

	long getWindowHandle();

	boolean isFullscreen();

	void updateFullscreen(final boolean isFullscreenIn);

	void cleanup();

	// Variables

	GLFWEventManager getGlfwEventManager();
}