package fr.seynax.onsiea.utils.performances.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.seynax.onsiea.utils.IFunction;
import fr.seynax.onsiea.utils.performances.measurer.IMeasurer;

public class PerformanceTester
{
	// Constructor variables

	private int								phases;

	private int								tests;

	private int								executions;

	// Variables

	private Map<String, IFunction>			functions;

	private Map<Integer, List<IMeasurer>>	measurers;

	// Constructor

	public PerformanceTester()
	{
		this.setPhases(1);
		this.setTests(1);
		this.setExecutions(1);
	}

	public PerformanceTester(final int phasesIn)
	{
		this.setPhases(phasesIn);
		this.setTests(1);
		this.setExecutions(1);
	}

	public PerformanceTester(final int phasesIn, final int testsIn)
	{
		this.setPhases(phasesIn);
		this.setTests(testsIn);
		this.setExecutions(1);
	}

	public PerformanceTester(final int phasesIn, final int testsIn, final int executionsIn)
	{
		this.setPhases(phasesIn);
		this.setTests(testsIn);
		this.setExecutions(executionsIn);
	}

	// Methods

	public void addFunction(final String functionNameIn, final IFunction functionIn)
	{
		this.getFunctions().put(functionNameIn, functionIn);
	}

	public void addMeasurer(final int priorityNumberIn, final IMeasurer... measurersIn)
	{
		var measurers = this.getMeasurers().get(priorityNumberIn);

		if (measurers == null)
		{
			measurers = new ArrayList<>();

			this.getMeasurers().put(priorityNumberIn, measurers);
		}

		for (final IMeasurer measurer : measurersIn)
		{
			measurers.add(measurer);
		}
	}

	public void check()
	{
		for (var phase = 0; phase < this.getPhases(); phase++)
		{
			for (final IFunction function : this.getFunctions().values())
			{
				for (final List<IMeasurer> measurers : this.getMeasurers().values())
				{
					for (var test = 0; test < this.getTests(); test++)
					{
						for (final IMeasurer measurer : measurers)
						{
							measurer.start();
						}

						for (var execution = 0; execution < this.getExecutions(); execution++)
						{
							function.execute();
						}

						for (final IMeasurer measurer : measurers)
						{
							measurer.stop();
						}
					}
				}
			}
		}
	}

	// Getter | Setter ; constructor variables

	public int getPhases()
	{
		return this.phases;
	}

	public void setPhases(final int phasesIn)
	{
		this.phases = phasesIn;
	}

	public int getTests()
	{
		return this.tests;
	}

	public void setTests(final int testsIn)
	{
		this.tests = testsIn;
	}

	public int getExecutions()
	{
		return this.executions;
	}

	public void setExecutions(final int executionsIn)
	{
		this.executions = executionsIn;
	}

	// Getter | setter ; variables

	public Map<String, IFunction> getFunctions()
	{
		return this.functions;
	}

	public void setFunctions(final Map<String, IFunction> functionsIn)
	{
		this.functions = functionsIn;
	}

	public Map<Integer, List<IMeasurer>> getMeasurers()
	{
		return this.measurers;
	}

	public void setMeasurers(final Map<Integer, List<IMeasurer>> measurersIn)
	{
		this.measurers = measurersIn;
	}
}