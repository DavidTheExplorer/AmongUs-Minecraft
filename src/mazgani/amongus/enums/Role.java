package mazgani.amongus.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum Role 
{
	CREWMATE,
	IMPOSTOR;
	
	public static Role getRandomRole() 
	{
		return ThreadLocalRandom.current().nextBoolean() ? CREWMATE : IMPOSTOR;
	}
}
