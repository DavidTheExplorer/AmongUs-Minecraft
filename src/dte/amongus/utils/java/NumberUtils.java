package dte.amongus.utils.java;

public class NumberUtils
{
	//Container of static methods
	private NumberUtils(){}
	
	//prototype pattern for numbers
	@SuppressWarnings("unchecked")
	public static <N extends Number> N increment(N number, int amount) 
	{
		if(number instanceof Integer) 
		{
			Integer value = (Integer) number;
			value += amount;
			
			return (N) value;
		}
		else if(number instanceof Double) 
		{
			Double value = (Double) number;
			value += amount;
			
			return (N) value;
		}
		return null;
	}
}
