package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{
	
	private List<Map.Entry<T, Integer>> histList;
	private Iterator<Map.Entry<T, Integer>> It;
	
	public HashMapHistogramIterator(HashMap<T, Integer> hist)
	{
		histList = new ArrayList<Map.Entry<T, Integer>>();
		for(Map.Entry<T, Integer> item : hist.entrySet())
		{
			this.histList.add(item);
		}
		
		histList.sort(new HistComparator<T>());
		this.It = this.histList.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return this.It.hasNext();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		return this.It.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
}
