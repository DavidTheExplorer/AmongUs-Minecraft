package dte.amongus.corpses.spawnselector;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.utils.blocks.BlockUtils;

@FunctionalInterface
public interface CorpseSpawnRule extends UnaryOperator<Location>
{
	Location apply(Location deathLocation);

	CorpseSpawnRule
	AT_DEATH_LOCATION = deathLocation -> deathLocation, //I can't call UnaryOperator#identity :(
	ON_GROUND = deathLocation -> BlockUtils.computeLowestBlock(deathLocation).getLocation(),
	UNDER_DEATH_LOCATION = deathLocation -> deathLocation.subtract(0, 1, 0),
	AVOID_WALLS = deathLocation ->
	{
		for(Block sideBlock : BlockUtils.getFacedBlocks(deathLocation.getBlock(), BlockUtils.CIRCLE_FACES))
		{
			boolean isWall = sideBlock.getLocation().add(0, 1, 0).getBlock().getType() != Material.AIR;

			if(isWall)
			{
				//TODO: move the corpse
			}
		}
		return deathLocation;
	},
	OPTIMAL = compose(AVOID_WALLS, ON_GROUND);

	static CorpseSpawnRule compose(CorpseSpawnRule... rules)
	{
		return deathLocation -> Arrays.stream(rules).reduce(deathLocation, (loc, rule) -> rule.apply(loc), Location::add);
	}
}