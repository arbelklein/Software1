package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter{
	
	private final int BASICANNUALCOST = 4000;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}
	
	public int getAnnualMaintenanceCost()
	{
		int cost = this.BASICANNUALCOST;
		int engineCost = (int) (500 * this.maximalSpeed);
		int memberCost = 500 * this.crewMembers.size();
		
		cost += (engineCost + memberCost);
		Iterator<Weapon> it = this.weapons.iterator();
		while(it.hasNext())
		{
			cost += it.next().getAnnualMaintenanceCost();
		}

		return cost;
	}
	
	public String toString()
	{
		return ("ColonialViper\n" + super.toString().substring(8));
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
