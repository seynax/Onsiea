package fr.seynax.onsiea.utils.log;

public class ConsoleLogger
{
	// Variables

	private String	startText;

	private String	endText;

	// Constructor

	public ConsoleLogger()
	{
		this.setStartText("");
		this.setEndText("");
	}

	public ConsoleLogger(final String startTextIn)
	{
		this.setStartText(startTextIn);
		this.setEndText("");
	}

	public ConsoleLogger(final String startTextIn, final String endTextIn)
	{
		this.setStartText(startTextIn);
		this.setEndText(endTextIn);
	}

	// Static methods

	public final static void staticLog(final Object... objectsIn)
	{
		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}
	}

	public final static void staticLogLn(final Object... objectsIn)
	{
		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}

		System.out.println();
	}

	public final static void staticLogErrLn(final Object... objectsIn)
	{
		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}

		System.out.println();
	}

	public final static void staticLogErr(final Object... objectsIn)
	{
		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}
	}

	// Methods

	public void log(final Object... objectsIn)
	{
		System.out.print(this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}
		System.out.println(this.getEndText());
	}

	public void logLn(final Object... objectsIn)
	{
		System.out.print(this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			System.out.print(object + " ");
		}
		System.out.print(this.getEndText());
	}

	public void logErr(final Object... objectsIn)
	{
		System.err.print(this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			System.err.print(object + " ");
		}

		System.err.print(this.getEndText());
	}

	public void logErrLn(final Object... objectsIn)
	{
		System.err.print(this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			System.err.print(object + " ");
		}

		System.err.println(this.getEndText());
	}

	// Getter | Setter

	public String getStartText()
	{
		return this.startText;
	}

	public void setStartText(final String startTextIn)
	{
		this.startText = startTextIn;
	}

	public String getEndText()
	{
		return this.endText;
	}

	public void setEndText(final String endTextIn)
	{
		this.endText = endTextIn;
	}
}