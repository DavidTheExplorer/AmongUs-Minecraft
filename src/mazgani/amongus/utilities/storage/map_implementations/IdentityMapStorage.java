package mazgani.amongus.utilities.storage.map_implementations;

import java.util.Map;

import mazgani.amongus.utilities.storage.IdentityStorage;
import mazgani.amongus.utilities.storage.identificators.Identifyable;

public class IdentityMapStorage<K, V extends Identifyable<K>> extends KeyValueMapStorage<K, V> implements IdentityStorage<K, V>
{
	public IdentityMapStorage(Map<K, V> base) 
	{
		super(base);
	}
}