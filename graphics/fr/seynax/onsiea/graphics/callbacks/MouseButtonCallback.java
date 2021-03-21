package fr.seynax.onsiea.graphics.callbacks;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class MouseButtonCallback implements GLFWMouseButtonCallbackI
{
	private Map<Integer, Integer> buttonsEvents;

	// Constructor

	public MouseButtonCallback()
	{
		this.setButtonsEvents(new HashMap<>());
	}

	// Interface methdos

	@Override
	public void invoke(final long windowIn, final int buttonIn, final int actionIn, final int modsIn)
	{
		this.getButtonsEvents().put(buttonIn, actionIn);
	}

	// Methods

	public void reset()
	{
		this.getButtonsEvents().clear();
	}

	public boolean isPress(final int buttonIn)
	{
		if (!this.getButtonsEvents().containsKey(buttonIn))
		{
			return false;
		}

		return this.getButtonsEvents().get(buttonIn) == GLFW.GLFW_PRESS;
	}

	public boolean isRepeat(final int buttonIn)
	{
		if (!this.getButtonsEvents().containsKey(buttonIn))
		{
			return false;
		}

		return this.getButtonsEvents().get(buttonIn) == GLFW.GLFW_REPEAT;
	}

	public boolean isRelease(final int buttonIn)
	{
		if (!this.getButtonsEvents().containsKey(buttonIn))
		{
			return false;
		}

		return this.getButtonsEvents().get(buttonIn) == GLFW.GLFW_RELEASE;
	}

	// Getter | Setter

	public Map<Integer, Integer> getButtonsEvents()
	{
		return this.buttonsEvents;
	}

	public void setButtonsEvents(final Map<Integer, Integer> buttonsEventsIn)
	{
		this.buttonsEvents = buttonsEventsIn;
	}
}