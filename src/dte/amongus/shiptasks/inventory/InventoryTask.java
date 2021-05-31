package dte.amongus.shiptasks.inventory;

import dte.amongus.shiptasks.ShipTask;

public interface InventoryTask extends ShipTask
{
	TaskInventoryManager getInventoryManager();
}