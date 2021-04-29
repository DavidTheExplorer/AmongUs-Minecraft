package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.corpses.composite.CompositeCorpse;
import dte.amongus.games.players.AUGamePlayer;

public class WoolsCompositeCorpse extends CompositeCorpse
{
	public WoolsCompositeCorpse(AUGamePlayer whoDied, Location deathLocation)
	{
		super(whoDied);

		addCorpse(new WoolCorpse(whoDied, deathLocation, Material.RED_WOOL));
		addCorpse(new WoolCorpse(whoDied, deathLocation, Material.WHITE_WOOL));
	}
	
	/*@Override
	public Set<BasicCorpseComponent> computeUniqueComponents() 
	{
		List<BasicCorpseComponent> woolsComponents = computeDuplicateComponents().get(BlockChangeComponent.class);
		
		return woolsComponents.stream()
				.map(component -> (BlockChangeComponent) component)
				.map(blockComponent -> new BlockChangeComponent(this, blockComponent.getBlock(), Material.OBSIDIAN))
				.collect(toSet());
	}*/
}