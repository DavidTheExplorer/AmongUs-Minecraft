package dte.amongus.listeners.games;

import static dte.amongus.player.statistics.Statistic.TOTAL_WINS;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.events.games.GameWinEvent;
import dte.amongus.games.AUGame;
import dte.amongus.internal.GamePlayerUtils;
import dte.amongus.player.AUPlayer;

public class GameWinListener implements Listener
{
	@EventHandler
	public void onGameWin(GameWinEvent event) 
	{
		AUGame game = event.getGame();
		
		List<AUPlayer> auWinners = game.getPlayers(event.getWinnerType().getPlayerClass()).stream()
				.map(GamePlayerUtils::toAUPlayer)
				.collect(toList());
		
		auWinners.forEach(winner -> winner.getStats().increment(TOTAL_WINS));
	}
}