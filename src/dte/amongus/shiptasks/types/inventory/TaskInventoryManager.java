package dte.amongus.shiptasks.types.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ObjectUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ShipTask;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.ItemBuilder;

public abstract class TaskInventoryManager<T extends ShipTask>
{
	protected final T task;
	
	public TaskInventoryManager(T task) 
	{
		this.task = task;
	}
	public InventoryBuilder newInventoryBuilder(int lines) 
	{
		return new InventoryBuilder(lines);
	}
	
	public abstract Inventory createInventory(AUGamePlayer opener);
	public abstract boolean wasInvolvedAt(InventoryEvent event);
	public abstract void onInventoryClick(InventoryClickEvent event); //change to return a boolean that indicates whether the player finished the task
	
	public class InventoryBuilder
	{
		private int lines;
		private String customName;
		
		//walls data
		private boolean buildWalls;
		private ItemStack wallsItem;
		
		public InventoryBuilder(int lines) 
		{
			this.lines = lines;
		}
		public InventoryBuilder withWalls(ItemStack wallsItem) 
		{
			this.buildWalls = true;
			this.wallsItem = wallsItem;
			return this;
		}
		public InventoryBuilder withWalls() 
		{
			return withWalls(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).named(ChatColor.BLACK + "-").createCopy());
		}
		public InventoryBuilder named(String customName) 
		{
			this.customName = customName;
			return this;
		}
		public Inventory build()
		{
			Inventory inv = Bukkit.createInventory(null, 9 * this.lines, createTitle());

			if(this.buildWalls) 
				InventoryUtils.buildWalls(inv, this.wallsItem);

			return inv;
		}
		
		private String createTitle() 
		{
			String description = ObjectUtils.defaultIfNull(this.customName, TaskInventoryManager.this.task.getDescription());
			
			return String.format("Task > %s", description);
		}
	}
}