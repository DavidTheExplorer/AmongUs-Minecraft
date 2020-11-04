package mazgani.amongus.shiptasks.inventorytasks;

import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.shiptasks.TaskInventoryManager;

public interface InventoryTask<T extends ShipTask>
{
	TaskInventoryManager<T> getInventoryManager();
}