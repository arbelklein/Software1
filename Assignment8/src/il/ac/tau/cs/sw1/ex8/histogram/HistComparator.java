package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;
import java.util.Map;

class HistComparator<T extends Comparable<T>> implements Comparator<Map.Entry<T, Integer>> {

	public int compare(Map.Entry<T,Integer> item1, Map.Entry<T, Integer> item2)
	{
		return item1.getKey().compareTo(item2.getKey());
	}
}
