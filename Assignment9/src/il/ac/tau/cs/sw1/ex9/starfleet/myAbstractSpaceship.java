package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;
import java.util.Set;

public abstract class myAbstractSpaceship implements Spaceship, Comparable<Spaceship>{
	
	protected String name;
	protected int commisionYear;
	protected float maximalSpeed;
	protected Set<? extends CrewMember> crewMembers;
	protected static final int BASICFIREPOWER = 10;
	
	
	public myAbstractSpaceship(String name, int commisionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers)
	{
		this.name = name;
		this.commisionYear = commisionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getCommisionYear()
	{
		return this.commisionYear;
	}
	
	public float getMaximalSpeed()
	{
		return this.maximalSpeed;
	}
	
	public int getFirePower()
	{
		return myAbstractSpaceship.BASICFIREPOWER;
	}
	
	public Set<? extends CrewMember> getCrewMembers()
	{
		return this.crewMembers;
	}
	
	public String toString()
	{
		return ("\tName=" + this.getName() + "\n\tCommissionYear=" + this.getCommisionYear() + "\n\tMaximalSpeed=" + this.getMaximalSpeed() + 
				"\n\tFirePower=" + this.getFirePower() + "\n\tCrewMembers=" + this.getCrewMembers().size());
	}

	@Override
	public int hashCode() {
		return Objects.hash(commisionYear, crewMembers, maximalSpeed, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		myAbstractSpaceship other = (myAbstractSpaceship) obj;
		return commisionYear == other.commisionYear && Objects.equals(crewMembers, other.crewMembers)
				&& Float.floatToIntBits(maximalSpeed) == Float.floatToIntBits(other.maximalSpeed)
				&& Objects.equals(name, other.name);
	}
	
	@Override
	public int compareTo(Spaceship other)
	{
		if(this.getFirePower() == other.getFirePower())
		{
			if(this.getCommisionYear() == other.getCommisionYear())
				return (this.getName().compareTo(other.getName()));
			
			else
				return ((Integer)other.getCommisionYear()).compareTo((Integer)this.getCommisionYear());
		}
		
		else
			return ((Integer)other.getFirePower()).compareTo((Integer)this.getFirePower());
	}
	
	public abstract int getAnnualMaintenanceCost();

}
