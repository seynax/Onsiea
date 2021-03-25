package fr.seynax.onsiea.opengl.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public abstract class ShaderProgram
{
	private int					programId;

	private int					vertexShaderId;

	private int					fragmentShaderId;

	private static FloatBuffer	matrixBuffer	= BufferUtils.createFloatBuffer(16);

	private static int loadShader(final String filenameIn, final int type)
	{
		final var		shaderSource	= new StringBuilder();

		BufferedReader	bufferedReader	= null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(filenameIn));
			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				shaderSource.append(line).append("//\n");
			}
		}
		catch (final IOException exception)
		{
			System.err.println("Could not read file !");

			exception.printStackTrace();

			System.exit(-1);
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		final var shaderId = GL20.glCreateShader(type);

		GL20.glShaderSource(shaderId, shaderSource);

		GL20.glCompileShader(shaderId);

		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderId, 4096));

			System.err.println("Could not compile shader !");

			System.exit(-1);
		}

		return shaderId;
	}

	public ShaderProgram(final String vertexFilenameIn, final String fragmentFilenameIn)
	{
		this.setVertexShaderId(ShaderProgram.loadShader(vertexFilenameIn, GL20.GL_VERTEX_SHADER));
		this.setFragmentShaderId(ShaderProgram.loadShader(fragmentFilenameIn, GL20.GL_FRAGMENT_SHADER));

		this.setProgramId(GL20.glCreateProgram());

		GL20.glAttachShader(this.getProgramId(), this.getVertexShaderId());
		GL20.glAttachShader(this.getProgramId(), this.getFragmentShaderId());

		this.bindAttributes();

		GL20.glLinkProgram(this.getProgramId());

		GL20.glValidateProgram(this.getProgramId());

		this.getAllUniformLocations();
	}

	protected abstract void getAllUniformLocations();

	protected int getUniformLocation(final String uniformVariableNameIn)
	{
		return GL20.glGetUniformLocation(this.getProgramId(), uniformVariableNameIn);
	}

	protected abstract void bindAttributes();

	protected void bindAttribute(final int attributeIn, final String variableNameIn)
	{
		GL20.glBindAttribLocation(this.getProgramId(), attributeIn, variableNameIn);
	}

	public void start()
	{
		GL20.glUseProgram(this.getProgramId());
	}

	public final static void stop()
	{
		GL20.glUseProgram(0);
	}

	public void setUniform(final int locationIn, final Matrix4f matrix4fIn)
	{
		// Dump the matrix into a float buffer

		try (var stack = MemoryStack.stackPush())
		{
			final var fb = stack.mallocFloat(16);

			matrix4fIn.get(fb);

			GL20.glUniformMatrix4fv(locationIn, false, fb);
		}
	}

	public void setUniform(final int locationIn, final Vector3f vectorIn)
	{
		GL20.glUniform3f(locationIn, vectorIn.x(), vectorIn.y(), vectorIn.z());
	}

	public void setUniform(final int locationIn, final fr.seynax.onsiea.utils.maths.vector.Matrix4f matrixIn)
	{
		matrixIn.store(ShaderProgram.matrixBuffer);
		ShaderProgram.matrixBuffer.flip();
		GL20.glUniformMatrix4fv(locationIn, false, ShaderProgram.matrixBuffer);
		ShaderProgram.matrixBuffer.clear();
	}

	public void cleanup()
	{
		ShaderProgram.stop();

		GL20.glDetachShader(this.getProgramId(), this.getVertexShaderId());
		GL20.glDetachShader(this.getProgramId(), this.getFragmentShaderId());

		GL20.glDeleteShader(this.getVertexShaderId());
		GL20.glDeleteShader(this.getFragmentShaderId());

		GL20.glDeleteProgram(this.getProgramId());
	}

	public int getProgramId()
	{
		return this.programId;
	}

	public void setProgramId(final int programIdIn)
	{
		this.programId = programIdIn;
	}

	public int getVertexShaderId()
	{
		return this.vertexShaderId;
	}

	public void setVertexShaderId(final int vertexShaderIdIn)
	{
		this.vertexShaderId = vertexShaderIdIn;
	}

	public int getFragmentShaderId()
	{
		return this.fragmentShaderId;
	}

	public void setFragmentShaderId(final int fragmentShaderIdIn)
	{
		this.fragmentShaderId = fragmentShaderIdIn;
	}
}