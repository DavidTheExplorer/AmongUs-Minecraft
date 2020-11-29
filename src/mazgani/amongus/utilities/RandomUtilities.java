package mazgani.amongus.utilities;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.RandomAccess;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class RandomUtilities
{
	//Container of static methods
	private RandomUtilities(){}

	private static Random random = new Random();

	/*
	 * Numbers
	 */
	/**
	 * Returns a random number in the range of {@code min} to {@code max}. 
	 * <p>
	 * Due to the ambiguity of the previous sentence, this method takes 2 more parameters.
	 * 
	 * @param min The min number.
	 * @param max The max number.
	 * @param includeMin Whether the {@code min} can be returned.
	 * @param includeMax Whether the {@code max} can be returned.
	 * @return The generated random number.
	 */
	public static int randomInt(int min, int max, boolean includeMin, boolean includeMax)
	{
		if(!includeMin) min++;
		if(includeMax) max++;

		return min + random.nextInt(max-min);
	}

	/**
	 * Returns a random number in the range of the provided {@code min}(inclusive) and {@code max}(exclusive).
	 * 
	 * @param min The (included) min number.
	 * @param max The (excluded) max number.
	 * @return The generated random number.
	 */
	public static int randomInt(int min, int max) 
	{
		return randomInt(min, max, true, false);
	}
	
	/**
	 * Returns a random number in the range of the provided {@code min}(inclusive) and {@code max}(exclusive) that the {@code tester} accepts. 
	 * <p>
	 * All the approved elements have an equal probability to be selected.
	 * 
	 * @param min The min number.
	 * @param max The max number.
	 * @param tester The elements tester.
	 * @return The generated accepted number.
	 */
	public static int randomInt(int minInclusive, int maxExclusive, Predicate<Integer> tester) 
	{
		List<Integer> rangedNumbers = IntStream.range(minInclusive, maxExclusive)
				.boxed()
				.collect(toList());
		
		return randomElement(rangedNumbers);
	}
	
	/*
	 * Collections & Arrays
	 */
	public static <E> E randomElement(E[] array)
	{
		int randomIndex = randomInt(0, array.length);

		return array[randomIndex];
	}
	public static <E> E randomElement(Collection<E> collection) 
	{
		if(collection.isEmpty()) 
		{
			return null;
		}
		int randomIndex = randomInt(0, collection.size());

		if(collection instanceof RandomAccess) 
		{
			List<E> list = (List<E>) collection;
			
			return list.get(randomIndex);
		}
		else 
		{
			//iterate a random amount of times and return the element at the reached index
			for(E element : collection)
			{
				if(randomIndex == 0)
				{
					return element;
				}
				randomIndex--;
			}
			return null; //unreachable
		}
	}
	public static <E> E findElementThat(Collection<E> collection, Predicate<E> tester) 
	{
		List<E> approvedElements = collection.stream()
				.filter(tester)
				.collect(toCollection(ArrayList::new));
		
		return randomElement(approvedElements);
	}

	/*
	 * Maps
	 */
	public static <K, V> K randomKey(Map<K, V> map) 
	{
		return randomElement(map.keySet());
	}
	public static <K, V> V randomValue(Map<K, V> map) 
	{
		return randomElement(map.values());
	}
	public static <K, V> Entry<K, V> randomEntry(Map<K, V> map)
	{
		return randomElement(map.entrySet());
	}
	
	/*
	 * Fluent Method Chaining for collection methods
	 */
	public static <E> CollectionHelper<E> from(Collection<E> collection) 
	{
		return new CollectionHelper<E>(collection);
	}
	public static <E> CollectionHelper<E> from(E[] array) 
	{
		return from(Arrays.asList(array));
	}
	public static CollectionHelper<Integer> fromRange(int minInclusive, int maxExclusive)
	{
		List<Integer> rangedNumbers = IntStream.range(minInclusive, maxExclusive)
				.boxed()
				.collect(toList());
		
		return from(rangedNumbers);
	}

	/**
	 * A Helper class that holds a collection reference and makes it easier to perform actions that involve randomness - on that collection.
	 * 
	 * @param <E> the type of the elements in the collection.
	 */
	public static class CollectionHelper<E>
	{
		private final Collection<E> collection;

		public CollectionHelper(Collection<E> collection) 
		{
			this.collection = collection;
		}

		/**
		 * Returns a random element from the collection that the {@code tester} approves.
		 * 
		 * @param tester The elements tester.
		 * @return A random element from the collection.
		 */
		public E findElementThat(Predicate<E> tester)
		{
			return RandomUtilities.findElementThat(this.collection, tester);
		}
	}
	
	/**
	 * A Helper class that holds a map reference and makes it easier to perform actions that involve randomness - on that map.
	 * 
	 * @param <E> the type of the elements in the map.
	 */
	public static class MapHelper<K, V>
	{
		private final Map<K, V> map;
		
		public MapHelper(Map<K, V> map) 
		{
			this.map = map;
		}
		public K getKeyThat(Predicate<K> tester) 
		{
			return RandomUtilities.findElementThat(this.map.keySet(), tester);
		}
		public V getValueThat(Predicate<V> tester) 
		{
			return RandomUtilities.findElementThat(this.map.values(), tester);
		}
		public Entry<K, V> getEntryThat(Predicate<Entry<K, V>> tester)
		{
			return RandomUtilities.findElementThat(this.map.entrySet(), tester);
		}
	}
}