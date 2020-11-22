package mazgani.amongus.corpses.components.holograms;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.CorpseComponentBase;
import mazgani.amongus.holograms.EquallbleHologram;

public class HologramComponent extends CorpseComponentBase
{
	protected final EquallbleHologram spawnedHologram;
	
	public HologramComponent(BasicGameCorpse corpse, EquallbleHologram spawnedHologram)
	{
		super(corpse);
		
		this.spawnedHologram = spawnedHologram;
	}
	
	@Override
	public Location getLocation() 
	{
		return this.spawnedHologram.getLocation();
	}
	public Hologram getHologram() 
	{
		return this.spawnedHologram;
	}
	
	@Override
	public void spawn()
	{
		//Since holograms are created & spawned at the same time, it's already spawned
	}
	
	@Override
	public void despawn() 
	{
		this.spawnedHologram.delete();
	}
	
	@Override
	public int hashCode() 
	{
		return this.spawnedHologram.hashCode();
	}
	
	@Override
	public boolean equals(Object object) 
	{
		if(!super.equals(object)) 
		{
			return false;
		}
		if(!(object instanceof HologramComponent)) 
		{
			return false;
		}
		HologramComponent component = (HologramComponent) object;
		
		return this.spawnedHologram.equals(component.spawnedHologram);
	}
}