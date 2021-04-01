package fr.seynax.onsiea.utils.maths;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Interval
{
	// IsIn

	public final static boolean isIn(final Vector2f fromIn, final Vector2f minIn, final Vector2f maxIn)
	{
		if (fromIn.x() < minIn.x())
		{
			return false;
		}
		if (fromIn.y() < minIn.y())
		{
			return false;
		}

		if (fromIn.x() > maxIn.x())
		{
			return false;
		}
		if (fromIn.y() > maxIn.y())
		{
			return false;
		}

		return true;
	}

	public final static boolean isIn(final Vector3f fromIn, final Vector3f minIn, final Vector3f maxIn)
	{
		if (fromIn.x() < minIn.x())
		{
			return false;
		}
		if (fromIn.y() < minIn.y())
		{
			return false;
		}
		if (fromIn.z() < minIn.z())
		{
			return false;
		}

		if (fromIn.x() > maxIn.x())
		{
			return false;
		}
		if (fromIn.y() > maxIn.y())
		{
			return false;
		}
		if (fromIn.z() > maxIn.z())
		{
			return false;
		}

		return true;
	}

	public final static boolean isInUnstrict(final Vector2f fromIn, final Vector2f minIn, final Vector2f maxIn)
	{
		if (fromIn.x() <= minIn.x())
		{
			return false;
		}
		if (fromIn.y() <= minIn.y())
		{
			return false;
		}

		if (fromIn.x() >= maxIn.x())
		{
			return false;
		}
		if (fromIn.y() >= maxIn.y())
		{
			return false;
		}

		return true;
	}

	public final static boolean isInUnstrict(final Vector3f fromIn, final Vector3f minIn, final Vector3f maxIn)
	{
		if (fromIn.x() <= minIn.x())
		{
			return false;
		}
		if (fromIn.y() <= minIn.y())
		{
			return false;
		}
		if (fromIn.z() <= minIn.z())
		{
			return false;
		}

		if (fromIn.x() >= maxIn.x())
		{
			return false;
		}
		if (fromIn.y() >= maxIn.y())
		{
			return false;
		}
		if (fromIn.z() >= maxIn.z())
		{
			return false;
		}

		return true;
	}

	// IsOut

	public final static boolean isOut(final Vector2f fromIn, final Vector2f minIn, final Vector2f maxIn)
	{
		if (fromIn.x() <= minIn.x())
		{
			return true;
		}
		if (fromIn.y() <= minIn.y())
		{
			return true;
		}

		if (fromIn.x() >= maxIn.x())
		{
			return true;
		}
		if (fromIn.y() >= maxIn.y())
		{
			return true;
		}

		return false;
	}

	public final static boolean isOut(final Vector3f fromIn, final Vector3f minIn, final Vector3f maxIn)
	{
		if (fromIn.x() <= minIn.x())
		{
			return true;
		}
		if (fromIn.y() <= minIn.y())
		{
			return true;
		}
		if (fromIn.z() <= minIn.z())
		{
			return true;
		}

		if (fromIn.x() >= maxIn.x())
		{
			return true;
		}
		if (fromIn.y() >= maxIn.y())
		{
			return true;
		}
		if (fromIn.z() >= maxIn.z())
		{
			return true;
		}

		return false;
	}

	public final static boolean isOutStrict(final Vector2f fromIn, final Vector2f minIn, final Vector2f maxIn)
	{
		if (fromIn.x() < minIn.x())
		{
			return true;
		}
		if (fromIn.y() < minIn.y())
		{
			return true;
		}

		if (fromIn.x() > maxIn.x())
		{
			return true;
		}
		if (fromIn.y() > maxIn.y())
		{
			return true;
		}

		return false;
	}

	public final static boolean isOutStrict(final Vector3f fromIn, final Vector3f minIn, final Vector3f maxIn)
	{
		if (fromIn.x() < minIn.x())
		{
			return true;
		}
		if (fromIn.y() < minIn.y())
		{
			return true;
		}
		if (fromIn.z() < minIn.z())
		{
			return true;
		}

		if (fromIn.x() > maxIn.x())
		{
			return true;
		}
		if (fromIn.y() > maxIn.y())
		{
			return true;
		}
		if (fromIn.z() > maxIn.z())
		{
			return true;
		}

		return false;
	}

	// IsOnLimit

	public final static boolean isOnLimit(final Vector2f fromIn, final Vector2f minIn, final Vector2f maxIn)
	{
		if (fromIn.x() == minIn.x() || fromIn.x() == maxIn.x())
		{
			return fromIn.y() >= minIn.y() && fromIn.y() <= maxIn.y();
		}

		if (fromIn.y() == minIn.y() || fromIn.y() == maxIn.y())
		{
			return fromIn.x() >= minIn.x() && fromIn.x() <= maxIn.x();
		}

		return false;
	}

	public final static boolean isOnLimit(final Vector3f fromIn, final Vector3f minIn, final Vector3f maxIn)
	{
		if ((fromIn.x() == minIn.x() || fromIn.x() == maxIn.x()) && (fromIn.y() == minIn.y() || fromIn.y() == maxIn.y())
				&& fromIn.z() >= minIn.z() && fromIn.z() <= maxIn.z())
		{
			return true;
		}

		if ((fromIn.x() == minIn.x() || fromIn.x() == maxIn.x()) && fromIn.y() >= minIn.y() && fromIn.y() <= maxIn.y()
				&& fromIn.z() == minIn.z() || fromIn.z() == maxIn.z())
		{
			return true;
		}

		if (fromIn.x() >= minIn.x() && fromIn.x() <= maxIn.x() && (fromIn.y() == minIn.y() || fromIn.y() == maxIn.y())
				&& fromIn.z() == minIn.z() && fromIn.z() == maxIn.z())
		{
			return true;
		}

		return false;
	}
}