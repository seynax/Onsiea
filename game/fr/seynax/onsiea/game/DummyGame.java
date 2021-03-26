package fr.seynax.onsiea.game;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.gamelogic.IGameLogic;
import fr.seynax.onsiea.gamelogic.TechnicEngine;
import fr.seynax.onsiea.gamelogic.item.AnimatedItem;
import fr.seynax.onsiea.gamelogic.item.GameItem;
import fr.seynax.onsiea.graphics.GraphicsConstants;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.LWJGL;
import fr.seynax.onsiea.graphics.Texture;
import fr.seynax.onsiea.graphics.gui.GuiScreenshots;
import fr.seynax.onsiea.graphics.gui.inventory.GuiInventory;
import fr.seynax.onsiea.graphics.gui.inventory.SlideItem;
import fr.seynax.onsiea.graphics.input.CursorExtensionMenu;
import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.opengl.OpenGL;
import fr.seynax.onsiea.opengl.OpenGLConstants;
import fr.seynax.onsiea.opengl.render.Renderer;
import fr.seynax.onsiea.opengl.renderer.RendererGuiElement;
import fr.seynax.onsiea.opengl.renderer.RendererGuiInventory;
import fr.seynax.onsiea.opengl.renderer.RendererGuiScreenshots;
import fr.seynax.onsiea.opengl.renderer.RendererGuiSlot;
import fr.seynax.onsiea.opengl.shader.ShaderGui;
import fr.seynax.onsiea.opengl.shader.ShaderProgram;
import fr.seynax.onsiea.opengl.shader.ShaderScreenshot;
import fr.seynax.onsiea.utils.FPSUtils;
import fr.seynax.onsiea.utils.Timer;
import fr.seynax.onsiea.utils.maths.Maths;

public class DummyGame implements IGameLogic
{
	// Variables

	private int												direction	= 0;

	private float											color		= 0.0f;

	private Renderer										renderer;

	// Variables ; Graphics data

	private AnimatedItem[]									gameItems;

	private Texture											grassBlockTexture;

	private Camera											camera;

	private RendererGuiInventory							rendererGuiInventory;
	private RendererGuiSlot									rendererGuiSlot;
	private RendererGuiScreenshots							rendererGuiScreenshots;
	private RendererGuiElement								rendererGuiElement;

	private GuiInventory									gui;
	private GuiScreenshots									guiScreenshots;

	private ShaderGui										shaderGui;
	private ShaderScreenshot								shaderScreenshot;

	private TechnicEngine									technicEngine;
	private fr.seynax.onsiea.utils.maths.vector.Matrix4f	viewMatrix;

	private Timer											timer0;
	private Timer											timer1;

	private FPSUtils										fpsUtils;

	/**
	 * private Mesh meshBoat; private GameItem boat; private Texture boatTexture;
	 **/

	// Constructor

	public DummyGame()
	{
	}

	// Methods

	@Override
	public void initialization(final IWindow windowIn) throws Exception
	{

		if (GraphicsConstants.isDebug())
		{
			LWJGL.enableDebugging();
			OpenGL.enableDebugging();
		}
		else
		{
			LWJGL.disableDebugging();
			OpenGL.disableDebugging();
		}

		if (GraphicsConstants.isDebugStack())
		{
			LWJGL.enableDebugStack();
		}
		else
		{
			LWJGL.disableDebugStack();
		}

		this.setRenderer(new Renderer(windowIn));

		final var	positions	= new float[] {
				// V0
				-0.5f, 0.5f, 0.5f,
				// V1
				-0.5f, -0.5f, 0.5f,
				// V2
				0.5f, -0.5f, 0.5f,
				// V3
				0.5f, 0.5f, 0.5f,
				// V4
				-0.5f, 0.5f, -0.5f,
				// V5
				0.5f, 0.5f, -0.5f,
				// V6
				-0.5f, -0.5f, -0.5f,
				// V7
				0.5f, -0.5f, -0.5f,

				// For text coords in top face
				// V8: V4 repeated
				-0.5f, 0.5f, -0.5f,
				// V9: V5 repeated
				0.5f, 0.5f, -0.5f,
				// V10: V0 repeated
				-0.5f, 0.5f, 0.5f,
				// V11: V3 repeated
				0.5f, 0.5f, 0.5f,

				// For text coords in right face
				// V12: V3 repeated
				0.5f, 0.5f, 0.5f,
				// V13: V2 repeated
				0.5f, -0.5f, 0.5f,

				// For text coords in left face
				// V14: V0 repeated
				-0.5f, 0.5f, 0.5f,
				// V15: V1 repeated
				-0.5f, -0.5f, 0.5f,

				// For text coords in bottom face
				// V16: V6 repeated
				-0.5f, -0.5f, -0.5f,
				// V17: V7 repeated
				0.5f, -0.5f, -0.5f,
				// V18: V1 repeated
				-0.5f, -0.5f, 0.5f,
				// V19: V2 repeated
				0.5f, -0.5f, 0.5f };

		final var	textCoords	= new float[] { 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 0.0f,

				0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f,

				// For text coords in top face
				0.0f, 0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 1.0f,

				// For text coords in right face
				0.0f, 0.0f, 0.0f, 0.5f,

				// For text coords in left face
				0.5f, 0.0f, 0.5f, 0.5f,

				// For text coords in bottom face
				0.5f, 0.0f, 1.0f, 0.0f, 0.5f, 0.5f, 1.0f, 0.5f };

		final var	indices		= new int[] {
				// Front face
				0, 1, 3, 3, 1, 2,
				// Top Face
				8, 10, 11, 9, 8, 11,
				// Right face
				12, 13, 7, 5, 12, 7,
				// Left face
				14, 15, 6, 4, 14, 6,
				// Bottom face
				16, 18, 19, 17, 16, 19,
				// Back face
				4, 6, 7, 5, 4, 7 };

		final var	mesh		= new Mesh(positions, new float[] {}, textCoords, indices);

		final var	number		= 40_000;

		this.setGameItems(new AnimatedItem[number]);

		for (var i = 0; i < number; i++)
		{
			final var	variation	= (int) (i * 2.0f);

			final var	x			= Maths.randInt(-variation - 1, variation + 1) + Maths.randFloat();
			final var	y			= Maths.randInt(-variation - 1, variation + 1) + Maths.randFloat();

			float		v			= (int) (-4 + -i / 2.0f);

			if (v >= -1f)
			{
				v = -1f;
			}

			final var	z			= Maths.randInt((int) v - 1, -1) + Maths.randFloat();

			final var	rx			= Maths.randInt(0, 360) + Maths.randFloat();
			final var	ry			= Maths.randInt(0, 360) + Maths.randFloat();
			final var	rz			= Maths.randInt(0, 360) + Maths.randFloat();

			final var	gameItem	= new GameItem(mesh);

			gameItem.setPosition(x, y, z);
			gameItem.setRotation(rx, ry, rz);

			final var	speedX			= (Maths.randInt(0, 1) + Maths.randFloat()) * 1.0f;
			final var	speedY			= (Maths.randInt(0, 1) + Maths.randFloat()) * 1.0f;
			final var	speedZ			= (Maths.randInt(0, 1) + Maths.randFloat()) * 1.0f;

			final var	speedRx			= Maths.randInt(0, 50) + Maths.randFloat();
			final var	speedRy			= Maths.randInt(0, 50) + Maths.randFloat();
			final var	speedRz			= Maths.randInt(0, 50) + Maths.randFloat();

			final var	animatedItem	= new AnimatedItem(gameItem);

			animatedItem.setTranslationSpeed(speedX, speedY, speedZ);

			animatedItem.setRotationSpeed(speedRx, speedRy, speedRz);

			this.getGameItems()[i] = animatedItem;
		}

		this.setGrassBlockTexture(Texture.loadTexture("cyan"));

		this.camera		= new Camera();
		this.viewMatrix	= Maths.convert(this.camera.getViewMatrix());

		// FrameBuffer
		/**
		 * { this.framebuffer0 = new OpenGLFramebuffer(1920, 1080); this.framebuffer1 =
		 * new OpenGLFramebuffer(1920, 1080); }
		 **/

		// Gui Shader
		{
			this.shaderGui = new ShaderGui();

			this.shaderGui.start();

			this.shaderGui.sendProjectrionMatrix(Maths.getProjectionMatrix());

			ShaderProgram.stop();

			this.shaderScreenshot = new ShaderScreenshot();

			this.shaderScreenshot.start();

			this.shaderScreenshot.sendProjectrionMatrix(Maths.getProjectionMatrix());

			ShaderProgram.stop();
		}

		// Gui
		{
			this.rendererGuiInventory	= new RendererGuiInventory();

			this.gui					= new GuiInventory(this.rendererGuiInventory);

			this.rendererGuiSlot		= new RendererGuiSlot();

			this.gui.initialization(this.rendererGuiSlot);

			this.rendererGuiScreenshots	= new RendererGuiScreenshots();

			this.rendererGuiElement		= new RendererGuiElement();

			this.guiScreenshots			= new GuiScreenshots(this.rendererGuiScreenshots, this.rendererGuiElement);
		}

		/**
		 * final var image =
		 * TextureMerger.getImage("resources/textures/blocks/test.png");
		 *
		 * final var w = 16F; for (var x = 0; x < 16; x++) { for (var y = 0; y < 16;
		 * y++) { final var r = image.getBuffer().get((int) (x * (w * 4) + y + 0)) &
		 * 0x0FF; final var g = image.getBuffer().get((int) (x * (w * 4) + y + 1)) &
		 * 0x0FF; final var b = image.getBuffer().get((int) (x * (w * 4) + y + 2)) &
		 * 0x0FF; final var a = image.getBuffer().get((int) (x * (w * 4) + y + 3)) &
		 * 0x0FF;
		 *
		 * if (r == g && g == b) { continue; }
		 *
		 * System.out.println(x + " : " + y + " = | R : " + r + " | G : " + g + " | B :
		 * " + b + " | A : " + a); } }
		 **/

		// Timer
		{
			this.timer0	= new Timer();
			this.timer1	= new Timer();
		}

		GLFW.glfwSetCursorPos(windowIn.getWindowHandle(), windowIn.getWidth() / 2.0D, windowIn.getHeight() / 2.0D);

		{
			this.technicEngine = new TechnicEngine(this.camera, windowIn);
			this.technicEngine.start();
		}

		{
			this.fpsUtils = new FPSUtils();
			this.fpsUtils.start();
		}
	}

	@Override
	public void input(final IWindow windowIn)
	{
		if (this.timer0.getElapsedTime() >= 1_000_000L)
		{
			this.timer0.start();

			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_KEY_UP))
			{
				this.setDirection(1);
			}
			else if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_KEY_DOWN))
			{
				this.setDirection(-1);
			}
			else
			{
				this.setDirection(0);
			}
		}

		if (windowIn.getGlfwEventManager().keyIsHasPress(GLFW.GLFW_KEY_P))
		{
			this.gui.setOpen(!this.gui.isOpen());

			if (this.gui.isOpen())
			{
				this.camera.setCanUpdate(false);

				windowIn.getGlfwEventManager().getCallbacksManager().menuView();
			}
			else
			{
				this.camera.setCanUpdate(true);

				windowIn.getGlfwEventManager().getCallbacksManager().FPSView();
			}
		}

		if (windowIn.getGlfwEventManager().keyIsHasPress(GLFW.GLFW_KEY_O))
		{
			this.guiScreenshots.setOpen(!this.guiScreenshots.isOpen());

			if (this.guiScreenshots.isOpen())
			{
				this.camera.setCanUpdate(false);

				windowIn.getGlfwEventManager().getCallbacksManager().menuView();
			}
			else
			{
				this.camera.setCanUpdate(true);

				windowIn.getGlfwEventManager().getCallbacksManager().FPSView();
			}
		}
	}

	@Override
	public void update(final double intervalIn, final IWindow windowIn)
	{
		this.setColor(this.getColor() + this.getDirection() * 0.01f);

		if (this.getColor() > 1)
		{
			this.setColor(1.0f);
		}
		else if (this.getColor() < 0)
		{
			this.setColor(0.0f);
		}

		if (this.gui.isOpen())
		{
			this.camera.setCanUpdate(false);

			this.gui.update(windowIn);
		}
		else if (this.guiScreenshots.isOpen())
		{
			this.camera.setCanUpdate(false);

			this.guiScreenshots.update(windowIn);
		}
		else
		{
			this.camera.setCanUpdate(true);
		}

		for (final AnimatedItem animatedItem : this.getGameItems())
		{
			animatedItem.setPosition(
					(float) (animatedItem.getPosition().x() + animatedItem.getTranslationSpeed().x() * intervalIn),
					(float) (animatedItem.getPosition().y() + animatedItem.getTranslationSpeed().y() * intervalIn),
					(float) (animatedItem.getPosition().z() + animatedItem.getTranslationSpeed().z() * intervalIn));

			animatedItem.setRotation(
					(float) (animatedItem.getRotation().x() + animatedItem.getRotationSpeed().x() * intervalIn),
					(float) (animatedItem.getRotation().y() + animatedItem.getRotationSpeed().y() * intervalIn),
					(float) (animatedItem.getRotation().z() + animatedItem.getRotationSpeed().z() * intervalIn));

			if (this.timer1.getElapsedTime() >= 1_000_000_000L)
			{
				this.timer1.start();

				final var	speedX	= (Maths.randInt(0, 1) + Maths.randFloat()) * 10.0f;
				final var	speedY	= (Maths.randInt(0, 1) + Maths.randFloat()) * 10.0f;
				final var	speedZ	= (Maths.randInt(0, 1) + Maths.randFloat()) * 10.0f;

				final var	speedRx	= Maths.randInt(0, 50) + Maths.randFloat();
				final var	speedRy	= Maths.randInt(0, 50) + Maths.randFloat();
				final var	speedRz	= Maths.randInt(0, 50) + Maths.randFloat();

				animatedItem.setTranslationSpeed(speedX, speedY, speedZ);

				animatedItem.setRotationSpeed(speedRx, speedRy, speedRz);
			}
		}
		this.camera.update(windowIn, 1.0D / 30.0D);
	}

	public boolean isIn(final Vector3f fromPositionIn, final float xMinIn, final float yMinIn, final float zMinIn,
			final float xMaxIn, final float yMaxIn, final float zMaxIn)
	{
		return fromPositionIn.x() >= xMinIn && fromPositionIn.y() >= yMinIn && fromPositionIn.z() >= zMinIn
				&& fromPositionIn.x() <= xMaxIn && fromPositionIn.y() <= yMaxIn && fromPositionIn.z() <= zMaxIn;
	}

	@Override
	public void render(final IWindow windowIn)
	{
		final var lastState = this.camera.isCanUpdate();

		this.camera.setCanUpdate(false);
		Maths.convert(this.camera.getViewMatrix(), this.viewMatrix);
		this.camera.setCanUpdate(lastState);

		// Start rendering
		{
			Renderer.clearColor(this.getColor(), this.getColor(), this.getColor(), 1.0f);

			this.getRenderer().startRendering(windowIn);

			this.getRenderer().getShaderProgram().start();
			this.getRenderer().getShaderProgram().sendViewMatrix(this.viewMatrix);

			// Framebuffer
			{
				/**
				 * this.framebuffer0.clear(); this.framebuffer1.clear();
				 *
				 * // Render to fbo
				 *
				 * this.framebuffer0.start(0, 0, 1920, 1080);
				 *
				 * this.getRenderer().getShaderProgram().start();
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(1.0f, 1.0f,
				 * 1.0f));
				 *
				 * Texture.bind(this.getGrassBlockTexture().getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.getGameItems());
				 *
				 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
				 *
				 * // Render to fbo1
				 *
				 * this.framebuffer0.start(0, 0, 1920, 1080);
				 *
				 * this.getRenderer().getShaderProgram().start();
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(1.0f, 1.0f,
				 * 1.0f)); this.getRenderer().getShaderProgram()
				 * .sendViewMatrix(Maths.loadViewMatrix(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
				 *
				 * Texture.bind(this.getGrassBlockTexture().getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.getGameItems());
				 *
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
				 * 0.0f));
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer0.getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.view);
				 *
				 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
				 *
				 * // Render to fbo pass 2
				 *
				 * this.framebuffer0.start(0, 0, 1920, 1080);
				 *
				 * this.getRenderer().getShaderProgram().start();
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
				 * 0.0f));
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer1.getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.view1);
				 *
				 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
				 **/
			}
		}

		// Normal render
		{
			// Final render

			// FrameBuffer
			{
				// render the texture

				/**
				 * this.getRenderer().getShaderProgram().start();
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
				 * 0.0f));
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer0.getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.view);
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				 *
				 * // render the texture1
				 *
				 * this.getRenderer().getShaderProgram().start();
				 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
				 * 0.0f));
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.framebuffer1.getTextureId());
				 *
				 * this.getRenderer().render(windowIn, this.view1);
				 *
				 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				 **/
			}

			// Gui
			{
				if (this.gui.isOpen())
				{
					this.shaderGui.start();
					this.shaderGui.sendViewMatrix(this.viewMatrix);

					final var cursorExtension = windowIn.getGlfwEventManager().getCallbacksManager()
							.getCursorPosCallback().getCursor().getCursorExtension();

					if (cursorExtension instanceof CursorExtensionMenu)
					{
						final var	cursorExtensionMenu	= (CursorExtensionMenu) cursorExtension;

						final var	mainCursorElement	= cursorExtensionMenu.getMainCursorElement();

						if (mainCursorElement instanceof SlideItem)
						{
							final var slideItem = (SlideItem) mainCursorElement;

							GL30.glBindVertexArray(Shapes.getRectangleVaoId());

							Texture.bind(slideItem.getItem().getTexture().getTextureId());

							this.shaderGui.sendTransformationMatrix(Maths.getWorldMatrix(
									new org.joml.Vector3f(slideItem.getPosition().getX(),
											slideItem.getPosition().getY(), 0.0F),
									new org.joml.Vector3f(slideItem.getRotation().getX(),
											slideItem.getRotation().getY(), 0.0F),
									new org.joml.Vector3f(slideItem.getSize().getX(), slideItem.getSize().getY(),
											1.0f)));

							GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length,
									GL11.GL_UNSIGNED_INT, 0);
						}
					}

					this.gui.getRenderer().startDrawing(this.shaderGui);
					this.gui.getRenderer().draw(this.shaderGui, this.gui);
					this.gui.getRenderer().endDrawing(this.shaderGui);

					ShaderProgram.stop();
				}

				if (this.guiScreenshots.isOpen())
				{
					this.guiScreenshots.getRenderer().startDrawing(this.shaderGui);
					this.guiScreenshots.getRenderer().draw(this.shaderGui, this.guiScreenshots);
					this.guiScreenshots.getRenderer().endDrawing(this.shaderGui);
				}
			}

			// 3D
			{
				this.getRenderer().getShaderProgram().start();
				this.getRenderer().getShaderProgram().sendColor(new Vector3f(1.0f, 1.0f, 1.0f));

				Texture.bind(this.getGrassBlockTexture().getTextureId());

				this.getRenderer().render(windowIn, this.getGameItems());
			}
		}

		if (GraphicsConstants.isFpsShowing())
		{
			final var title = this.fpsUtils.updateFPS();

			if (title != null)
			{
				GLFW.glfwSetWindowTitle(windowIn.getWindowHandle(), title);
			}
		}

		// Debugging

		if (OpenGLConstants.isShowError())
		{
			OpenGL.showAllError();
		}
	}

	@Override
	public void cleanup()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		Texture.cleanUp();

		for (final AnimatedItem gameItem : this.getGameItems())
		{
			gameItem.getMesh().cleanup();
		}

		this.getRenderer().cleanup();
		OpenGL.cleanup();

		this.technicEngine.stop();
	}

	// Getter | Setter

	public int getDirection()
	{
		return this.direction;
	}

	public void setDirection(final int direction)
	{
		this.direction = direction;
	}

	public float getColor()
	{
		return this.color;
	}

	public void setColor(final float color)
	{
		this.color = color;
	}

	public Renderer getRenderer()
	{
		return this.renderer;
	}

	public void setRenderer(final Renderer renderer)
	{
		this.renderer = renderer;
	}

	public AnimatedItem[] getGameItems()
	{
		return this.gameItems;
	}

	public void setGameItems(final AnimatedItem[] gameItemsIn)
	{
		this.gameItems = gameItemsIn;
	}

	public Texture getGrassBlockTexture()
	{
		return this.grassBlockTexture;
	}

	public void setGrassBlockTexture(final Texture grassBlockTextureIn)
	{
		this.grassBlockTexture = grassBlockTextureIn;
	}
}
