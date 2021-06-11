package dte.amongus.corpses.compound.components.holograms;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import dte.amongus.corpses.compound.CompoundCorpse;
import dte.amongus.corpses.compound.components.CompoundCorpseComponent;
import dte.amongus.holograms.EquallableHologram;
import dte.amongus.hooks.HolographicDisplaysHook;
import dte.amongus.trackables.Trackable;

public class HologramComponent extends CompoundCorpseComponent implements Trackable
{
	protected final Hologram hologram;
	
	private static HolographicDisplaysHook WG_HOOK = null;
	
	public HologramComponent(CompoundCorpse corpse, EquallableHologram hiddenHologram)
	{
		super(corpse);
		
		this.hologram = hiddenHologram;
	}
	public static HologramComponent of(CompoundCorpse corpse, EquallableHologram hologram) 
	{
		WG_HOOK.setVisibility(hologram, false);

		return new HologramComponent(corpse, hologram);
	}
	public static void setHolographicDisplaysHook(HolographicDisplaysHook hdHook) 
	{
		WG_HOOK = hdHook;
	}

	@Override
	public Location getLocation() 
	{
		return this.hologram.getLocation();
	}

	@Override
	public void spawn()
	{
		WG_HOOK.setVisibility(this.hologram, true);
	}

	@Override
	public void despawn() 
	{
		WG_HOOK.setVisibility(this.hologram, false);
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
			return false;

		if(!(object instanceof HologramComponent)) 
			return false;

		HologramComponent component = (HologramComponent) object;

		return this.hologram.equals(component.hologram);
	}
}