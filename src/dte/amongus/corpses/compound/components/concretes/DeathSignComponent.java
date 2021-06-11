package dte.amongus.corpses.compound.components.concretes;

import static dte.amongus.utils.blocks.transformers.BlockTransformer.toSign;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.compound.CompoundCorpse;
import dte.amongus.corpses.compound.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.Crewmate;

public class DeathSignComponent extends BlockChangeComponent
{
	public DeathSignComponent(CompoundCorpse corpse, Block block, Material signMaterial)
	{
		super(corpse, block, toSign(signMaterial, createLinesFor(corpse.whoDied())));
	}
	private static String[] createLinesFor(Crewmate whoDied)
	{
		String playerName = whoDied.getPlayer().getName();
		
		return new String[]{playerName, "was assasinated", "Here.", "R.I.P"};
	}
}