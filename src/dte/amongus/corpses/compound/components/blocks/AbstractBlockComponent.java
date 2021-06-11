package dte.amongus.corpses.compound.components.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;

import dte.amongus.corpses.compound.CompoundCorpse;
import dte.amongus.corpses.compound.components.CompoundCorpseComponent;
import dte.amongus.trackables.Trackable;

public abstract class AbstractBlockComponent extends CompoundCorpseComponent implements Trackable
{
	private final Location blockLocation;
	
	protected AbstractBlockComponent(CompoundCorpse parentCorpse, Block block)
	{
		super(parentCorpse);
		
		this.blockLocation = block.getLocation();
	}
	public Block getBlock() 
	{
		return this.blockLocation.getBlock();
	}
	
	@Override
	public Location getLocation() 
	{
		return this.blockLocation;
	}
	
	@Override
	public int hashCode()
	{
		return this.blockLocation.hashCode();
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
		
		AbstractBlockComponent component = (AbstractBlockComponent) object;
		
		return getLocation().equals(component.getLocation());
	}
}
