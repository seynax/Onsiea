package fr.seynax.onsiea.maths;

import fr.seynax.onsiea.utils.maths.vector.Vector2f;
import fr.seynax.onsiea.utils.maths.vector.Vector3f;

public class MathsInstances
{
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ------------------ Static variables header
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	private static final Vector3f	NULL	= new Vector3f(0.0f, 0.0f, 0.0f);

	private static final Vector3f	ONE		= new Vector3f(1.0f, 1.0f, 1.0f);

	private static final Vector3f	AXE_X	= new Vector3f(1.0f, 0.0f, 0.0f);

	private static final Vector3f	AXE_Y	= new Vector3f(0.0f, 1.0f, 0.0f);

	private static final Vector3f	AXE_Z	= new Vector3f(0.0f, 0.0f, 1.0f);

	private static final double		PI_180	= Math.PI / 180.0f;

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ------------------ Methods and Getters | Setters
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// Default vector (Vector2f, Vector3f)

	// AXES

	// AXE X

	/**
	 * GETTER (Vector3f) AXE_X = Vector3f(1.0f, 0.0f, 0.0f)
	 *
	 * @return AXE_X
	 */
	public static Vector3f getAxeX()
	{
		return MathsInstances.AXE_X;
	}

	public static boolean isOnAxeX(final Vector2f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getAxeX().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getAxeX().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final Vector3f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getAxeX().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getAxeX().getY())
		{
			return false;
		}

		if (valueIn.getZ() != MathsInstances.getAxeX().getZ())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeX().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeX().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeX().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeX().getY())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeX().getZ())
		{
			return false;
		}

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// AXE Y

	/**
	 * GETTER (Vector3f) AXE_Y = Vector3f(0.0f, 1.0f, 0.0f)
	 *
	 * @return AXE_Y
	 */
	public static Vector3f getAxeY()
	{
		return MathsInstances.AXE_Y;
	}

	public static boolean isOnAxeY(final Vector3f valueIn)
	{
		if (valueIn.getY() != MathsInstances.getAxeY().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getAxeY().getY())
		{
			return false;
		}

		if (valueIn.getZ() != MathsInstances.getAxeY().getZ())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final Vector2f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getAxeY().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getAxeY().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeY().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeY().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeY().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeY().getY())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeY().getZ())
		{
			return false;
		}

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// AXE Z

	/**
	 * GETTER (Vector3f) AXE_Z = Vector3f(0.0f, 0.0f, 1.0f)
	 *
	 * @return AXE_Z
	 */
	public static Vector3f getAxeZ()
	{
		return MathsInstances.AXE_Z;
	}

	public static boolean isOnAxeZ(final Vector3f valueIn)
	{
		if (valueIn.getY() != MathsInstances.getAxeZ().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getAxeZ().getY())
		{
			return false;
		}

		if (valueIn.getZ() != MathsInstances.getAxeZ().getZ())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeZ(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeZ().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeZ().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeZ(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeZ().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeZ().getY())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeZ().getZ())
		{
			return false;
		}

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// NULL

	/**
	 * GETTER (Vector3f) NULL = Vector3f(0.0f, 0.0f, 0.0f)
	 *
	 * @return NULL
	 */
	public static Vector3f getNull()
	{
		return MathsInstances.NULL;
	}

	public static boolean isNull(final Vector2f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getNull().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getNull().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final Vector3f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getNull().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getNull().getY())
		{
			return false;
		}

		if (valueIn.getZ() != MathsInstances.getNull().getZ())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getNull().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getNull().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getNull().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getNull().getX())
		{
			return false;
		}

		if (zIn != MathsInstances.getNull().getZ())
		{
			return false;
		}

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// ONE

	/**
	 * GETTER (Vector3f) ONE = Vector3f(1.0f, 1.0f, 1.0f)
	 *
	 * @return ONE
	 */
	public static Vector3f getOne()
	{
		return MathsInstances.ONE;
	}

	public static boolean isOne(final Vector2f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getOne().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getOne().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final Vector3f valueIn)
	{
		if (valueIn.getX() != MathsInstances.getOne().getX())
		{
			return false;
		}

		if (valueIn.getY() != MathsInstances.getOne().getY())
		{
			return false;
		}

		if (valueIn.getZ() != MathsInstances.getOne().getZ())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getOne().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getOne().getY())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getOne().getX())
		{
			return false;
		}

		if (yIn != MathsInstances.getOne().getX())
		{
			return false;
		}

		if (zIn != MathsInstances.getOne().getZ())
		{
			return false;
		}

		return true;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	public static double getPi180()
	{
		return MathsInstances.PI_180;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
}