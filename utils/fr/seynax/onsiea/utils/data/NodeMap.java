package fr.seynax.onsiea.utils.data;

import java.util.Iterator;

public class NodeMap<K, V> implements Iterable<V>
{
	// Variables

	private Node<K, V>			first;
	private Node<K, V>			last;

	private ValueIterator<K, V>	valueIterator;
	private KeyIterator<K, V>	keyIterator;

	// Constructor

	public NodeMap()
	{
		this.setFirst(new Node<K, V>(null, null));
		this.setLast(this.getFirst());

		this.setValueIterator(new ValueIterator<>(this.getFirst()));
		this.setKeyIterator(new KeyIterator<>(this.getFirst()));
	}

	// Methods

	public void put(final K keyIn, final V valueIn)
	{
		final var node = new Node<>(keyIn, valueIn);

		this.getLast().addNext(node);

		this.setLastRecurse(node);
	}

	public boolean putNext(final int orderIn, final K keyIn, final V valueIn)
	{
		final var	node		= new Node<>(keyIn, valueIn);

		var			i			= 0;

		var			iterator	= this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (i == orderIn)
			{
				iterator.addNext(node);

				if (iterator.getKey() == keyIn)
				{
					this.setLast(node);
				}

				return true;
			}

			i++;
		}

		return false;
	}

	public boolean putPrevious(final int orderIn, final K keyIn, final V valueIn)
	{
		final var	node		= new Node<>(keyIn, valueIn);

		var			i			= 0;

		var			iterator	= this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (i == orderIn)
			{
				iterator.addPrevious(node);

				return true;
			}

			i++;
		}

		return false;
	}

	public void setLastRecurse(final Node<K, V> toIn)
	{
		if (toIn.hasNext())
		{
			this.setLastRecurse(toIn.getNext());
		}
		else
		{
			this.setLast(toIn);
		}
	}

	public boolean containsKey(final K keyIn)
	{
		var iterator = this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (iterator.getKey().equals(keyIn))
			{
				return true;
			}
		}

		return false;
	}

	public boolean containsValue(final V valueIn)
	{
		var iterator = this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (iterator.getValue().equals(valueIn))
			{
				return true;
			}
		}

		return false;
	}

	public V get(final K keyIn)
	{
		var iterator = this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (iterator.getKey().equals(keyIn))
			{
				return iterator.getValue();
			}
		}

		return null;
	}

	public K getKey(final V valueIn)
	{
		var iterator = this.getFirst();

		while (iterator != null ? iterator.hasNext() : false)
		{
			iterator = iterator.getNext();

			if (iterator.getValue().equals(valueIn))
			{
				return iterator.getKey();
			}
		}

		return null;
	}

	public Iterator<V> valuesIterator()
	{
		this.getValueIterator().setIterator(this.getFirst());

		return this.getValueIterator();
	}

	public Iterator<K> keyIterator()
	{
		this.getKeyIterator().setIterator(this.getFirst());

		return this.getKeyIterator();
	}

	public V remove(final K keyIn)
	{
		if (keyIn == null)
		{
			return null;
		}

		var iterator = this.getFirst();

		while (iterator.hasNext())
		{
			iterator = iterator.getNext();

			if (iterator.getKey().equals(keyIn))
			{
				if (iterator.getKey().equals(this.getLast().getKey()))
				{
					this.setLast(this.getLast().getPrevious());
				}

				iterator.remove();

				return iterator.getValue();
			}
		}

		return null;
	}

	// Interface methods

	@Override
	public Iterator<V> iterator()
	{
		return this.getValueIterator();
	}

	// Getter | Setter

	public Node<K, V> getFirst()
	{
		return this.first;
	}

	public void setFirst(final Node<K, V> firstIn)
	{
		this.first = firstIn;
	}

	public Node<K, V> getLast()
	{
		return this.last;
	}

	public void setLast(final Node<K, V> lastIn)
	{
		this.last = lastIn;
	}

	public ValueIterator<K, V> getValueIterator()
	{
		return this.valueIterator;
	}

	public void setValueIterator(final ValueIterator<K, V> valueIteratorIn)
	{
		this.valueIterator = valueIteratorIn;
	}

	public KeyIterator<K, V> getKeyIterator()
	{
		return this.keyIterator;
	}

	public void setKeyIterator(final KeyIterator<K, V> keyIteratorIn)
	{
		this.keyIterator = keyIteratorIn;
	}

	// Class

	final static class ValueIterator<K, V> implements Iterator<V>
	{
		// Variables

		private Node<K, V> iterator;

		// Constructor

		ValueIterator(final Node<K, V> firstIn)
		{
			this.setIterator(firstIn);
		}

		// Interface methods

		@Override
		public boolean hasNext()
		{
			return this.getIterator().getNext() != null;
		}

		@Override
		public V next()
		{
			this.setIterator(this.getIterator().getNext());

			return this.getIterator().getValue();
		}

		// Getter | Setter

		public Node<K, V> getIterator()
		{
			return this.iterator;
		}

		public void setIterator(final Node<K, V> iteratorIn)
		{
			this.iterator = iteratorIn;
		}
	}

	final static class KeyIterator<K, V> implements Iterator<K>, Iterable<K>
	{
		// Variables

		private Node<K, V> iterator;

		// Constructor

		KeyIterator(final Node<K, V> firstIn)
		{
			this.setIterator(firstIn);
		}

		// Interface methods

		@Override
		public boolean hasNext()
		{
			return this.getIterator().getNext() != null;
		}

		@Override
		public K next()
		{
			this.setIterator(this.getIterator().getNext());

			return this.getIterator().getKey();
		}

		@Override
		public Iterator<K> iterator()
		{
			return this;
		}

		// Getter | Setter

		public Node<K, V> getIterator()
		{
			return this.iterator;
		}

		public void setIterator(final Node<K, V> iteratorIn)
		{
			this.iterator = iteratorIn;
		}
	}
}