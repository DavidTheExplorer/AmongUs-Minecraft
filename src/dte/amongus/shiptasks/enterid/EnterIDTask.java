package dte.amongus.shiptasks.enterid;

import java.util.Optional;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.SimpleShipTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.type.TaskType;

public class EnterIDTask extends SimpleShipTask implements InventoryTask<EnterIDInventoryManager>
{
	private final EnterIDInventoryManager inventoryManager = new EnterIDInventoryManager(this);
	
	public EnterIDTask(AUGame game)
	{
		super("Enter ID", "Enter your personal ID", TaskType.COMMON, game);
	}

	@Override
	public EnterIDInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
	
	public void setID(AUGamePlayer gamePlayer, int digit) 
	{
		
	}
	
	public void addDigit(AUGamePlayer gamePlayer, int digit) 
	{
		Optional<String> playerID = getData(gamePlayer, "ID").map(String.class::cast);
		
		if(!playerID.isPresent()) 
		{
			setData(gamePlayer, "ID", String.valueOf(digit));
			return;
		}
		else 
		{
			String updatedID = playerID.get() + String.valueOf(digit);
			
			setData(gamePlayer, "ID", updatedID);
		}
	}
}