package mazgani.amongus.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtilities 
{
	//Container of static methods
	private CollectionUtilities(){}
	
	public static <E> List<E> findAllDuplicates(Collection<E> elements)
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
	public static <E> List<E> findAllDuplicates(E[] array)
	{
		return findAllDuplicates(Arrays.asList(array));
	}
}