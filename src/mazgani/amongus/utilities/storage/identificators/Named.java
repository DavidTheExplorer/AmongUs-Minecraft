package mazgani.amongus.utilities.storage.identificators;

public interface Named extends Identifyable<String>
{
	default String getName() 
	{
		return identifyableBy();
	}
}
