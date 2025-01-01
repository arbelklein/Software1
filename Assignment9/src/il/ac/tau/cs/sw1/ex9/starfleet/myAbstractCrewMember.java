package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public abstract class myAbstractCrewMember implements CrewMember{
	
	protected String name;
	protected int age;
	protected int yearsInService;
	
	public myAbstractCrewMember(String name, int age, int yearsInService)
	{
		this.name = name;
		this.age = age;
		this.yearsInService = yearsInService;
	}
	
	public String getName()
	{
		return this.name;
	}

	public int getAge()
	{
		return this.age;
	}
	
	public int getYearsInService()
	{
		return this.yearsInService;
	}
	
	public String toString()
	{
		return ("\tName=" + this.getName() + "\n\tAge=" + this.getAge() + "\n\tYearsInService=" + this.getYearsInService());
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, name, yearsInService);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		myAbstractCrewMember other = (myAbstractCrewMember) obj;
		return age == other.age && Objects.equals(name, other.name) && yearsInService == other.yearsInService;
	}
	
}
