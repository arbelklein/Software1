import java.util.Arrays;

public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		
		if(array.length == 0) //the array is empty
		{
			return array;
		}
		
		if(direction != 'L' && direction != 'R') //the direction is invalid
		{
			return array;
		}
		
		
		if(direction == 'L') //moving to the left
		{
			return shiftArrayCyclic(array, (0-move), 'R');
		}
				
		
		if(direction == 'R') //moving to the right
		{
			int[] tempArray = new int[array.length];
			tempArray = Arrays.copyOf(array, array.length);
			
			move = move % array.length;
			if(move < 0)
			{
				move = array.length + move;
			}
			
			for(int i=0; i < array.length; i++)
			{
				int j = (i + move) % array.length;
				array[j]= tempArray[i]; 
			}
		}
				
		
		return array;

	}


	public static int findShortestPath(int[][] m, int i, int j) {
		
		if(i == j)//path from node to itself
		{
			return 0;
		}
		
		
		int[] zeroedArr = new int[m.length];
		if(Arrays.compare(m[i], zeroedArr) == 0)//there isnt an edge going out from node i
		{
			return -1;
		}
		
		
		int row = 0;
		while(row < m.length)
		{
			if(m[row][j] != 0)
				break;
			row++;
		}
		if(row == m.length)//there isnt an edge going in to node j
		{
			return -1;
		}
		
		
		int min = m.length;
		for(int numOfEdges: findAllPaths(m, i, j))
		{
			min = Math.min(min, numOfEdges);
		}
		
		
		return min;

	}
	
	
	public static int[] findAllPaths(int[][] mat, int i, int j)
	{
		int[] pathsArray = new int[mat.length];
		int pathIndex = 0;
		
		for (int col=0; col < mat.length; col++)
		{
			int numOfEdges = 0;
			if(mat[i][col] == 1)//there is a path from node i to node col
			{
				numOfEdges = countEdges(mat, i, col, j, i);
			}
			
			if(numOfEdges > 0) //there is a path from i to j
			{
				pathsArray[pathIndex] = numOfEdges;
				pathIndex++;
			}
		}
		
		return Arrays.copyOf(pathsArray, pathIndex);
	}
	
	public static int countEdges(int[][] mat, int rowSrc, int colSrc, int originDestNode, int originSrcNode)
	{
		//while(colSrc < mat.length)
		//{
			if(mat[rowSrc][colSrc] == 0)// there isnt a path from rowSrc node to colSrc node
			{
				return 0 + countEdges(mat, rowSrc, colSrc+1, originDestNode, originSrcNode);
			}
			
			if(colSrc == originDestNode)//reached the desire destination node
			{
				return 1;
			}
			
			//there is a path from rowSrc node to colSrc node
			for(int col=0; col < mat.length; col++)
			{
				if(colSrc == originSrcNode)//reached a loop
				{
					return 0;
				}
				
				int numOfEdges = countEdges(mat, colSrc, col, originDestNode, originSrcNode);
				if(numOfEdges > 0)
				{
					return 1 + numOfEdges;
				}
			}
		//}
		
		return -1;
	}

}
