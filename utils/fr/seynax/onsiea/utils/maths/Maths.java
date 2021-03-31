package fr.seynax.onsiea.utils.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;

public class Maths
{
	// Variables

	private static Random	random;

	private static Matrix4f	projectionMatrix;

	private static Matrix4f	worldMatrix;

	// Methods

	public static double round(final double value, final int places)
	{
		if (places < 0)
		{
			throw new IllegalArgumentException();
		}

		var bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public final static void initialization(final IWindow windowIn)
	{
		Maths.setProjectionMatrix(Maths.projectionMatrix(windowIn.getWidth(), windowIn.getHeight(),
				GraphicsConstants.getFov(), GraphicsConstants.getZNear(), GraphicsConstants.getZFar()));

		Maths.setWorldMatrix(new Matrix4f());

		final var	random	= new Random();

		final var	seed	= random.nextLong();

		random.setSeed(seed);

		Maths.setRandom(random);
	}

	public static Matrix4f projectionMatrix(final float widthIn, final float heightIn, final float fovIn,
			final float zNearIn, final float zFarIn)
	{
		final var	aspectRatio			= widthIn / heightIn;

		final var	projectionMatrix	= new Matrix4f();

		projectionMatrix.identity();

		projectionMatrix.perspective(fovIn, aspectRatio, zNearIn, zFarIn);

		return projectionMatrix;
	}

	public static Matrix4f getWorldMatrix(final Vector3f offsetIn, final Vector3f rotationIn, final float scaleIn)
	{
		Maths.getWorldMatrix().identity().translate(offsetIn).rotateX((float) Math.toRadians(rotationIn.x))
				.rotateY((float) Math.toRadians(rotationIn.y)).rotateZ((float) Math.toRadians(rotationIn.z))
				.scale(scaleIn);

		return Maths.getWorldMatrix();
	}

	public static Matrix4f getWorldMatrix(final Vector3f offsetIn, final Vector3f rotationIn, final Vector3f scaleIn)
	{
		Maths.getWorldMatrix().identity().translate(offsetIn).rotateX((float) Math.toRadians(rotationIn.x))
				.rotateY((float) Math.toRadians(rotationIn.y)).rotateZ((float) Math.toRadians(rotationIn.z))
				.scale(scaleIn);

		return Maths.getWorldMatrix();
	}

	public final static Matrix4f createViewMatrix(final Camera cameraIn)
	{
		final var	viewMatrix			= new Matrix4f();

		final var	negativePosition	= new Vector3f(-cameraIn.getPosition().x(), -cameraIn.getPosition().y(),
				-cameraIn.getPosition().z());

		viewMatrix.identity().rotateX(cameraIn.getOrientation().x()).rotateY(cameraIn.getOrientation().y())
				.rotateZ(cameraIn.getOrientation().z()).translate(negativePosition);

		return viewMatrix;
	}

	public static Matrix4f loadViewMatrix(final Matrix4f viewMatrixIn, final Camera cameraIn)
	{
		final var negativePosition = new Vector3f(-cameraIn.getPosition().x(), -cameraIn.getPosition().y(),
				-cameraIn.getPosition().z());

		viewMatrixIn.identity().rotateX(cameraIn.getOrientation().x()).rotateY(cameraIn.getOrientation().y())
				.rotateZ(cameraIn.getOrientation().z()).translate(negativePosition);

		return viewMatrixIn;
	}

	public static Matrix4f loadViewMatrix(final Matrix4f viewMatrixIn, final float xIn, final float yIn,
			final float zIn, final float rxIn, final float ryIn, final float rzIn)
	{
		viewMatrixIn.identity();
		final var negativePosition = new Vector3f(-xIn, -yIn, -zIn);

		viewMatrixIn.rotateX((float) Math.toRadians(rxIn)).rotateY((float) Math.toRadians(ryIn))
				.rotateZ((float) Math.toRadians(rzIn)).translate(negativePosition);

		return viewMatrixIn;
	}

	public static int randInt(final int minIn, final int maxIn)
	{
		return Maths.getRandom().nextInt(maxIn - minIn + 1) + minIn;
	}

	public static float randFloat()
	{
		return Maths.getRandom().nextFloat();
	}

	public static Matrix4f copy(final Matrix4f matrixIn)
	{
		final var matrix = new Matrix4f();

		matrix.identity();

		matrix.m00(matrixIn.m00());
		matrix.m01(matrixIn.m01());
		matrix.m02(matrixIn.m02());
		matrix.m03(matrixIn.m03());

		matrix.m10(matrixIn.m10());
		matrix.m11(matrixIn.m11());
		matrix.m12(matrixIn.m12());
		matrix.m13(matrixIn.m13());

		matrix.m20(matrixIn.m20());
		matrix.m21(matrixIn.m21());
		matrix.m22(matrixIn.m22());
		matrix.m23(matrixIn.m23());

		matrix.m30(matrixIn.m30());
		matrix.m31(matrixIn.m31());
		matrix.m32(matrixIn.m32());
		matrix.m33(matrixIn.m33());

		return matrix;
	}

	public static Matrix4f copy(final Matrix4f fromMatrixIn, final Matrix4f toMatrixIn)
	{
		toMatrixIn.identity();

		toMatrixIn.m00(fromMatrixIn.m00());
		toMatrixIn.m01(fromMatrixIn.m01());
		toMatrixIn.m02(fromMatrixIn.m02());
		toMatrixIn.m03(fromMatrixIn.m03());

		toMatrixIn.m10(fromMatrixIn.m10());
		toMatrixIn.m11(fromMatrixIn.m11());
		toMatrixIn.m12(fromMatrixIn.m12());
		toMatrixIn.m13(fromMatrixIn.m13());

		toMatrixIn.m20(fromMatrixIn.m20());
		toMatrixIn.m21(fromMatrixIn.m21());
		toMatrixIn.m22(fromMatrixIn.m22());
		toMatrixIn.m23(fromMatrixIn.m23());

		toMatrixIn.m30(fromMatrixIn.m30());
		toMatrixIn.m31(fromMatrixIn.m31());
		toMatrixIn.m32(fromMatrixIn.m32());
		toMatrixIn.m33(fromMatrixIn.m33());

		return toMatrixIn;
	}

	// Getter | Setter

	public static Matrix4f getProjectionMatrix()
	{
		return Maths.projectionMatrix;
	}

	private static void setProjectionMatrix(final Matrix4f projectionMatrixIn)
	{
		Maths.projectionMatrix = projectionMatrixIn;
	}

	public static Matrix4f getWorldMatrix()
	{
		return Maths.worldMatrix;
	}

	private static void setWorldMatrix(final Matrix4f worldMatrixIn)
	{
		Maths.worldMatrix = worldMatrixIn;
	}

	public static Random getRandom()
	{
		return Maths.random;
	}

	public static void setRandom(final Random randomIn)
	{
		Maths.random = randomIn;
	}
}