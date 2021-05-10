package dte.amongus.shiptasks.list.wires;

import java.util.Optional;

import org.bukkit.inventory.ItemStack;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ProgressionTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.type.TaskType;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresTask extends ProgressionTask implements InventoryTask
{
	private final WiresInventoryManager inventoryManager = new WiresInventoryManager(this);

	public static final int WIRES_AMOUNT = 4;
	
	public WiresTask(AUGame game)
	{
		super("Wires Fix", "Connect all 4 wires.", TaskType.COMMON, game, WIRES_AMOUNT);
	}
	
	@Override
	public WiresInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
	
	public void setCurrentWire(AUGamePlayer gamePlayer, int inventorySlot, ItemStack wire) 
	{
		setData(gamePlayer, "Current Wire", Pair.of(inventorySlot, wire));
	}
	
	public void removeCurrentWire(AUGamePlayer gamePlayer) 
	{
		removeData(gamePlayer, "Current Wire");
	}
	
	@SuppressWarnings("unchecked") //safe cast if the API is used correctly
	public Optional<Pair<Integer, ItemStack>> getCurrentWire(AUGamePlayer gamePlayer)
	{
		return getData(gamePlayer, "Current Wire", Pair.class).map(pair -> (Pair<Integer, ItemStack>) pair);
	}
}