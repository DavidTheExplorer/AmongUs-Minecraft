package dte.amongus.shiptasks.inventory.cleano2filter;

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
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.CleanO2FilterTask;
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
	public Inventory createInventory(Crewmate opener) 
	{
		Inventory taskInventory = Bukkit.createInventory(null, 9 * 6, createTitle("Clean the Leaves"));
		InventoryUtils.buildWalls(taskInventory, createDummyItem(Material.BLACK_STAINED_GLASS_PANE));
		
		for(int i : new int[]{1, 10, 37, 46})
			taskInventory.setItem(i, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));

		for(int i : new int[]{19, 28})
			taskInventory.setItem(i, CHAIN_BUILDER.createCopy());

		for(ItemStack leaf : createRandomLeaves())
			taskInventory.setItem(InventoryUtils.randomEmptySlot(taskInventory), leaf);

		InventoryUtils.fillColumn(taskInventory, 0, null);

		return taskInventory;
	}

	@Override
	public void onInventoryClick(Crewmate crewmate, InventoryClickEvent event) 
	{
		Inventory taskInventory = event.getInventory();
		ItemStack item = event.getCurrentItem();
		Player crewmatePlayer = crewmate.getPlayer();
		Optional<Integer> currentLeafIndex = this.cleanO2FilterTask.getCurrentLeafData(crewmate);

		if(BlockUtils.isLeaf(item.getType()))
		{
			if(currentLeafIndex.isPresent())
			{
				crewmatePlayer.sendMessage(RED + "One by One!");
				return;
			}
			GlowEffect.addGlow(item);
			this.cleanO2FilterTask.setCurrentLeafData(crewmate, event.getRawSlot());
			setLeavesTo(taskInventory, leaf -> createInformativeLeaf(leaf.getType()));
		}
		else if(item.getType() == Material.CHAIN) 
		{
			if(!currentLeafIndex.isPresent()) 
			{
				crewmatePlayer.sendMessage(RED + "You have to select a Leaf!");
				return;
			}
			List<Integer> exitPath = calculateExitPath(currentLeafIndex.get(), event.getRawSlot());

			new LeafCleaningRunnable(this.cleanO2FilterTask, taskInventory, crewmate, exitPath, this.cleaningSound).runTaskTimer(AmongUs.getInstance(), 0, 5);
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
	
	static void setLeavesTo(Inventory taskInventory, UnaryOperator<ItemStack> leafReplacer) 
	{
		InventoryUtils.allSlotsThat(taskInventory, item -> BlockUtils.isLeaf(item.getType())).forEach(slot ->
		{
			ItemStack newLeaf = leafReplacer.apply(taskInventory.getItem(slot));

			taskInventory.setItem(slot, newLeaf);
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
