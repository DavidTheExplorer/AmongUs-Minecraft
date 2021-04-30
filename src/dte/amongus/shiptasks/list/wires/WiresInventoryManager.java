package dte.amongus.shiptasks.list.wires;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import dte.amongus.AmongUs;
import dte.amongus.cooldown.Cooldown;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.types.inventory.TaskInventoryManager;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresInventoryManager extends TaskInventoryManager<WiresTask>
{
	private final Cooldown workCooldown;

	private static final Integer[] 
			LEFT_SLOTS = {10, 19, 28, 37}, 
			RIGHT_SLOTS = {16, 25, 34, 43};

	private static final Material[] PROGRESSION_MATERIALS = {Material.GRAY_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE};
	private static final Set<Material> WIRES_MATERIALS = Sets.newHashSet(Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE);

	static
	{
		//getRightWireSlot() uses a binary search
		Arrays.sort(LEFT_SLOTS);
		Arrays.sort(RIGHT_SLOTS);
	}

	public WiresInventoryManager(WiresTask wiresTask) 
	{
		super(wiresTask);

		this.workCooldown = new Cooldown.CooldownBuilder("Wires")
				.rejectWithMessage(ChatColor.RED + "You are still working on the previous wires.")
				.whenOver((player, cooldown) ->
				{
					player.closeInventory();
					String finishedWireName = this.task.getData(this.task.getGame().getPlayer(player), "Wire").map(String.class::cast).get();
					player.sendMessage(ChatColor.WHITE + "You finished the " + ChatColor.GREEN + finishedWireName + ChatColor.WHITE + "Wires!");
				})
				.build();
	}
	
	@Override
	public boolean wasInvolvedAt(InventoryEvent event)
	{
		return event.getView().getTitle().equals("Task > Fix The Wires");
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inv = newInventoryBuilder(6)
				.named("Fix The Wires")
				.withWalls()
				.build();
		
		Set<Material> wires = Sets.newHashSet(WIRES_MATERIALS);
		Set<Integer> leftSlots = Sets.newHashSet(LEFT_SLOTS);
		Set<Integer> rightSlots = Sets.newHashSet(RIGHT_SLOTS);
		
		for(int i = 1; i <= WiresTask.WIRES_AMOUNT; i++) 
		{
			//create a new wire from random data
			int leftSlot = RandomUtils.randomElement(leftSlots);
			int rightSlot = getRightWireSlot(leftSlot);
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
		
		Pair<Integer, ItemStack> currentWireData = this.task.getCurrentWire(gamePlayer);
		
		if(currentWireData == null)
		{
			if(this.workCooldown.wasRejected(player))
				return;
			
			GlowEffect.addGlow(wire);
			this.task.setCurrentWire(gamePlayer, event.getRawSlot(), wire);
			return;
		}
		if(event.getRawSlot() == getRightWireSlot(currentWireData.getFirst()) && wire.getType() == currentWireData.getSecond().getType())
		{
			this.task.addProgression(gamePlayer, 25);
			this.task.removeData(gamePlayer, "Start");
			
			new WiresConnectionRunnable(event.getInventory(), currentWireData.getFirst()+1, event.getRawSlot()-1, PROGRESSION_MATERIALS).runTaskTimer(AmongUs.getInstance(), 0, 2 * 20);
			GlowEffect.deleteGlow(currentWireData.getSecond());
			this.workCooldown.put(player, TimeUnit.SECONDS, 10);
		}
	}
	
	private static int getRightWireSlot(int leftInvSlot) 
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