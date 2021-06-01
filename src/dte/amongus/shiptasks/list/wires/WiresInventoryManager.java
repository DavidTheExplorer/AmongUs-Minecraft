package dte.amongus.shiptasks.list.wires;

import static dte.amongus.utils.InventoryUtils.createDummyItem;
import static org.bukkit.ChatColor.RED;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import dte.amongus.AmongUs;
import dte.amongus.cooldown.Cooldown;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresInventoryManager extends TaskInventoryManager
{
	private final WiresTask wiresTask;
	
	private static final Cooldown WORK_COOLDOWN = new Cooldown.Builder("Wires")
			.rejectWithMessage(RED + "You are still working on the previous wires.")
			.build();
	
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
		this.wiresTask = wiresTask;
	}
	
	@Override
	public Inventory createInventory(Crewmate crewmate) 
	{
		Inventory inventory = Bukkit.createInventory(null, 6 * 9, createTitle("Fix The Wires"));
		InventoryUtils.buildWalls(inventory, createDummyItem(Material.BLACK_STAINED_GLASS_PANE));

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
			inventory.setItem(leftSlot, wire);
			inventory.setItem(rightSlot, wire);

			//remove the wire's data from the remaining data
			leftSlots.remove(leftSlot);
			rightSlots.remove(rightSlot);
			wires.remove(material);
		}
		return inventory;
	}

	@Override
	public void onInventoryClick(Crewmate crewmate, InventoryClickEvent event) 
	{
		ItemStack wire = event.getCurrentItem();
		
		if(!WIRES_MATERIALS.contains(wire.getType()))
			return;
		
		Player crewmatePlayer = (Player) event.getWhoClicked();
		
		if(WORK_COOLDOWN.wasRejected(crewmatePlayer)) 
			return;
		
		Pair<Integer, ItemStack> currentWireData = this.wiresTask.getCurrentWire(crewmate).orElse(null);

		if(currentWireData == null)
		{
			if(!isLeftSlot(event.getRawSlot())) 
			{
				crewmatePlayer.sendMessage(RED + "You have to click a Left Wire first!");
				return;
			}
			if(event.getInventory().getItem(event.getRawSlot()+1) != null) 
			{
				crewmatePlayer.sendMessage(RED + "Wire already connected!");
				return;
			}
			GlowEffect.addGlow(wire);
			this.wiresTask.setCurrentWire(crewmate, event.getRawSlot(), wire);
			return;
		}
		int rightSlot = getRightSlot(currentWireData.getFirst());
		
		if(event.getRawSlot() == rightSlot)
		{
			WORK_COOLDOWN.put(crewmatePlayer, TimeUnit.SECONDS, 3);
			
			ItemStack rightWire = event.getInventory().getItem(rightSlot);
			GlowEffect.addGlow(rightWire);
			
			this.wiresTask.addProgression(crewmate, 25);
			this.wiresTask.removeCurrentWire(crewmate);
			
			startConnectionAnimation(event.getInventory(), currentWireData.getFirst()+1, event.getRawSlot()-1, rightWire.getType());
		}
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Fix The Wires");
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
	
	private static void startConnectionAnimation(Inventory inventory, int start, int end, Material wireMaterial) 
	{
		new WiresConnectionRunnable(inventory, start, end, getWireColor(wireMaterial), wireMaterial).runTaskTimer(AmongUs.getInstance(), 0, 10);
	}
}