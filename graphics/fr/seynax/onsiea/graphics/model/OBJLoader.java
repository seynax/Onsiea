package fr.seynax.onsiea.graphics.model;

import java.util.ArrayList;
import java.util.List;

import fr.seynax.onsiea.graphics.matter.Mesh;
import fr.seynax.onsiea.utils.file.FileUtils;
import fr.seynax.onsiea.utils.maths.vector.Vector2f;
import fr.seynax.onsiea.utils.maths.vector.Vector3f;

public class OBJLoader
{
	public OBJLoader()
	{

	}

	public static Mesh load(final String fileNameIn)
	{
		final var				lines		= FileUtils.loadLines(fileNameIn);

		final List<Vector3f>	vertices	= new ArrayList<>();
		final List<Vector2f>	textures	= new ArrayList<>();
		final List<Vector3f>	normals		= new ArrayList<>();
		final List<Face>		faces		= new ArrayList<>();

		for (final String line : lines)
		{
			final var tokens = line.split("\\s+");
			switch (tokens[0])
			{
				case "v":
					// Geometric vertex
					final var vec3f = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					vertices.add(vec3f);
					break;
				case "vt":
					// Texture coordinate
					final var vec2f = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
					textures.add(vec2f);
					break;
				case "vn":
					// Vertex normal
					final var vec3fNorm = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					normals.add(vec3fNorm);
					break;
				case "f":
					final var face = new Face(tokens[1], tokens[2], tokens[3]);
					faces.add(face);
					break;
				default:
					// Ignore other lines
					break;
			}
		}

		return OBJLoader.reorderLists(vertices, textures, normals, faces);
	}

	private static Mesh reorderLists(final List<Vector3f> posList, final List<Vector2f> textCoordList,
			final List<Vector3f> normList, final List<Face> facesList)
	{

		final List<Integer>	indices	= new ArrayList<>();

		// Create position array in the order it has been declared

		final var			posArr	= new float[posList.size() * 3];
		var					i		= 0;
		for (final Vector3f pos : posList)
		{
			posArr[i * 3]		= pos.x;
			posArr[i * 3 + 1]	= pos.y;
			posArr[i * 3 + 2]	= pos.z;
			i++;
		}
		final var	textCoordArr	= new float[posList.size() * 2];
		final var	normArr			= new float[posList.size() * 3];

		for (final Face face : facesList)
		{
			final var faceVertexIndices = face.getFaceVertexIndices();
			for (final IdxGroup indValue : faceVertexIndices)
			{
				OBJLoader.processFaceVertex(indValue, textCoordList, normList, indices, textCoordArr, normArr);
			}
		}

		var indicesArr = new int[indices.size()];
		indicesArr = indices.stream().mapToInt((final Integer v) -> v).toArray();
		final var mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);

		return mesh;
	}

	private static void processFaceVertex(final IdxGroup indices, final List<Vector2f> textCoordList,
			final List<Vector3f> normList, final List<Integer> indicesList, final float[] texCoordArr,
			final float[] normArr)
	{

		// Set index for vertex coordinates
		final var posIndex = indices.idxPos;
		indicesList.add(posIndex);

		// Reorder texture coordinates
		if (indices.idxTextCoord >= 0)
		{
			final var textCoord = textCoordList.get(indices.idxTextCoord);
			texCoordArr[posIndex * 2]		= textCoord.x;
			texCoordArr[posIndex * 2 + 1]	= 1 - textCoord.y;
		}
		if (indices.idxVecNormal >= 0)
		{
			// Reorder vectornormals
			final var vecNorm = normList.get(indices.idxVecNormal);
			normArr[posIndex * 3]		= vecNorm.x;
			normArr[posIndex * 3 + 1]	= vecNorm.y;
			normArr[posIndex * 3 + 2]	= vecNorm.z;
		}
	}

	protected static class IdxGroup
	{

		public static final int	NO_VALUE	= -1;

		public int				idxPos;

		public int				idxTextCoord;

		public int				idxVecNormal;

		public IdxGroup()
		{
			this.idxPos			= IdxGroup.NO_VALUE;
			this.idxTextCoord	= IdxGroup.NO_VALUE;
			this.idxVecNormal	= IdxGroup.NO_VALUE;
		}
	}

	protected static class Face
	{

		/**
		 * List of idxGroup groups for a face triangle (3 vertices per face).
		 */
		private IdxGroup[] idxGroups = new IdxGroup[3];

		public Face(final String v1, final String v2, final String v3)
		{
			this.idxGroups		= new IdxGroup[3];
			// Parse the lines
			this.idxGroups[0]	= this.parseLine(v1);
			this.idxGroups[1]	= this.parseLine(v2);
			this.idxGroups[2]	= this.parseLine(v3);
		}

		private IdxGroup parseLine(final String line)
		{
			final var	idxGroup	= new IdxGroup();

			final var	lineTokens	= line.split("/");
			final var	length		= lineTokens.length;
			idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
			if (length > 1)
			{
				// It can be empty if the obj does not define text coords
				final var textCoord = lineTokens[1];
				idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
				if (length > 2)
				{
					idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
				}
			}

			return idxGroup;
		}

		public IdxGroup[] getFaceVertexIndices()
		{
			return this.idxGroups;
		}
	}
}