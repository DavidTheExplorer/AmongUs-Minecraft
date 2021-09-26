package dte.amongus.player.visual;

import java.util.HashSet;
import java.util.Set;

import dte.amongus.player.AUPlayer;

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
	
	public Set<PlayerColor> getUnlockedColors()
	{
		return new HashSet<>(this.unlockedColors);
	}
}