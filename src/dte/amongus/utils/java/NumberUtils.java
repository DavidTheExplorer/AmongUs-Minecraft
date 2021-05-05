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
	
	//throws ArithmeticException if the result had overflowed
	public static int add(int number, String digits) throws ArithmeticException
	{
		return digits.chars()
				.map(Character::getNumericValue)
				.reduce(number, (currentNumber, digit) -> Math.addExact(Math.multiplyExact(currentNumber, 10), digit));
	}
	public static int add(int number, int digits) throws ArithmeticException
	{
		return add(number, String.valueOf(digits));
	}
}
