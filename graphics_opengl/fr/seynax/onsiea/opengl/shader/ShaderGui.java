package fr.seynax.onsiea.opengl.shader;

import org.joml.Matrix4f;

public class ShaderGui extends ShaderProgram
{
	// Variables

	private int	locationProjectionMatrix;
	private int	locationTransformationMatrix;
	private int	locationViewMatrix;

	// Constructor

	public ShaderGui()
	{
		super("resources/shaders/GLSL/guiVertex.glsl", "resources/shaders/GLSL/guiFragment.glsl");
	}

	// Methods

	@Override
	protected void getAllUniformLocations()
	{
		this.setLocationProjectionMatrix(super.getUniformLocation("projectionMatrix"));
		this.setLocationTransformationMatrix(super.getUniformLocation("transformationMatrix"));
		this.setLocationViewMatrix(super.getUniformLocation("viewMatrix"));
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_Position");
	}

	public void sendProjectrionMatrix(final Matrix4f projectionMatrixIn)
	{
		super.setUniform(this.getLocationProjectionMatrix(), projectionMatrixIn);
	}

	public void sendTransformationMatrix(final Matrix4f transformationMatrixIn)
	{
		super.setUniform(this.getLocationTransformationMatrix(), transformationMatrixIn);
	}

	public void sendViewMatrix(final Matrix4f viewMatrixIn)
	{
		super.setUniform(this.getLocationViewMatrix(), viewMatrixIn);
	}

	// Getter | Setters

	public int getLocationTransformationMatrix()
	{
		return this.locationTransformationMatrix;
	}

	public void setLocationTransformationMatrix(final int locationTransformationMatrixIn)
	{
		this.locationTransformationMatrix = locationTransformationMatrixIn;
	}

	public int getLocationProjectionMatrix()
	{
		return this.locationProjectionMatrix;
	}

	public void setLocationProjectionMatrix(final int locationProjectionMatrixIn)
	{
		this.locationProjectionMatrix = locationProjectionMatrixIn;
	}

	public int getLocationViewMatrix()
	{
		return this.locationViewMatrix;
	}

	public void setLocationViewMatrix(final int locationViewMatrixIn)
	{
		this.locationViewMatrix = locationViewMatrixIn;
	}
}
