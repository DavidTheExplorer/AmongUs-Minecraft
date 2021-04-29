package dte.amongus.player.visual;

import dte.amongus.player.AUPlayer;

public class VisibilityManager
{
	private final Wardrobe wardrobe;
	
	private PlayerColor currentColor;
	
	public VisibilityManager(AUPlayer owner) 
	{
		this.wardrobe = new Wardrobe(owner);
	}
	public Wardrobe getWardrobe() 
	{
		return this.wardrobe;
	}
	public PlayerColor getCurrentColor() 
	{
		return this.currentColor;
	}
	public void setCurrentColor(PlayerColor color) 
	{
		this.currentColor = color;
	}
}