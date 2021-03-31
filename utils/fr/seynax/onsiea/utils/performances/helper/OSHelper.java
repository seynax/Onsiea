package fr.seynax.onsiea.utils.performances.helper;

import fr.seynax.onsiea.utils.performances.Beans;

public class OSHelper
{
	public final static String getVersion()
	{
		return Beans.getOperatingSystem().getVersion();
	}

	public final static String getName()
	{
		return Beans.getOperatingSystem().getName();
	}

	public final static String getArch()
	{
		return Beans.getOperatingSystem().getArch();
	}
}
