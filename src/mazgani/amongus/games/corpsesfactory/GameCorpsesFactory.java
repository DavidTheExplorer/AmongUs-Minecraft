package mazgani.amongus.games.corpsesfactory;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public interface GameCorpsesFactory
{
	BasicGameCorpse generateCorpse(GamePlayer whoDied, AUGame game);
}
