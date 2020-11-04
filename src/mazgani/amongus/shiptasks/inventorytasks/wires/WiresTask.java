package mazgani.amongus.shiptasks.inventorytasks.wires;

import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import mazgani.amongus.AmongUs;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.shiptasks.TaskInventoryManager;
import mazgani.amongus.shiptasks.inventorytasks.InventoryTask;
import mazgani.amongus.shiptasks.types.ProgressionTask;
import mazgani.amongus.utilities.GlowEffect;
import mazgani.amongus.utilities.cooldown.Cooldown;
import mazgani.amongus.utilities.objectholders.Pair;

public class WiresTask extends ProgressionTask implements InventoryTask<WiresTask>
{
	private Cooldown workCooldown;
	
	private WiresInventoryManager wiresInvManager;
	
	public static final int WIRES_AMOUNT = 4;

	public WiresTask(AUGame game)
	{
		super("Wires Fix", game, WIRES_AMOUNT);
		
		this.wiresInvManager = new WiresInventoryManager(getGame(), this);

		this.workCooldown = new Cooldown.CooldownBuilder("Wires")
				.rejectWithMessage(ChatColor.RED + "You are still working on the previous wires.")
				.whenOver((player, cooldown) -> 
				{
					player.closeInventory();
					String finishedWireName = ((String) getData(game.getPlayer(player.getUniqueId()), "Wire"));
					player.sendMessage(ChatColor.WHITE + "You finished the " + ChatColor.GREEN + finishedWireName + ChatColor.WHITE + "Wires!");
				})
				.build();
	}
	
	@Override
	public TaskInventoryManager<WiresTask> getInventoryManager() 
	{
		return this.wiresInvManager;
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onWiresClick(InventoryClickEvent event) 
	{
		if(!event.getView().getTitle().equals("Task > Fix The Wires")) 
		{
			return;
		}
		event.setCancelled(true);
		ItemStack item = event.getCurrentItem();

		if(item == null) 
		{
			return;
		}
		if(!this.wiresInvManager.isWireMaterial(item.getType())) 
		{
			return;
		}
		Player player = (Player) event.getWhoClicked();
		GamePlayer gamePlayer = getGame().getPlayer(player.getUniqueId());

		Pair<Integer, ItemStack> startData = (Pair<Integer, ItemStack>) getData(gamePlayer, "Start");

		if(startData == null)
		{
			if(this.workCooldown.wasRejected(player))
			{
				return;
			}
			GlowEffect.addGlow(item);
			setData(gamePlayer, "Start", Pair.of(event.getRawSlot(), item));
			return;
		}
		if(event.getRawSlot() == this.wiresInvManager.getRightWireSlot(startData.getFirst()) && item.getType() == startData.getSecond().getType())
		{
			addProgression(gamePlayer, 25);
			removeData(gamePlayer, "Start");
			
			new WiresConnectionTask(event.getInventory(), startData.getFirst()+1, event.getRawSlot()-1, this.wiresInvManager.getProgressionMaterials()).runTaskTimer(AmongUs.getInstance(), 0, 2 * 20);
			GlowEffect.deleteGlow(startData.getSecond());
			this.workCooldown.put(player, TimeUnit.SECONDS, 10);
		}
	}
}