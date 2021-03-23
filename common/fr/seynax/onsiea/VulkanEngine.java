package fr.seynax.onsiea;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.VulkanBuffer;
import fr.seynax.onsiea.vulkan.VulkanCommandPool;
import fr.seynax.onsiea.vulkan.VulkanDescriptor;
import fr.seynax.onsiea.vulkan.VulkanDevice;
import fr.seynax.onsiea.vulkan.VulkanInstance;
import fr.seynax.onsiea.vulkan.VulkanPhysicalDevice;
import fr.seynax.onsiea.vulkan.VulkanSwapChain;
import fr.seynax.onsiea.vulkan.VulkanWindow;
import fr.seynax.onsiea.vulkan.VulkanWindowSurface;

public class VulkanEngine implements Runnable
{
	// Variables

	private IWindow					window;
	private int						targetUps		= 0;
	private float					targetUpsTiming	= 0.0f;
	private Thread					gameLoopThread;
	private boolean					isRunning;

	// Variables ; Vulkan

	private VulkanInstance			instance;
	private VulkanPhysicalDevice	physicalDevice;
	private VulkanDevice			device;
	private VulkanCommandPool		commandPool;
	private VulkanBuffer			vulkanBuffer;
	private VulkanDescriptor		vulkanDescriptor;
	private VulkanWindowSurface		vulkanWindowSurface;
	private VulkanSwapChain			vulkanSwapChain;

	// Constructor

	public VulkanEngine()
	{

	}

	// Methods

	private void initialization()
	{

		this.setWindow(new VulkanWindow(1920, 1080, "Vulkan !", 60, true, 1));

		this.setTargetUps(50);
		this.setTargetUpsTiming(1.0f / this.getTargetUps());

		this.getWindow().initialization(this.getTargetUps());

		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		/**
		 * Vulkan
		 */

		// Vulkan ; instance

		this.setInstance(new VulkanInstance("Aeison", "Onsiea"));

		// Vulkan ; physical device

		this.setPhysicalDevice(this.getInstance().createPhysicalDevice());

		// Vulkan ; device

		this.setDevice(this.getPhysicalDevice().createLogicalDevice());

		// Vulkan ; command pool

		this.setCommandPool(this.getDevice().createCommandPool());

		// Vulkan ; buffer

		/**
		 * Example Data
		 */

		final var data = new int[64];

		for (var i = 0; i < data.length; i++)
		{
			data[i] = i;
		}

		/**
		 *
		 */

		this.setVulkanBuffer(this.getDevice().createBuffer(data));

		this.setVulkanDescriptor(this.getVulkanBuffer().createDescriptor());

		this.setVulkanWindowSurface(this.getDevice().createVulkanWindowSurface(this.getWindow()));

		/**
		 * Fin vulkan
		 */
	}

	public void start()
	{
		this.setGameLoopThread(new Thread(this));

		final var osName = System.getProperty("os.name");

		if (osName.contains("Mac"))
		{
			this.getGameLoopThread().run();
		}
		else
		{
			this.getGameLoopThread().start();
		}
	}

	@Override
	public void run()
	{
		try
		{
			this.initialization();

			this.setRunning(true);

			// this.gameLoop();
		}
		catch (final Exception excp)
		{
			excp.printStackTrace();
		}
		finally
		{

			this.cleanup();
		}
	}

	@SuppressWarnings("unused")
	private void gameLoop()
	{
		while (this.isRunning() && !this.getWindow().windowShouldClose())
		{
			this.input();

			this.update();

			this.startRender();

			this.render();

			this.endRender();
		}
	}

	private void input()
	{
		this.getWindow().getGlfwEventManager().pollEvents();
	}

	private void update()
	{

	}

	private void startRender()
	{

	}

	private void render()
	{

	}

	private void endRender()
	{
		this.getWindow().updateRender();
	}

	private void cleanup()
	{
		this.getVulkanDescriptor().cleanup(this.getDevice());
		this.getVulkanBuffer().cleanup(this.getDevice());
		this.getCommandPool().cleanup(this.getDevice());
		this.getDevice().cleanup();
	}

	// Getter | Setter

	private IWindow getWindow()
	{
		return this.window;
	}

	@SuppressWarnings("unused")
	private void setWindow(final IWindow windowIn)
	{
		this.window = windowIn;
	}

	private int getTargetUps()
	{
		return this.targetUps;
	}

	private void setTargetUps(final int targetUpsIn)
	{
		this.targetUps = targetUpsIn;
	}

	float getTargetUpsTiming()
	{
		return this.targetUpsTiming;
	}

	private void setTargetUpsTiming(final float targetUpsTimingIn)
	{
		this.targetUpsTiming = targetUpsTimingIn;
	}

	private Thread getGameLoopThread()
	{
		return this.gameLoopThread;
	}

	private void setGameLoopThread(final Thread gameLoopThreadIn)
	{
		this.gameLoopThread = gameLoopThreadIn;
	}

	private boolean isRunning()
	{
		return this.isRunning;
	}

	private void setRunning(final boolean isRunningIn)
	{
		this.isRunning = isRunningIn;
	}

	// Getter | Setter ; Vulkan

	public VulkanInstance getInstance()
	{
		return this.instance;
	}

	public void setInstance(final VulkanInstance instanceIn)
	{
		this.instance = instanceIn;
	}

	public VulkanPhysicalDevice getPhysicalDevice()
	{
		return this.physicalDevice;
	}

	public void setPhysicalDevice(final VulkanPhysicalDevice physicalDeviceIn)
	{
		this.physicalDevice = physicalDeviceIn;
	}

	public VulkanDevice getDevice()
	{
		return this.device;
	}

	public void setDevice(final VulkanDevice deviceIn)
	{
		this.device = deviceIn;
	}

	public VulkanCommandPool getCommandPool()
	{
		return this.commandPool;
	}

	public void setCommandPool(final VulkanCommandPool commandPoolIn)
	{
		this.commandPool = commandPoolIn;
	}

	public VulkanBuffer getVulkanBuffer()
	{
		return this.vulkanBuffer;
	}

	public void setVulkanBuffer(final VulkanBuffer vulkanBufferIn)
	{
		this.vulkanBuffer = vulkanBufferIn;
	}

	public VulkanDescriptor getVulkanDescriptor()
	{
		return this.vulkanDescriptor;
	}

	public void setVulkanDescriptor(final VulkanDescriptor vulkanDescriptorIn)
	{
		this.vulkanDescriptor = vulkanDescriptorIn;
	}

	public VulkanWindowSurface getVulkanWindowSurface()
	{
		return this.vulkanWindowSurface;
	}

	public void setVulkanWindowSurface(final VulkanWindowSurface vulkanWindowSurfaceIn)
	{
		this.vulkanWindowSurface = vulkanWindowSurfaceIn;
	}

	public VulkanSwapChain getVulkanSwapChain()
	{
		return this.vulkanSwapChain;
	}

	public void setVulkanSwapChain(final VulkanSwapChain vulkanSwapChainIn)
	{
		this.vulkanSwapChain = vulkanSwapChainIn;
	}
}