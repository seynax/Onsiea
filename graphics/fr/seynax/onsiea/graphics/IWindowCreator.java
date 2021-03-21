package fr.seynax.onsiea.graphics;

public interface IWindowCreator
{
	boolean isSupported();

	long create(int widthIn, int heightIn, String titleIn, long monitorIn, long shareIn);

	boolean initialization();

	boolean enableDebug();

	boolean disableDebug();
}
