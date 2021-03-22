package fr.seynax.onsiea.vulkan;

import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.utils.VKUtil;

// VulkanSurface
// Get window surface thank to GLFW

public class VulkanWindowSurface
{
	// Variables

	private long vulkanWindowSurface;

	// Constructor

	VulkanWindowSurface(final VulkanInstance vulkanInstanceIn, final IWindow windowIn)
	{
		final var	passVulkanSurfacePointer	= MemoryUtil.memAllocLong(1);

		final var	err							= GLFWVulkan.glfwCreateWindowSurface(vulkanInstanceIn.getInstance(),
				windowIn.getWindowHandle(), null, passVulkanSurfacePointer);

		if (err != VK10.VK_SUCCESS)
		{
			throw new AssertionError("Failed to create window surface : " + VKUtil.translateVulkanResult(err));
		}

		this.setVulkanWindowSurface(passVulkanSurfacePointer.get(0));
	}

	// Static methods

	public final static VulkanWindowSurface create(final VulkanInstance vulkanInstanceIn, final IWindow windowIn)

	{
		return new VulkanWindowSurface(vulkanInstanceIn, windowIn);
	}

	// Methods

	// Getter | Setter

	public long getVulkanWindowSurface()
	{
		return this.vulkanWindowSurface;
	}

	public void setVulkanWindowSurface(final long vulkanWindowSurfaceIn)
	{
		this.vulkanWindowSurface = vulkanWindowSurfaceIn;
	}
}