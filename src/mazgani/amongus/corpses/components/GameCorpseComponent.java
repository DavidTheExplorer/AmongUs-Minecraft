package mazgani.amongus.corpses.components;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.interfaces.Trackable;

public interface GameCorpseComponent extends Trackable
{
	BasicGameCorpse getParentCorpse();
	void spawn();
	void despawn();
	
	/**
	 * Checks whether this component equals to the provided {@code component} to avoid spawning 2 identical components.
	 * @param object The other component.
	 * @return Whether the given component equals to this component.
	 */
	boolean equals(Object object);
}