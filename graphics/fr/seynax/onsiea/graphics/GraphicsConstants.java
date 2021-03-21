package fr.seynax.onsiea.graphics;

// Add

public class GraphicsConstants
{
	// Constants

	// Ratio
	// 16 / 9

	private final static int		WIDTH_RATIO			= 16;
	private final static int		HEIGHT_RATIO		= 9;
	private final static float		RATIO				= GraphicsConstants.WIDTH_RATIO
			/ GraphicsConstants.HEIGHT_RATIO;

	// Width

	// @formatter:off
	private final static int	MIN_WIDTH		= 15 * GraphicsConstants.WIDTH_RATIO;							// 15 * 16
	private final static int	DEFAULT_WIDTH	= 120 * GraphicsConstants.WIDTH_RATIO;							// 120 * 16
	private final static int	MAX_WIDTH		= 240 * GraphicsConstants.WIDTH_RATIO;							// 240 * 16

	// Height

	private final static int	MIN_HEIGHT		= 15 * GraphicsConstants.HEIGHT_RATIO;							// 15 * 9
	private final static int	DEFAULT_HEIGHT	= 120 * GraphicsConstants.HEIGHT_RATIO;							// 120 * 9
	private final static int	MAX_HEIGHT		= 240 * GraphicsConstants.HEIGHT_RATIO;							// 240 * 9
	// @formatter:on

	// Titl;e

	private final static String		DEFAULT_TITLE		= "Onsiea !";
	// FrameRate

	private final static int		MIN_FRAMERATE		= 1;
	private final static int		DEFAULT_FRAMERATE	= 60;
	private final static int		MAX_FRAMERATE		= Integer.MAX_VALUE;

	// Sync

	// IsSync

	private final static boolean	IS_SYNC_BY_DEFAULT	= true;

	// Sync

	private final static int		MIN_SYNC			= 0;
	private final static int		DEFAULT_SYNC		= 1;
	private final static int		MAX_SYNC			= 4;

	// Color

	private final static float		DEFAULT_COLOR_R		= 0.0f;
	private final static float		DEFAULT_COLOR_G		= 0.0f;
	private final static float		DEFAULT_COLOR_B		= 0.0f;
	private final static float		DEFAULT_COLOR_A		= 0.0f;

	// Perspective

	/**
	 * Field of View in Radians
	 */
	private static final float		FOV					= (float) Math.toRadians(90.0f);

	private static final float		Z_NEAR				= 0.01f;

	private static final float		Z_FAR				= 1000.f;

	// Fullsreen

	private static final boolean	FULLSCREEN			= false;

	// Constants getter

	// Ratio

	// Width ratio

	public static int getWidthRatio()
	{
		return GraphicsConstants.WIDTH_RATIO;
	}

	// HeightRatio

	public static int getHeightRatio()
	{
		return GraphicsConstants.HEIGHT_RATIO;
	}

	// General ratio

	public static float getRatio()
	{
		return GraphicsConstants.RATIO;
	}

	// Width

	public static int getMinWidth()
	{
		return GraphicsConstants.MIN_WIDTH;
	}

	public static int getDefaultWidth()
	{
		return GraphicsConstants.DEFAULT_WIDTH;
	}

	public static int getMaxWidth()
	{
		return GraphicsConstants.MAX_WIDTH;
	}

	// Height

	public static int getMinHeight()
	{
		return GraphicsConstants.MIN_HEIGHT;
	}

	public static int getDefaultHeight()
	{
		return GraphicsConstants.DEFAULT_HEIGHT;
	}

	public static int getMaxHeight()
	{
		return GraphicsConstants.MAX_HEIGHT;
	}

	// Title

	public static String getDefaultTitle()
	{
		return GraphicsConstants.DEFAULT_TITLE;
	}

	// Framerate

	public static int getMinFramerate()
	{
		return GraphicsConstants.MIN_FRAMERATE;
	}

	public static int getDefaultFramerate()
	{
		return GraphicsConstants.DEFAULT_FRAMERATE;
	}

	public static int getMaxFramerate()
	{
		return GraphicsConstants.MAX_FRAMERATE;
	}

	// IsSyncByDefault

	public static boolean isSyncByDefault()
	{
		return GraphicsConstants.IS_SYNC_BY_DEFAULT;
	}

	// Sync

	public static int getMinSync()
	{
		return GraphicsConstants.MIN_SYNC;
	}

	public static int getDefaultSync()
	{
		return GraphicsConstants.DEFAULT_SYNC;
	}

	public static int getMaxSync()
	{
		return GraphicsConstants.MAX_SYNC;
	}

	// Color

	public static float getDefaultColorR()
	{
		return GraphicsConstants.DEFAULT_COLOR_R;
	}

	public static float getDefaultColorG()
	{
		return GraphicsConstants.DEFAULT_COLOR_G;
	}

	public static float getDefaultColorB()
	{
		return GraphicsConstants.DEFAULT_COLOR_B;
	}

	public static float getDefaultColorA()
	{
		return GraphicsConstants.DEFAULT_COLOR_A;
	}

	// Perspective

	public static float getFov()
	{
		return GraphicsConstants.FOV;
	}

	public static float getZNear()
	{
		return GraphicsConstants.Z_NEAR;
	}

	public static float getZFar()
	{
		return GraphicsConstants.Z_FAR;
	}

	// Fullscreen

	public static boolean isFullscreen()
	{
		return GraphicsConstants.FULLSCREEN;
	}
}
