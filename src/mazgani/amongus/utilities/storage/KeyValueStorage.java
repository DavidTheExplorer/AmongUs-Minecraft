package mazgani.amongus.utilities.storage;

import java.util.Collection;
import java.util.Set;

public interface KeyValueStorage<K, V>
{
	V get(K key);
	V put(K key, V value);
	
	Set<K> getKeys();
	Collection<V> getValues();
}