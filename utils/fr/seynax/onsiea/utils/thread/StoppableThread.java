package fr.seynax.onsiea.utils.thread;

import fr.seynax.onsiea.utils.OS;

public class StoppableThread implements IStoppableThread
{
	// Variables

	private boolean	running;

	private Thread	thread;

	private String	name;

	// Constructor

	public StoppableThread()
	{
		this.setName("THREAD-" + ThreadManager.addThreadNumber());
	}

	public StoppableThread(final String nameIn)
	{
		this.setName(nameIn);
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
		return false;
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
}
