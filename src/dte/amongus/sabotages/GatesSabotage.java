package dte.amongus.sabotages;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.sabotages.types.BlockSabotage;
import dte.amongus.sabotages.types.OpenableSabotage;
import dte.amongus.utils.blocks.BlockUtils;
import dte.amongus.utils.blocks.transformers.BlockTransformer;

public class GatesSabotage extends BlockSabotage
{
	private static final BlockTransformer GATES_TRANSFORMER = new BlockTransformer(OpenableSabotage.REVERSE_OPEN)
			.toMaterial(Material.IRON_DOOR, false);
	
	protected GatesSabotage(Block... gateBlocks) 
	{
		super(gateBlocks, GATES_TRANSFORMER);
	}
	
	public static GatesSabotage from(Block... gateBlocks) 
	{
		verifyType(gateBlocks, "Gates Sabotage", BlockUtils::isWoodenDoor);
		
		return new GatesSabotage(gateBlocks);
	}
}
