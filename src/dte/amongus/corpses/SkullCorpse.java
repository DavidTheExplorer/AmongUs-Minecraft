package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.utils.blocks.transformers.BlockTransformer;

public class SkullCorpse extends BasicCorpse
{
	private static final BlockTransformer HEAD_TRANSFORMER = new BlockTransformer()
			.toMaterial(Material.PLAYER_HEAD);
	
	public SkullCorpse(Crewmate whoDied, Location deathLocation) 
	{
		super(whoDied);
		
		addComponent(new BlockChangeComponent(this, deathLocation.getBlock(), HEAD_TRANSFORMER));
	}
}