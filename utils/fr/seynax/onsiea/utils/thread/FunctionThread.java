package fr.seynax.onsiea.utils.thread;

import fr.seynax.onsiea.utils.OS;

public class FunctionThread implements IStoppableThread
{
	// Variables

	private static int					threadNumber	= 0;

	private boolean						running;

	private Thread						thread;

	private String						name;

	private IThreadExecutionFunction	threadExecutionFunction;

	// Constructor

	public FunctionThread(final IThreadExecutionFunction threadExecutionFunctionIn)
	{
		this.setName("THREAD-" + FunctionThread.getThreadNumber());

		FunctionThread.setThreadNumber(FunctionThread.getThreadNumber() + 1);

		this.setThreadExecutionFunction(threadExecutionFunctionIn);
	}

	public FunctionThread(final String nameIn, final IThreadExecutionFunction threadExecutionFunctionIn)
	{
		this.setName(nameIn);

		this.setThreadExecutionFunction(threadExecutionFunctionIn);
	}

	// Interface methods

	@Override
	public Thread start()
	{
		this.setRunning(true);

		this.setThread(new Thread(this, this.getName()));

		if (OS.getOsName().contains("Mac"))
		{
			this.getThread().run();
		}
		else
		{
			this.getThread().start();
		}

		return this.getThread();
	}

	@Override
	public void run()
	{
		while (this.isRunning())
		{
			this.execute();
		}
	}

	@Override
	public void stop()
	{
		this.setRunning(false);
	}

	// Methods

	public boolean execute()
	{
		return this.getThreadExecutionFunction().execute();
	}

	// Static getter | setter

	public final static int getThreadNumber()
	{
		return FunctionThread.threadNumber;
	}

	public final static void setThreadNumber(final int threadNumberIn)
	{
		FunctionThread.threadNumber = threadNumberIn;
	}

	// Interface getter

	@Override
	public Thread getThread()
	{
		return this.thread;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	// Getter | Setter

	public boolean isRunning()
	{
		return this.running;
	}

	private void setRunning(final boolean runningIn)
	{
		this.running = runningIn;
	}

	private void setThread(final Thread threadIn)
	{
		this.thread = threadIn;
	}

	private void setName(final String nameIn)
	{
		this.name = nameIn;
	}

	public IThreadExecutionFunction getThreadExecutionFunction()
	{
		return this.threadExecutionFunction;
	}

	public void setThreadExecutionFunction(final IThreadExecutionFunction threadExecutionFunctionIn)
	{
		this.threadExecutionFunction = threadExecutionFunctionIn;
	}
}
