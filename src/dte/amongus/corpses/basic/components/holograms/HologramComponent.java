package dte.amongus.corpses.basic.components.holograms;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.BasicCorpseComponent;
import dte.amongus.holograms.equallable.EquallableHologram;
import dte.amongus.hooks.HolographicDisplaysHook;
import dte.amongus.trackables.Trackable;

public class HologramComponent extends BasicCorpseComponent implements Trackable
{
	protected final Hologram hologram;
	
	private static HolographicDisplaysHook WG_HOOK = null;
	
	public HologramComponent(BasicCorpse corpse, EquallableHologram hiddenHologram)
	{
		super(corpse);
		
		this.hologram = hiddenHologram;
	}
	public static HologramComponent of(BasicCorpse corpse, EquallableHologram hologram) 
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