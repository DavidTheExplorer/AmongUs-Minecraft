package mazgani.amongus.corpses.types;

import org.bukkit.Location;
import org.bukkit.Material;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import mazgani.amongus.AmongUs;
import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.BlockChangeComponent;
import mazgani.amongus.corpses.components.defaults.DeathHologramComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.holograms.CommonEquallableHologram;

public class SkullCorpse extends BasicGameCorpse
{
	public SkullCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}
	
	@Override
	public void initComponents(Location location)
	{
		addComponent(new BlockChangeComponent(location.getBlock(), Material.PLAYER_HEAD));
		
		Hologram deathHologram = HologramsAPI.createHologram(AmongUs.getInstance(), location);
		addComponent(new DeathHologramComponent(new CommonEquallableHologram(deathHologram), this, getWhoDied(), getGame()));
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		return deathLocation;
	}
}