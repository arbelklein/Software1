package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 extends A2{
	private boolean flag;
	
	public B2()
	{
		this.flag = false;
	}
	
	public B2(boolean flag)
	{
		this.flag = flag;
	}
	
	public A2 getA(boolean bool)
	{
		return new B2(bool);
	}
	
	public String foo(String s) {
		if(this.flag)
			return s.toUpperCase();
		return s.toLowerCase();
	}
}
