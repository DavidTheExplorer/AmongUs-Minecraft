package mazgani.amongus.utilities.storage;

import mazgani.amongus.utilities.storage.identificators.Identifyable;

public interface IdentityStorage<K, V extends Identifyable<K>> extends KeyValueStorage<K, V>
{
	default V put(V value)
	{
		return put(value.identifyableBy(), value);
	}
}