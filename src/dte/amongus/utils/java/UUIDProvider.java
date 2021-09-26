package dte.amongus.utils.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UUIDProvider 
{
	//Container of static methods
	private UUIDProvider(){}
	
	private static Map<Object, Set<UUID>> objectTakenIDs = new HashMap<>();
	
	/**
	 * Generates a Unique ID and marks it as <i>taken</i> for the specified {@code object} - so it would never be returned again provided the same object. 
	 * <p>
	 * Mainly intended to get classes as inputs, e.g:
	 * <pre>
	 * UUID gameID = UUIDProvider.generateFor(Game.class); 
	 * Game game = new Game(gameID);
	 * </pre>
	 * 
	 * @param object the object for which a news ID will be created.
	 * @return the generated ID.
	 */
	public static UUID generateFor(Object object) 
	{
		Set<UUID> takenIDs = objectTakenIDs.computeIfAbsent(object, o -> new HashSet<>());
		
		UUID freeID;
		
		do
		{
			freeID = UUID.randomUUID();
		}
		while(takenIDs.contains(freeID));
		
		takenIDs.add(freeID);
		
		return freeID;
	}
	
	public static void returnUUID(Object object, UUID uuid) 
	{
		MapUtils.ifMapped(objectTakenIDs, object, takenIDs -> takenIDs.remove(uuid));
	}
}