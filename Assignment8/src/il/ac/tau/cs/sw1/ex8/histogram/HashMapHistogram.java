package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private HashMap<T, Integer> hist;
	
	
	//add constructor here, if needed

	
	public HashMapHistogram(){
		this.hist = new HashMap<T, Integer>();
	}
	
	@Override
	public void addItem(T item) {
		if(this.hist.containsKey(item)) //item is already in the histogram
		{
			int appears = hist.get(item);
			this.hist.put(item, appears+1);
		}
		else
			this.hist.put(item, 1);
	}
	
	@Override
	public boolean removeItem(T item)  {
		if(this.hist.containsKey(item)) //item is in the histogram
		{
			int appears = hist.get(item);
			if(appears != 0)	
				this.hist.put(item, appears-1);
			return true;
		}
		return false;
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for(T item : items)
		{
			addItem(item);
		}
	}

	@Override
	public int getCountForItem(T item) {
		if(this.hist.containsKey(item)) //item is in the histogram
			return this.hist.get(item);
		
		return 0; //item is not in the histogram
	}

	@Override
	public void clear() {
		this.hist.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		Set<T> itemsSet = new HashSet<T>();
		
		for(T item : this.hist.keySet())
		{
			if(this.hist.get(item) > 0)
				itemsSet.add(item);
		}
		
		return itemsSet;
	}
	
	@Override
	public int getCountsSum() {
		int sum=0;
		
		for(T item : this.hist.keySet())
		{
			sum += getCountForItem(item);
		}
		
		return sum;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<T>(this.hist);
	}

}
