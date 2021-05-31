package dte.amongus.shiptasks.list.enterid;

import java.util.Optional;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.SimpleShipTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.type.TaskType;
import dte.amongus.utils.java.NumberUtils;
import dte.amongus.utils.java.RandomUtils;

public class EnterIDTask extends SimpleShipTask implements InventoryTask
{
	private final EnterIDInventoryManager inventoryManager;

	public EnterIDTask(AUGame game, Sound digitEnterSound)
	{
		super("Enter ID", "Enter your Personal ID", TaskType.COMMON, game);
		
		this.inventoryManager = new EnterIDInventoryManager(this, digitEnterSound);
	}

	@Override
	public TaskInventoryManager getInventoryManager() 
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
	public void onStart(Crewmate crewmate) 
	{
		//set the player's personal ID as a 3-5 digits number
		int randomID = RandomUtils.randomInt(100, 99999, true, true);
		
		setData(crewmate, "Personal ID", randomID);
	}
	
	@Override
	public void onFinish(Crewmate crewmate) 
	{
		removeData(crewmate, "Personal ID");
	}
}