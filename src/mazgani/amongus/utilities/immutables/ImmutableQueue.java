package mazgani.amongus.utilities.immutables;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

public class ImmutableQueue<E> implements Queue<E>
{
	private final Queue<E> queue;

	private ImmutableQueue(Queue<E> queue) 
	{
		this.queue = queue;
	}
	public static <E> ImmutableQueue<E> of(Queue<E> queue)
	{
		return new ImmutableQueue<>(queue);
	}

	@Override
	public int size() 
	{
		return this.queue.size();
	}

	@Override
	public boolean isEmpty() 
	{
		return this.queue.isEmpty();
	}

	@Override
	public boolean contains(Object o) 
	{
		return this.queue.contains(o);
	}

	@Override
	public Iterator<E> iterator() 
	{
		return new UnmodifiableQueueIterator();
	}

	@Override
	public Object[] toArray() 
	{
		return this.queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) 
	{
		return this.queue.toArray(a);
	}

	@Override
	public boolean remove(Object o) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) 
	{
		return this.queue.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean offer(E e) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public E poll() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public E element() 
	{
		return this.queue.element();
	}

	@Override
	public E peek() 
	{
		return this.queue.peek();
	}

	private class UnmodifiableQueueIterator implements Iterator<E>
	{
		private final Iterator<E> iterator = ImmutableQueue.this.queue.iterator();

		@Override
		public boolean hasNext() 
		{
			return this.iterator.hasNext();
		}

		@Override
		public E next() 
		{
			return this.iterator.next();
		}

		@Override
		public void remove() 
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void forEachRemaining(Consumer<? super E> action) 
		{
			this.iterator.forEachRemaining(action);
		}
	};
}