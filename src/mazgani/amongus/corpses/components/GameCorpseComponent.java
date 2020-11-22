package mazgani.amongus.corpses.components;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.interfaces.Trackable;

public interface GameCorpseComponent extends Trackable
{
	AbstractGameCorpse getCorpse();
	void spawn();
	void despawn();
	boolean equals(Object object);
}