package dte.amongus.utils.java;

import java.util.function.BiPredicate;

public enum StringsEqaller
{
	EXACT(String::equals),
	IGNORE_CASE(String::equalsIgnoreCase);

	private final BiPredicate<String, String> tester;

	StringsEqaller(BiPredicate<String, String> tester)
	{
		this.tester = tester;
	}
	public boolean areEquals(String text1, String text2) 
	{
		return this.tester.test(text1, text2);
	}
}