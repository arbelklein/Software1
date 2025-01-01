package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3{

	private static final long serialVersionUID = 1L;

	public B3(String s) {
		super(s);
	}
	
	public void foo(String s) throws B3{
		throw new B3(s);
	}
	
	public String getMessage()
	{
		return super.s;
	}
	
}