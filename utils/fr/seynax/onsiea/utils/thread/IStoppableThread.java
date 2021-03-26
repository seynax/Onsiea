package fr.seynax.onsiea.utils.thread;

public interface IStoppableThread extends Runnable
{
	Thread start();

	Thread getThread();

	String getName();

	void stop();
}