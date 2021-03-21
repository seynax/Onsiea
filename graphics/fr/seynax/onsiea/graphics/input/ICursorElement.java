package fr.seynax.onsiea.graphics.input;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

public interface ICursorElement
{
	void update(Cursor cursorIn, ICursorExtension cursorExtensionIn, IWindow windowIn);

	// Position

	void move(Vector2f deltaIn);

	void move(float deltaXIn, float deltaYIn);

	void setPosition(Vector2f positionIn);

	void setPosition(float xIn, float yIn);

	Vector2f getPosition();

	// Rotation

	void rotate(Vector2f deltaIn);

	void rotate(float deltaXIn, float deltaYIn);

	void setRotation(Vector2f rotationIn);

	void setRotation(float xIn, float yIn);

	Vector2f getRotation();

	// Size

	void setSize(Vector2f sizeIn);

	void setSize(float xIn, float yIn);

	void resize(Vector2f deltaIn);

	void resize(float deltaXIn, float deltaYIn);

	Vector2f getSize();
}
