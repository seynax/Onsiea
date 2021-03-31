package fr.seynax.onsiea.utils.performances.helper;

import java.io.File;

public class DiskHelper
{
	private File file;

	public DiskHelper(final String filepathIn)
	{
		this.setFile(new File(filepathIn));
	}

	// Static methods

	public final static long usable(final String diskpathIn)
	{
		return new File(diskpathIn).getUsableSpace();
	}

	public final static long free(final String diskpathIn)
	{
		return new File(diskpathIn).getFreeSpace();
	}

	public final static long total(final String diskpathIn)
	{
		return new File(diskpathIn).getTotalSpace();
	}

	// Methods

	public long usable()
	{
		return this.getFile().getUsableSpace();
	}

	public long free()
	{
		return this.getFile().getFreeSpace();
	}

	public long total()
	{
		return this.getFile().getTotalSpace();
	}

	// Getter | Setter

	public File getFile()
	{
		return this.file;
	}

	public void setFile(final File fileIn)
	{
		this.file = fileIn;
	}
}