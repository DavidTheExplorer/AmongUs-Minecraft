package mazgani.amongus.corpses.types;

import org.bukkit.Material;

import mazgani.amongus.corpses.types.specials.CompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class GraveSignCorpse extends CompositeCorpse
{
	public GraveSignCorpse(GamePlayer whoDied, AUGame game, Material signMaterial, String... signLines)
	{
		super(whoDied, game);
		
		addCorpse(new GraveCorpse(whoDied, game));
		addCorpse(new SignCorpse(whoDied, game, signMaterial, signLines));
	}
}