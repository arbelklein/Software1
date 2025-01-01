package il.ac.tau.cs.sw1.ex7;

import java.util.*;


public interface Greedy<T>{

    /**
     * A selection function, which chooses the best candidate to be added to the solution
     */
    Iterator<T> selection();

    /**
     * A feasibility function, that is used to determine if a candidate can be used to contribute to a solution
     */
    boolean feasibility(List<T> lst, T element);

    /**
     * An assign function, which assigns a value to a solution, or a partial solution
     */
    void assign(List<T> lst, T element);

    /**
     * A solution function, which will indicate when we have discovered a complete solution
     */
    boolean solution(List<T> lst);

    /**
     * The Greedy Algorithm
     */
    default List<T> greedyAlgorithm(){
    	List<T> condidates_list = new ArrayList<>();
    	
    	Iterator<T> it = selection();
    	
    	while(it.hasNext())
    	{
    		T element = it.next();
    		if(feasibility(condidates_list, element) == true)
    		{
    			assign(condidates_list, element);
    		}
    		
    		if(solution(condidates_list) == true)
    			return condidates_list;
    	}
    	
        return null;
    }
}
