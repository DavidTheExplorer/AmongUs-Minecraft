package dte.amongus.shiptasks.types.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.ItemBuilder;

public abstract class TaskInventoryManager<T extends InventoryTask<?>>
{
	protected final T task;
	
	private final String inventoryTitle;
	
	public TaskInventoryManager(T task, String inventoryTitle) 
	{
		this.task = task;
		this.inventoryTitle = String.format(InventoryBuilder.TITLE_PATTERN, inventoryTitle);
	}
	public InventoryBuilder newInventoryBuilder(int lines) 
	{
		return new InventoryBuilder(lines);
	}
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return event.getView().getTitle().equals(this.inventoryTitle);
	}
	public abstract Inventory createInventory(AUGamePlayer opener);
	public abstract void onInventoryClick(InventoryClickEvent event); //change to return a boolean that indicates whether the player finished the task
	
	public class InventoryBuilder
	{
		private int lines;
		
		//walls data
		private boolean buildWalls;
		private ItemStack wallsItem;
		
		private static final String TITLE_PATTERN = "Task > %s";
		
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
		public Inventory build()
		{
			Inventory inv = Bukkit.createInventory(null, 9 * this.lines, TaskInventoryManager.this.inventoryTitle);
			
			if(this.buildWalls)
				InventoryUtils.buildWalls(inv, this.wallsItem);
			
			return inv;
		}
	}
}