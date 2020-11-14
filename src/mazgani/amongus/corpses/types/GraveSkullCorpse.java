package mazgani.amongus.corpses.types;

import mazgani.amongus.corpses.types.specials.CompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class GraveSkullCorpse extends CompositeCorpse //implements ISkullCorpse, IGraveCorpse
{
	private final SkullCorpse skullCorpse;
	private final GraveCorpse graveCorpse;
	
	public GraveSkullCorpse(GamePlayer whoDied, AUGame game)
	{
		super(whoDied, game);
		
		this.skullCorpse = new SkullCorpse(whoDied, game);
		this.graveCorpse = new GraveCorpse(whoDied, game);
		
		setSpawnOrder(this.skullCorpse, this.graveCorpse);
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