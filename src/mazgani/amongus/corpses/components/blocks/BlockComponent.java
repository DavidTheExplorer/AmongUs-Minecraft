package mazgani.amongus.corpses.components.blocks;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.components.GameCorpseComponent;

public abstract class BlockComponent implements GameCorpseComponent
{
	protected final Location blockLocation;

	/**
	 * Creates a component that gets a block and does certain actions on it.
	 * 
	 * @param The subject block.
	 */
	public BlockComponent(Block block) 
	{
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
		return Objects.hash(this.blockLocation);
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

		BlockComponent other = (BlockComponent) object;

		return Objects.equals(this.blockLocation, other.blockLocation);
	}
}