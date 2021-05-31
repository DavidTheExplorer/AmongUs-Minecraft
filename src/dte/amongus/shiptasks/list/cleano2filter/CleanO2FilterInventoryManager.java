package dte.amongus.shiptasks.list.cleano2filter;

import static dte.amongus.utils.InventoryUtils.createDummyItem;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import dte.amongus.AmongUs;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.blocks.BlockUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class CleanO2FilterInventoryManager extends TaskInventoryManager
{
	private final CleanO2FilterTask cleanO2FilterTask;
	private final Sound cleaningSound;

	private static final ItemBuilder CHAIN_BUILDER = new ItemBuilder(Material.CHAIN, AQUA + "Out!")
			.withLore(WHITE + "Click to throw the selected Leaf.");

	public CleanO2FilterInventoryManager(CleanO2FilterTask cleanO2FilterTask, Sound cleaningSound) 
	{
		this.cleanO2FilterTask = cleanO2FilterTask;
		this.cleaningSound = cleaningSound;
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, createTitle("Clean the Leaves"));
		InventoryUtils.buildWalls(inventory, createDummyItem(Material.BLACK_STAINED_GLASS_PANE));
		
		for(int i : new int[]{1, 10, 37, 46})
			inventory.setItem(i, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));

		for(int i : new int[]{19, 28})
			inventory.setItem(i, CHAIN_BUILDER.createCopy());

		for(ItemStack leaf : createRandomLeaves())
			inventory.setItem(InventoryUtils.randomEmptySlot(inventory), leaf);

		InventoryUtils.fillColumn(inventory, 0, null);

		return inventory;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		Inventory inventory = event.getInventory();
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = this.cleanO2FilterTask.getGame().getPlayer(player);
		Optional<Integer> currentLeafIndex = this.cleanO2FilterTask.getCurrentLeafData(gamePlayer);

		if(BlockUtils.isLeaf(item.getType()))
		{
			if(currentLeafIndex.isPresent())
			{
				player.sendMessage(RED + "One by One!");
				return;
			}
			GlowEffect.addGlow(item);
			this.cleanO2FilterTask.setCurrentLeafData(gamePlayer, event.getRawSlot());
			setLeavesTo(inventory, leaf -> CleanO2FilterInventoryManager.createInformativeLeaf(leaf.getType()));
		}
		else if(item.getType() == Material.CHAIN) 
		{
			if(!currentLeafIndex.isPresent()) 
			{
				player.sendMessage(RED + "You have to select a Leaf!");
				return;
			}
			List<Integer> exitPath = calculateExitPath(currentLeafIndex.get(), event.getRawSlot());

			new LeafCleaningRunnable(this.cleanO2FilterTask, inventory, gamePlayer, exitPath, this.cleaningSound).runTaskTimer(AmongUs.getInstance(), 0, 5);
		}
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Clean the Leaves");
	}
	
	private ItemStack[] createRandomLeaves() 
	{
		return Arrays.stream(Material.values())
				.filter(material -> material.name().endsWith("_LEAVES"))
				.limit(this.cleanO2FilterTask.getLeavesAmount())
				.map(CleanO2FilterInventoryManager::createLeaf)
				.toArray(ItemStack[]::new);
	}

	private static ItemStack createInformativeLeaf(Material leafType)
	{
		return new ItemBuilder(leafType, GREEN + "Leaf")
				.withLore(AQUA + "Click on a Chain to release out!")
				.createCopy();
	}
	
	static ItemStack createLeaf(Material leafType)
	{
		return new ItemBuilder(leafType, GREEN + "Leaf" + WHITE + " (Left Click)").createCopy();
	}
	
	static void setLeavesTo(Inventory inventory, UnaryOperator<ItemStack> leafReplacer) 
	{
		InventoryUtils.allSlotsThat(inventory, item -> BlockUtils.isLeaf(item.getType()))
		.forEach(slot ->
		{
			ItemStack newLeaf = leafReplacer.apply(inventory.getItem(slot));

			inventory.setItem(slot, newLeaf);
		});
	}

	private static List<Integer> calculateExitPath(int leafIndex, int chainIndex) 
	{
		int[] chainData = InventoryUtils.toLineAndIndex(chainIndex);

		List<Integer> exitPath = Lists.newArrayList(leafIndex);

		while(leafIndex != chainIndex)
		{
			int[] leafData = InventoryUtils.toLineAndIndex(leafIndex);
			
			//if the chain and leaf are on the same line
			if(leafData[0] == chainData[0]) 
				leafIndex = InventoryUtils.toSlot(leafData[0], --leafData[1]); //go one slot backwards
			
			//if the leaf's line is below the chain's line
			else if(leafData[0] < chainData[0]) 
				leafIndex = InventoryUtils.toSlot(++leafData[0], leafData[1]); //go one line up
			
			//if the leaf's line is above the chain's line
			else if(leafData[0] > chainData[0])
				leafIndex = InventoryUtils.toSlot(--leafData[0], leafData[1]); //go one line down

			else
				throw new RuntimeException("I am a Free Leaf!!!");

			//add the calculated index to the path
			exitPath.add(leafIndex);
		}

		//the last index in the path is out of the "machine"
		exitPath.add(chainIndex-1);

		return exitPath;
	}
}
