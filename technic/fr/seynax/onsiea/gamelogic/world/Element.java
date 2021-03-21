package fr.seynax.onsiea.gamelogic.world;

import fr.seynax.onsiea.utils.Texture;

public class Element
{
	// Constructor variables

	private String	name;

	private int		textureId;

	// Constructr

	public Element(final String nameIn, final int textureIdIn)
	{
		this.setName(nameIn);

		this.setTextureId(textureIdIn);
	}

	// Methods

	public boolean isSame(final String nameIn)
	{
		return this.getName().contentEquals(nameIn);
	}

	public void bindTexture()
	{
		Texture.bind(this.getTextureId());
	}

	@Override
	public int hashCode()
	{
		final var	prime	= 31;
		var			result	= 1;
		result	= prime * result + (this.name == null ? 0 : this.name.hashCode());
		result	= prime * result + this.textureId;
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (Element) obj;
		if (this.name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!this.name.equals(other.name))
		{
			return false;
		}
		if (this.textureId != other.textureId)
		{
			return false;
		}
		return true;
	}

	// Getter | Setter

	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}

	public int getTextureId()
	{
		return this.textureId;
	}

	public void setTextureId(final int textureIdIn)
	{
		this.textureId = textureIdIn;
	}
}