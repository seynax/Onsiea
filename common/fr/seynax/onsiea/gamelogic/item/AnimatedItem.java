package fr.seynax.onsiea.gamelogic.item;

import org.joml.Vector3f;

import fr.seynax.onsiea.graphics.matter.Mesh;

public class AnimatedItem
{
	// Constructor variables

	private GameItem	gameItem;

	private Vector3f	translationSpeed;
	private Vector3f	rotationSpeed;

	// Constructor

	public AnimatedItem(final GameItem gameItemIn)
	{
		this.setGameItem(gameItemIn);

		this.setTranslationSpeed(new Vector3f(0.0f, 0.0f, 0.0f));

		this.setRotationSpeed(new Vector3f(0.0f, 0.0f, 0.0f));
	}

	public AnimatedItem(final GameItem gameItemIn, final Vector3f translationSpeedIn)
	{
		this.setGameItem(gameItemIn);

		this.setTranslationSpeed(translationSpeedIn);

		this.setRotationSpeed(new Vector3f(0.0f, 0.0f, 0.0f));
	}

	public AnimatedItem(final GameItem gameItemIn, final Vector3f translationSpeedIn, final Vector3f rotationSpeedIn)
	{
		this.setGameItem(gameItemIn);

		this.setTranslationSpeed(translationSpeedIn);

		this.setRotationSpeed(rotationSpeedIn);
	}

	// Methods

	public void setTranslationSpeed(final float xIn, final float yIn, final float zIn)
	{
		this.getTranslationSpeed().x	= xIn;
		this.getTranslationSpeed().y	= yIn;
		this.getTranslationSpeed().z	= zIn;
	}

	public void setRotationSpeed(final float xIn, final float yIn, final float zIn)
	{
		this.getRotationSpeed().x	= xIn;
		this.getRotationSpeed().y	= yIn;
		this.getRotationSpeed().z	= zIn;
	}

	// Delegated Methods

	public GameItem getGameItem()
	{
		return this.gameItem;
	}

	public Vector3f getPosition()
	{
		return this.getGameItem().getPosition();
	}

	public void setPosition(final float xIn, final float yIn, final float zIn)
	{
		this.getGameItem().setPosition(xIn, yIn, zIn);
	}

	public float getScale()
	{
		return this.getGameItem().getScale();
	}

	public void setScale(final float scaleIn)
	{
		this.getGameItem().setScale(scaleIn);
	}

	public Vector3f getRotation()
	{
		return this.getGameItem().getRotation();
	}

	public void setRotation(final float xIn, final float yIn, final float zIn)
	{
		this.getGameItem().setRotation(xIn, yIn, zIn);
	}

	// Getter | Setter

	public Mesh getMesh()
	{
		return this.getGameItem().getMesh();
	}

	public void setGameItem(final GameItem gameItemIn)
	{
		this.gameItem = gameItemIn;
	}

	public Vector3f getTranslationSpeed()
	{
		return this.translationSpeed;
	}

	public void setTranslationSpeed(final Vector3f translationSpeedIn)
	{
		this.translationSpeed = translationSpeedIn;
	}

	public Vector3f getRotationSpeed()
	{
		return this.rotationSpeed;
	}

	public void setRotationSpeed(final Vector3f rotationSpeedIn)
	{
		this.rotationSpeed = rotationSpeedIn;
	}
}
