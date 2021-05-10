package dte.amongus.shiptasks.inventory;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ShipTask;

public interface InventoryTask extends ShipTask
{
	TaskInventoryManager getInventoryManager();
	
	@Override
	default void onFinish(AUGamePlayer gamePlayer) 
	{
		gamePlayer.getPlayer().closeInventory();
	}
}