package mazgani.amongus.corpses.types;

import mazgani.amongus.corpses.types.specials.CompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class GraveSkullCorpse extends CompositeCorpse //implements ISkullCorpse, IGraveCorpse
{
	private final GraveCorpse graveCorpse;
	private final SkullCorpse skullCorpse;
	
	public GraveSkullCorpse(GamePlayer whoDied, AUGame game)
	{
		super(whoDied, game);
		
		this.graveCorpse = new GraveCorpse(whoDied, game);
		this.skullCorpse = new SkullCorpse(whoDied, game);
		
		addCorpse(this.graveCorpse);
		addCorpse(this.skullCorpse);
	}
	
	/*@Override
	public Location getSkullLocation() 
	{
		return this.skullCorpse.getSkullLocation();
	}

	@Override
	public Location[] getGraveBlocksLocations() 
	{
		return this.graveCorpse.getGraveBlocksLocations();
	}*/
}