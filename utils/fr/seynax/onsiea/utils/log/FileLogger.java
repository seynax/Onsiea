package fr.seynax.onsiea.utils.log;

import fr.seynax.onsiea.utils.file.FileUtils;

public class FileLogger
{
	// Variables

	private String	content;

	private String	startText;

	private String	endText;

	private String	filepath;

	// Constructor

	public FileLogger(final String filepathIn)
	{
		this.setStartText("");
		this.setFilepath(filepathIn);
		this.setEndText("");
		this.setContent("");
	}

	public FileLogger(final String filepathIn, final String startTextIn)
	{
		this.setFilepath(filepathIn);
		this.setStartText(startTextIn);
		this.setEndText("");
		this.setContent("");
	}

	public FileLogger(final String filepathIn, final String startTextIn, final String endTextIn)
	{
		this.setFilepath(filepathIn);
		this.setStartText(startTextIn);
		this.setEndText(endTextIn);
		this.setContent("");
	}

	public void log(final Object... objectsIn)
	{
		this.setContent(this.getContent() + this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			this.setContent(this.getContent() + " " + object);
		}

		this.setContent(this.getContent() + this.getEndText());
	}

	public void logLn(final Object... objectsIn)
	{
		this.setContent(this.getContent() + this.getStartText() + " ");

		for (final Object object : objectsIn)
		{
			this.setContent(this.getContent() + " " + object);
		}

		this.setContent(this.getContent() + this.getEndText() + "\n");
	}

	public boolean write()
	{
		return this.write(this.getFilepath());
	}

	public boolean write(final String filepathIn)
	{
		return FileUtils.write(filepathIn, this.getContent());
	}

	// Getter | Setter

	public String getStartText()
	{
		return this.startText;
	}

	private void setStartText(final String startTextIn)
	{
		this.startText = startTextIn;
	}

	public String getEndText()
	{
		return this.endText;
	}

	private void setEndText(final String endTextIn)
	{
		this.endText = endTextIn;
	}

	public String getContent()
	{
		return this.content;
	}

	private void setContent(final String contentIn)
	{
		this.content = contentIn;
	}

	public String getFilepath()
	{
		return this.filepath;
	}

	private void setFilepath(final String filepathIn)
	{
		this.filepath = filepathIn;
	}
}