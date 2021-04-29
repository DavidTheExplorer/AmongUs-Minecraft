package dte.amongus.corpses.basic.components.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.BasicCorpseComponent;
import dte.amongus.trackables.Trackable;

public abstract class AbstractBlockComponent extends BasicCorpseComponent implements Trackable
{
	private final Location blockLocation;
	
	protected AbstractBlockComponent(BasicCorpse parentCorpse, Block block)
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
