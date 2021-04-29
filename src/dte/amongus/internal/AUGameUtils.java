package dte.amongus.internal;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Set;

import org.bukkit.entity.Player;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;

public class AUGameUtils 
{
	//Container of static methods
	private AUGameUtils(){}
	
	public static Set<Player> getBukkitPlayers(AUGame game)
	{
		return game.getPlayers().stream()
				.map(AUGamePlayer::getPlayer)
				.collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
	}
}