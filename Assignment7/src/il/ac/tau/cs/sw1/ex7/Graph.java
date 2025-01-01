package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
    	List<Edge> copy = new ArrayList<Edge>();
    	Iterator<Edge> orgIT = this.lst.iterator(); 	
    	
    	while(orgIT.hasNext())
    	{
    		Edge element = orgIT.next();
    		copy.add(element);
    	}
    	
    	Collections.sort(copy, new Comparator<Edge>() {
    		@Override
    		public int compare(Edge a, Edge b) {
    			if(a.weight > b.weight)
    				return 1;
    			else if(a.weight < b.weight)
    				return -1;
    			else //a.weight == b.weight
    			{
    				if(a.node1 > b.node1)
    					return 1;
    				else if(a.node1 < b.node1)
    					return -1;
    				else //a.node1 == b.node1
    				{
    					if(a.node2 > b.node2)
    						return 1;
    					else
    						return -1;
    				}
    			}
    		}
    	});
    	
        return copy.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        return (!Circle(candidates_lst, element) && (candidates_lst.size() <= n));
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
    	candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return (isTree(candidates_lst) && isSpreading(candidates_lst));
    }
    
    /*
     * @pre !Circle()
     */
    private boolean Circle(List<Edge> lst, Edge element){
    	if(lst.isEmpty())
    		return false;
    	
    	List<Integer> relationNode1 = relations(lst, element.node1);
    	List<Integer> relationNode2 = relations(lst, element.node2);
    	
    	Iterator<Integer> it = relationNode1.iterator();
    	
    	while(it.hasNext())
    	{
    		Integer node = it.next();
    		if(relationNode2.contains(node))
    			return true;
    	}
    	
    	return false;
    }
    
    private List<Integer> relations(List<Edge> lst, int node)
    {
    	List<Integer> ret = new ArrayList<Integer>();
    	
    	Iterator<Edge> it = lst.iterator();
    	while(it.hasNext())
    	{
    		Edge element = it.next();
    		if(element.node1 == node)
    			ret.add(element.node2);
    		if(element.node2 == node)
    			ret.add(element.node1);
    	}
    	
    	return ret;
    }
    
    private void extenededRelations(List<Integer> allRelations, List<Edge> lst, int node, int src)
    {
    	if((allRelations.size() == n+1) || lst.isEmpty() || node == src)
    		return;
    	
    	else
    	{
	    	if(node == -1) //first iteration
	    		node = src;
	    	
	    	List<Integer> relations = relations(lst, node);
	    	Iterator<Integer> it = relations.iterator();
	    	
	    	while(it.hasNext())
	    	{
	    		Integer n = it.next();
	    		if(!allRelations.contains(n))
	    		{
	    			allRelations.add(n);
	    			extenededRelations(allRelations, lst, n, src);
	    		}
	    	}
    	}
    }
    
    private boolean isTree(List<Edge> lst)
    {
    	for(int i=0; i<=n; i++)
    	{
    		List<Integer> allRelations = new ArrayList<Integer>();
    		extenededRelations(allRelations, lst, -1, i);
    		
    		if(allRelations.size() != n+1)
    			return false;
    	}
    	
    	return true;
    }
    
    private boolean isSpreading(List<Edge> lst)
    {
    	for(int i=0; i<=n; i++)
    	{
    		List<Integer> relations = relations(lst, i);
    		if(relations.isEmpty())
    			return false;
    	}
    	
    	return true;
    }
}
