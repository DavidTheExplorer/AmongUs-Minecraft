package dte.amongus.internal;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.service.AUPlayerService;
import dte.amongus.player.visual.PlayerColor;

public class GamePlayerUtils 
{
	//Container of static methods
	private GamePlayerUtils(){}

	private static AUPlayerService auPlayerService;

	public static void setup(AUPlayerService auPlayerService) 
	{
		GamePlayerUtils.auPlayerService = auPlayerService;
	}
	
	public static AUPlayer toAUPlayer(AUGamePlayer gamePlayer) 
	{
		return auPlayerService.getAUPlayer(gamePlayer);
	}
	
	public static PlayerColor getColor(AUGamePlayer gamePlayer) 
	{
		return toAUPlayer(gamePlayer).getVisibilityManager().getCurrentColor();
	}
	
	public static String getColoredName(AUGamePlayer gamePlayer)
	{
		return getColor(gamePlayer).getChatColor() + gamePlayer.getPlayer().getName();
	}
}