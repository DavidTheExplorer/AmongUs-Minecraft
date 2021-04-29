package dte.amongus.player.statistics;

import static dte.amongus.player.statistics.Statistic.Type.CREWMATE;
import static dte.amongus.player.statistics.Statistic.Type.GENERAL;
import static dte.amongus.player.statistics.Statistic.Type.IMPOSTOR;

import java.util.Objects;
import java.util.function.UnaryOperator;

import org.apache.commons.lang.WordUtils;

public class Statistic<VT>
{
	private final Type type;
	private final String name, description;
	
	public static final Statistic<Integer>
	TOTAL_WINS = new Statistic<>(GENERAL, "Total Wins", "The amount of times you won."),
	TOTAL_KNOCKOUTS = new Statistic<>(GENERAL, "Total Knockouts", "The amount of times you were voted out."),

	IMPOSTOR_KILLS = new Statistic<>(IMPOSTOR, "Impostor Kills", "The amount of Crewmates you killed as Impostor."),
	IMPOSTOR_KNOCKOUTS = Statistic.Extender.toType(TOTAL_KNOCKOUTS, IMPOSTOR).build(),

	CREWMATE_DEATHS = new Statistic<>(CREWMATE, "Crewmate Deaths", "The amount of times you died to an Impostor."),
	CREWMATE_KNOCKOUTS = Statistic.Extender.toType(TOTAL_KNOCKOUTS, CREWMATE).addToDescription(old -> old + ".").build();
	
	public Statistic(Type type, String name, String description) 
	{
		this.type = type;
		this.name = name;
		this.description = description;
	}
	public Type getType() 
	{
		return this.type;
	}
	public String getName() 
	{
		return this.name;
	}
	public String getDescription() 
	{
		return this.description;
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(this.description, this.name, this.type);
	}
	
	@Override
	public boolean equals(Object object) 
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		Statistic<?> other = (Statistic<?>) object;
		
		return Objects.equals(this.description, other.description) &&
				Objects.equals(this.name, other.name) && 
				this.type == other.type;
	}
	
	@Override
	public String toString() 
	{
		return String.format("Stat [type=%s, name=%s, description=%s]", this.type, this.name, this.description);
	}

	public enum Type
	{
		GENERAL, CREWMATE, IMPOSTOR;

		private final String name = WordUtils.capitalizeFully(name().toLowerCase());

		public String getName()
		{
			return this.name;
		}
	}

	public static class Extender<T>
	{
		private Type type;
		private String name, description;

		public Extender(Statistic<?> parent) 
		{
			this.type = parent.getType();
			this.name = parent.getName();
			this.description = parent.getDescription();
		}
		public static <T> Extender<T> toType(Statistic<T> parent, Type newType)
		{
			String typeName = WordUtils.capitalizeFully(newType.name());
			
			return new Extender<T>(parent)
					.changeNameTo(oldName -> String.format("%s %s", typeName, oldName))
					.changeTypeTo(newType)
					.addToDescription(old -> String.format("%s as a %s", prepareToExtension(old), typeName));
		}
		public Extender<T> changeTypeTo(Type type)
		{
			this.type = type;
			return this;
		}
		public Extender<T> changeNameTo(UnaryOperator<String> newNameProvider) 
		{
			this.name = newNameProvider.apply(this.name);
			return this;
		}
		public Extender<T> addToDescription(UnaryOperator<String> newDescriptionProvider) 
		{
			this.description = newDescriptionProvider.apply(this.description);
			return this;
		}
		public Statistic<T> build() 
		{
			return new Statistic<T>(this.type, this.name, this.description);
		}
		
		private static String prepareToExtension(String sentence) 
		{
			char lastLetter = sentence.charAt(sentence.length()-1);
			
			if(lastLetter == '.')
				sentence = sentence.substring(0, sentence.length()-1);
			
			return sentence;
		}
	}
}