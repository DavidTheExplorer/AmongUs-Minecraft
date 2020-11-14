package mazgani.amongus.utilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CollectionUtilities 
{
	//Container of static methods
	private CollectionUtilities(){}
	
	public static <E> Set<E> findDuplicates(Collection<E> collection)
	{
	    Set<E> duplicates = new LinkedHashSet<>();
	    Set<E> uniques = new HashSet<>();
	    
	    for(E element : collection)
	    {
	    	boolean isUnique = uniques.add(element);
	    	
	        if(!isUnique) 
	        {
	            duplicates.add(element);
	        }
	    }
	    return duplicates;
	}
	public static <E> Set<E> findDuplicates(E[] array)
	{
		return findDuplicates(Arrays.asList(array));
	}
}
