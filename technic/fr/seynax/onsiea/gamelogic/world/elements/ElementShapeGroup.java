package fr.seynax.onsiea.gamelogic.world.elements;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import fr.seynax.onsiea.graphics.matter.Shape;

public class ElementShapeGroup
{
	// Variables

	private Shape					shape;

	private Map<Vector3f, Element>	elements;

	// Constructor

	public ElementShapeGroup()
	{
		this.setElements(new HashMap<>());
	}

	public ElementShapeGroup(final Shape shapeIn)
	{
		this.setShape(shapeIn);
		this.setElements(new HashMap<>());
	}

	// Methods

	public boolean add(final Vector3f positionIn, final Element elementIn)
	{
		if (this.getElements().containsKey(positionIn))
		{
			return false;
		}

		this.getElements().put(positionIn, elementIn);

		return true;
	}

	public boolean has(final Vector3f positionIn)
	{
		return this.getElements().containsKey(positionIn);
	}

	public boolean hasValue(final Element elementIn)
	{
		for (final Element element : this.getElements().values())
		{
			if (element.equals(elementIn))
			{
				return true;
			}
		}

		return false;
	}

	public Element get(final Vector3f positionIn)
	{
		return this.getElements().get(positionIn);
	}

	public void remove(final Vector3f positionIn)
	{
		this.getElements().remove(positionIn);
	}

	// Getter | Setter

	public Shape getShape()
	{
		return this.shape;
	}

	private void setShape(final Shape shapeIn)
	{
		this.shape = shapeIn;
	}

	public Map<Vector3f, Element> getElements()
	{
		return this.elements;
	}

	public void setElements(final Map<Vector3f, Element> elementsIn)
	{
		this.elements = elementsIn;
	}
}