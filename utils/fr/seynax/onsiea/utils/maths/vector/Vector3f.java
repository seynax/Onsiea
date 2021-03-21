/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.seynax.onsiea.utils.maths.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

/**
 *
 * Holds a 3-tuple vector.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$ $Id$
 */

public class Vector3f extends Vector implements Serializable, ReadableVector3f, WritableVector3f
{

	private static final long	serialVersionUID	= 1L;

	public float				x, y, z;

	/**
	 * Constructor for Vector3f.
	 */
	public Vector3f()
	{
		super();
	}

	/**
	 * Constructor
	 */
	public Vector3f(final ReadableVector3f src)
	{
		this.set(src);
	}

	/**
	 * Constructor
	 */
	public Vector3f(final float x, final float y, final float z)
	{
		this.set(x, y, z);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lwjgl.util.vector.WritableVector2f#set(float, float)
	 */
	@Override
	public void set(final float x, final float y)
	{
		this.x	= x;
		this.y	= y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lwjgl.util.vector.WritableVector3f#set(float, float, float)
	 */
	@Override
	public void set(final float x, final float y, final float z)
	{
		this.x	= x;
		this.y	= y;
		this.z	= z;
	}

	/**
	 * Load from another Vector3f
	 *
	 * @param src The source vector
	 * @return this
	 */
	public Vector3f set(final ReadableVector3f src)
	{
		this.x	= src.getX();
		this.y	= src.getY();
		this.z	= src.getZ();
		return this;
	}

	/**
	 * @return the length squared of the vector
	 */
	@Override
	public float lengthSquared()
	{
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	/**
	 * Translate a vector
	 *
	 * @param x The translation in x
	 * @param y the translation in y
	 * @return this
	 */
	public Vector3f translate(final float x, final float y, final float z)
	{
		this.x	+= x;
		this.y	+= y;
		this.z	+= z;
		return this;
	}

	/**
	 * Add a vector to another vector and place the result in a destination vector.
	 *
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @param dest  The destination vector, or null if a new vector is to be created
	 * @return the sum of left and right in dest
	 */
	public static Vector3f add(final Vector3f left, final Vector3f right, final Vector3f dest)
	{
		if (dest == null)
		{
			return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
		}
		else
		{
			dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
			return dest;
		}
	}

	/**
	 * Subtract a vector from another vector and place the result in a destination
	 * vector.
	 *
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @param dest  The destination vector, or null if a new vector is to be created
	 * @return left minus right in dest
	 */
	public static Vector3f sub(final Vector3f left, final Vector3f right, final Vector3f dest)
	{
		if (dest == null)
		{
			return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
		}
		else
		{
			dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
			return dest;
		}
	}

	/**
	 * The cross product of two vectors.
	 *
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @param dest  The destination result, or null if a new vector is to be created
	 * @return left cross right
	 */
	public static Vector3f cross(final Vector3f left, final Vector3f right, Vector3f dest)
	{

		if (dest == null)
		{
			dest = new Vector3f();
		}

		dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x,
				left.x * right.y - left.y * right.x);

		return dest;
	}

	/**
	 * Negate a vector
	 *
	 * @return this
	 */
	@Override
	public Vector negate()
	{
		this.x	= -this.x;
		this.y	= -this.y;
		this.z	= -this.z;
		return this;
	}

	/**
	 * Negate a vector and place the result in a destination vector.
	 *
	 * @param dest The destination vector or null if a new vector is to be created
	 * @return the negated vector
	 */
	public Vector3f negate(Vector3f dest)
	{
		if (dest == null)
		{
			dest = new Vector3f();
		}
		dest.x	= -this.x;
		dest.y	= -this.y;
		dest.z	= -this.z;
		return dest;
	}

	/**
	 * Normalise this vector and place the result in another vector.
	 *
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return the normalised vector
	 */
	public Vector3f normalise(Vector3f dest)
	{
		final var l = this.length();

		if (dest == null)
		{
			dest = new Vector3f(this.x / l, this.y / l, this.z / l);
		}
		else
		{
			dest.set(this.x / l, this.y / l, this.z / l);
		}

		return dest;
	}

	/**
	 * The dot product of two vectors is calculated as v1.x * v2.x + v1.y * v2.y +
	 * v1.z * v2.z
	 *
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static float dot(final Vector3f left, final Vector3f right)
	{
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	/**
	 * Calculate the angle between two vectors, in radians
	 *
	 * @param a A vector
	 * @param b The other vector
	 * @return the angle between the two vectors, in radians
	 */
	public static float angle(final Vector3f a, final Vector3f b)
	{
		var dls = Vector3f.dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
		{
			dls = -1f;
		}
		else if (dls > 1.0f)
		{
			dls = 1.0f;
		}
		return (float) Math.acos(dls);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lwjgl.vector.Vector#load(FloatBuffer)
	 */
	@Override
	public Vector load(final FloatBuffer buf)
	{
		this.x	= buf.get();
		this.y	= buf.get();
		this.z	= buf.get();
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lwjgl.vector.Vector#scale(float)
	 */
	@Override
	public Vector scale(final float scale)
	{

		this.x	*= scale;
		this.y	*= scale;
		this.z	*= scale;

		return this;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lwjgl.vector.Vector#store(FloatBuffer)
	 */
	@Override
	public Vector store(final FloatBuffer buf)
	{

		buf.put(this.x);
		buf.put(this.y);
		buf.put(this.z);

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final var sb = new StringBuilder(64);

		sb.append("Vector3f[");
		sb.append(this.x);
		sb.append(", ");
		sb.append(this.y);
		sb.append(", ");
		sb.append(this.z);
		sb.append(']');
		return sb.toString();
	}

	/**
	 * @return x
	 */
	@Override
	public final float getX()
	{
		return this.x;
	}

	/**
	 * @return y
	 */
	@Override
	public final float getY()
	{
		return this.y;
	}

	/**
	 * Set X
	 *
	 * @param x
	 */
	@Override
	public final void setX(final float x)
	{
		this.x = x;
	}

	/**
	 * Set Y
	 *
	 * @param y
	 */
	@Override
	public final void setY(final float y)
	{
		this.y = y;
	}

	/**
	 * Set Z
	 *
	 * @param z
	 */
	@Override
	public void setZ(final float z)
	{
		this.z = z;
	}

	/*
	 * (Overrides)
	 *
	 * @see org.lwjgl.vector.ReadableVector3f#getZ()
	 */
	@Override
	public float getZ()
	{
		return this.z;
	}

	@Override
	public int hashCode()
	{
		final var	prime	= 31;
		var			result	= 1;
		result	= prime * result + Float.floatToIntBits(this.x);
		result	= prime * result + Float.floatToIntBits(this.y);
		result	= prime * result + Float.floatToIntBits(this.z);
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (Vector3f) obj;
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x))
		{
			return false;
		}
		if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y))
		{
			return false;
		}
		if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z))
		{
			return false;
		}
		return true;
	}
}
