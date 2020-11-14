package mazgani.amongus.corpses.components.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockChangeComponent extends BlockComponent
{
	private final Material oldMaterial, newMaterial;

	public BlockChangeComponent(Block block, Material newMaterial)
	{
		super(block);
		
		this.oldMaterial = block.getType();
		this.newMaterial = newMaterial;
	}
	
	@Override
	public void spawn() 
	{
		this.blockLocation.getBlock().setType(this.newMaterial);
	}

	@Override
	public void despawn() 
	{
		this.blockLocation.getBlock().setType(this.oldMaterial);
	}
}
