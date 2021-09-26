package dte.amongus.utils.java;

import java.util.Map;
import java.util.function.Consumer;

public class MapUtils 
{
	public static <K, V> void ifMapped(Map<K, V> map, K key, Consumer<V> consumer) 
	{
		if(!map.containsKey(key))
			return;
		
		consumer.accept(map.get(key));
	}
}