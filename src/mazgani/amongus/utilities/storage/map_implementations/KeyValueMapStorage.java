package mazgani.amongus.utilities.storage.map_implementations;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import mazgani.amongus.utilities.storage.KeyValueStorage;

public class KeyValueMapStorage<K, V> implements KeyValueStorage<K, V>
{
	private final Map<K, V> storage;
	
	public KeyValueMapStorage(Map<K, V> base) 
	{
		this.storage = base;
	}
	
	@Override
	public V get(K key)
	{
		return this.storage.get(key);
	}

	@Override
	public V put(K key, V value) 
	{
		return this.storage.put(key, value);
	}

	@Override
	public Set<K> getKeys()
	{
		return this.storage.keySet();
	}

	@Override
	public Collection<V> getValues()
	{
		return this.storage.values();
	}
}
