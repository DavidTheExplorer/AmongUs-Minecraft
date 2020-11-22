package mazgani.amongus.corpses.types;

import org.bukkit.Location;
import org.bukkit.Material;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.BlockChangeComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class SkullCorpse extends BasicGameCorpse
{
	public SkullCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}
	
	@Override
	public void initComponents(Location bestLocation)
	{
		addComponent(new BlockChangeComponent(this, bestLocation.getBlock(), Material.PLAYER_HEAD));
		
		//Hologram deathHologram = HologramsAPI.createHologram(AmongUs.getInstance(), location);
		//addComponent(new DeathHologramComponent(this, new CommonEquallableHologram(deathHologram), getWhoDied(), getGame()));
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		return deathLocation;
	}
}