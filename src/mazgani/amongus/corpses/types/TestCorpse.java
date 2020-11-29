package mazgani.amongus.corpses.types;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import mazgani.amongus.AmongUs;
import mazgani.amongus.corpses.components.holograms.HologramComponent;
import mazgani.amongus.corpses.types.specials.CompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.holograms.equallable.CommonEquallableHologram;
import mazgani.amongus.holograms.equallable.EquallableHologram;

public class TestCorpse extends CompositeCorpse
{
	public TestCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}

	@Override
	public void initComponents(Location bestLocation) 
	{
		/*Block block = bestLocation.getBlock();
		
		addComponent(new BlockChangeComponent(this, block, Material.OBSIDIAN));
		addComponent(new DeathSignComponent(this, block, Material.BIRCH_SIGN, getWhoDied().getPlayer().getName()));*/
		addComponent(HologramComponent.of(this, createHologram(bestLocation)));
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		return deathLocation.add(0, 2, 0);
	}
	private EquallableHologram createHologram(Location location) 
	{
		Hologram hologram = HologramsAPI.createHologram(AmongUs.getInstance(), location);
		
		hologram.appendTextLine(getWhoDied().getAUPlayer().getPlayer().getName());
		hologram.appendTextLine("is a fucking");
		hologram.appendItemLine(new ItemStack(Material.DIAMOND_SWORD));
		
		return new CommonEquallableHologram(hologram);
	}
}