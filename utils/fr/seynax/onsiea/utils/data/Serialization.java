package fr.seynax.onsiea.utils.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization
{
	/**
	 * This method is used to read data from file for deSerialization.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerialization(final String file) throws IOException, ClassNotFoundException
	{
		final var	fileInputStream		= new FileInputStream(file);
		final var	bufferedInputStream	= new BufferedInputStream(fileInputStream);
		final var	objectInputStream	= new ObjectInputStream(bufferedInputStream);
		final var	object				= objectInputStream.readObject();
		objectInputStream.close();
		return object;
	}

	/**
	 * This method is used to write data to file for Serialization.
	 *
	 * @param file
	 * @param object
	 * @throws IOException
	 */
	public static void serialization(final String filePath, final Object object) throws IOException
	{
		final var file = new File(filePath);

		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}

		final var	fileOutputStream		= new FileOutputStream(file);
		final var	bufferedOutputStream	= new BufferedOutputStream(fileOutputStream);
		final var	objectOutputStream		= new ObjectOutputStream(bufferedOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
	}
}