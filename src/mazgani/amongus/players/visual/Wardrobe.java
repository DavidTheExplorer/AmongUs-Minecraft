package mazgani.amongus.players.visual;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mazgani.amongus.players.AUPlayer;

public class Wardrobe 
{
	private final AUPlayer owner;
	
	private final Set<PlayerColor> unlockedColors = new HashSet<>();
	
	public Wardrobe(AUPlayer owner) 
	{
		this.owner = owner;
	}
	public AUPlayer getOwner() 
	{
		return this.owner;
	}
	public boolean hasUnlocked(PlayerColor color) 
	{
		return this.unlockedColors.contains(color);
	}
	public Set<PlayerColor> getUnlockedColorsView()
	{
		return Collections.unmodifiableSet(this.unlockedColors);
	}
}