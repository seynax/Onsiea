package fr.seynax.onsiea.gamelogic.world;

import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.utils.maths.vector.Vector3f;

public class ElementGroup
{
	// Variables

	private int						vaoId;

	private Map<Vector3f, Element>	elements;

	// Constructor

	public ElementGroup()
	{
		this.setElements(new HashMap<>());
	}

	public ElementGroup(final int vaoIdIn)
	{
		this.setVaoId(vaoIdIn);
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

	public int getVaoId()
	{
		return this.vaoId;
	}

	public void setVaoId(final int vaoIdIn)
	{
		this.vaoId = vaoIdIn;
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