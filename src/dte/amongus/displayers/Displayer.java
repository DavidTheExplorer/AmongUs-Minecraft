package dte.amongus.displayers;

public interface Displayer<U>
{
	U createUpdate(boolean firstUpdate);
	
	void update(boolean firstUpdate);
}