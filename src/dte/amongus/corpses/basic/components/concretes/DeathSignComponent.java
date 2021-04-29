package dte.amongus.corpses.basic.components.concretes;

import static dte.amongus.utils.blocks.transformers.BlockTransformer.toSign;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.AUGamePlayer;

public class DeathSignComponent extends BlockChangeComponent
{
	public DeathSignComponent(BasicCorpse corpse, Block block, Material signMaterial, AUGamePlayer whoDied)
	{
		super(corpse, block, toSign(signMaterial, createLinesFor(whoDied)));
	}
	private static String[] createLinesFor(AUGamePlayer whoDied)
	{
		String playerName = whoDied.getPlayer().getName();
		
		return new String[]{playerName, "was assasu", "nated here.", "R.I.P"};
	}
}