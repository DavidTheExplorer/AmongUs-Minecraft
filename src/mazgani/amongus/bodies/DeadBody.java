package mazgani.amongus.bodies;

import org.bukkit.Location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeadBody 
{
	protected final BodyColor color;
	
	public abstract void spawn(String deadName, Location deathLocation);
}
