package mazgani.amongus.corpses.components.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.CorpseComponentBase;

public abstract class BlockComponent extends CorpseComponentBase
{
	protected final Location blockLocation;

	/**
	 * Creates a component that stores a block and delegates the <i>spawn / despawn</i> actions to its subclasses.
	 * 
	 * @param The block this class holds.
	 */
	public BlockComponent(BasicGameCorpse corpse, Block block) 
	{
		super(corpse);
		
		this.blockLocation = block.getLocation();
	}
	
	@Override
	public Location getLocation() 
	{
		return this.blockLocation;
	}
	public Block getBlock() 
	{
		return this.blockLocation.getBlock();
	}

	@Override
	public int hashCode() 
	{
		return this.blockLocation.hashCode();
	}
}