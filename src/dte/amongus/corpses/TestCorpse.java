package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import dte.amongus.AmongUs;
import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.holograms.HologramComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.holograms.EquallableHologram;

public class TestCorpse extends BasicCorpse
{
	public TestCorpse(Crewmate whoDied, Location deathLocation) 
	{
		super(whoDied);
		
		Block deathBlock = deathLocation.getBlock();

		/*addComponent(new BlockChangeComponent(this, deathBlock, Material.OBSIDIAN));
		addComponent(new DeathSignComponent(this, deathBlock, Material.BIRCH_SIGN, getWhoDied().getPlayer().getName()));*/
		
		addComponent(HologramComponent.of(this, createHologram(deathBlock.getLocation())));
	}
	
	private EquallableHologram createHologram(Location location) 
	{
		Hologram hologram = HologramsAPI.createHologram(AmongUs.getInstance(), location);

		hologram.appendTextLine(whoDied().getPlayer().getName());
		hologram.appendTextLine("is a fucking");
		hologram.appendItemLine(new ItemStack(Material.DIAMOND_SWORD));

		return new EquallableHologram(hologram);
	}
}