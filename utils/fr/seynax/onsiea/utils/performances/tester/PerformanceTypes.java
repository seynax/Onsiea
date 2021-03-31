package fr.seynax.onsiea.utils.performances.tester;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTypes
{
	// Variables

	private List<EnumTypes> types;

	// Constructor

	public PerformanceTypes(final EnumTypes... typesIn)
	{
		this.setTypes(new ArrayList<>());

		for (final EnumTypes type : typesIn)
		{
			this.getTypes().add(type);
		}
	}

	public boolean has(final EnumTypes typeIn)
	{
		for (final EnumTypes type : this.getTypes())
		{
			if (type.equals(typeIn))
			{
				return true;
			}
		}

		return false;
	}

	// Getter | Setter

	public List<EnumTypes> getTypes()
	{
		return this.types;
	}

	public void setTypes(final List<EnumTypes> typesIn)
	{
		this.types = typesIn;
	}

	// Internal enum

	public enum EnumTypes
	{
		TIMING;
	}
}
