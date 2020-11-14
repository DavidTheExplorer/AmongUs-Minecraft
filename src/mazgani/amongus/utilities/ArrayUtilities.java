package mazgani.amongus.utilities;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class ArrayUtilities 
{
	//Container of static methods
	private ArrayUtilities(){}
	
	@SafeVarargs
	public static <E> List<E> add(E[] array, E... elements) 
	{
		List<E> list = new ArrayList<>(array.length + elements.length);
		list.addAll(Arrays.asList(array));
		list.addAll(Arrays.asList(elements));
		
		return list;
	}
	public static <E> E[] merge(E[] values, E[] array)
	{
		E[] newArray = Arrays.copyOf(values, values.length + array.length);
		
		for(int i = 0; i < array.length; i++) 
		{
			newArray[values.length + i] = array[i];
		}
		return newArray;
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E[] merge(E value, E[] array)
	{
		E[] valueArray = (E[]) new Object[]{value};
		
		return merge(valueArray, array);
	}
	public static <E> List<E> mergeList(E value, E[] array)
	{
		List<E> list = Lists.newArrayList(array);
		list.add(value);
		
		return list;
	}
	
	@SafeVarargs
	public static <E> List<E> merge(E[]... arrays)
	{
		return Arrays.asList(arrays).stream()
				.flatMap(Arrays::stream)
				.collect(toList());
	}
}
