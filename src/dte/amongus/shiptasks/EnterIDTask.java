package dte.amongus.shiptasks;

import java.util.Optional;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.inventory.enterid.EnterIDInventoryManager;
import dte.amongus.shiptasks.simple.SimpleShipTask;
import dte.amongus.utils.java.NumberUtils;
import dte.amongus.utils.java.RandomUtils;

public class EnterIDTask extends SimpleShipTask implements InventoryTask
{
	private final EnterIDInventoryManager inventoryManager;

	public EnterIDTask(AUGame game, Sound digitEnterSound, Sound identificationSucceedSound)
	{
		super("Enter ID", "Enter your Personal ID", TaskType.COMMON, game);
		
		this.inventoryManager = new EnterIDInventoryManager(this, digitEnterSound, identificationSucceedSound);
	}

	@Override
	public TaskInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
	
	public Optional<Integer> getPersonalID(Crewmate crewmate) 
	{
		return getData(crewmate, "Personal ID", Integer.class);
	}
	
	public Optional<Integer> getEnteredID(Crewmate crewmate)
	{
		return getData(crewmate, "Entered ID", Integer.class);
	}
	
	public void enterDigit(Crewmate crewmate, int digit) throws ArithmeticException
	{
		int currentEnteredID = getOrPut(crewmate, "Entered ID", 0);

		setData(crewmate, "Entered ID", NumberUtils.add(currentEnteredID, digit));
	}
	
	@Override
	public void onStart(Crewmate crewmate) 
	{
		//set the player's personal ID as a 3-5 digits number
		int randomID = RandomUtils.randomInt(100, 99999, true, true);
		
		setData(crewmate, "Personal ID", randomID);
	}
	
	@Override
	public void onFinish(Crewmate finisher) 
	{
		removeData(finisher, "Personal ID");
		removeData(finisher, "Entered ID");
	}
}