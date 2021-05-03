package dte.amongus.shiptasks.inventory;

import dte.amongus.shiptasks.ShipTask;

public interface InventoryTask<IM extends TaskInventoryManager<?>> extends ShipTask
{
	IM getInventoryManager();
}