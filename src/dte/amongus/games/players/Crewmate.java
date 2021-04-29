package dte.amongus.games.players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.AUGame;
import dte.amongus.player.PlayerRole;
import dte.amongus.shiptasks.ShipTask;

public class Crewmate extends AUGamePlayer
{
	private final List<ShipTask> remainingTasks = new ArrayList<>(), completedTasks = new ArrayList<>();
	
	private AbstractCorpse corpse;
	
	public Crewmate(Player player, AUGame game) 
	{
		super(player, game, PlayerRole.CREWMATE);
	}
	public List<ShipTask> getRemainingTasks()
	{
		return this.remainingTasks;
	}
	public List<ShipTask> getCompletedTasks()
	{
		return this.completedTasks;
	}
	public AbstractCorpse getCorpse() 
	{
		return this.corpse;
	}
	public void setCorpse(AbstractCorpse corpse) 
	{
		this.corpse = corpse;
	}
}
