package fr.seynax.onsiea.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	public final static boolean writeAll(final String filepathNameIn, final String contentIn)
	{
		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(new File(filepathNameIn)));

			bufferedWriter.write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			if (bufferedWriter != null)
			{
				try
				{
					bufferedWriter.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public static List<String> loadLines(final String filetPathNameIn)
	{
		final var file = new File(filetPathNameIn);

		if (!file.exists() || file.isDirectory())
		{
			return null;
		}

		final List<String>	lines			= new ArrayList<>();

		BufferedReader		bufferedReader	= null;

		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return lines;
	}

	public final static String loadResource(final String filePathIn)
	{
		final var file = new File(filePathIn);

		if (!file.exists() || file.isDirectory())
		{
			return null;
		}

		BufferedReader	bufferedReader	= null;

		final var		content			= new StringBuilder();

		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				content.append(line).append("//\n");
			}
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return content.toString();
	}
}