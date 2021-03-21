package fr.seynax.onsiea.game;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import fr.onsiea.graphics.gui.inventory.GuiInventory;
import fr.onsiea.graphics.gui.inventory.SlideItem;
import fr.seynax.onsiea.entity.Camera;
import fr.seynax.onsiea.gamelogic.IGameLogic;
import fr.seynax.onsiea.gamelogic.TechnicEngine;
import fr.seynax.onsiea.gamelogic.item.AnimatedItem;
import fr.seynax.onsiea.gamelogic.item.GameItem;
import fr.seynax.onsiea.gamelogic.item.TexturedRectangle;
import fr.seynax.onsiea.graphics.IWindow;
import fr.seynax.onsiea.graphics.input.CursorExtensionMenu;
import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.graphics.matter.Shapes;
import fr.seynax.onsiea.graphics.render.Renderer;
import fr.seynax.onsiea.graphics.shader.ShaderGui;
import fr.seynax.onsiea.graphics.shader.ShaderProgram;
import fr.seynax.onsiea.graphics.shader.ShaderScreenshot;
import fr.seynax.onsiea.utils.Texture;
import fr.seynax.onsiea.utils.maths.Maths;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;

@SuppressWarnings("unused")
public class DummyGame implements IGameLogic
{
	// Variables

	private int					direction	= 0;

	private float				color		= 0.0f;

	private Renderer			renderer;

	// Variables ; Graphics data

	private AnimatedItem[]		gameItems;

	private Texture				grassBlockTexture;

	private Camera				camera;

	private int					fboId;
	private int					depthBufferId;
	private int					frameBufferTextureId;

	private int					fboId1;
	private int					depthBufferId1;
	private int					frameBufferTextureId1;

	private GuiInventory		gui;

	private ShaderGui			shaderGui;
	private ShaderScreenshot	shaderScreenshot;

	private TechnicEngine		technicEngine;

	private Matrix4f			viewMatrix;

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
		this.setRenderer(new Renderer());

		this.getRenderer().initialization(windowIn);

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

			final var	z			= Maths.randInt((int) v - 4, -4) + Maths.randFloat();

			final var	rx			= Maths.randInt(0, 360) + Maths.randFloat();
			final var	ry			= Maths.randInt(0, 360) + Maths.randFloat();
			final var	rz			= Maths.randInt(0, 360) + Maths.randFloat();

			final var	gameItem	= new GameItem(mesh);

			gameItem.setPosition(x, y, z);
			gameItem.setRotation(rx, ry, rz);

			final var	speedX			= (Maths.randInt(0, 1) + Maths.randFloat()) * 100.0f;
			final var	speedY			= (Maths.randInt(0, 1) + Maths.randFloat()) * 100.0f;
			final var	speedZ			= (Maths.randInt(0, 1) + Maths.randFloat()) * 100.0f;

			final var	speedRx			= Maths.randInt(0, 50) + Maths.randFloat();
			final var	speedRy			= Maths.randInt(0, 50) + Maths.randFloat();
			final var	speedRz			= Maths.randInt(0, 50) + Maths.randFloat();

			final var	animatedItem	= new AnimatedItem(gameItem);

			animatedItem.setTranslationSpeed(speedX, speedY, speedZ);

			animatedItem.setRotationSpeed(speedRx, speedRy, speedRz);

			this.getGameItems()[i] = animatedItem;
		}

		this.setGrassBlockTexture(Texture.loadTexture("cyan"));

		final var face = new Mesh(Shapes.getSurface(), Shapes.getSurfaceColours(),
				Shapes.getSurfaceTextureCoordinates(), Shapes.getSurfaceIndices());

		this.view = new GameItem(face);

		this.view.setPosition(0.0f, 0.0f, -1.6f);
		this.view.setRotation(0.0f, 0.0f, 0.0f);
		this.view.setScale(4.0f);

		this.view1 = new GameItem(face);

		this.view1.setPosition(10.5f, 0.0f, -1.6f);
		this.view1.setRotation(0.0f, 0.0f, 0.0f);
		this.view1.setScale(4.0f);

		this.camera	= new Camera();

		// FrameBuffer

		this.fboId	= GL30.glGenFramebuffers();

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId);

		this.depthBufferId = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.depthBufferId);

		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, 1920, 1080);

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				this.depthBufferId);

		// LoadTexture

		this.frameBufferTextureId = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		// Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up
		// or down
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
		// range
		// Note: GL_CLAMP_TO_EDGE is part of GL12
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1920, 1080, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
				(ByteBuffer) null);

		// End loadTexture

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D,
				this.frameBufferTextureId, 0);

		if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Frame buffer créer avec succés !");
		}
		else
		{
			System.err.println("La création du frame buffer a échoué !");
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		/**
		 *
		 */

		// FrameBuffer1

		this.fboId1 = GL30.glGenFramebuffers();

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId1);

		this.depthBufferId1 = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.depthBufferId1);

		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, 1920, 1080);

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				this.depthBufferId1);

		// LoadTexture

		this.frameBufferTextureId1 = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId1);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		// Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up
		// or down
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected
		// range
		// Note: GL_CLAMP_TO_EDGE is part of GL12
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1920, 1080, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
				(ByteBuffer) null);

		// End loadTexture

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D,
				this.frameBufferTextureId1, 0);

		if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Frame buffer créer avec succés !");
		}
		else
		{
			System.err.println("La création du frame buffer a échoué !");
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		// GuiButton

		final var texturedRectangle = new TexturedRectangle(new Vector2f(0.0f, 0.0f), new Vector2f(2.0f, 2.0f),
				Texture.loadTexture("gui.png").getTextureId());

		this.gui = new GuiInventory();

		this.gui.initialization();

		this.shaderGui = new ShaderGui();

		this.shaderGui.start();

		this.shaderGui.sendProjectrionMatrix(Maths.getProjectionMatrix());

		ShaderProgram.stop();

		this.shaderScreenshot = new ShaderScreenshot();

		this.shaderScreenshot.start();

		this.shaderScreenshot.sendProjectrionMatrix(Maths.getProjectionMatrix());

		ShaderProgram.stop();

		/**
		 * this.meshBoat = OBJLoader.load("D:\\Utilisateurs\\Seynax\\Objects 3D\\Galion
		 * Modèle SVM6.obj");
		 *
		 * this.boat = new GameItem(this.meshBoat);
		 *
		 * this.boatTexture = Texture.loadTexture("boat");
		 **/

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

		this.technicEngine = new TechnicEngine(this.camera, windowIn);

		this.technicEngine.start();

		this.viewMatrix = Maths.copy(this.camera.getViewMatrix());

		GLFW.glfwSetCursorPos(windowIn.getWindowHandle(), windowIn.getWidth() / 2.0D, windowIn.getHeight() / 2.0D);
	}

	private long last1 = System.nanoTime();

	@Override
	public void input(final IWindow windowIn)
	{
		if (System.nanoTime() - this.last1 >= 1_000_000_00L)
		{
			this.last1 = System.nanoTime();

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

			/**
			 * if (GLFW.glfwGetKey(windowIn.getWindowHandle(), GLFW.GLFW_KEY_F2) ==
			 * GLFW.GLFW_PRESS) { GL11.glReadBuffer(GL11.GL_FRONT); final var width =
			 * windowIn.getWidth(); final var height = windowIn.getHeight(); final var bpp =
			 * 4; // Assuming a 32-bit // display with a byte // each for red, green, //
			 * blue, and alpha. final var buffer = BufferUtils.createByteBuffer(width *
			 * height * bpp); GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA,
			 * GL11.GL_UNSIGNED_BYTE, buffer);
			 *
			 * this.gui.addScreenshot(Texture.loadTexture(buffer, width,
			 * height).getTextureId()); }
			 **/

			if (windowIn.getGlfwEventManager().keyIsPress(GLFW.GLFW_KEY_P))
			{
				this.gui.setOpen(!this.gui.isOpen());

				if (this.gui.isOpen())
				{
					windowIn.getGlfwEventManager().getCallbacksManager().menuView();
				}
				else
				{
					windowIn.getGlfwEventManager().getCallbacksManager().FPSView();
				}
			}
		}
	}

	long last0 = System.nanoTime();

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

		// GuiButton

		if (this.gui.isOpen())
		{
			this.gui.update(windowIn);

			this.camera.setCanUpdate(false);
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

			if (System.nanoTime() - this.last0 >= 1_000_000_000L)
			{
				this.last0 = System.nanoTime();

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
	}

	public boolean isIn(final Vector3f fromPositionIn, final float xMinIn, final float yMinIn, final float zMinIn,
			final float xMaxIn, final float yMaxIn, final float zMaxIn)
	{
		return fromPositionIn.x() >= xMinIn && fromPositionIn.y() >= yMinIn && fromPositionIn.z() >= zMinIn
				&& fromPositionIn.x() <= xMaxIn && fromPositionIn.y() <= yMaxIn && fromPositionIn.z() <= zMaxIn;
	}

	final int[]			ints			= new int[1920 * 1080 * 4];
	final ByteBuffer	otherByteBuffer	= BufferUtils.createByteBuffer(this.ints.length * 4);

	Texture				viewTexture;

	private GameItem	view;
	private GameItem	view1;

	long				last			= 0;

	@Override
	public void render(final IWindow windowIn)
	{
		/**
		 * if (System.nanoTime() - this.last >= 1_000_000_000) { this.last =
		 * System.nanoTime();
		 *
		 * GL11.glReadPixels(0, 0, 1920, 1080, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
		 * this.ints);
		 *
		 * for (final int pixel : this.ints) { final var r = (byte) (pixel >> 16 &
		 * 0xFF); final var g = (byte) (pixel >> 8 & 0xFF); final var b = (byte) (pixel
		 * & 0xFF); final var a = (byte) (pixel >> 24 & 0xFF);
		 *
		 * // // if (r == 0.0f && g == 0.0f && b == 0.0f || a == 0.0f) { //
		 * this.otherByteBuffer.put((byte) 1); this.otherByteBuffer.put((byte) 1); //
		 * this.otherByteBuffer.put((byte) 1); this.otherByteBuffer.put((byte) 1); } //
		 * else {
		 *
		 *
		 * // if (r == 0 && b == 0 && g == 0) { this.otherByteBuffer.put((byte) (255 &
		 * 0xFF // << 16)); this.otherByteBuffer.put((byte) (255 & 0xFF << 8)); //
		 * this.otherByteBuffer.put((byte) (255 & 0xFF)); //
		 * this.otherByteBuffer.put((byte) (255 & 0xFF << 24)); } else {
		 *
		 * this.otherByteBuffer.put((byte) pixel);
		 *
		 * // } // } }
		 *
		 * this.otherByteBuffer.flip();
		 *
		 * if (this.viewTexture == null) { this.viewTexture =
		 * Texture.loadTexture(this.otherByteBuffer, 1920, 1080); } else {
		 * Texture.bind(this.viewTexture.getTextureId());
		 *
		 * GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1920, 1080, 0,
		 * GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.otherByteBuffer);
		 *
		 * GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D); //
		 * STBImage.stbi_image_free(this.byteBuffer);
		 *
		 * Texture.unbind(); } }
		 *
		 * Texture.bind(this.viewTexture.getTextureId());
		 *
		 * this.getRenderer().render(windowIn, this.view);
		 **/

		// Start rendering

		Renderer.clearColor(this.getColor(), this.getColor(), this.getColor(), 1.0f);

		this.getRenderer().startRendering(windowIn);

		// Start render to fbo

		/**
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId);
		 *
		 * GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		 *
		 * // Start render to fbo1
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId1);
		 *
		 * GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		 *
		 * // Render to fbo
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId);
		 *
		 * GL11.glViewport(0, 0, 1920, 1080);
		 *
		 * this.getRenderer().getShaderProgram().start();
		 * this.getRenderer().getShaderProgram()
		 * .sendViewMatrix(Maths.loadViewMatrix(this.viewMatrix, 0.0f, 0.0f, 0.0f, 0.0f,
		 * 0.0f, 0.0f)); this.getRenderer().getShaderProgram().sendColor(new
		 * Vector3f(1.0f, 1.0f, 1.0f));
		 *
		 * Texture.bind(this.getGrassBlockTexture().getTextureId());
		 *
		 * this.getRenderer().render(windowIn, this.getGameItems());
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		 *
		 *
		 * // Render to fbo1
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId1);
		 *
		 * GL11.glViewport(0, 0, 1920, 1080);
		 *
		 * this.getRenderer().getShaderProgram().start();
		 * this.getRenderer().getShaderProgram().sendViewMatrix(this.camera.getViewMatrix());
		 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(1.0f, 1.0f,
		 * 1.0f));
		 *
		 * Texture.bind(this.getGrassBlockTexture().getTextureId());
		 *
		 * this.getRenderer().render(windowIn, this.getGameItems());
		 *
		 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
		 * 0.0f));
		 *
		 * /** GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId);
		 *
		 * this.getRenderer().render(windowIn, this.view);
		 **/

		/**
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		 *
		 * // Render to fbo pass 2
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fboId);
		 *
		 * GL11.glViewport(0, 0, 1920, 1080);
		 *
		 * this.getRenderer().getShaderProgram().start();
		 * this.getRenderer().getShaderProgram()
		 * .sendViewMatrix(Maths.loadViewMatrix(this.viewMatrix, 0.0f, 0.0f, 0.0f, 0.0f,
		 * 0.0f, 0.0f)); this.getRenderer().getShaderProgram().sendColor(new
		 * Vector3f(1.0f, 1.0f, 1.0f));
		 *
		 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
		 * 0.0f));
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId1);
		 *
		 * this.getRenderer().render(windowIn, this.view1);
		 *
		 * GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		 *
		 * GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		 *
		 * // Final render
		 *
		 * this.getRenderer().getShaderProgram().sendViewMatrix(this.camera.getViewMatrix());
		 *
		 * // render the texture
		 *
		 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
		 * 0.0f));
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId);
		 *
		 * this.getRenderer().render(windowIn, this.view);
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		 *
		 * // render the texture1
		 *
		 * this.getRenderer().getShaderProgram().sendColor(new Vector3f(0.5f, 1.0f,
		 * 0.0f));
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.frameBufferTextureId1);
		 *
		 * this.getRenderer().render(windowIn, this.view1);
		 *
		 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		 **/

		// normal render

		this.camera.setCanUpdate(false);
		Maths.copy(this.camera.getViewMatrix(), this.viewMatrix);
		this.camera.setCanUpdate(true);

		if (this.gui.isOpen())
		{
			this.shaderGui.start();

			this.shaderGui.sendViewMatrix(this.viewMatrix);

			final var cursorExtension = windowIn.getGlfwEventManager().getCallbacksManager().getCursorPosCallback()
					.getCursor().getCursorExtension();

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
							new org.joml.Vector3f(slideItem.getPosition().getX(), slideItem.getPosition().getY(), 0.0F),
							new org.joml.Vector3f(slideItem.getRotation().getX(), slideItem.getRotation().getY(), 0.0F),
							new org.joml.Vector3f(slideItem.getSize().getX(), slideItem.getSize().getY(), 1.0f)));

					GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.getSurface2dindices().length, GL11.GL_UNSIGNED_INT,
							0);
				}
			}

			this.gui.draw(this.shaderGui);

			ShaderProgram.stop();
		}

		this.getRenderer().getShaderProgram().start();
		this.getRenderer().getShaderProgram().sendColor(new Vector3f(1.0f, 1.0f, 1.0f));
		this.getRenderer().getShaderProgram().sendViewMatrix(this.viewMatrix);

		Texture.bind(this.getGrassBlockTexture().getTextureId());

		this.getRenderer().render(windowIn, this.getGameItems());

		// Texture.bind(this.boatTexture.getTextureId());

		// this.getRenderer().render(windowIn, this.boat);
	}

	@Override
	public void cleanup()
	{
		this.technicEngine.setRunning(false);

		this.getRenderer().cleanup();

		for (final AnimatedItem gameItem : this.getGameItems())
		{
			gameItem.getMesh().cleanUp();
		}

		if (this.technicEngine.getThread().isAlive())
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}

			this.technicEngine.getThread().interrupt();
		}

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
