package dte.amongus.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.amongus.AmongUs;

public class Cooldown
{
	private final String name;
	private final Map<UUID, Long> playersEndTimes = new HashMap<>();
	private final CooldownStrategy rejectBehaviour, whenOver;

	Cooldown(Builder builder)
	{
		this.name = builder.name;
		this.rejectBehaviour = builder.rejectBehaviour;
		this.whenOver = builder.whenOver;
	}
	public String getName() 
	{
		return this.name;
	}
	public void put(Player player, TimeUnit unit, long unitAmount) 
	{
		long endTime = System.currentTimeMillis() + unit.toMillis(unitAmount);
		this.playersEndTimes.put(player.getUniqueId(), endTime);

		if(this.whenOver != null)
		{
			long timeInTicks = (unit.toSeconds(unitAmount) * 20);
			Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () ->  this.whenOver.run(player, this), timeInTicks);
		}
	}
	public boolean isOnCooldown(Player player) 
	{
		return isOnCooldown(player.getUniqueId());
	}
	public boolean isOnCooldown(UUID playerUUID) 
	{
		Long endTime = this.playersEndTimes.get(playerUUID);

		if(endTime == null) 
			return false; //the player isn't on cooldown

		//if we passed the set end time - the player isn't on cooldown
		if(System.currentTimeMillis() >= endTime) 
		{
			//remove the player's time from the map
			delete(playerUUID);
			return false;
		}
		return true;
	}
	public void delete(UUID playerUUID) 
	{
		this.playersEndTimes.remove(playerUUID);
	}

	/**
	 * Should be used only after {@code isInCooldown(player)} returned true.
	 * @return the player's time left in seconds.
	 */
	public long getTimeLeft(Player player) 
	{
		long endTime = this.playersEndTimes.get(player.getUniqueId());
		long timeLeft = (endTime - System.currentTimeMillis());

		return TimeUnit.MILLISECONDS.toSeconds(timeLeft);
	}

	/**
	 * If the {@code player} was on cooldown the rejection behaviour happens and the method returns false, otherwise it just returns true.
	 * @param player the potentially on cooldown player
	 * @return whether the player was rejected or not.
	 */
	public boolean wasRejected(Player player)
	{
		if(!isOnCooldown(player))
			return false;
		
		this.rejectBehaviour.run(player, this);
		return true;
	}

	public static class Builder
	{
		private String name;
		private CooldownStrategy rejectBehaviour = DO_NOTHING;
		private CooldownStrategy whenOver = null; //null instead of DO_NOTHING - no task will be scheduled for efficiency

		public static final CooldownStrategy
		DO_NOTHING = (player, cooldown) -> {},
		DEFAULT_REJECT_MESSAGE = (player, cooldown) ->
		{
			player.sendMessage(String.format("Your %s cooldown will be over in %d seconds.", cooldown.getName(), cooldown.getTimeLeft(player)));
		};

		private static CooldownService cooldownService;

		public Builder(String name)
		{
			this.name = name;
		}
		public static void setCooldownService(CooldownService cooldownService) 
		{
			Builder.cooldownService = cooldownService;
		}
		public Builder rejectWithDefaultMessage()
		{
			this.rejectBehaviour = DEFAULT_REJECT_MESSAGE;
			return this;
		}

		/**
		 * Sets the rejection strategy to send the player to provided {@code message}, including these placeholders:
		 * <nl>
		 * 	<li> %time - the remaining time of the player</li>
		 * 	<li> %player - the rejected player's name</li>
		 * </nl>
		 * @param message
		 * @return
		 */
		public Builder rejectWithMessage(String message)
		{
			this.rejectBehaviour = (player, cooldown) -> 
			{
				String timeLeft = Long.toString(cooldown.getTimeLeft(player));

				player.sendMessage(message
						.replace("%time", timeLeft)
						.replace("%player", player.getName()));
			};
			return this;
		}
		public Builder rejectWith(CooldownStrategy rejectionBehaviour) 
		{
			this.rejectBehaviour = rejectionBehaviour;
			return this;
		}
		public Builder whenOver(CooldownStrategy whenOver) 
		{
			this.whenOver = whenOver;
			return this;
		}
		public Cooldown build() 
		{
			if(cooldownService == null) 
				throw new RuntimeException("Cannot create new cooldowns when the manager is not set.");
			
			Cooldown cooldown = new Cooldown(this);
			cooldownService.register(cooldown);
			return cooldown;
		}
	}
}