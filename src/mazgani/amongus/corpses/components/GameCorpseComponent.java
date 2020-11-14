package mazgani.amongus.corpses.components;

import mazgani.amongus.interfaces.Trackable;

public interface GameCorpseComponent extends Trackable
{
	void spawn();
	void despawn();
}