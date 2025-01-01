

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		
		for(int i = 0; i < Integer.parseInt(args[0]); i++)
		{
			
			if(i % 2 == 0) // i is even
			{
				piEstimation += (1.0 / (i*2 + 1)); //adding to the sum
			}
			else //i is odd
			{
				piEstimation -= (1.0 / (i*2 + 1)); // subtracting from the sum
			}
		}
		
		piEstimation *= 4; //multiplying by 4
		
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
