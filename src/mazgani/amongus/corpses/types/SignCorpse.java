package mazgani.amongus.corpses.types;

import org.bukkit.Location;
import org.bukkit.Material;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.SignComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class SignCorpse extends BasicGameCorpse
{
	private final Material signMaterial;
	private final String[] newLines;
	
	public SignCorpse(GamePlayer whoDied, AUGame game, Material signMaterial, String... newLines) 
	{
		super(whoDied, game);
		
		this.signMaterial = signMaterial;
		this.newLines = newLines;
	}
	
	@Override
	public void initComponents(Location bestLocation) 
	{
		addComponent(new SignComponent(this, bestLocation.getBlock(), this.signMaterial, this.newLines));
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		return deathLocation;
	}
}