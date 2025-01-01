
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = -1;
		
		n = Integer.parseInt(args[0]);
		
		int prev1 = 1, prev2 = 1; //Initialising the first two Fibonacci numbers
		
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		
		if(n == 1) //asking only the first Fibonacci number
		{
			System.out.println(prev1);
			numOfOdd = 1;
		}
		
		else if(n == 2) //asking only the first rwo Fibonnaci numbers
		{
			System.out.println(prev1 + " " + prev2);
			numOfOdd = 2;
		}
		
		else
		{
			int current;
			
			//we already know the first two numbers are odd
			System.out.print(prev1 + " " + prev2 + " ");
			numOfOdd = 2;
			
			for(int i = 2; i < n; i++)
			{
				current = prev1 + prev2;
				
				//printing the numbers
				if(i == (n-1)) //we are at the last number
				{
					System.out.println(current);
				}
				else
				{
					System.out.print(current + " ");
				}
				
				//checking if the current number is odd
				if(current % 2 == 1)
				{
					numOfOdd++;
				}
				
				prev1 = prev2;
				prev2 = current;
			}
		}
		
		System.out.println("The number of odd numbers is: "+numOfOdd);

	}

}
