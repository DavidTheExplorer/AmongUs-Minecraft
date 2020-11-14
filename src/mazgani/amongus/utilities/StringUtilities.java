package mazgani.amongus.utilities;

import java.util.function.BiPredicate;

public class StringUtilities 
{
	public enum EqualityTester 
	{
		EXACT(String::equals),
		IGNORE_CASE(String::equalsIgnoreCase);
		
		private BiPredicate<String, String> tester;
		
		EqualityTester(BiPredicate<String, String> tester)
		{
			this.tester = tester;
		}
		public boolean equals(String text1, String text2) 
		{
			return this.tester.test(text1, text2);
		}
	}
}
