package mazgani.amongus.shiptasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.utilities.InventoryUtilities;

public abstract class TaskInventoryManager<T extends ShipTask>
{
	protected final T task;
	protected final AUGame game;

	protected TaskInventoryManager(T task, AUGame game) 
	{
		this.task = task;
		this.game = game;
	}
	protected Inventory createDefaultInventory(int lines, boolean buildWalls, String taskDescription) 
	{
		Inventory inv = Bukkit.createInventory(null, 9 * lines, "Task > " + taskDescription);

		if(buildWalls) 
			InventoryUtilities.buildWalls(inv, Material.BLACK_STAINED_GLASS_PANE);

		return inv;
	}
	public abstract Inventory createInventory(GamePlayer opener);
	public abstract boolean wasInvolvedAt(InventoryEvent event);
}
