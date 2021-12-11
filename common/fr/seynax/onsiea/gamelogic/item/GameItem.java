package fr.seynax.onsiea.gamelogic.item;

import org.joml.Vector3f;

import fr.seynax.onsiea.graphics.matter.Mesh;

public class GameItem
{
	private final Mesh		mesh;

	private final Vector3f	position;

	private float			scale;

	private final Vector3f	rotation;

	public GameItem(final Mesh meshIn)
	{
		this.mesh		= meshIn;
		this.position	= new Vector3f(0, 0, 0);
		this.scale		= 1;
		this.rotation	= new Vector3f(0, 0, 0);
	}

	public Vector3f getPosition()
	{
		return this.position;
	}

	public void setPosition(final float xIn, final float yIn, final float zIn)
	{
		this.position.x	= xIn;
		this.position.y	= yIn;
		this.position.z	= zIn;
	}

	public float getScale()
	{
		return this.scale;
	}

	public void setScale(final float scaleIn)
	{
		this.scale = scaleIn;
	}

	public Vector3f getRotation()
	{
		return this.rotation;
	}

	public void setRotation(final float xIn, final float yIn, final float zIn)
	{
		this.rotation.x	= xIn;
		this.rotation.y	= yIn;
		this.rotation.z	= zIn;
	}

	public Mesh getMesh()
	{
		return this.mesh;
	}
}
