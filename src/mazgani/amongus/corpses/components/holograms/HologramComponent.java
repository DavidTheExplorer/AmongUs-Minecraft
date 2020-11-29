package mazgani.amongus.corpses.components.holograms;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.CorpseComponentBase;
import mazgani.amongus.utilities.HologramUtilities;

public class HologramComponent extends CorpseComponentBase
{
	protected Hologram hologram;

	public HologramComponent(BasicGameCorpse corpse, Hologram hiddenHologram)
	{
		super(corpse);
		
		this.hologram = hiddenHologram;
	}
	public static HologramComponent of(BasicGameCorpse corpse, Hologram hologram) 
	{
		HologramUtilities.setVisibility(hologram, false);
		
		return new HologramComponent(corpse, hologram);
	}

	@Override
	public Location getLocation() 
	{
		return this.hologram.getLocation();
	}
	public Hologram getSpawnedHologram() 
	{
		return this.hologram;
	}

	@Override
	public void spawn()
	{
		HologramUtilities.setVisibility(this.hologram, true);
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