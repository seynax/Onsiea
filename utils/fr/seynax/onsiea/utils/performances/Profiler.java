package fr.seynax.onsiea.utils.performances;

public class Profiler
{
	// Variables

	private static int	profileNumber;

	private long		start;
	private long		end;

	private long		time;

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

	public void stop()
	{
		this.setEnd(System.nanoTime());

		this.setTime(this.getEnd() - this.getStart());
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

	public long getTime()
	{
		return this.time;
	}

	public void setTime(final long timeIn)
	{
		this.time = timeIn;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}
}