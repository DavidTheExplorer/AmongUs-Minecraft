package dte.amongus.shiptasks.list.cleano2filter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class CleanO2FilterInventoryManager extends TaskInventoryManager
{
	private final CleanO2FilterTask cleanO2FilterTask;
	
	private static final ItemBuilder OUT_BUILDER = new ItemBuilder(Material.CHAIN, ChatColor.AQUA + "Out!")
			.withLore(ChatColor.WHITE + "Click to throw the selected Leaf!");
	
	private static final Material[] LEAVES = Arrays.stream(Material.values())
			.filter(material -> material.name().endsWith("_LEAVES"))
			.limit(CleanO2FilterTask.LEAVES_AMOUNT)
			.toArray(Material[]::new);
	
	private static final Set<Integer> OUTSIDE_SLOTS = Sets.newHashSet(18, 27);
	
	public CleanO2FilterInventoryManager(CleanO2FilterTask cleanO2FilterTask) 
	{
		this.cleanO2FilterTask = cleanO2FilterTask;
	}
	
	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, createTitle("Remove the Leaves!"));
		InventoryUtils.buildWalls(inventory, Material.BLACK_STAINED_GLASS_PANE);
		
		for(int i : new int[]{1, 10, 37, 46})
			inventory.setItem(i, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		
		InventoryUtils.fillColumn(inventory, 0, new ItemStack(Material.AIR));
		
		for(int i : new int[]{19, 28})
			inventory.setItem(i, OUT_BUILDER.createCopy());
		
		for(int i : OUTSIDE_SLOTS)
			inventory.setItem(i, null);
		
		//add the random leaves
		Queue<Material> leaves = new LinkedList<>(Arrays.asList(LEAVES));
		
		for(int i = 1; i <= CleanO2FilterTask.LEAVES_AMOUNT; i++) 
		{
			int randomLeafIndex = randomLeafIndex(inventory);
			
			inventory.setItem(randomLeafIndex, new ItemStack(leaves.poll()));
		}
		
		return inventory;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = this.cleanO2FilterTask.getGame().getPlayer(player);
		
		Optional<Integer> currentLeafIndex = this.cleanO2FilterTask.getCurrentLeafData(gamePlayer);
		
		if(item.getType() == Material.CHAIN) 
		{
			if(!currentLeafIndex.isPresent()) 
			{
				player.sendMessage(ChatColor.RED + "You have to select a Leaf!");
				return;
			}
			new LeafCleaningRunnable(event.getInventory(), currentLeafIndex.get(), event.getRawSlot());
			GlowEffect.deleteGlow(item);
		}
		else if(item.getType().name().endsWith("_LEAVES")) 
		{
			if(currentLeafIndex.isPresent()) 
			{
				player.sendMessage(ChatColor.RED + "One by One!");
				return;
			}
			this.cleanO2FilterTask.setCurrentLeafData(gamePlayer, event.getRawSlot());
			GlowEffect.addGlow(item);
		}
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Remove the Leaves!");
	}
	
	private static int randomLeafIndex(Inventory inventory) 
	{
		int leafIndex;
		
		do
		{
			leafIndex = InventoryUtils.randomEmptySlot(inventory);
		}
		while(OUTSIDE_SLOTS.contains(leafIndex));
		
		return leafIndex;
	}

}
