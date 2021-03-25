package fr.seynax.onsiea.opengl.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BaseColourShader extends ShaderProgram
{
	private int	locationProjectionMatrix;
	private int	locationTransformationMatrix;
	private int	locationViewMatrix;
	private int	locationColor;

	public BaseColourShader()
	{
		super("resources/shaders/GLSL/baseColourVertex.glsl", "resources/shaders/GLSL/baseColourFragment.glsl");
	}

	@Override
	protected void getAllUniformLocations()
	{
		this.setLocationProjectionMatrix(super.getUniformLocation("projectionMatrix"));
		this.setLocationTransformationMatrix(super.getUniformLocation("transformationMatrix"));
		this.setLocationViewMatrix(super.getUniformLocation("viewMatrix"));
		this.setLocationColor(super.getUniformLocation("color"));
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_Position");
		super.bindAttribute(1, "in_TexCoords");
		super.bindAttribute(2, "in_Colour");
	}

	public void sendProjectionMatrix(final Matrix4f projectionMatrixIn)
	{
		this.setUniform(this.getLocationProjectionMatrix(), projectionMatrixIn);
	}

	public void sendTransformationMatrix(final Matrix4f transformationMatrixIn)
	{
		this.setUniform(this.getLocationTransformationMatrix(), transformationMatrixIn);
	}

	public void sendViewMatrix(final Matrix4f viewMatrixIn)
	{
		this.setUniform(this.getLocationViewMatrix(), viewMatrixIn);
	}

	public void sendColor(final Vector3f colorIn)
	{
		super.setUniform(this.getLocationColor(), colorIn);
	}

	private int getLocationProjectionMatrix()
	{
		return this.locationProjectionMatrix;
	}

	private void setLocationProjectionMatrix(final int locationProjectionMatrixIn)
	{
		this.locationProjectionMatrix = locationProjectionMatrixIn;
	}

	public int getLocationTransformationMatrix()
	{
		return this.locationTransformationMatrix;
	}

	public void setLocationTransformationMatrix(final int locationTransformationMatrixIn)
	{
		this.locationTransformationMatrix = locationTransformationMatrixIn;
	}

	public int getLocationViewMatrix()
	{
		return this.locationViewMatrix;
	}

	public void setLocationViewMatrix(final int locationViewMatrixIn)
	{
		this.locationViewMatrix = locationViewMatrixIn;
	}

	public int getLocationColor()
	{
		return this.locationColor;
	}

	public void setLocationColor(final int locationColorIn)
	{
		this.locationColor = locationColorIn;
	}
}