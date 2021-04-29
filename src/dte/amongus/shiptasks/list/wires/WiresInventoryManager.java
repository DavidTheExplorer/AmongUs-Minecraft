package dte.amongus.shiptasks.list.wires;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.types.inventory.TaskInventoryManager;
import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;

public class WiresInventoryManager extends TaskInventoryManager<WiresTask>
{
	private static final Integer[] 
			LEFT_SLOTS = {10, 19, 28, 37}, 
			RIGHT_SLOTS = {16, 25, 34, 43};
	
	private static final Material[] PROGRESSION_MATERIALS = {Material.GRAY_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE};
	private static final Set<Material> WIRES_MATERIALS = Sets.newHashSet(Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE);
	
	static
	{
		//just to make sure, because getRightWireSlot() uses a binary search
		Arrays.sort(LEFT_SLOTS);
		Arrays.sort(RIGHT_SLOTS);
	}

	public WiresInventoryManager(WiresTask wiresTask, AUGame game) 
	{
		super(wiresTask, game);
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inv = newInventoryBuilder(6)
				.named("Fix the Wires")
				.withWalls()
				.build();
		
		Set<Material> remainingWires = new HashSet<>(WIRES_MATERIALS);
		Set<Integer> remainingLeftSlots = new HashSet<>(Arrays.asList(LEFT_SLOTS));
		Set<Integer> remainingRightSlots = new HashSet<>(Arrays.asList(RIGHT_SLOTS));

		//4 wires
		for(int i = 1; i <= WiresTask.WIRES_AMOUNT; i++) 
		{
			//create a new wire from random data
			int leftSlot = RandomUtils.randomElement(remainingLeftSlots);
			int rightSlot = getRightWireSlot(leftSlot);
			Material wireMaterial = RandomUtils.randomElement(remainingWires);

			ItemStack wire = new ItemBuilder(wireMaterial, getWireName(wireMaterial) + " Wire").createCopy();

			inv.setItem(leftSlot, wire);
			inv.setItem(rightSlot, wire);

			//remove the data from the remaining data lists
			remainingLeftSlots.remove(leftSlot);
			remainingRightSlots.remove(rightSlot);
			remainingWires.remove(wireMaterial);
		}
		return inv;
	}

	@Override
	public boolean wasInvolvedAt(InventoryEvent event)
	{
		return event.getView().getTitle().equals("Task > Fix The Wires");
	}
	public boolean isWireMaterial(Material material)
	{
		return WIRES_MATERIALS.contains(material);
	}
	public Material[] getProgressionMaterials()
	{
		return PROGRESSION_MATERIALS.clone();
	}
	public int getRightWireSlot(int leftInvSlot) 
	{
		int slot = Arrays.binarySearch(LEFT_SLOTS, leftInvSlot);
		
		//get the parallel cell in the right slots array
		return RIGHT_SLOTS[slot];
	}
	private static String getWireName(Material wireMaterial) 
	{
		String colorName = wireMaterial.name().substring(0, wireMaterial.name().indexOf('_'));

		return getWireColor(wireMaterial) + WordUtils.capitalizeFully(colorName);
	}
	private static ChatColor getWireColor(Material wireMaterial) 
	{
		if(wireMaterial == Material.PINK_STAINED_GLASS_PANE) 
			return ChatColor.LIGHT_PURPLE;
		
		String materialColor = wireMaterial.name().substring(0, wireMaterial.name().indexOf('_'));

		return ChatColor.valueOf(materialColor);
	}
}