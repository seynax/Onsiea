package fr.seynax.onsiea.maths;

import org.joml.Vector2f;
import org.joml.Vector3f;

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
		if (valueIn.x() != MathsInstances.getAxeX().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getAxeX().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final Vector3f valueIn)
	{
		if (valueIn.x() != MathsInstances.getAxeX().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getAxeX().y())
		{
			return false;
		}

		if (valueIn.z() != MathsInstances.getAxeX().z())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeX().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeX().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeX(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeX().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeX().y())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeX().z())
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
		if (valueIn.y() != MathsInstances.getAxeY().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getAxeY().y())
		{
			return false;
		}

		if (valueIn.z() != MathsInstances.getAxeY().z())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final Vector2f valueIn)
	{
		if (valueIn.x() != MathsInstances.getAxeY().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getAxeY().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeY().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeY().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeY(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeY().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeY().y())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeY().z())
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
		if (valueIn.y() != MathsInstances.getAxeZ().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getAxeZ().y())
		{
			return false;
		}

		if (valueIn.z() != MathsInstances.getAxeZ().z())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeZ(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getAxeZ().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeZ().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOnAxeZ(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getAxeZ().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getAxeZ().y())
		{
			return false;
		}

		if (zIn != MathsInstances.getAxeZ().z())
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
		if (valueIn.x() != MathsInstances.getNull().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getNull().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final Vector3f valueIn)
	{
		if (valueIn.x() != MathsInstances.getNull().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getNull().y())
		{
			return false;
		}

		if (valueIn.z() != MathsInstances.getNull().z())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getNull().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getNull().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isNull(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getNull().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getNull().y())
		{
			return false;
		}

		if (zIn != MathsInstances.getNull().z())
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
		if (valueIn.x() != MathsInstances.getOne().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getOne().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final Vector3f valueIn)
	{
		if (valueIn.x() != MathsInstances.getOne().x())
		{
			return false;
		}

		if (valueIn.y() != MathsInstances.getOne().y())
		{
			return false;
		}

		if (valueIn.z() != MathsInstances.getOne().z())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final float xIn, final float yIn)
	{
		if (xIn != MathsInstances.getOne().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getOne().y())
		{
			return false;
		}

		return true;
	}

	public static boolean isOne(final float xIn, final float yIn, final float zIn)
	{
		if (xIn != MathsInstances.getOne().x())
		{
			return false;
		}

		if (yIn != MathsInstances.getOne().y())
		{
			return false;
		}

		if (zIn != MathsInstances.getOne().z())
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