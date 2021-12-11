package fr.seynax.onsiea.game.block;

public class Block
{
	// Variables

	private String name;

	// Constructor

	public Block(final String nameIn)
	{
		this.setName(nameIn);
	}

	// Getter | Setter

	public String getName()
	{
		return this.name;
	}

	private void setName(final String nameIn)
	{
		this.name = nameIn;
	}
}