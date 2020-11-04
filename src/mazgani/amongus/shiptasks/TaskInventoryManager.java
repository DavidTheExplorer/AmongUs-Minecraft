package mazgani.amongus.shiptasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.utilities.InventoryUtilities;

@RequiredArgsConstructor
public abstract class TaskInventoryManager<T extends ShipTask>
{
	@Getter(value = AccessLevel.PACKAGE)
	protected final AUGame game;
	
	@Getter(value = AccessLevel.PACKAGE)
	protected final T task;
	
	protected Inventory createDefaultInventory(int lines, boolean buildWalls, String taskDescription) 
	{
		Inventory inv = Bukkit.createInventory(null, 9 * lines, "Task > " + taskDescription);

		if(buildWalls) 
		{
			InventoryUtilities.buildWalls(inv, Material.BLACK_STAINED_GLASS_PANE);
		}
		return inv;
	}
	public abstract Inventory createInventory();
}
