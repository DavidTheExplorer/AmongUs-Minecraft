package mazgani.amongus.sabotage.list;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;

import mazgani.amongus.sabotage.EntitySabotage;

public class CreepersTrap extends DoorSabotage implements EntitySabotage
{
	private final int creepersAmount;
	private final Location creepersLocation;
	private final Set<Entity> creepers = new HashSet<>();
	
	public CreepersTrap(Block[] openableDoors, int creepersAmount, Location creepersLocation) 
	{
		super(openableDoors);
		
		this.creepersAmount = creepersAmount;
		this.creepersLocation = creepersLocation;
	}
	
	@Override
	public void sabotage() 
	{
		super.sabotage();
		
		spawnCreeper("Suprise");
		spawnCreeper("Madafaka");
		
		/*for(int i = 1; i <= this.creepersAmount; i++)
		{
			this.creepersLocation.getWorld().spawn(this.creepersLocation, Creeper.class, creeper -> 
			{
				creeper.setHealth(10.0);
				creeper.setCustomName(ChatColor.GOLD + "Suprise Madafaka");
			});
		}*/
	}
	
	@Override
	public void unsabotage() 
	{
		super.unsabotage();
		
		this.creepers.forEach(Entity::remove);
	}
	
	@Override
	public Set<Entity> getCurrentEntities() 
	{
		return this.creepers;
	}
	private void spawnCreeper(String name) 
	{
		this.creepersLocation.getWorld().spawn(this.creepersLocation, Creeper.class, creeper -> 
		{
			creeper.setCustomName(ChatColor.GREEN + name);
		});
	}
}