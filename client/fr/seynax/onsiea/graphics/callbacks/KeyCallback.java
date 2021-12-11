package fr.seynax.onsiea.graphics.callbacks;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyCallback implements GLFWKeyCallbackI
{
	// Variables

	private Map<Integer, Integer>	hasPress;
	private Map<Integer, Integer>	press;
	private Map<Integer, Integer>	repeat;
	private Map<Integer, Integer>	hasRelease;

	// Constructor

	public KeyCallback()
	{
		this.setHasPress(new HashMap<>());
		this.setPress(new HashMap<>());
		this.setRepeat(new HashMap<>());
		this.setHasRelease(new HashMap<>());
	}

	// Interface methods

	@Override
	public void invoke(final long windowIn, final int keyIn, final int scancodeIn, final int actionIn, final int modsIn)
	{
		if (keyIn == GLFW.GLFW_KEY_ESCAPE && actionIn == GLFW.GLFW_RELEASE)
		{
			GLFW.glfwSetWindowShouldClose(windowIn, true); // We will detect this in the rendering loop
		}

		if (actionIn == GLFW.GLFW_PRESS)
		{
			this.getHasPress().put(keyIn, keyIn);
		}
		else if (actionIn == GLFW.GLFW_REPEAT)
		{
			this.getRepeat().put(keyIn, keyIn);
		}
		else if (actionIn == GLFW.GLFW_RELEASE)
		{
			this.getPress().remove(keyIn);
			this.getRepeat().remove(keyIn);

			this.getHasRelease().put(keyIn, keyIn);
		}
	}

	// methods

	public void reset()
	{
		// Press

		for (final int keycode : this.getHasPress().keySet())
		{
			if (this.getPress().containsKey(keycode))
			{
				continue;
			}

			this.getPress().put(keycode, keycode);
		}

		this.getHasPress().clear();

		// Release

		this.getHasRelease().clear();
	}

	public boolean isHasPress(final int keyCodeIn)
	{
		return this.getHasPress().containsKey(keyCodeIn);
	}

	public boolean isPress(final int keyCodeIn)
	{
		return this.getPress().containsKey(keyCodeIn);
	}

	public boolean isRepeat(final int keyCodeIn)
	{
		return this.getRepeat().containsKey(keyCodeIn);
	}

	public boolean isHasRelease(final int keyCodeIn)
	{
		return this.getHasRelease().containsKey(keyCodeIn);
	}

	// Getter | Setter

	private Map<Integer, Integer> getHasPress()
	{
		return this.hasPress;
	}

	private void setHasPress(final Map<Integer, Integer> hasPressIn)
	{
		this.hasPress = hasPressIn;
	}

	private Map<Integer, Integer> getPress()
	{
		return this.press;
	}

	private void setPress(final Map<Integer, Integer> pressIn)
	{
		this.press = pressIn;
	}

	private Map<Integer, Integer> getRepeat()
	{
		return this.repeat;
	}

	private void setRepeat(final Map<Integer, Integer> repeatIn)
	{
		this.repeat = repeatIn;
	}

	private Map<Integer, Integer> getHasRelease()
	{
		return this.hasRelease;
	}

	private void setHasRelease(final Map<Integer, Integer> hasReleaseIn)
	{
		this.hasRelease = hasReleaseIn;
	}
}