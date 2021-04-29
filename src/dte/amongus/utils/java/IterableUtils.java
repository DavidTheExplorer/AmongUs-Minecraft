package dte.amongus.utils.java;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IterableUtils
{
	//Container of static methods
	private IterableUtils(){}

	public static final <T> List<T> getElementsOf(Class<T> typeClass, Iterable<? super T> iterable) 
	{
		return StreamSupport.stream(iterable.spliterator(), false)
				.filter(typeClass::isInstance)
				.map(typeClass::cast)
				.collect(toList());
	}
	public static <E> List<E> findAllDuplicates(E[] array)
	{
		return findAllDuplicates(Arrays.asList(array));
	}
	public static <E> List<E> findAllDuplicates(Iterable<E> elements)
	{
		List<E> duplicates = new ArrayList<>();
		Map<E, Integer> visitsAmounts = new HashMap<>();

		for(E element : elements) 
		{
			int appearances = (1 + visitsAmounts.getOrDefault(element, 0));

			visitsAmounts.put(element, appearances);

			//if NOW we know that this element is a duplicate - add it 2 times to the list(this, and the first encounters)
			if(appearances == 2)
			{
				duplicates.add(element);
				duplicates.add(element);
			}
			else if(appearances > 2)
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}
}