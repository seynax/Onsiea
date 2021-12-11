package fr.seynax.onsiea.gamelogic.world;

import org.joml.Vector3f;

import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.maths.MathsInstances;
import fr.seynax.onsiea.opengl.shader.BaseColourShader;
import fr.seynax.onsiea.utils.maths.Maths;

public class Floor
{
	// Variables

	private Vector3f	length;

	private Vector3f	position;

	private Mesh		mesh;

	// Constructor

	public Floor(final float lengthXIn, final float lengthYIn, final float lengthZIn, final Mesh surfaceIn)
	{
		this.setLength(new Vector3f(lengthXIn, lengthYIn, lengthZIn));

		this.setPosition(new Vector3f());

		this.setMesh(surfaceIn);
	}

	public void setPosition(final int xIn, final int yIn, final int zIn)
	{
		this.getPosition().x	= xIn;
		this.getPosition().y	= yIn;
		this.getPosition().z	= zIn;
	}

	// Methods

	public void draw(final BaseColourShader baseColourShaderIn)
	{
		final var worldMatrix = Maths.getWorldMatrix(this.getPosition(), MathsInstances.getNull(), this.getLength());
		baseColourShaderIn.sendTransformationMatrix(worldMatrix);

		this.getMesh().render();
	}

	// Getter | Setter

	private Vector3f getLength()
	{
		return this.length;
	}

	private void setLength(final Vector3f lengthIn)
	{
		this.length = lengthIn;
	}

	private Vector3f getPosition()
	{
		return this.position;
	}

	private void setPosition(final Vector3f positionIn)
	{
		this.position = positionIn;
	}

	private Mesh getMesh()
	{
		return this.mesh;
	}

	private void setMesh(final Mesh meshIn)
	{
		this.mesh = meshIn;
	}

}