package fr.seynax.onsiea.graphics.callbacks;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class MouseButtonCallback implements GLFWMouseButtonCallbackI
{
	// Variables

	private Map<Integer, Long>	hasPress;
	private Map<Integer, Long>	press;
	private Map<Integer, Long>	repeat;
	private Map<Integer, Long>	hasRelease;

	// Constructor

	public MouseButtonCallback()
	{
		this.setHasPress(new HashMap<>());
		this.setPress(new HashMap<>());
		this.setRepeat(new HashMap<>());
		this.setHasRelease(new HashMap<>());
	}

	// Interface methdos

	@Override
	public void invoke(final long windowIn, final int buttonIn, final int actionIn, final int modsIn)
	{
		final var time = System.nanoTime();

		if (actionIn == GLFW.GLFW_PRESS)
		{
			this.getHasPress().put(buttonIn, time);
		}
		else if (actionIn == GLFW.GLFW_REPEAT)
		{
			this.getRepeat().put(buttonIn, time);
		}
		else if (actionIn == GLFW.GLFW_RELEASE)
		{
			this.getPress().remove(buttonIn);
			this.getRepeat().remove(buttonIn);

			this.getHasRelease().put(buttonIn, time);
		}
	}

	// Methods

	public void reset()
	{
		// Press

		final var iterator = this.getHasPress().entrySet().iterator();

		while (iterator.hasNext())
		{
			final var	entry	= iterator.next();

			final int	keycode	= entry.getKey();

			if (this.getPress().containsKey(keycode))
			{
				continue;
			}

			final long time = entry.getValue();

			this.getPress().put(keycode, time);
		}

		this.getHasPress().clear();

		// Release

		this.getHasRelease().clear();
	}

	public boolean isHasPress(final int keyCodeIn)
	{
		return this.getHasPress().containsKey(keyCodeIn);
	}

	public long getTimeIsHasPress(final int keyCodeIn)
	{
		if (!this.getHasPress().containsKey(keyCodeIn))
		{
			return -1;
		}

		return this.getHasPress().get(keyCodeIn);
	}

	public boolean isPress(final int keyCodeIn)
	{
		return this.getPress().containsKey(keyCodeIn);
	}

	public long getTimeIsPress(final int keyCodeIn)
	{
		if (!this.getPress().containsKey(keyCodeIn))
		{
			return -1;
		}

		return this.getPress().get(keyCodeIn);
	}

	public boolean isRepeat(final int keyCodeIn)
	{
		return this.getRepeat().containsKey(keyCodeIn);
	}

	public long getTimeIsRepeat(final int keyCodeIn)
	{
		if (!this.getRepeat().containsKey(keyCodeIn))
		{
			return -1;
		}

		return this.getRepeat().get(keyCodeIn);
	}

	public boolean isHasRelease(final int keyCodeIn)
	{
		return this.getHasRelease().containsKey(keyCodeIn);
	}

	public long getTimeIsRelease(final int keyCodeIn)
	{
		if (!this.getHasRelease().containsKey(keyCodeIn))
		{
			return -1;
		}

		return this.getHasRelease().get(keyCodeIn);
	}

	// Getter | Setter

	private Map<Integer, Long> getHasPress()
	{
		return this.hasPress;
	}

	private void setHasPress(final Map<Integer, Long> hasPressIn)
	{
		this.hasPress = hasPressIn;
	}

	private Map<Integer, Long> getPress()
	{
		return this.press;
	}

	private void setPress(final Map<Integer, Long> pressIn)
	{
		this.press = pressIn;
	}

	private Map<Integer, Long> getRepeat()
	{
		return this.repeat;
	}

	private void setRepeat(final Map<Integer, Long> repeatIn)
	{
		this.repeat = repeatIn;
	}

	private Map<Integer, Long> getHasRelease()
	{
		return this.hasRelease;
	}

	private void setHasRelease(final Map<Integer, Long> hasReleaseIn)
	{
		this.hasRelease = hasReleaseIn;
	}
}