package dte.amongus.test;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.test.corpses.AbstractCorpse;
import dte.amongus.test.corpses.grave.GraveCorpse;
import dte.amongus.test.corpses.grave.SimpleGraveCorpse;
import dte.amongus.test.corpses.gravesign.GraveSignCorpse;
import dte.amongus.test.corpses.sign.SignCorpse;
import dte.amongus.test.corpses.sign.SimpleSignCorpse;

public class Main
{
	public static void main(String[] args)
	{
		testCorpses();
	}
	private static void testCorpses()
	{
		Location deathLocation = new Location(null, 1, 1, 1);
		
		GraveCorpse graveCorpse = new SimpleGraveCorpse(deathLocation, "Clay");
		SignCorpse signCorpse = new SimpleSignCorpse(deathLocation, Material.BIRCH_SIGN);
		
		AbstractCorpse corpse = new GraveSignCorpse(deathLocation, graveCorpse, signCorpse);
		corpse.spawn();
	}
}