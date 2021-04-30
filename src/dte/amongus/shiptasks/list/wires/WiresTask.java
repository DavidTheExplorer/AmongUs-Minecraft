package dte.amongus.shiptasks.list.wires;

import org.bukkit.inventory.ItemStack;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.types.ProgressionTask;
import dte.amongus.shiptasks.types.inventory.InventoryTask;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresTask extends ProgressionTask implements InventoryTask<WiresInventoryManager>
{
	private final WiresInventoryManager wiresInvManager = new WiresInventoryManager(this);

	public static final int WIRES_AMOUNT = 4;
	
	public WiresTask(AUGame game)
	{
		super("Wires Fix", "Connect all 4 wires.", game, WIRES_AMOUNT);
	}
	
	@Override
	public WiresInventoryManager getInventoryManager() 
	{
		return this.wiresInvManager;
	}
	
	@SuppressWarnings("unchecked") //this project calls this method in the appropriate times
	public Pair<Integer, ItemStack> getCurrentWire(AUGamePlayer gamePlayer)
	{
		return getData(gamePlayer, "Current Wire")
				.map(data -> (Pair<Integer, ItemStack>) data)
				.orElse(null);
	}
	
	public void setCurrentWire(AUGamePlayer gamePlayer, int inventorySlot, ItemStack wire) 
	{
		setData(gamePlayer, "Current Wire", Pair.of(inventorySlot, wire));
	}
}