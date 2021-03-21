package fr.seynax.onsiea.utils;

public class TextureData
{
	private byte[] data;

	public TextureData()
	{
	}

	TextureData(final byte[] dataIn)
	{
		this.setData(dataIn);
	}

	public byte[] getData()
	{
		return this.data;
	}

	void setData(final byte[] dataIn)
	{
		this.data = dataIn;
	}
}
