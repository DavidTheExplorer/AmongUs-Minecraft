package dte.amongus.utils.java.objectholders;

import java.util.Objects;

public class Pair<A, B> 
{
	private A first;
	private B second;
	
	protected Pair(A object1, B object2)
	{
		this.first = object1;
		this.second = object2;
	}
	public static <A, B> Pair<A, B> of(A object1, B object2)
	{
		return new Pair<A, B>(object1, object2);
	}
	public void setFirst(A object)
	{
		this.first = object;
	}
	public void setSecond(B object) 
	{
		this.second = object;
	}
	public A getFirst()
	{
		return this.first;
	}
	public B getSecond()
	{
		return this.second;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this.first, this.second);
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if(getClass() != obj.getClass())
			return false;
		
		Pair<?, ?> other = (Pair<?, ?>) obj;
		
		return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
	}
}