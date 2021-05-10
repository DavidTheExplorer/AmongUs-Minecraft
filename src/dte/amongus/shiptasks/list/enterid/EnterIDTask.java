package dte.amongus.shiptasks.list.enterid;

import java.util.Optional;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.SimpleShipTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.type.TaskType;
import dte.amongus.utils.java.NumberUtils;
import dte.amongus.utils.java.RandomUtils;

public class EnterIDTask extends SimpleShipTask implements InventoryTask
{
	private final EnterIDInventoryManager inventoryManager = new EnterIDInventoryManager(this, Sound.BLOCK_ANVIL_USE);

	public EnterIDTask(AUGame game)
	{
		super("Enter ID", "Enter your Personal ID", TaskType.COMMON, game);
	}

	@Override
	public EnterIDInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
	
	public Optional<Integer> getPersonalID(AUGamePlayer gamePlayer) 
	{
		return getData(gamePlayer, "Personal ID", Integer.class);
	}
	
	public Optional<Integer> getEnteredID(AUGamePlayer gamePlayer)
	{
		return getData(gamePlayer, "Entered ID", Integer.class);
	}
	
	public void enterDigit(AUGamePlayer gamePlayer, int digit) throws ArithmeticException
	{
		int currentEnteredID = getOrPut(gamePlayer, "Entered ID", 0);

		setData(gamePlayer, "Entered ID", NumberUtils.add(currentEnteredID, digit));
	}
	
	@Override
	public void onStart(AUGamePlayer gamePlayer) 
	{
		//set the player's personal ID as a 3-5 digits number
		int randomID = RandomUtils.randomInt(100, 99999, true, true);
		
		setData(gamePlayer, "Personal ID", randomID);
	}
	
	@Override
	public void onFinish(AUGamePlayer gamePlayer) 
	{
		InventoryTask.super.onFinish(gamePlayer);
		
		removeData(gamePlayer, "Personal ID");
	}
}