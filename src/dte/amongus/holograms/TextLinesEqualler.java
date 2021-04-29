package dte.amongus.holograms;

import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import dte.amongus.utils.java.StringsEqaller;

public class TextLinesEqualler extends LinesEqualler<TextLine>
{
	private boolean checkText;
	private StringsEqaller stringsEqualler;

	public TextLinesEqualler checkText(StringsEqaller stringsEqualler) 
	{
		this.checkText = true;
		this.stringsEqualler = stringsEqualler;

		return this;
	}
	
	@Override
	public boolean test(TextLine line1, TextLine line2) 
	{
		if(!super.test(line1, line2)) 
			return false;
		
		if(this.checkText && !this.stringsEqualler.areEquals(line1.getText(), line2.getText())) 
			return false;
		
		return true;
	}
}