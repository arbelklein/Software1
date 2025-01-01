package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Bomber extends myAbstractSpaceship{
	
	private List<Weapon> weapons;
	private int numberOfTechnicians;
	private final int BASICANNUALCOST = 5000;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weapons = weapons;
		this.numberOfTechnicians = numberOfTechnicians;
	}
	
	public List<Weapon> getWeapon()
	{
		return this.weapons;
	}

	public int getFirePower()
	{
		int firePower = super.getFirePower();
		Iterator<Weapon> it = this.weapons.iterator();
		while(it.hasNext())
		{
			firePower += it.next().getFirePower();
		}

		return firePower;
	}
	
	public int getNumberOfTechnicians()
	{
		return this.numberOfTechnicians;
	}
	
	public int getAnnualMaintenanceCost()
	{
		float cost = this.BASICANNUALCOST;
		
		float precentage = (this.numberOfTechnicians * 10) / (float)100;
		
		Iterator<Weapon> it = this.weapons.iterator();
		while(it.hasNext())
		{
			cost += (it.next().getAnnualMaintenanceCost() * (1 - precentage));
		}
		
		return (int)cost;
	}
	
	public String toString()
	{
		return ("Bomber\n" + super.toString() + "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost() + 
				"\n\tWeaponArray=" + this.getWeapon() + "\n\tNumberOfTechnicians=" + this.getNumberOfTechnicians());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(numberOfTechnicians, weapons);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bomber other = (Bomber) obj;
		return numberOfTechnicians == other.numberOfTechnicians && Objects.equals(weapons, other.weapons);
	}
}
