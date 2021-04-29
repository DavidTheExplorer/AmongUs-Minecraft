package dte.amongus.utils.java;

import java.util.function.Predicate;

public class Predicates 
{
	//Container of static methods
	private Predicates(){}
	
	public static <T> Predicate<T> of(Predicate<T> predicate)
	{
		return predicate;
	}
	
	//a depressing method
	public static <T> Predicate<T> negate(Predicate<T> predicate)
	{
		return happyObject -> !predicate.test(happyObject);
	}
}