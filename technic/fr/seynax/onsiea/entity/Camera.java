package fr.seynax.onsiea.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.gamelogic.logic.LogicConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.maths.MathsInstances;
import fr.seynax.onsiea.utils.maths.Maths;

public class Camera
{
	// Variables

	private Matrix4f	viewMatrix;

	private Vector3f	position;
	private Vector3f	orientation;

	private Vector3f	chunkPosition;

	private boolean		canUpdate;
	private boolean		hasMoveChunk;

	// Constructor

	public Camera()
	{
		this.setChunkPosition(new Vector3f());

		this.setViewMatrix(new Matrix4f());
		this.setPosition(new Vector3f());
		this.setOrientation(new Vector3f());
		this.setCanUpdate(true);
	}

	public Camera(final float xIn, final float yIn, final float zIn)
	{
		this.setChunkPosition(new Vector3f((int) xIn, (int) yIn, (int) zIn));

		this.setViewMatrix(new Matrix4f());
		this.setPosition(new Vector3f(xIn, yIn, zIn));
		this.setOrientation(new Vector3f());
		this.setCanUpdate(true);
	}

	// Methods

	public void update(final IWindow windowIn, final double intervalIn)
	{
		if (!this.isCanUpdate())
		{
			return;
		}

		var	currentRotateSpeed	= (float) (0.090f * intervalIn);
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

		if (this.getOrientation().x() < -90)
		{
			this.getOrientation().x = -90;
		}
		if (this.getOrientation().x() > 90)
		{
			this.getOrientation().x = 90;
		}

		// this.getOrientation().x = this.getOrientation().x() % 360;
		this.getOrientation().y = this.getOrientation().y() % 360;

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_T) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().x = 0;
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_Y) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().y = 0;
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_U) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().z = 0;
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_I) == GLFW.GLFW_PRESS)
		{
			this.getOrientation().x	= 0;
			this.getOrientation().y	= 0;
			this.getOrientation().z	= 0;
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin(this.getOrientation().y() * MathsInstances.getPi180()) * currentSpeed, 0.0f,
					(float) -Math.cos(this.getOrientation().y() * MathsInstances.getPi180()) * currentSpeed);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
		{
			this.move((float) -Math.sin(this.getOrientation().y() * MathsInstances.getPi180()) * currentSpeed, 0.0f,
					(float) Math.cos(this.getOrientation().y() * MathsInstances.getPi180()) * currentSpeed);
		}

		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin((-this.getOrientation().y() - 90) * MathsInstances.getPi180()) * currentSpeed,
					0.0f,
					(float) -Math.cos((-this.getOrientation().y() - 90) * MathsInstances.getPi180()) * currentSpeed);
		}
		if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS)
		{
			this.move((float) Math.sin((-this.getOrientation().y() + 90) * MathsInstances.getPi180()) * currentSpeed,
					0.0f,
					(float) -Math.cos((-this.getOrientation().y() + 90) * MathsInstances.getPi180()) * currentSpeed);
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
		 * this.getAt().x(this.getViewMatrix().m02);
		 * this.getAt().y(this.getViewMatrix().m12);
		 * this.getAt().z(this.getViewMatrix().m22);
		 * this.getUp().x(this.getViewMatrix().m01);
		 * this.getUp().y(this.getViewMatrix().m11);
		 * this.getUp().z(this.getViewMatrix().m21);
		 **/

		if ((int) (this.getPosition().x() / LogicConstants.Chunk.getMaxX()) != this.getChunkPosition().x()
				/**
				 * || (int) (this.getPosition().y() / LogicConstants.Chunk.getMaxY()) !=
				 * this.getChunkPosition().y()
				 **/
				|| (int) (this.getPosition().z() / LogicConstants.Chunk.getMaxZ()) != this.getChunkPosition().z())
		{
			this.setHasMoveChunk(true);

			this.getChunkPosition().x	= (int) (this.getPosition().x() / LogicConstants.Chunk.getMaxX());
			/**
			 * this.getChunkPosition().y = (int) (this.getPosition().y() /
			 * LogicConstants.Chunk.getMaxY());
			 **/
			this.getChunkPosition().z	= (int) (this.getPosition().z() / LogicConstants.Chunk.getMaxZ());
		}
	}

	public void reset()
	{
		this.setHasMoveChunk(false);
	}

	public void move(final float deltaXIn, final float deltaYIn, final float deltaZIn)
	{
		this.getPosition().x	= this.getPosition().x() + deltaXIn;
		this.getPosition().y	= this.getPosition().y() + deltaYIn;
		this.getPosition().z	= this.getPosition().z() + deltaZIn;
	}

	public void rotate(final float deltaXIn, final float deltaYIn, final float deltaZIn)
	{
		this.getOrientation().x	= this.getOrientation().x() + deltaXIn;
		this.getOrientation().y	= this.getOrientation().y() + deltaYIn;
		this.getOrientation().z	= this.getOrientation().z() + deltaZIn;
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

	public boolean isCanUpdate()
	{
		return this.canUpdate;
	}

	public void setCanUpdate(final boolean canUpdateIn)
	{
		this.canUpdate = canUpdateIn;
	}

	public Vector3f getChunkPosition()
	{
		return this.chunkPosition;
	}

	private void setChunkPosition(final Vector3f chunkPositionIn)
	{
		this.chunkPosition = chunkPositionIn;
	}

	public boolean isHasMoveChunk()
	{
		return this.hasMoveChunk;
	}

	private void setHasMoveChunk(final boolean hasMoveChunkIn)
	{
		this.hasMoveChunk = hasMoveChunkIn;
	}
}