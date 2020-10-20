package mazgani.amongus.utilities.objectholders;

public class Triple<A, B, C> extends Pair<A, B>
{
	private C third;

	protected Triple(A object1, B object2, C object3)
	{
		super(object1, object2);
		
		this.third = object3;
	}
	public static <A, B, C> Triple<A, B, C> of(A object1, B object2, C object3)
	{
		return new Triple<A, B, C>(object1, object2, object3);
	}
	public C getThird() 
	{
		return this.third;
	}
	public void setThird(C object) 
	{
		this.third = object;
	}
}