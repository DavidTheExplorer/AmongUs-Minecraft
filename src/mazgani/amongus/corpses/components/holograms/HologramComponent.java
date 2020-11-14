package mazgani.amongus.corpses.components.holograms;

import java.util.Objects;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import mazgani.amongus.corpses.components.GameCorpseComponent;
import mazgani.amongus.holograms.EquallbleHologram;

public class HologramComponent implements GameCorpseComponent
{
	protected final EquallbleHologram hologram;
	
	public HologramComponent(EquallbleHologram hologram)
	{
		this.hologram = hologram;
	}
	
	@Override
	public Location getLocation() 
	{
		return this.hologram.getLocation();
	}
	public Hologram getHologram() 
	{
		return this.hologram;
	}
	
	@Override
	public void spawn()
	{
		//Since holograms are created & spawned at the same time, it's already spawned
	}
	
	@Override
	public void despawn() 
	{
		this.hologram.delete();
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(this.hologram);
	}

	@Override
	public boolean equals(Object object)
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		return this.hologram.equals(object);
	}
}