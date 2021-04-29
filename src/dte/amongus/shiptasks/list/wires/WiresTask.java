package dte.amongus.shiptasks.list.wires;

import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.AmongUs;
import dte.amongus.cooldown.Cooldown;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.types.ProgressionTask;
import dte.amongus.shiptasks.types.inventory.InventoryTask;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.java.objectholders.Pair;

public class WiresTask extends ProgressionTask implements InventoryTask<WiresInventoryManager>
{
	private final Cooldown workCooldown;
	private final WiresInventoryManager wiresInvManager;

	public static final int WIRES_AMOUNT = 4;

	public WiresTask(AUGame game)
	{
		super("Wires Fix", "Connect all 4 wires.", game, WIRES_AMOUNT);

		this.wiresInvManager = new WiresInventoryManager(this, getGame());
		
		this.workCooldown = new Cooldown.CooldownBuilder("Wires")
				.rejectWithMessage(ChatColor.RED + "You are still working on the previous wires.")
				.whenOver((player, cooldown) ->
				{
					player.closeInventory();
					String finishedWireName = ((String) getData(game.getPlayer(player), "Wire"));
					player.sendMessage(ChatColor.WHITE + "You finished the " + ChatColor.GREEN + finishedWireName + ChatColor.WHITE + "Wires!");
				})
				.build();
	}
	
	@Override
	public WiresInventoryManager getInventoryManager() 
	{
		return this.wiresInvManager;
	}
	
	@SuppressWarnings("unchecked")
	public Pair<Integer, ItemStack> getCurrentWire(AUGamePlayer gamePlayer)
	{
		return (Pair<Integer, ItemStack>) getData(gamePlayer, "Start");
	}
	public void setCurrentWire(AUGamePlayer gamePlayer, int inventorySlot, ItemStack wire) 
	{
		setData(gamePlayer, "Start", Pair.of(inventorySlot, wire));
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		if(!this.wiresInvManager.wasInvolvedAt(event)) 
			return;
		
		event.setCancelled(true);
		ItemStack item = event.getCurrentItem();

		if(item == null) 
			return;
		
		if(!this.wiresInvManager.isWireMaterial(item.getType())) 
			return;
		
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = getGame().getPlayer(player);

		Pair<Integer, ItemStack> startData = getCurrentWire(gamePlayer);

		if(startData == null)
		{
			if(this.workCooldown.wasRejected(player))
				return;
			
			GlowEffect.addGlow(item);
			setCurrentWire(gamePlayer, event.getRawSlot(), item);
			return;
		}
		if(event.getRawSlot() == this.wiresInvManager.getRightWireSlot(startData.getFirst()) && item.getType() == startData.getSecond().getType())
		{
			addProgression(gamePlayer, 25);
			removeData(gamePlayer, "Start");

			startConnectingWires(event.getInventory(), startData.getFirst()+1, event.getRawSlot()-1, this.wiresInvManager.getProgressionMaterials());
			GlowEffect.deleteGlow(startData.getSecond());
			this.workCooldown.put(player, TimeUnit.SECONDS, 10);
		}
	}
	private void startConnectingWires(Inventory inv, int startIndex, int endIndex, Material[] progressionMaterials) 
	{
		new WiresConnectionRunnable(inv, startIndex+1, endIndex-1, this.wiresInvManager.getProgressionMaterials())
		.runTaskTimer(AmongUs.getInstance(), 0, 2 * 20);
	}
}