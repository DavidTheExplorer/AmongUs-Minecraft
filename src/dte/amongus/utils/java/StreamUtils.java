package dte.amongus.utils.java;

import java.util.Collections;
import java.util.List;

public class StreamUtils 
{
	//Container of static methods
	private StreamUtils(){}
	
	public static <E> List<E> randomized(List<E> list)
	{
		Collections.shuffle(list);
		
		return list;
	}
}
