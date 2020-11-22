package mazgani.amongus.games.corpsesfactory;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.types.GraveSkullCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class SimpleCorpsesFactory implements GameCorpsesFactory
{
	@Override
	public BasicGameCorpse generateCorpse(GamePlayer whoDied, AUGame game) 
	{
		return new GraveSkullCorpse(whoDied, game);
	}
}