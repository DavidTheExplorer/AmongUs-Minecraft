package mazgani.amongus.shiptasks.list;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.shiptasks.InventoryTask;
import mazgani.amongus.shiptasks.ProgressionTask;
import mazgani.amongus.utilities.GlowEffect;
import mazgani.amongus.utilities.InventoryUtilities;
import mazgani.amongus.utilities.ItemBuilder;
import mazgani.amongus.utilities.JavaUtilities;
import mazgani.amongus.utilities.RandomUtilities;
import mazgani.amongus.utilities.objectholders.Pair;

public class WiresTask extends ProgressionTask implements InventoryTask
{
	private static final Set<Material> WIRES = Sets.newHashSet(Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE);
	
	private static final Integer[] 
			RIGHT_SLOTS = {16, 25, 34, 43},
			LEFT_SLOTS = {10, 19, 28, 37};

	public WiresTask(AUGame game)
	{
		super("Wires Fix", game);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onWiresClick(InventoryClickEvent event) 
	{
		if(!event.getView().getTitle().equals("Task > Fix The Wires")) 
		{
			return;
		}
		ItemStack item = event.getCurrentItem();

		if(item == null) 
		{
			return;
		}
		if(!WIRES.contains(item.getType())) 
		{
			return;
		}
		event.setCancelled(true);
		
		GamePlayer player = this.game.getPlayer(event.getWhoClicked().getUniqueId());
		Pair<ItemStack, Integer> startData = (Pair<ItemStack, Integer>) getData(player, "Start");

		if(startData == null)
		{
			GlowEffect.addGlow(item);
			setData(player, "Start", Pair.of(event.getRawSlot(), item));
			return;
		}
		if(startData.getFirst().getType() == item.getType() && 
				item.hasItemMeta() && item.getItemMeta().hasDisplayName() && !item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Connected"))
		{
			addProgression(player, 25);
			removeData(player, "Start");
			
			ItemBuilder wire = new ItemBuilder(item.getType(), ChatColor.YELLOW + "Connected");
			
			for(int i = startData.getSecond(); i <= event.getRawSlot(); i++) 
			{
				event.getInventory().setItem(i, wire.createCopy());
			}
		}
	}

	@Override
	public Inventory createInventory() 
	{
		Inventory inv = Bukkit.createInventory(null, 9 * 6, "Task > Fix The Wires");
		InventoryUtilities.fillWith(inv, Material.BLACK_STAINED_GLASS_PANE);
		
		Set<Material> remainingWires = new HashSet<>(WIRES);
		Set<Integer> remainingLeftSlots = Sets.newHashSet(LEFT_SLOTS);
		Set<Integer> remainingRightSlots = Sets.newHashSet(RIGHT_SLOTS);
		
		//4 wires
		for(int i = 1; i <= 4; i++) 
		{
			//create a new wire from random data
			int leftSlot = RandomUtilities.randomElement(remainingLeftSlots);
			int rightSlot = RandomUtilities.randomElement(remainingRightSlots);
			Material wire = JavaUtilities.randomElement(remainingWires);
			
			inv.setItem(leftSlot, new ItemStack(wire));
			inv.setItem(rightSlot, new ItemStack(wire));
			
			//remove the data from the remaining data lists
			remainingLeftSlots.remove(leftSlot);
			remainingRightSlots.remove(rightSlot);
			remainingWires.remove(wire);
		}
		return inv;
	}
}
