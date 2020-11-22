package mazgani.amongus.corpses.components.holograms;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.corpses.components.CorpseComponentBase;
import mazgani.amongus.holograms.EquallbleHologram;

public class HologramComponent extends CorpseComponentBase
{
	protected final EquallbleHologram hologram;
	
	public HologramComponent(AbstractGameCorpse corpse, EquallbleHologram hologram)
	{
		super(corpse);
		this.hologram = hologram;
	}
	
	@Override
	public Location getCurrentLocation() 
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
		return this.hologram.hashCode();
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
		
		return this.hologram.equals(component.hologram);
	}
}