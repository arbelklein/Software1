package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter{
	
	private static int numOfEngines = 0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		StealthCruiser.numOfEngines++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, new ArrayList<Weapon>(Arrays.asList(new Weapon ("Laser Cannons", 10, 100))));
	}

	public int getAnnualMaintenanceCost()
	{
		return (super.getAnnualMaintenanceCost() + StealthCruiser.numOfEngines * 50);
	}
	
	public String toString()
	{
		return ("StealthCruiser\n" + super.toString().substring(8));
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
