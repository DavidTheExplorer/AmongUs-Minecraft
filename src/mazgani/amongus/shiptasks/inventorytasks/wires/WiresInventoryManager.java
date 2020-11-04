package mazgani.amongus.shiptasks.inventorytasks.wires;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.shiptasks.TaskInventoryManager;
import mazgani.amongus.utilities.ItemBuilder;
import mazgani.amongus.utilities.RandomUtilities;

public class WiresInventoryManager extends TaskInventoryManager<WiresTask>
{
	private static final Integer[] 
			LEFT_SLOTS = {10, 19, 28, 37}, 
			RIGHT_SLOTS = {16, 25, 34, 43};
	
	private static final Material[] PROGRESSION_MATERIALS = {Material.GRAY_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE};
	
	private static final Set<Material> WIRES_MATERIALS = Sets.newHashSet(Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE);
	
	public WiresInventoryManager(AUGame game, WiresTask wiresTask) 
	{
		super(game, wiresTask);
	}
	
	@Override
	public Inventory createInventory() 
	{
		Inventory inv = createDefaultInventory(6, true, "Fix the Wires");
		
		Set<Material> remainingWires = Sets.newHashSet(WIRES_MATERIALS);
		Set<Integer> remainingLeftSlots = Sets.newHashSet(LEFT_SLOTS);
		Set<Integer> remainingRightSlots = Sets.newHashSet(RIGHT_SLOTS);
		
		//4 wires
		for(int i = 1; i <= WiresTask.WIRES_AMOUNT; i++) 
		{
			//create a new wire from random data
			int leftSlot = RandomUtilities.randomElement(remainingLeftSlots);
			int rightSlot = getRightWireSlot(leftSlot);
			Material wireMaterial = RandomUtilities.randomElement(remainingWires);
			
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
		{
			return ChatColor.LIGHT_PURPLE;
		}
		String materialColor = wireMaterial.name().substring(0, wireMaterial.name().indexOf('_'));

		return ChatColor.valueOf(materialColor);
	}
}