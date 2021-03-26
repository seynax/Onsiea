package fr.seynax.onsiea.utils;

import java.util.Calendar;

public class Date
{
	public final static String getDate()
	{
		return Calendar.getInstance().get(Calendar.MONTH) + 1 + "." + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ "." + Calendar.getInstance().get(Calendar.HOUR) + "." + Calendar.getInstance().get(Calendar.MINUTE)
				+ "." + (Calendar.getInstance().get(Calendar.SECOND) + 1);
	}
}