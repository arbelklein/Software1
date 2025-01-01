package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		
		return fleet.stream().sorted((x,y)->x.compareTo(y)).map(x->x.toString()).collect(Collectors.toList());
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> shipMap = new HashMap<>();
		for(Spaceship ship : fleet)
		{
			if(shipMap.containsKey(ship.getClass().getSimpleName()))
				shipMap.put(ship.getClass().getSimpleName(), shipMap.get(ship.getClass().getSimpleName()) + 1);
			
			else
				shipMap.put(ship.getClass().getSimpleName(), 1);
		}

		return shipMap;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		return fleet.stream().collect(Collectors.summingInt(x->x.getAnnualMaintenanceCost()));

	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> weaponSet = new HashSet<>();
		for(Spaceship ship : fleet)
		{
			if(ship instanceof Fighter)
			{
				for(Weapon weapon : ((Fighter)ship).getWeapon())
					weaponSet.add(weapon.getName());
			}
			
			else if(ship instanceof Bomber)
			{
				for(Weapon weapon : ((Bomber)ship).getWeapon())
					weaponSet.add(weapon.getName());
			}
		}

		return weaponSet;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		return fleet.stream().map(x->x.getCrewMembers().size()).collect(Collectors.summingInt(x->x));

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int numOfOfficers = 0;
		int ageOfOfficers = 0;
		
		for(Spaceship ship : fleet)
		{
			for(CrewMember member : ship.getCrewMembers())
			{
				if(member instanceof Officer)
				{
					numOfOfficers++;
					ageOfOfficers += member.getAge();
				}
			}
		}
		
		return ((float)ageOfOfficers / (float)numOfOfficers);
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> officerMap = new HashMap<>();
		List<Officer> rankList = new ArrayList<>();
		
		for(Spaceship ship : fleet)
		{
			Officer maxRank;
			
			for(CrewMember member : ship.getCrewMembers())
			{
				if(member instanceof Officer)
					rankList.add((Officer)member);
			}
			
			if(!rankList.isEmpty())
			{
				Collections.sort(rankList); //sort in descending order by rank
				maxRank = rankList.get(0);
			
				officerMap.put(maxRank, ship);
			
				rankList.clear();
			}
		}
		
		return officerMap;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank, Integer> rankMap = new HashMap<>();
		
		for(Spaceship ship : fleet)
		{
			for (CrewMember member : ship.getCrewMembers())
			{
				if(member instanceof Officer)
				{
					if(rankMap.containsKey(((Officer)member).getRank()))
							rankMap.put(((Officer)member).getRank(), rankMap.get(((Officer)member).getRank()) + 1);
					
					else
						rankMap.put(((Officer)member).getRank(), 1);
				}
			}
		}
		
		List<Map.Entry<OfficerRank, Integer>> entryList = new ArrayList<>(rankMap.entrySet());
		entryList.sort(new OfficerRankIntegerComprator());
		
		return entryList;
	}

}
