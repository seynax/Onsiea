package fr.seynax.onsiea.utils;

public class OS
{
	// Constants

	private final static String OS_NAME = System.getProperty("os.name");

	// Static getter

	public final static String getOsName()
	{
		return OS.OS_NAME;
	}
}