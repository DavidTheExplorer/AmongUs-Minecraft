package dte.amongus.shiptasks.types.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ShipTask;
import dte.amongus.utils.InventoryUtils;

public abstract class TaskInventoryManager<T extends ShipTask>
{
	protected final T task;
	protected final AUGame game;

	public TaskInventoryManager(T task, AUGame game) 
	{
		this.task = task;
		this.game = game;
	}
	protected InventoryBuilder newInventoryBuilder(int lines) 
	{
		return new InventoryBuilder(lines);
	}
	public abstract Inventory createInventory(AUGamePlayer opener);
	public abstract boolean wasInvolvedAt(InventoryEvent event);
	
	public class InventoryBuilder
	{
		private int lines;
		private String customName;
		
		//walls data
		private boolean buildWalls;
		private Material wallsMaterial;
		
		public InventoryBuilder(int lines) 
		{
			this.lines = lines;
		}
		public InventoryBuilder withWalls(Material material) 
		{
			this.buildWalls = true;
			this.wallsMaterial = material;
			return this;
		}
		public InventoryBuilder withWalls() 
		{
			return withWalls(Material.BLACK_STAINED_GLASS_PANE);
		}
		public InventoryBuilder named(String customName) 
		{
			this.customName = customName;
			return this;
		}
		public Inventory build()
		{
			Inventory inv = Bukkit.createInventory(null, 9 * this.lines, getName());

			if(this.buildWalls) 
				InventoryUtils.buildWalls(inv, this.wallsMaterial);

			return inv;
		}
		private String getName() 
		{
			String description = this.customName != null ? this.customName : TaskInventoryManager.this.task.getDescription();
			
			return String.format("Task > %s", description);
		}
	}
}