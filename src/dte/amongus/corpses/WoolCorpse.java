package dte.amongus.corpses;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.utils.blocks.BlockUtils;

public class WoolCorpse extends BasicCorpse
{
	public WoolCorpse(Crewmate whoDied, Location deathLocation, Material woolColor) 
	{
		super(whoDied);
		
		registerComponents(deathLocation, woolColor);
	}
	
	private void registerComponents(Location deathLocation, Material woolColor) 
	{
		List<Block> blocks = BlockUtils.getFacedBlocks(deathLocation.getBlock(), BlockUtils.CIRCLE_FACES);
		
		List<Block> half = blocks.subList(0, blocks.size()/2);
		Collections.shuffle(half);
		
		for(Block block : half)
			addComponent(new BlockChangeComponent(this, block, woolColor));
	}
}