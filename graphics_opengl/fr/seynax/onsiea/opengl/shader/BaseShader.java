package fr.seynax.onsiea.opengl.shader;

public class BaseShader extends ShaderProgram
{

	public BaseShader()
	{
		super("resources/shaders/baseVertex.glsl", "resources/shaders/baseFragment.glsl");
	}

	@Override
	protected void getAllUniformLocations()
	{
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_Position");
	}

}
