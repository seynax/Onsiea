package fr.seynax.onsiea.entity;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.maths.MathsInstances;
import fr.seynax.onsiea.utils.maths.Maths;
import fr.seynax.onsiea.utils.maths.vector.Vector3f;

public class Camera
{
	// Variables

	private Matrix4f	viewMatrix;

	private Vector3f	position;
	private Vector3f	orientation;

	private boolean		canUpdate;

	// Constructor

	public Camera()
	{
		this.setViewMatrix(new Matrix4f());
		this.setPosition(new Vector3f());
		this.setOrientation(new Vector3f());
		this.setCanUpdate(true);
	}

	public Camera(final float xIn, final float yIn, final float zIn)
	{
		this.setViewMatrix(new Matrix4f());
		this.setPosition(new Vector3f(xIn, yIn, zIn));
		this.setOrientation(new Vector3f());
		this.setCanUpdate(true);
	}

	// Methods

	public void update(final double intervalIn, final IWindow windowIn)
	{
		if (!this.isCanUpdate())
		{
			return;
		}

		var	currentRotateSpeed	= (float) (0.05f * intervalIn);
		var	currentSpeed		= (float) (40.00f * intervalIn);

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS)
		{
			currentSpeed		*= 1.5f;
			currentRotateSpeed	*= 1.5f;
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS)
		{
			currentSpeed		/= 1.5f;
			currentRotateSpeed	/= 1.5f;
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_Q) == GLFW.GLFW_PRESS)
		{
			this.rotate(currentRotateSpeed, 0.0f, 0.0f);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_E) == GLFW.GLFW_PRESS)
		{
			this.rotate(-currentRotateSpeed, 0.0f, 0.0f);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_R) == GLFW.GLFW_PRESS)
		{
			this.rotate(0, currentRotateSpeed, 0.0f);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_F) == GLFW.GLFW_PRESS)
		{
			this.rotate(0, -currentRotateSpeed, 0.0f);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_Z) == GLFW.GLFW_PRESS)
		{
			this.rotate(0.0f, 0.0f, currentRotateSpeed);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS)
		{
			this.rotate(0.0f, 0.0f, -currentRotateSpeed);
		}

		this.rotate(
				(float) (windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getTranslation().x() * currentRotateSpeed),
				(float) (windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback().getCursor()
						.getTranslation().y() * currentRotateSpeed),
				0);

		if (this.getOrientation().getX() < -90)
		{
			this.getOrientation().setX(-90);
		}
		if (this.getOrientation().getX() > 90)
		{
			this.getOrientation().setX(90);
		}

		// this.getOrientation().setX(this.getOrientation().getX() % 360);
		this.getOrientation().setY(this.getOrientation().getY() % 360);

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_T) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().setX(0);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_Y) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().setY(0);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_U) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().setZ(0);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_I) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().setX(0);
			this.getOrientation().setY(0);
			this.getOrientation().setZ(0);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin(this.getOrientation().getY() * MathsInstances.getPi180()) * currentSpeed, 0.0f,
					(float) -Math.cos(this.getOrientation().getY() * MathsInstances.getPi180()) * currentSpeed);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
		{
			this.move((float) -Math.sin(this.getOrientation().getY() * MathsInstances.getPi180()) * currentSpeed, 0.0f,
					(float) Math.cos(this.getOrientation().getY() * MathsInstances.getPi180()) * currentSpeed);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin((-this.getOrientation().getY() - 90) * MathsInstances.getPi180()) * currentSpeed,
					0.0f,
					(float) -Math.cos((-this.getOrientation().getY() - 90) * MathsInstances.getPi180()) * currentSpeed);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin((-this.getOrientation().getY() + 90) * MathsInstances.getPi180()) * currentSpeed,
					0.0f,
					(float) -Math.cos((-this.getOrientation().getY() + 90) * MathsInstances.getPi180()) * currentSpeed);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS)
		{
			this.move(0.0f, currentSpeed, 0.0f);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS)
		{
			this.move(0.0f, -currentSpeed, 0.0f);
		}

		Maths.loadViewMatrix(this.getViewMatrix(), this);

		/**
		 * this.getAt().setX(this.getViewMatrix().m02);
		 * this.getAt().setY(this.getViewMatrix().m12);
		 * this.getAt().setZ(this.getViewMatrix().m22);
		 * this.getUp().setX(this.getViewMatrix().m01);
		 * this.getUp().setY(this.getViewMatrix().m11);
		 * this.getUp().setZ(this.getViewMatrix().m21);
		 **/
	}

	public void move(final float deltaXIn, final float deltaYIn, final float deltaZIn)
	{
		this.getPosition().setX(this.getPosition().getX() + deltaXIn);
		this.getPosition().setY(this.getPosition().getY() + deltaYIn);
		this.getPosition().setZ(this.getPosition().getZ() + deltaZIn);
	}

	public void rotate(final float deltaXIn, final float deltaYIn, final float deltaZIn)
	{
		this.getOrientation().setX(this.getOrientation().getX() + deltaXIn);
		this.getOrientation().setY(this.getOrientation().getY() + deltaYIn);
		this.getOrientation().setZ(this.getOrientation().getZ() + deltaZIn);
	}

	// Getter | Setter

	public Matrix4f getViewMatrix()
	{
		return this.viewMatrix;
	}

	public void setViewMatrix(final Matrix4f viewMatrixIn)
	{
		this.viewMatrix = viewMatrixIn;
	}

	public Vector3f getPosition()
	{
		return this.position;
	}

	public void setPosition(final Vector3f positionIn)
	{
		this.position = positionIn;
	}

	public Vector3f getOrientation()
	{
		return this.orientation;
	}

	public void setOrientation(final Vector3f orientationIn)
	{
		this.orientation = orientationIn;
	}

	private boolean isCanUpdate()
	{
		return this.canUpdate;
	}

	public void setCanUpdate(final boolean canUpdateIn)
	{
		this.canUpdate = canUpdateIn;
	}
}