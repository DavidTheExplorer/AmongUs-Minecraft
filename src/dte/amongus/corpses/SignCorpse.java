package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.utils.blocks.transformers.BlockTransformer;

public class SignCorpse extends BasicCorpse
{
	public SignCorpse(Crewmate whoDied, Location deathLocation, Material signMaterial, String... lines) 
	{
		super(whoDied);
		
		addComponent(new BlockChangeComponent(this, deathLocation.getBlock(), BlockTransformer.toSign(signMaterial, lines)));
	}
}