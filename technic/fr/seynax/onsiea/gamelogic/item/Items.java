package fr.seynax.onsiea.gamelogic.item;

import java.util.HashMap;
import java.util.Map;

import fr.seynax.onsiea.utils.Texture;

public class Items
{
	// Constants

	private final static Map<String, Item>	ITEMS		= new HashMap<>();

	private final static Item				ITEM_TEST	= Items.createItem("test", Texture.loadTexture("item_test"));

	// methods

	private final static Item createItem(final String itemNameIn, final Texture textureIn)
	{
		final var item = new Item(itemNameIn, textureIn);

		Items.getItems().put(itemNameIn, item);

		return item;
	}

	// Getter | Setter

	public final static Map<String, Item> getItems()
	{
		return Items.ITEMS;
	}

	public final static Item getItemTest()
	{
		return Items.ITEM_TEST;
	}
}