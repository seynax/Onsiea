package fr.seynax.onsiea.graphics.gui.elements;

import java.util.Map;

import fr.seynax.onsiea.gamelogic.item.Rectangle;

public interface IGuiElement
{
	String getActiveTextureName();

	int getActiveTextureId();

	Map<String, Integer> getTextures();

	Rectangle getRectangle();
}