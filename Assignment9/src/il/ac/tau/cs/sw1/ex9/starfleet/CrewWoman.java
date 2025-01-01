package il.ac.tau.cs.sw1.ex9.starfleet;

public class CrewWoman extends myAbstractCrewMember{

	public CrewWoman(int age, int yearsInService, String name){
		super(name, age, yearsInService);
	}
	
	public String toString()
	{
		return ("CrewWoman\n" + super.toString());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
