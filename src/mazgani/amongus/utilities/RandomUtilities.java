package mazgani.amongus.utilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Predicate;

public class RandomUtilities
{
	//Container of static methods
	private RandomUtilities(){}
	
	private static Random random = new Random();
	
	/*
	 * Numbers Methods
	 */
	/**
	 * Returns a random number in the range of {@code min} to {@code max}. 
	 * <p>
	 * Due to the ambiguity of the previous sentence, this method takes 2 more parameters.
	 * 
	 * @param min the min number
	 * @param max the max number
	 * @param includeMin whether the {@code min} can be returned
	 * @param includeMax whether the {@code max} can be returned
	 * @return the random number
	 */
	public static int randomInt(int min, int max, boolean includeMin, boolean includeMax)
	{
		if(!includeMin) min++;
		if(includeMax) max++;
		
		return min + random.nextInt(max-min);
	}
	
	/**
	 * Returns a random number in the range of {@code min} to {@code max} ({@code min} included, {@code max} excluded)
	 * @param min the min number
	 * @param max the max number
	 * @return the random number
	 */
	public static int randomInt(int min, int max) 
	{
		return randomInt(min, max, true, false);
	}
	
	/*
	 * Collection Methods
	 */
	public static <E> E randomElement(E[] array)
	{
		return array[randomInt(0, array.length)];
	}
	public static <E> E randomElement(Collection<E> collection) 
	{
		if(collection.isEmpty()) 
		{
			return null;
		}
		if(collection instanceof List) 
		{
			List<E> list = (List<E>) collection;
			
			return list.get(random.nextInt(list.size()));
		}
		return collection.stream()
				.skip(random.nextInt(collection.size()))
				.findFirst().get();
	}
	
	/*
	 * Map Methods
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
	 * Fluent Method Chaining to handle methods on collections
	 */
	public static <E> CollectionHelper<E> from(Collection<E> collection) 
	{
		return new CollectionHelper<E>(collection);
	}
	public static <E> CollectionHelper<E> from(E[] array) 
	{
		return from(Arrays.asList(array));
	}
	
	/**
	 * A class that holds a collection(so it's safe to reuse) and helps with actions that involves randomness.
	 * @param <E> the type of the collection
	 */
	public static class CollectionHelper<E>
	{
		private Collection<E> collection;
		
		public CollectionHelper(Collection<E> collection) 
		{
			this.collection = collection;
		}
		public E getElementThat(Predicate<E> tester)
		{
			if(this.collection.isEmpty()) 
			{
				return null;
			}
			E element;
			
			do 
			{
				element = randomElement(this.collection);
			}
			while(!tester.test(element));
			
			return element;
		}
	}
}
