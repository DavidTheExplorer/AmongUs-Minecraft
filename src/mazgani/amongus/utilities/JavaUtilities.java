package mazgani.amongus.utilities;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;

public class JavaUtilities 
{
	public static <E> E randomElement(Collection<E> collection) 
	{
		if(collection.isEmpty()) 
		{
			return null;
		}
		if(collection instanceof RandomAccess) 
		{
			List<E> list = (List<E>) collection;
			
			return list.get(ThreadLocalRandom.current().nextInt(list.size()));
		}
		int random = ThreadLocalRandom.current().nextInt(collection.size());
		
		for(Iterator<E> iterator = collection.iterator(); iterator.hasNext();) 
		{
			E element = iterator.next();
			
			if(random == 0)
			{
				return element;
			}
			random--;
		}
		return null;
	}
}
