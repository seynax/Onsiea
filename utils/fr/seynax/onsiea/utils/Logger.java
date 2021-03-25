package fr.seynax.onsiea.utils;

/**
 *
 * @author Seynax Console AND file logger
 */
public class Logger
{
	// Variables

	private FileLogger		fileLogger;
	private ConsoleLogger	consoleLogger;

	// Constructor

	public Logger(final String pathIn)
	{
		this.setFileLogger(new FileLogger(pathIn));
		this.setConsoleLogger(new ConsoleLogger());
	}

	public Logger(final String pathIn, final String startTextIn)
	{
		this.setFileLogger(new FileLogger(pathIn, startTextIn));
		this.setConsoleLogger(new ConsoleLogger(startTextIn));
	}

	public Logger(final String pathIn, final String startTextIn, final String endTextIn)
	{
		this.setFileLogger(new FileLogger(pathIn, startTextIn, endTextIn));
		this.setConsoleLogger(new ConsoleLogger(startTextIn, endTextIn));
	}

	// Methods

	public void log(final Object... objectsIn)
	{
		this.getConsoleLogger().log(objectsIn);
		this.getFileLogger().log(objectsIn);
	}

	public void logLn(final Object... objectsIn)
	{
		this.getConsoleLogger().logLn(objectsIn);
		this.getFileLogger().logLn(objectsIn);
	}

	public void logErr(final Object... objectsIn)
	{
		this.getConsoleLogger().logErr(objectsIn);
		this.getFileLogger().log(objectsIn);
	}

	public void logErrLn(final Object... objectsIn)
	{
		this.getConsoleLogger().logErrLn(objectsIn);
		this.getFileLogger().logLn(objectsIn);
	}

	public boolean write()
	{
		return this.getFileLogger().write();
	}

	public boolean write(final String filepathIn)
	{
		return this.getFileLogger().write(filepathIn);
	}

	// Getter | Setter

	public FileLogger getFileLogger()
	{
		return this.fileLogger;
	}

	public void setFileLogger(final FileLogger fileLoggerIn)
	{
		this.fileLogger = fileLoggerIn;
	}

	public ConsoleLogger getConsoleLogger()
	{
		return this.consoleLogger;
	}

	public void setConsoleLogger(final ConsoleLogger consoleLoggerIn)
	{
		this.consoleLogger = consoleLoggerIn;
	}
}