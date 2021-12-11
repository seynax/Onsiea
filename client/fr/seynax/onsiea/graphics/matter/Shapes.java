package fr.seynax.onsiea.graphics.matter;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Shapes
{
	// Static variables

	private static int				rectangleVaoId;

	private static int				rectangleVboId;

	private static int				rectangleIndicesId;

	// Constants

	// Graphics Data - Mesh

	private final static float[]	surface2D					= new float[] { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
			0.5f, 0.5f };

	private final static int[]		surface2DIndices			= new int[] { 0, 1, 3, 3, 1, 2 };

	private final static float[]	triangle					= new float[] { 0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f };

	private final static float[]	surface						= new float[] { -0.5f, 0.5f, -1.6f, -0.5f, -0.5f, -1.6f,
			0.5f, -0.5f, -1.6f, 0.5f, 0.5f, -1.6f };

	private final static float[]	surfaceColours				= new float[] { 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f };

	private final static float[]	surfaceTextureCoordinates	= new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };

	private final static int[]		surfaceIndices				= new int[] { 0, 1, 3, 3, 1, 2 };

	private final static Shape		SHAPE_SURFACE				= new Shape(Shapes.getSurface(),
			Shapes.getSurfaceTextureCoordinates(), Shapes.getSurface2dindices());

	private final static float[]	cube						= new float[] {
			// VO
			-0.5f, 0.5f, 0.5f,
			// V1
			-0.5f, -0.5f, 0.5f,
			// V2
			0.5f, -0.5f, 0.5f,
			// V3
			0.5f, 0.5f, 0.5f,
			// V4
			-0.5f, 0.5f, -0.5f,
			// V5
			0.5f, 0.5f, -0.5f,
			// V6
			-0.5f, -0.5f, -0.5f,
			// V7
			0.5f, -0.5f, -0.5f };

	private final static float[]	cubeColours					= new float[] { 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.5f,
			0.5f };

	private final static int[]		cubeIndices					= new int[] {
			// Front face
			0, 1, 3, 3, 1, 2,
			// Top Face
			4, 0, 3, 5, 4, 3,
			// Right face
			3, 2, 7, 5, 3, 7,
			// Left face
			6, 1, 0, 6, 0, 4,
			// Bottom face
			2, 1, 6, 2, 6, 7,
			// Back face
			7, 6, 4, 7, 4, 5 };

	private final static Shape		SHAPE_CUBE					= new Shape(Shapes.getCube(), null,
			Shapes.getCubeindices());

	// Methods

	public final static void initialization()
	{
		// Vao

		Shapes.setRectangleVaoId(GL30.glGenVertexArrays());

		GL30.glBindVertexArray(Shapes.getRectangleVaoId());

		// Vbo

		GL20.glEnableVertexAttribArray(0);

		Shapes.setRectangleVboId(GL15.glGenBuffers());

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, Shapes.getRectangleVboId());

		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Shapes.getSurface2d(), GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);

		// Indices

		Shapes.setRectangleIndicesId(GL15.glGenBuffers());

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, Shapes.getRectangleIndicesId());

		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Shapes.getSurface2dindices(), GL15.GL_STATIC_DRAW);

		// Unbind Vao

		GL30.glBindVertexArray(0);
	}

	// Constants getter

	// Surface2D

	public static float[] getSurface2d()
	{
		return Shapes.surface2D;
	}

	public static int[] getSurface2dindices()
	{
		return Shapes.surface2DIndices;
	}

	// Triangle

	public static float[] getTriangle()
	{
		return Shapes.triangle;
	}

	// Surface

	public static float[] getSurface()
	{
		return Shapes.surface;
	}

	public static float[] getSurfaceTextureCoordinates()
	{
		return Shapes.surfaceTextureCoordinates;
	}

	public static float[] getSurfaceColours()
	{
		return Shapes.surfaceColours;
	}

	public static int[] getSurfaceIndices()
	{
		return Shapes.surfaceIndices;
	}

	public final static Shape getShapeSurface()
	{
		return Shapes.SHAPE_SURFACE;
	}

	// Cube

	public static float[] getCube()
	{
		return Shapes.cube;
	}

	public static float[] getCubecolours()
	{
		return Shapes.cubeColours;
	}

	public static int[] getCubeindices()
	{
		return Shapes.cubeIndices;
	}

	public final static Shape getShapeCube()
	{
		return Shapes.SHAPE_CUBE;
	}

	// Static Variables

	// Rectangle graphics components

	public static int getRectangleVaoId()
	{
		return Shapes.rectangleVaoId;
	}

	public static void setRectangleVaoId(final int rectangleVaoIdIn)
	{
		Shapes.rectangleVaoId = rectangleVaoIdIn;
	}

	public static int getRectangleVboId()
	{
		return Shapes.rectangleVboId;
	}

	public static void setRectangleVboId(final int rectangleVboIdIn)
	{
		Shapes.rectangleVboId = rectangleVboIdIn;
	}

	public static int getRectangleIndicesId()
	{
		return Shapes.rectangleIndicesId;
	}

	public static void setRectangleIndicesId(final int rectangleIndicesIdIn)
	{
		Shapes.rectangleIndicesId = rectangleIndicesIdIn;
	}
}