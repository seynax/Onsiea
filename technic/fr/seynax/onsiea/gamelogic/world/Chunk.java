package fr.seynax.onsiea.gamelogic.world;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import fr.seynax.onsiea.gamelogic.world.generator.IGenerator;

public class Chunk
{
	// Constructor variables

	private float					x;
	private float					y;
	private float					z;

	private Map<Vector3f, Element>	positionedElements;

	private ElementGroups			elementGroups;
	private ElementShapeGroups		elementShapeGroups;

	private IGenerator				generator;

	// Constructor

	public Chunk(final float xIn, final float yIn, final float zIn)
	{
		this.setX(xIn);
		this.setY(yIn);
		this.setZ(zIn);

		this.setPositionedElements(new HashMap<>());
		this.setElementGroups(new ElementGroups());
		this.setElementShapeGroups(new ElementShapeGroups());
	}

	// Methods

	public void initialization()
	{
	}

	public void generate()
	{
		final var generatedElements = this.getGenerator().generate(this.getX(), this.getY(), this.getZ());

		for (final ElementGroup elementGroup : generatedElements)
		{
			this.addElementGroup(elementGroup);
		}
	}

	public void sendBuffer()
	{

	}

	public void addElementGroup(final ElementGroup elementGroupIn)
	{
		final var iterator = elementGroupIn.getElements().entrySet().iterator();

		while (iterator.hasNext())
		{
			final var	entry		= iterator.next();

			final var	position	= entry.getKey();

			final var	element		= entry.getValue();

			this.addElement(position, element, elementGroupIn.getVaoId());
		}
	}

	public boolean addElement(final Vector3f positionIn, final Element elementIn, final int vaoIdIn)
	{
		if (this.getPositionedElements().containsKey(positionIn) || this.getElementGroups().contains(positionIn))
		{
			return false;
		}

		var elementGroup = this.getElementGroups().get(vaoIdIn);

		if (elementGroup == null)
		{
			elementGroup = new ElementGroup(vaoIdIn);

			this.getElementGroups().add(vaoIdIn, elementGroup);
		}

		this.getPositionedElements().put(positionIn, elementIn);

		return true;
	}

	public void update()
	{

	}

	public void draw()
	{

	}

	public void cleanup()
	{

	}

	// Getter | Setter

	public float getX()
	{
		return this.x;
	}

	public void setX(final float xIn)
	{
		this.x = xIn;
	}

	public float getY()
	{
		return this.y;
	}

	public void setY(final float yIn)
	{
		this.y = yIn;
	}

	public float getZ()
	{
		return this.z;
	}

	public void setZ(final float zIn)
	{
		this.z = zIn;
	}

	public Map<Vector3f, Element> getPositionedElements()
	{
		return this.positionedElements;
	}

	public void setPositionedElements(final Map<Vector3f, Element> positionedElementsIn)
	{
		this.positionedElements = positionedElementsIn;
	}

	public ElementGroups getElementGroups()
	{
		return this.elementGroups;
	}

	public ElementShapeGroups getElementShapeGroups()
	{
		return this.elementShapeGroups;
	}

	private void setElementShapeGroups(final ElementShapeGroups elementShapeGroupsIn)
	{
		this.elementShapeGroups = elementShapeGroupsIn;
	}

	public void setElementGroups(final ElementGroups elementGroupsIn)
	{
		this.elementGroups = elementGroupsIn;
	}

	public IGenerator getGenerator()
	{
		return this.generator;
	}

	public void setGenerator(final IGenerator generatorIn)
	{
		this.generator = generatorIn;
	}
}