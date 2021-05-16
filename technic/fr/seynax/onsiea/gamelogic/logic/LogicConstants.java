package fr.seynax.onsiea.gamelogic.logic;

public class LogicConstants
{
	public final static class Chunk
	{
		// Variables

		// - Min

		private final static int	MIN_X		= 0;
		private final static int	MIN_Y		= 0;
		private final static int	MIN_Z		= 0;

		// - Max

		private final static int	MAX_X		= 16;
		private final static int	MAX_Y		= 16;
		private final static int	MAX_Z		= 16;

		// - Distances

		private final static float	DISTANCE_X	= Chunk.MAX_X - Chunk.MIN_X;
		private final static float	DISTANCE_Y	= Chunk.MAX_Y - Chunk.MIN_Y;
		private final static float	DISTANCE_Z	= Chunk.MAX_Z - Chunk.MIN_Z;

		// - Distance

		private final static float	DISTANCE	= Chunk.DISTANCE_X * Chunk.DISTANCE_X
				+ Chunk.DISTANCE_Y * Chunk.DISTANCE_Y + Chunk.DISTANCE_Z * Chunk.DISTANCE_Z;
		// Getter | Setter

		public static int getMinX()
		{
			return Chunk.MIN_X;
		}

		public static int getMinY()
		{
			return Chunk.MIN_Y;
		}

		public static int getMinZ()
		{
			return Chunk.MIN_Z;
		}

		public static int getMaxX()
		{
			return Chunk.MAX_X;
		}

		public static int getMaxY()
		{
			return Chunk.MAX_Y;
		}

		public static int getMaxZ()
		{
			return Chunk.MAX_Z;
		}

		public static float getDistanceX()
		{
			return Chunk.DISTANCE_X;
		}

		public static float getDistanceY()
		{
			return Chunk.DISTANCE_Y;
		}

		public static float getDistanceZ()
		{
			return Chunk.DISTANCE_Z;
		}

		public static float getDistance()
		{
			return Chunk.DISTANCE;
		}
	}

	public final static class RenderDistance
	{
		// Variables

		// - Choose render distance

		private final static int	CHOOSE_RENDER_DISTANCE	= 2;

		// - Distance

		private final static float	DISTANCE				= RenderDistance.CHOOSE_RENDER_DISTANCE
				* RenderDistance.CHOOSE_RENDER_DISTANCE
				+ RenderDistance.CHOOSE_RENDER_DISTANCE * RenderDistance.CHOOSE_RENDER_DISTANCE
				+ RenderDistance.CHOOSE_RENDER_DISTANCE * RenderDistance.CHOOSE_RENDER_DISTANCE;

		// Getter | Setter

		// - ChooseRenderDistance

		public static int getChooseRenderDistance()
		{
			return RenderDistance.CHOOSE_RENDER_DISTANCE;
		}

		// - Min

		// - Distance

		public static float getDistance()
		{
			return RenderDistance.DISTANCE;
		}
	}

	public final static class Region
	{
		// Variables

		// - Length

		private final static int	LENGTH_X	= 16;
		private final static int	LENGTH_Y	= 16;
		private final static int	LENGTH_Z	= 16;

		// Getter | Setter

		public static int getLengthX()
		{
			return Region.LENGTH_X;
		}

		public static int getLengthY()
		{
			return Region.LENGTH_Y;
		}

		public static int getLengthZ()
		{
			return Region.LENGTH_Z;
		}
	}

}