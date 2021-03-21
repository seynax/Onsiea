package fr.seynax.onsiea.utils;

public class Pair<K, V>
{
	// Constructor variables

	private K	one;

	private V	two;

	// Constructor

	public Pair()
	{

	}

	public Pair(final K oneIn)
	{
		this.setOne(oneIn);
	}

	public Pair(final K oneIn, final V twoIn)
	{
		this.setOne(oneIn);
		this.setTwo(twoIn);
	}

	// Getter | Setter

	public K getOne()
	{
		return this.one;
	}

	public void setOne(final K oneIn)
	{
		this.one = oneIn;
	}

	public V getTwo()
	{
		return this.two;
	}

	public void setTwo(final V twoIn)
	{
		this.two = twoIn;
	}
}
