package dte.amongus.utils.items;

import org.bukkit.Material;

public enum LeatherArmorPart
{
	HELMET(Material.LEATHER_HELMET),
	CHESTPLATE(Material.LEATHER_CHESTPLATE),
	LEGGINGS(Material.LEATHER_LEGGINGS),
	BOOTS(Material.LEATHER_BOOTS);

	private final Material material;

	LeatherArmorPart(Material material)
	{
		this.material = material;
	}
	public Material getMaterial() 
	{
		return this.material;
	}
}