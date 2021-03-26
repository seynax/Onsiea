package fr.seynax.onsiea.utils.performances;

public class Profiler
{
	// Static variables

	private static int	profileNumber;

	// Variables

	private long		start;
	private long		end;

	private double		time;

	private long		totalTime;

	private int			iterationNumber;

	private String		name;

	// Constructor

	public Profiler()
	{
		this.setName("PROFILE-" + Profiler.addProfileNumber());

	}

	public Profiler(final String nameIn)
	{
		this.setName(nameIn);

		Profiler.addProfileNumber();
	}

	// Static methods

	public final static int addProfileNumber()
	{
		return Profiler.profileNumber++;
	}

	// Methods

	public void start()
	{
		this.setStart(System.nanoTime());
	}

	/**
	 * @return currentTime
	 */
	public long stop()
	{
		this.setEnd(System.nanoTime());

		final var currentTime = this.getEnd() - this.getStart();

		this.setTotalTime(this.getTotalTime() + currentTime);

		this.setTime((this.getTime() + currentTime) / 2.0D);

		this.setIterationNumber(this.getIterationNumber() + 1);

		return currentTime;
	}

	@Override
	public String toString()
	{
		return this.getName() + " -> [" + this.getEnd() + " - " + this.getStart() + " = " + this.getTime() + "]";
	}

	// Static getter | setter

	public final static int getProfileNumber()
	{
		return Profiler.profileNumber;
	}

	public final static void setProfileNumber(final int profileNumberIn)
	{
		Profiler.profileNumber = profileNumberIn;
	}

	// Getter | Setter

	public long getStart()
	{
		return this.start;
	}

	public void setStart(final long startIn)
	{
		this.start = startIn;
	}

	public long getEnd()
	{
		return this.end;
	}

	public void setEnd(final long endIn)
	{
		this.end = endIn;
	}

	public double getTime()
	{
		return this.time;
	}

	public void setTime(final double timeIn)
	{
		this.time = timeIn;
	}

	public long getTotalTime()
	{
		return this.totalTime;
	}

	public void setTotalTime(final long totalTimeIn)
	{
		this.totalTime = totalTimeIn;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}

	public int getIterationNumber()
	{
		return this.iterationNumber;
	}

	public void setIterationNumber(final int iterationNumberIn)
	{
		this.iterationNumber = iterationNumberIn;
	}
}