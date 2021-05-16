package fr.seynax.onsiea.gamelogic.logic;

import fr.seynax.onsiea.graphics.IWindow;

public interface IGameLogic
{
	// Methods

	void initialization(IWindow windowIn) throws Exception;

	void input(IWindow windowIn);

	void update(double intervalIn, IWindow windowIn);

	void render(IWindow windowIn);

	void cleanup();
}
