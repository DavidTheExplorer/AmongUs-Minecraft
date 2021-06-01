package dte.amongus.sabotages;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RED;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import dte.amongus.AmongUs;
import dte.amongus.utils.EntityUtils;

public class CreepersTrap extends GatesSabotage
{
	private final int creepersAmount;
	private final Location creepersLocation;
	private final Set<Creeper> creepers = new HashSet<>();

	private static final Set<Creeper> SPAWNED_CREEPERS = new HashSet<>();

	static 
	{
		Bukkit.getPluginManager().registerEvents(new AntiCreeperDamageListener(), AmongUs.getInstance());
	}

	public CreepersTrap(Block[] gateBlocks, Location creepersLocation, int creepersAmount) 
	{
		super(gateBlocks);

		this.creepersLocation = creepersLocation;
		this.creepersAmount = creepersAmount;
	}

	@Override
	public void activate() 
	{
		super.activate();
		
		for(int i = 1; i <= this.creepersAmount; i++) 
		{
			Creeper creeper = spawnCreeper();
			
			this.creepers.add(creeper);
			SPAWNED_CREEPERS.add(creeper);
		}
	}

	@Override
	public void disable() 
	{
		super.disable();

		despawnCreepers();
	}
	
	private Creeper spawnCreeper() 
	{
		Creeper creeper = this.creepersLocation.getWorld().spawn(this.creepersLocation, Creeper.class);
		creeper.setHealth(10.0);
		creeper.setCustomName(GOLD + "Suprise Madafaka");
		
		return creeper;
	}
	
	private void despawnCreepers() 
	{
		SPAWNED_CREEPERS.removeAll(this.creepers);
		this.creepers.clear();
	}
	
	private static class AntiCreeperDamageListener implements Listener
	{
		@EventHandler
		public void preventExplosion(EntityExplodeEvent event)
		{
			if(!SPAWNED_CREEPERS.contains(event.getEntity()))
				return;
			
			event.blockList().clear();
			EntityUtils.getNearby(event.getEntity(), Player.class, 10).forEach(player -> player.sendMessage(RED + "You didn't get scared... Right?"));
		}

		@EventHandler
		public void preventDamage(EntityDamageByEntityEvent event)
		{
			if(!SPAWNED_CREEPERS.contains(event.getDamager()))
				return;

			event.setDamage(0);
		}
	}
}