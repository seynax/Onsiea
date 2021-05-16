package fr.seynax.onsiea.utils.data;

public class Node<K, V>
{
	// Variables

	private Node<K, V>	previous;

	private K			key;
	private V			value;

	private Node<K, V>	next;

	// Constructor

	public Node(final K keyIn, final V valueIn)
	{
		this.setKey(keyIn);
		this.setValue(valueIn);
	}

	// Methods

	public void remove()
	{
		if (this.getPrevious() != null)
		{
			this.getPrevious().setNext(this.getNext());
		}
		if (this.getNext() != null)
		{
			this.getNext().setPrevious(this.getPrevious());
		}

		this.setKey(null);
		this.setValue(null);
		this.setPrevious(null);
		this.setNext(null);
	}

	public void addNext(final Node<K, V> nodeIn)
	{
		nodeIn.setNext(this.getNext());
		nodeIn.setPrevious(this);

		if (this.getNext() != null)
		{
			this.getNext().setPrevious(nodeIn);
		}
		this.setNext(nodeIn);
	}

	public void addNext(final K keyIn, final V valueIn)
	{
		final var node = new Node<>(keyIn, valueIn);

		node.setNext(this.getNext());
		node.setPrevious(this);

		if (this.getNext() != null)
		{
			this.getNext().setPrevious(node);
		}
		this.setNext(node);
	}

	public void addPrevious(final Node<K, V> nodeIn)
	{
		nodeIn.setNext(this);
		nodeIn.setPrevious(this.getPrevious());

		if (this.getPrevious() != null)
		{
			this.getPrevious().setNext(nodeIn);
		}
		this.setPrevious(nodeIn);
	}

	public void addPrevious(final K keyIn, final V valueIn)
	{
		final var node = new Node<>(keyIn, valueIn);

		node.setNext(this);
		node.setPrevious(this.getPrevious());

		if (this.getPrevious() != null)
		{
			this.getPrevious().setNext(node);
		}
		this.setPrevious(node);
	}

	public boolean hasNext()
	{
		return this.getNext() != null;
	}

	public boolean hasPrevious()
	{
		return this.getPrevious() != null;
	}
	// Getter | Setter

	public Node<K, V> getPrevious()
	{
		return this.previous;
	}

	public void setPrevious(final Node<K, V> previousIn)
	{
		this.previous = previousIn;
	}

	public K getKey()
	{
		return this.key;
	}

	public void setKey(final K keyIn)
	{
		this.key = keyIn;
	}

	public V getValue()
	{
		return this.value;
	}

	public void setValue(final V valueIn)
	{
		this.value = valueIn;
	}

	public Node<K, V> getNext()
	{
		return this.next;
	}

	public void setNext(final Node<K, V> nextIn)
	{
		this.next = nextIn;
	}
}
