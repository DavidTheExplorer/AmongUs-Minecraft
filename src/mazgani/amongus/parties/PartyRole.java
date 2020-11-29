package mazgani.amongus.parties;

public enum PartyRole
{
	MEMBER,
	MODERATOR;

	private static final PartyRole[] VALUES = values();

	public PartyRole getNextRole() 
	{
		if(ordinal() == VALUES.length-1) 
			return null;
		
		return VALUES[ordinal()+1];
	}
	public PartyRole getPreviousRole() 
	{
		if(ordinal() == 0) 
			return null;
		
		return VALUES[ordinal()-1];
	}
}
