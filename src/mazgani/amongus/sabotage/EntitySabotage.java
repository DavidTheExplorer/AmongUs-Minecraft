package mazgani.amongus.sabotage;

import java.util.Set;

import org.bukkit.entity.Entity;

public interface EntitySabotage extends Sabotage
{
	Set<Entity> getCurrentEntities();
}