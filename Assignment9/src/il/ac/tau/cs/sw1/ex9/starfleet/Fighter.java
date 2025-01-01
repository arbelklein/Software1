package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Fighter extends myAbstractSpaceship{
	
	protected List<Weapon> weapons;
	private final int BASICANNUALCOST = 2500;
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weapons = weapons;
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
	
	public int getAnnualMaintenanceCost()
	{
		int cost = this.BASICANNUALCOST;
		int engineCost = (int) (1000 * this.maximalSpeed);
		
		cost += engineCost;
		Iterator<Weapon> it = this.weapons.iterator();
		while(it.hasNext())
		{
			cost += it.next().getAnnualMaintenanceCost();
		}

		return cost;
	}
	
	public String toString()
	{
		return ("Fighter\n" + super.toString() + "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost() + 
				"\n\tWeaponArray=" + this.getWeapon());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(weapons);
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
		Fighter other = (Fighter) obj;
		return Objects.equals(weapons, other.weapons);
	}
	
}
