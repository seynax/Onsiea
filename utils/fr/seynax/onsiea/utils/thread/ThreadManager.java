package fr.seynax.onsiea.utils.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadManager
{
	// Variables

	private Map<String, IStoppableThread> threads;

	// Constructor

	public ThreadManager()
	{
		this.setThreads(new HashMap<>());
	}

	// Methods

	public void startAll()
	{
		for (final IStoppableThread stoppableThread : this.getThreads().values())
		{
			stoppableThread.start();
		}
	}

	public Thread start(final String threadNameIn)
	{
		final var stoppableThread = this.getStoppableThread(threadNameIn);

		if (stoppableThread == null)
		{
			return null;
		}

		stoppableThread.start();

		return stoppableThread.getThread();
	}

	public String put(final IThreadExecutionFunction threadExecutionFunctionIn)
	{
		final var functionThread = new FunctionThread(threadExecutionFunctionIn);

		this.getThreads().put(functionThread.getName(), functionThread);

		return functionThread.getName();
	}

	public void put(final String threadNameIn, final IThreadExecutionFunction threadExecutionFunctionIn)
	{
		this.getThreads().put(threadNameIn, new FunctionThread(threadNameIn, threadExecutionFunctionIn));
	}

	public void put(final IStoppableThread... threadsRunnableIn)
	{
		for (final IStoppableThread stoppableThread : threadsRunnableIn)
		{
			this.getThreads().put(stoppableThread.getName(), stoppableThread);
		}
	}

	public boolean remove(final String threadNameIn)
	{
		return this.getThreads().remove(threadNameIn) != null;
	}

	public Thread getThread(final String threadNameIn)
	{
		final var stoppableThread = this.getThreads().get(threadNameIn);

		if (stoppableThread != null)
		{
			return stoppableThread.getThread();
		}

		return null;
	}

	public IStoppableThread getStoppableThread(final String threadNameIn)
	{
		return this.getThreads().get(threadNameIn);
	}

	public void clear()
	{
		this.stop();

		this.getThreads().clear();
	}

	public boolean stopAll(final String threadNameIn)
	{
		final var stoppableThread = this.getStoppableThread(threadNameIn);

		if (stoppableThread == null)
		{
			return false;
		}

		stoppableThread.stop();

		return true;
	}

	public void stop()
	{
		for (final IStoppableThread stoppableThread : this.getThreads().values())
		{
			stoppableThread.stop();
		}
	}

	// Getter | Setter

	public Map<String, IStoppableThread> getThreads()
	{
		return this.threads;
	}

	public void setThreads(final Map<String, IStoppableThread> threadsIn)
	{
		this.threads = threadsIn;
	}
}