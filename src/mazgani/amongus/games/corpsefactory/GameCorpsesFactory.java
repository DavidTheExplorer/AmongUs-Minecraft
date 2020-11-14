package mazgani.amongus.games.corpsefactory;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public interface GameCorpsesFactory
{
	public BasicGameCorpse generateCorpse(GamePlayer whoDied, AUGame game);
}
