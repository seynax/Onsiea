package fr.seynax.onsiea;

import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.vulkan.Pipeline;
import fr.seynax.onsiea.vulkan.VulkanBuffer;
import fr.seynax.onsiea.vulkan.VulkanCommandPool;
import fr.seynax.onsiea.vulkan.VulkanDevice;
import fr.seynax.onsiea.vulkan.VulkanInstance;
import fr.seynax.onsiea.vulkan.VulkanPhysicalDevice;

public class VulkanEngine implements Runnable
{
	// Variables

	private IWindow					window;
	private int						targetUps		= 0;
	private float					targetUpsTiming	= 0.0f;
	private Thread					gameLoopThread;
	private boolean					isRunning;

	// Variables ; pipeline

	private Pipeline				pipeline;
	private VulkanPhysicalDevice	physicalDevice;
	private VulkanDevice			device;
	private VulkanCommandPool		commandPool;
	private VulkanBuffer			vulkanBuffer;

	// Constructor

	public VulkanEngine()
	{

	}

	// Methods

	private void initialization()
	{
		/**
		 * this.setWindow(new GenericWindow(1920, 1080, "Vulkan !", 60, true, 1, new
		 * VulkanWindowCreator()));
		 **/
		this.setTargetUps(50);
		this.setTargetUpsTiming(1.0f / this.getTargetUps());
		// this.getWindow().initialization(this.getTargetUps());
		{
			if (!GLFW.glfwInit())
			{
				throw new IllegalStateException("Unable to initialize GLFW");
			}
		}

		/**
		 * Vulkan
		 */

		// Vulkan ; instance

		final var	vulkanInstance	= new VulkanInstance("Aeison", "Onsiea");
		final var	instance		= vulkanInstance.initialization();

		// Vulkan ; physical device

		this.setPhysicalDevice(new VulkanPhysicalDevice());

		this.getPhysicalDevice().initialization(instance);

		// Vulkan ; device

		this.setDevice(new VulkanDevice());

		this.getDevice().initiallization(this.getPhysicalDevice());

		// Vulkan ; command pool

		this.setCommandPool(new VulkanCommandPool());

		this.getCommandPool().initialization(this.getPhysicalDevice(), this.getDevice());

		// Vulkan ; buffer

		final var data = new int[64];

		for (var i = 0; i < data.length; i++)
		{
			data[i] = i;
		}

		this.setVulkanBuffer(new VulkanBuffer());

		this.getVulkanBuffer().initialization(this.getPhysicalDevice(), this.getDevice(), data);

		// Vulkan ; Pipeline

		this.setPipeline(
				new Pipeline("resources/shaders/SPIRV/base.vert.spv", "resources/shaders/SPIRV/base.frag.spv"));
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
		this.getVulkanBuffer().cleanup(this.getDevice());
		this.getCommandPool().cleanup(this.getDevice());
		this.getDevice().cleanup();
		this.getPhysicalDevice().cleanup();
	}

	// Getter | Setter

	private IWindow getWindow()
	{
		return this.window;
	}

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

	// Getter | Setter ; pipeline

	Pipeline getPipeline()
	{
		return this.pipeline;
	}

	private void setPipeline(final Pipeline pipelineIn)
	{
		this.pipeline = pipelineIn;
	}

	private VulkanPhysicalDevice getPhysicalDevice()
	{
		return this.physicalDevice;
	}

	private void setPhysicalDevice(final VulkanPhysicalDevice physicalDeviceIn)
	{
		this.physicalDevice = physicalDeviceIn;
	}

	private VulkanDevice getDevice()
	{
		return this.device;
	}

	private void setDevice(final VulkanDevice deviceIn)
	{
		this.device = deviceIn;
	}

	private VulkanCommandPool getCommandPool()
	{
		return this.commandPool;
	}

	private void setCommandPool(final VulkanCommandPool commandPoolIn)
	{
		this.commandPool = commandPoolIn;
	}

	private VulkanBuffer getVulkanBuffer()
	{
		return this.vulkanBuffer;
	}

	private void setVulkanBuffer(final VulkanBuffer vulkanBufferIn)
	{
		this.vulkanBuffer = vulkanBufferIn;
	}
}