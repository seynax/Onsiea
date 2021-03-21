package fr.seynax.onsiea.gamelogic.world;

import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.utils.maths.vector.Vector3f;

public class ElementShapeGroups
{
	// Variables

	private Map<Integer, ElementShapeGroup> ElementShapeGroups;

	// Constructor

	public ElementShapeGroups()
	{
		this.setElementShapeGroups(new HashMap<>());
	}

	// Methods

	public boolean contains(final Vector3f positionIn)
	{
		for (final ElementShapeGroup ElementShapeGroup : this.getElementShapeGroups().values())
		{
			if (ElementShapeGroup.has(positionIn))
			{
				return true;
			}
		}

		return false;
	}

	public boolean add(final int vaoIdIn, final ElementShapeGroup ElementShapeGroupIn)
	{
		if (this.getElementShapeGroups().containsKey(vaoIdIn))
		{
			return false;
		}

		this.getElementShapeGroups().put(vaoIdIn, ElementShapeGroupIn);

		return true;
	}

	public boolean has(final int vaoIdIn)
	{
		return this.getElementShapeGroups().containsKey(vaoIdIn);
	}

	public boolean hasValue(final ElementShapeGroup ElementShapeGroupIn)
	{
		for (final ElementShapeGroup ElementShapeGroup : this.getElementShapeGroups().values())
		{
			if (ElementShapeGroup.equals(ElementShapeGroupIn))
			{
				return true;
			}
		}

		return false;
	}

	public ElementShapeGroup get(final int vaoIdIn)
	{
		return this.getElementShapeGroups().get(vaoIdIn);
	}

	public void remove(final int vaoIdIn)
	{
		this.getElementShapeGroups().remove(vaoIdIn);
	}

	// Getter | Setter

	public Map<Integer, ElementShapeGroup> getElementShapeGroups()
	{
		return this.ElementShapeGroups;
	}

	public void setElementShapeGroups(final Map<Integer, ElementShapeGroup> ElementShapeGroupsIn)
	{
		this.ElementShapeGroups = ElementShapeGroupsIn;
	}
}