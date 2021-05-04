package dte.amongus.shiptasks.wires;

import java.util.Arrays;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import dte.amongus.AmongUs;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresInventoryManager extends TaskInventoryManager<WiresTask>
{
	private static final Integer[]
			LEFT_SLOTS = {10, 19, 28, 37}, 
			RIGHT_SLOTS = {16, 25, 34, 43};
	
	private static final Set<Material> WIRES_MATERIALS = Sets.newHashSet(Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE);
	
	static
	{
		//getRightSlot() uses a binary search
		Arrays.sort(LEFT_SLOTS);
		Arrays.sort(RIGHT_SLOTS);
	}
	
	public WiresInventoryManager(WiresTask wiresTask) 
	{
		super(wiresTask, "Fix The Wires");
	}
	
	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inv = newInventoryBuilder(6)
				.withWalls()
				.build();

		Set<Material> wires = Sets.newHashSet(WIRES_MATERIALS);
		Set<Integer> leftSlots = Sets.newHashSet(LEFT_SLOTS);
		Set<Integer> rightSlots = Sets.newHashSet(RIGHT_SLOTS);

		for(int i = 1; i <= WiresTask.WIRES_AMOUNT; i++)
		{
			//create a new wire from random data
			int leftSlot = RandomUtils.randomElement(leftSlots);
			int rightSlot = getRightSlot(leftSlot);
			Material material = RandomUtils.randomElement(wires);

			//set the wire at the left & right slots
			ItemStack wire = new ItemBuilder(material, getWireName(material) + " Wire").createCopy();
			inv.setItem(leftSlot, wire);
			inv.setItem(rightSlot, wire);

			//remove the wire's data from the remaining data
			leftSlots.remove(leftSlot);
			rightSlots.remove(rightSlot);
			wires.remove(material);
		}
		return inv;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		ItemStack wire = event.getCurrentItem();
		
		if(!WIRES_MATERIALS.contains(wire.getType()))
			return;
		
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = this.task.getGame().getPlayer(player);
		
		Pair<Integer, ItemStack> currentWireData = this.task.getCurrentWire(gamePlayer).orElse(null);

		if(currentWireData == null)
		{
			if(!isLeftSlot(event.getRawSlot())) 
			{
				player.sendMessage(ChatColor.RED + "You have to click a Left wire first!");
				return;
			}
			GlowEffect.addGlow(wire);
			this.task.setCurrentWire(gamePlayer, event.getRawSlot(), wire);
		}
		else if(event.getRawSlot() == getRightSlot(currentWireData.getFirst()))
		{
			ItemStack rightWire = event.getInventory().getItem(getRightSlot(currentWireData.getFirst()));
			GlowEffect.addGlow(rightWire);
			
			this.task.addProgression(gamePlayer, 25);
			this.task.removeCurrentWire(gamePlayer);
			
			new WiresConnectionRunnable(event.getInventory(), currentWireData.getFirst()+1, event.getRawSlot()-1, Material.WHITE_STAINED_GLASS).runTaskTimer(AmongUs.getInstance(), 0, 10);
		}
	}
	
	private static boolean isLeftSlot(int slot) 
	{
		return Arrays.binarySearch(LEFT_SLOTS, slot) > -1;
	}
	private static int getRightSlot(int leftSlot) 
	{
		int slot = Arrays.binarySearch(LEFT_SLOTS, leftSlot);

		//get the parallel cell in the right slots array
		return RIGHT_SLOTS[slot];
	}
	private static String getWireName(Material wireMaterial) 
	{
		String colorName = WordUtils.capitalizeFully(wireMaterial.name().substring(0, wireMaterial.name().indexOf('_')));

		return getWireColor(wireMaterial) + colorName;
	}
	private static ChatColor getWireColor(Material wireMaterial) 
	{
		if(wireMaterial == Material.PINK_STAINED_GLASS_PANE) 
			return ChatColor.LIGHT_PURPLE;

		String materialColor = wireMaterial.name().substring(0, wireMaterial.name().indexOf('_'));

		return ChatColor.valueOf(materialColor);
	}
}