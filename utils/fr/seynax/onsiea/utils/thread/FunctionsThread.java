package fr.seynax.onsiea.utils.thread;

import fr.seynax.onsiea.utils.IFunction;
import fr.seynax.onsiea.utils.Pair;
import fr.seynax.onsiea.utils.data.NodeMap;

public class FunctionsThread extends StoppableThread implements IStoppableThread
{
	// Variables

	private NodeMap<String, Pair<Boolean, IFunction>> functions;

	// Constructor

	public FunctionsThread()
	{
		super();

		this.setFunctions(new NodeMap<>());
	}

	public FunctionsThread(final String nameIn)
	{
		super(nameIn);

		this.setFunctions(new NodeMap<>());
	}

	// Methods

	// Put previosu orderIn
	public boolean add(final IFunction functionIn, final String functionNameIn, final int orderIn)
	{
		if (this.getFunctions().containsKey(functionNameIn))
		{
			this.getFunctions().remove(functionNameIn);
		}

		final var pair = new Pair<>(true, functionIn);

		if (!this.getFunctions().putPrevious(orderIn, functionNameIn, pair))
		{
			this.getFunctions().put(functionNameIn, pair);
		}

		return true;
	}

	// Put previosu orderIn
	public boolean add(final IFunction functionIn, final String functionNameIn, final int orderIn,
			final boolean isEnableIn)
	{
		if (this.getFunctions().containsKey(functionNameIn))
		{
			this.getFunctions().remove(functionNameIn);
		}

		final var pair = new Pair<>(true, functionIn);

		if (!this.getFunctions().putPrevious(orderIn, functionNameIn, pair))
		{
			this.getFunctions().put(functionNameIn, pair);
		}

		return true;
	}

	public boolean addNext(final IFunction functionIn, final String functionNameIn, final int orderIn)
	{
		if (this.getFunctions().containsKey(functionNameIn))
		{
			this.getFunctions().remove(functionNameIn);
		}

		final var pair = new Pair<>(true, functionIn);

		if (!this.getFunctions().putNext(orderIn, functionNameIn, pair))
		{
			this.getFunctions().put(functionNameIn, pair);
		}

		return true;
	}

	public boolean addNext(final IFunction functionIn, final String functionNameIn, final int orderIn,
			final boolean isEnableIn)
	{
		if (this.getFunctions().containsKey(functionNameIn))
		{
			this.getFunctions().remove(functionNameIn);
		}

		final var pair = new Pair<>(true, functionIn);

		if (!this.getFunctions().putNext(orderIn, functionNameIn, pair))
		{
			this.getFunctions().put(functionNameIn, pair);
		}

		return true;
	}

	public Pair<Boolean, IFunction> get(final String functionNameIn)
	{
		return this.getFunctions().get(functionNameIn);
	}

	public IFunction getFunction(final String functionNameIn)
	{
		final var pair = this.getFunctions().get(functionNameIn);

		return pair != null ? pair.getTwo() : null;
	}

	public boolean has(final String functionNameIn)
	{
		return this.getFunctions().containsKey(functionNameIn);
	}

	public boolean isEnable(final String functionNameIn)
	{
		final var pair = this.getFunctions().get(functionNameIn);

		return pair != null ? pair.getOne() : false;
	}

	public void disable(final String functionNameIn)
	{
		final var pair = this.getFunctions().get(functionNameIn);

		if (pair == null)
		{
			return;
		}

		pair.setOne(false);
	}

	public boolean changeActivation(final String functionNameIn)
	{
		final var pair = this.getFunctions().get(functionNameIn);

		if (pair == null)
		{
			return false;
		}

		pair.setOne(!pair.getOne());

		return pair.getOne();
	}

	public void enable(final String functionNameIn)
	{
		final var pair = this.getFunctions().get(functionNameIn);

		if (pair == null)
		{
			return;
		}

		pair.setOne(true);
	}

	public void setOrder(final String functionNameIn, final int orderIn)
	{
		final var pair = this.getFunctions().remove(functionNameIn);

		if (pair != null)
		{
			this.getFunctions().putNext(orderIn, functionNameIn, pair);
		}
	}

	public void remove(final String functionNameIn)
	{
		this.getFunctions().remove(functionNameIn);
	}

	// Extends methods

	@Override
	public boolean execute()
	{
		for (final Pair<Boolean, IFunction> pair : this.getFunctions())
		{
			if (pair.getOne())
			{
				pair.getTwo().execute();
			}
		}

		return true;
	}

	// Getter | Setter

	public NodeMap<String, Pair<Boolean, IFunction>> getFunctions()
	{
		return this.functions;
	}

	public void setFunctions(final NodeMap<String, Pair<Boolean, IFunction>> functionsIn)
	{
		this.functions = functionsIn;
	}
}