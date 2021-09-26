package dte.amongus.utils;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class RandomUtils
{
	//Container of static methods
	private RandomUtils(){}
	
	/*
	 * Numbers Methods
	 */
	/**
	 * Generates a random number in the range of {@code min} to {@code max}. 
	 * <p>
	 * Due to the ambiguity of the previous sentence, this method takes 2 more parameters that determine that range.
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
		
		return min + ThreadLocalRandom.current().nextInt(max-min);
	}
	
	/**
	 * Generates a random number in the range of {@code min}(included) to {@code max}(excluded).
	 * 
	 * @param min The min number.
	 * @param max The max number.
	 * @return the The generated random number.
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
		if(array.length == 0)
			return null;
		
		return array[randomInt(0, array.length)];
	}
	
	public static <E> E randomElement(Collection<E> collection) 
	{
		if(collection.isEmpty()) 
			return null;
		
		if(collection instanceof List) 
		{
			List<E> list = (List<E>) collection;
			
			return list.get(ThreadLocalRandom.current().nextInt(list.size()));
		}
		return collection.stream()
				.skip(ThreadLocalRandom.current().nextInt(collection.size()))
				.findFirst()
				.get();
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
		 * @param tester the elements tester.
		 * @return a random element.
		 */
		public E findElementThat(Predicate<E> tester)
		{
			if(this.collection.isEmpty()) 
				return null;
			
			Set<E> approvedElements = this.collection.stream()
					.filter(tester)
					.collect(toSet());
			
			return randomElement(approvedElements);
		}
	}
}