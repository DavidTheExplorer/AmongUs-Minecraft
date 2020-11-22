package mazgani.amongus.utilities.storage.map_implementations;

import java.util.Map;

import mazgani.amongus.utilities.storage.ByNameStorage;
import mazgani.amongus.utilities.storage.identificators.Named;

public class ByNameMapStorage<E extends Named> extends IdentityMapStorage<String, E> implements ByNameStorage<E>
{
	public ByNameMapStorage(Map<String, E> base)
	{
		super(base);
	}
}
