package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;
import java.util.Map;

public class OfficerRankIntegerComprator implements Comparator<Map.Entry<OfficerRank, Integer>>{
	
	public int compare(Map.Entry<OfficerRank, Integer> entry1, Map.Entry<OfficerRank, Integer> entry2)
	{
		if(entry1.getValue() == entry2.getValue())
			return entry1.getKey().compareTo(entry2.getKey());
		
		else
			return entry1.getValue().compareTo(entry2.getValue());
	}

}
