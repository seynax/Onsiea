package fr.seynax.onsiea.gamelogic.world;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

public class ElementGroups
{
	// Variables

	private Map<Integer, ElementGroup> elementGroups;

	// Constructor

	public ElementGroups()
	{
		this.setElementGroups(new HashMap<>());
	}

	// Methods

	public boolean contains(final Vector3f positionIn)
	{
		for (final ElementGroup elementGroup : this.getElementGroups().values())
		{
			if (elementGroup.has(positionIn))
			{
				return true;
			}
		}

		return false;
	}

	public boolean add(final int vaoIdIn, final ElementGroup elementGroupIn)
	{
		if (this.getElementGroups().containsKey(vaoIdIn))
		{
			return false;
		}

		this.getElementGroups().put(vaoIdIn, elementGroupIn);

		return true;
	}

	public boolean has(final int vaoIdIn)
	{
		return this.getElementGroups().containsKey(vaoIdIn);
	}

	public boolean hasValue(final ElementGroup elementGroupIn)
	{
		for (final ElementGroup elementGroup : this.getElementGroups().values())
		{
			if (elementGroup.equals(elementGroupIn))
			{
				return true;
			}
		}

		return false;
	}

	public ElementGroup get(final int vaoIdIn)
	{
		return this.getElementGroups().get(vaoIdIn);
	}

	public void remove(final int vaoIdIn)
	{
		this.getElementGroups().remove(vaoIdIn);
	}

	// Getter | Setter

	public Map<Integer, ElementGroup> getElementGroups()
	{
		return this.elementGroups;
	}

	public void setElementGroups(final Map<Integer, ElementGroup> elementGroupsIn)
	{
		this.elementGroups = elementGroupsIn;
	}
}