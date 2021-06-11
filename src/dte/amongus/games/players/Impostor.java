package dte.amongus.games.players;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import dte.amongus.games.AUGame;
import dte.amongus.player.PlayerRole;
import dte.amongus.shiptasks.ShipTask;

public class Impostor extends AUGamePlayer
{
	private final Set<Crewmate> crewmatesKilled = new HashSet<>();
	private final List<ShipTask> fakeTasks = new ArrayList<>();
	
	public Impostor(Player player, AUGame game) 
	{
		super(player, game, PlayerRole.IMPOSTOR);
	}
	
	public Set<Crewmate> getKilledPlayers()
	{
		return new HashSet<>(this.crewmatesKilled); 
	}
	
	public List<ShipTask> getFakeTasks()
	{
		return this.fakeTasks;
	}
	
	public void addKill(Crewmate crewmate)
	{
		this.crewmatesKilled.add(crewmate);
	}
}