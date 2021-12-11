package fr.seynax.onsiea.graphics.matter;

public class Shape
{
	// Constructor variables

	private float[]	positions;
	private float[]	textureCoordinates;
	private int[]	indices;

	// Constructor

	public Shape(final float[] positionsIn, final float[] textureCoordinatesIn, final int[] indicesIn)
	{
		this.setPositions(positionsIn);
		this.setTextureCoordinates(textureCoordinatesIn);
		this.setIndices(indicesIn);
	}

	// Methods

	// Positions

	public float[] getTranslated(final int xIn, final int yIn, final int zIn)
	{
		final var translatedPositions = new float[this.getPositions().length];

		for (var i = 0; i < this.getPositions().length; i += 3)
		{
			translatedPositions[i]		= this.getPositions()[i] + xIn;
			translatedPositions[i + 1]	= this.getPositions()[i + 1] + yIn;
			translatedPositions[i + 2]	= this.getPositions()[i + 2] + zIn;
		}

		return translatedPositions;
	}

	public float[] getResized(final float sizeXIn, final float sizeYIn, final float sizeZIn)
	{
		final var translatedPositions = new float[this.getPositions().length];

		for (var i = 0; i < this.getPositions().length; i += 3)
		{
			translatedPositions[i]		= this.getPositions()[i] * sizeXIn;
			translatedPositions[i + 1]	= this.getPositions()[i + 1] * sizeYIn;
			translatedPositions[i + 2]	= this.getPositions()[i + 2] * sizeZIn;
		}

		return translatedPositions;
	}

	public float[] getTranslatedResized(final float xIn, final float yIn, final float zIn, final float sizeXIn,
			final float sizeYIn, final float sizeZIn)
	{
		final var translatedPositions = new float[this.getPositions().length];

		for (var i = 0; i < this.getPositions().length; i += 3)
		{
			translatedPositions[i]		= this.getPositions()[i] * sizeXIn + xIn;
			translatedPositions[i + 1]	= this.getPositions()[i + 1] * sizeYIn + yIn;
			translatedPositions[i + 2]	= this.getPositions()[i + 2] * sizeZIn + zIn;
		}

		return translatedPositions;
	}

	// Indices

	public int[] getIndices(final int offsetIn)
	{
		final var offsetIndices = new int[this.getIndices().length];

		for (var i = 0; i < this.getIndices().length; i++)
		{
			offsetIndices[i] = this.getIndices()[i] + offsetIn;
		}

		return offsetIndices;
	}

	// Getter | Setter

	public float[] getPositions()
	{
		return this.positions;
	}

	private void setPositions(final float[] positionsIn)
	{
		this.positions = positionsIn;
	}

	public float[] getTextureCoordinates()
	{
		return this.textureCoordinates;
	}

	private void setTextureCoordinates(final float[] textureCoordinatesIn)
	{
		this.textureCoordinates = textureCoordinatesIn;
	}

	public int[] getIndices()
	{
		return this.indices;
	}

	private void setIndices(final int[] indicesIn)
	{
		this.indices = indicesIn;
	}
}
