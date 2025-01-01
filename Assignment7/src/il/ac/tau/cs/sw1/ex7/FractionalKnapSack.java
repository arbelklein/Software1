package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
    	List<Item> copy = new ArrayList<Item>();
    	Iterator<Item> orgIT = this.lst.iterator();
    	
    	while(orgIT.hasNext())
    	{
    		Item element = orgIT.next();
    		copy.add(element);
    	}
    	
    	Collections.sort(copy, new Comparator<Item>() {
    		@Override
    		public int compare(Item a, Item b) {
    			//compare by the relative weight.
    			if((a.value / a.weight) < (b.value / b.weight))
    				return 1;
    			else return -1;
    		}
    	});
    	
        return copy.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	return sum(candidates_lst) <= capacity;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
    	int sumLst = sum(candidates_lst);
    	
    	if(sumLst + element.weight <= capacity)
    		candidates_lst.add(element);
    	
    	if(sumLst + element.weight > capacity)
    	{
    		int leftSpace = capacity - sumLst;
    		double relativeValue = leftSpace / element.weight;
    		candidates_lst.add(new Item(leftSpace, relativeValue * element.value));
    	}
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        return sum(candidates_lst) == capacity;
    }
    
    private int sum(List<Item> lst){
    	int sum = 0;
        for (Item element : lst){
            sum += element.weight;
        }
        return sum;
    }
    
}
