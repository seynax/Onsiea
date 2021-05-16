package fr.seynax.onsiea.utils.thread;

import fr.seynax.onsiea.utils.IFunction;

public class FunctionThread extends StoppableThread implements IStoppableThread
{
	// Variables

	private IFunction threadExecutionFunction;

	// Constructor

	public FunctionThread(final IFunction threadExecutionFunctionIn)
	{
		super();

		this.setThreadExecutionFunction(threadExecutionFunctionIn);
	}

	public FunctionThread(final String nameIn, final IFunction threadExecutionFunctionIn)
	{
		super(nameIn);

		this.setThreadExecutionFunction(threadExecutionFunctionIn);
	}

	// Extends methods

	@Override
	public boolean execute()
	{
		return this.getThreadExecutionFunction().execute();
	}

	// Getter | Setter

	public IFunction getThreadExecutionFunction()
	{
		return this.threadExecutionFunction;
	}

	public void setThreadExecutionFunction(final IFunction threadExecutionFunctionIn)
	{
		this.threadExecutionFunction = threadExecutionFunctionIn;
	}
}
