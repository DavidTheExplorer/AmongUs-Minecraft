package dte.amongus.corpses;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.utils.blocks.BlockUtils;
import dte.amongus.utils.java.StreamUtils;

public class WoolCorpse extends BasicCorpse
{
	public WoolCorpse(Crewmate whoDied, Location deathLocation, Material woolColor) 
	{
		super(whoDied);
		
		getWoolBlocks(deathLocation, woolColor).forEach(this::addComponent);
	}
	
	private List<BlockChangeComponent> getWoolBlocks(Location deathLocation, Material woolColor)
	{
		List<Block> blocks = BlockUtils.getFacedBlocks(deathLocation.getBlock(), BlockUtils.CIRCLE_FACES);
		
		return blocks.stream()
				.limit(blocks.size()/2)
				.map(block -> new BlockChangeComponent(this, block, woolColor))
				.collect(collectingAndThen(toList(), StreamUtils::randomized));
	}
}