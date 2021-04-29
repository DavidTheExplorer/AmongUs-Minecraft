package dte.amongus.test.corpses.sign;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.test.corpses.BasicCorpse;

public class SimpleSignCorpse extends BasicCorpse implements SignCorpse
{
	private final Material signMaterial;

	public SimpleSignCorpse(Location deathLocation, Material signMaterial) 
	{
		super(deathLocation);
		
		this.signMaterial = signMaterial;
	}

	@Override
	public Material getSignMaterial() 
	{
		return this.signMaterial;
	}
	
	@Override
	public void spawn() 
	{
		System.out.println(String.format("Spawned a %s Sign!",this.signMaterial.name()));
	}
}
