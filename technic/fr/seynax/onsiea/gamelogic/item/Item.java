package fr.seynax.onsiea.gamelogic.item;

import fr.seynax.onsiea.graphics.Texture;

public class Item
{
	// Variables

	private String	itemName;

	private Texture	texture;

	// Constructor

	public Item()
	{

	}

	public Item(final String itemNameIn, final Texture textureIn)
	{
		this.setItemName(itemNameIn);
		this.setTexture(textureIn);
	}

	// Getter | Setter

	public String getItemName()
	{
		return this.itemName;
	}

	public void setItemName(final String itemNameIn)
	{
		this.itemName = itemNameIn;
	}

	public Texture getTexture()
	{
		return this.texture;
	}

	public void setTexture(final Texture textureIn)
	{
		this.texture = textureIn;
	}
}