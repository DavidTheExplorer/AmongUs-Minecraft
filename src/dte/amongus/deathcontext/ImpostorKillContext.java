package dte.amongus.deathcontext;

import org.bukkit.Location;

import dte.amongus.games.players.Impostor;

public class ImpostorKillContext extends DeathContext
{
	private final Impostor killer;
	
	public ImpostorKillContext(Location deathLocation, Impostor killer)
	{
		super(deathLocation);
		
		this.killer = killer;
	}
	
	public Impostor getKiller() 
	{
		return this.killer;
	}
}
