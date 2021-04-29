package dte.amongus.player;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;

public enum PlayerRole 
{
	CREWMATE(Crewmate.class),
	IMPOSTOR(Impostor.class);
	
	private final String name;
	private final Class<? extends AUGamePlayer> playerClass;
	
	PlayerRole(Class<? extends AUGamePlayer> playerClass)
	{
		this.name = WordUtils.capitalizeFully(name().toLowerCase());
		this.playerClass = playerClass;
	}
	public String getName()
	{
		return this.name;
	}
	public Class<? extends AUGamePlayer> getPlayerClass()
	{
		return this.playerClass;
	}
	public static PlayerRole getRandomRole() 
	{
		return ThreadLocalRandom.current().nextBoolean() ? CREWMATE : IMPOSTOR;
	}
	public static PlayerRole getLoserRole(PlayerRole winnerRole) 
	{
		switch(winnerRole) 
		{
		case CREWMATE:
			return IMPOSTOR;
		case IMPOSTOR:
			return CREWMATE;
		default:
			return null;
		}
	}
}