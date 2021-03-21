package fr.seynax.onsiea.utils.maths;

import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.maths.MathsInstances;

public class Maths
{
	// Variables

	private static Random	random;

	private static Matrix4f	projectionMatrix;

	private static Matrix4f	worldMatrix;

	// Methods

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

		final var	negativePosition	= new Vector3f(-cameraIn.getPosition().getX(), -cameraIn.getPosition().getY(),
				-cameraIn.getPosition().getZ());

		viewMatrix.identity().rotateX(cameraIn.getOrientation().getX()).rotateY(cameraIn.getOrientation().getY())
				.rotateZ(cameraIn.getOrientation().getZ()).translate(negativePosition);

		return viewMatrix;
	}

	public static Matrix4f loadViewMatrix(final Matrix4f viewMatrixIn, final Camera cameraIn)
	{
		final var negativePosition = new Vector3f(-cameraIn.getPosition().getX(), -cameraIn.getPosition().getY(),
				-cameraIn.getPosition().getZ());

		viewMatrixIn.identity().rotateX(cameraIn.getOrientation().getX()).rotateY(cameraIn.getOrientation().getY())
				.rotateZ(cameraIn.getOrientation().getZ()).translate(negativePosition);

		return viewMatrixIn;
	}

	public static fr.seynax.onsiea.utils.maths.vector.Matrix4f loadViewMatrix(
			final fr.seynax.onsiea.utils.maths.vector.Matrix4f viewMatrixIn, final float xIn, final float yIn,
			final float zIn, final float rxIn, final float ryIn, final float rzIn)
	{
		viewMatrixIn.setIdentity();

		fr.seynax.onsiea.utils.maths.vector.Matrix4f.rotate((float) Math.toRadians(rxIn), MathsInstances.getAxeX(),
				viewMatrixIn, viewMatrixIn);
		fr.seynax.onsiea.utils.maths.vector.Matrix4f.rotate((float) Math.toRadians(ryIn), MathsInstances.getAxeY(),
				viewMatrixIn, viewMatrixIn);
		fr.seynax.onsiea.utils.maths.vector.Matrix4f.rotate((float) Math.toRadians(rzIn), MathsInstances.getAxeZ(),
				viewMatrixIn, viewMatrixIn);

		final var negativePosition = new fr.seynax.onsiea.utils.maths.vector.Vector3f(-xIn, -yIn, -zIn);
		fr.seynax.onsiea.utils.maths.vector.Matrix4f.translate(negativePosition, viewMatrixIn, viewMatrixIn);

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
}