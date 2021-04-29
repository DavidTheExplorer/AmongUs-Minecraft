package dte.amongus.shiptasks.types.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryTask<IM extends TaskInventoryManager<?>>
{
	IM getInventoryManager();
	void onInventoryClick(InventoryClickEvent event);
}