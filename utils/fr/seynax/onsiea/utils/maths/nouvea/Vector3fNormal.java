package fr.seynax.onsiea.utils.maths.nouvea;

public class Vector3fNormal
{
	// Variables

	private float x, y, z;

	// Constructor

	public Vector3fNormal(final float xIn, final float yIn, final float zIn)
	{
		this.x(xIn);
		this.y(yIn);
		this.z(zIn);
	}

	// Methods

	public Vector3fNormal xy(final float xIn, final float yIn)
	{
		this.x(xIn);
		this.y(yIn);

		return this;
	}

	public Vector3fNormal xz(final float xIn, final float zIn)
	{
		this.x(xIn);
		this.z(zIn);

		return this;
	}

	public Vector3fNormal yz(final float xIn, final float yIn, final float zIn)
	{
		this.y(yIn);
		this.z(zIn);

		return this;
	}

	public Vector3fNormal xyz(final float xIn, final float yIn, final float zIn)
	{
		this.x(xIn);
		this.y(yIn);
		this.z(zIn);

		return this;
	}

	// Getter | Setter

	public float x()
	{
		return this.x;
	}

	public Vector3fNormal x(final float xIn)
	{
		this.x = xIn;

		return this;
	}

	public float y()
	{
		return this.y;
	}

	public Vector3fNormal y(final float yIn)
	{
		this.y = yIn;

		return this;
	}

	public float z()
	{
		return this.z;
	}

	public Vector3fNormal z(final float zIn)
	{
		this.z = zIn;

		return this;
	}
}