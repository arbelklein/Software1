package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Test {
	
public static void main(String[] args) {
		
	
		int[] arr1 = {1, 2, 3, 4, 5, 6};
		int[] emptyArr = {};
		int[] arr2 = {6, 1, 3, 2, 5, 4};
		
		if(SectionB.contains(arr1, 0) == true)
			System.out.println("Error 1.1");
		else System.out.println("GOOD 1.1");
		
		if(SectionB.contains(arr1, 3) == false)
			System.out.println("Error 1.2");
		else System.out.println("GOOD 1.2");
		
		if(SectionB.contains(emptyArr, 3) == true)
			System.out.println("Error 1.3");
		else System.out.println("GOOD 1.3");
		
		if(SectionB.unknown(emptyArr) != 0)
			System.out.println("Error 2.1");
		else System.out.println("GOOD 2.1");
		
		if(SectionB.unknown(arr1) != 0)
			System.out.println("Error 2.2");
		else System.out.println("GOOD 2.2");
		
		if(SectionB.max(arr1) != 6)
			System.out.println("Error 3.1");
		else System.out.println("GOOD 3.1");
		
		if(SectionB.min(arr2) != 1)
			System.out.println("Error 4.1");
		else System.out.println("GOOD 4.1");
		
		
		if(SectionB.reverse("arbel").equals("lebra") == false)
			System.out.println("Error 5.1");
		else System.out.println("GOOD 5.1");
		
		if(SectionB.reverse("HELLP").equals("PLLEH") == false)
			System.out.println("Error 5.2");
		else System.out.println("GOOD 5.2");
		
		if(SectionB.reverse("aba").equals("aba") == false)
			System.out.println("Error 5.3");
		else System.out.println("GOOD 5.3");
		
		if(SectionB.reverse("1234%").equals("%4321") == false)
			System.out.println("Error 5.4");
		else System.out.println("GOOD 5.4");
		
		int[] ret = SectionB.guess(arr1);
		int[] copy = Arrays.copyOf(ret, ret.length);
		Arrays.sort(ret);
		if(Arrays.equals(copy, ret) == true)
			System.out.println("Error 6.1");
		else System.out.println("GOOD 6.1");
		
		for(int i=0; i<copy.length; i++)
			if(SectionB.contains(arr1, copy[i]) == false)
				System.out.println("Error 6.2");
		System.out.println("GOOD 6.2");
		
		
	
		Polynomial p1 = new Polynomial(); // p1 = 0
		printPol(p1);
		
		System.out.println("1. p1.getDegree()= " + p1.getDegree());
		printError(p1.getDegree() == 0,
				"the degree of p1 is 0");
		
		System.out.println("2. p1.computePolynomial(3)= " + p1.computePolynomial(3));
		printError(p1.computePolynomial(3) == 0.0,
				"the value of p1 with x=3 is 0.0");
		
		double[] coefficients = new double[]{1.0,2.0,3.0};
		Polynomial p2 = new Polynomial(coefficients); // p2 = 1.0+2.0*x+3.0*x^2
		printPol(p2);
		
		System.out.println("3. p2.computePolynomial(3)= " + p2.computePolynomial(3));
		printError(p2.computePolynomial(3) == 34.0,
				"the value of p2 with x=3 is 34.0");
		
		System.out.println("4. p2.getCoefficient(1)= " + p2.getCoefficient(1));
		printError(p2.getCoefficient(1) == 2.0,
				"the coefficient of x is 2.0");
		
		System.out.println("9. p2.isExtrema(-1/3)= " + p2.isExtrema(-1.0/3.0));
		printError(p2.isExtrema(-1.0/3.0) == true,
				"-1/3 is Extrema");
		
		System.out.println("10. p2.isExtrema(5.0)= " + p2.isExtrema(5.0));
		printError(p2.isExtrema(5.0) == false,
				"5 is not Extrema");
		
		Polynomial p3 = p2.multiply(2.0);
		printPol(p3);
		/*
		 * p2 = 1.0+2.0*x+3.0*x^2
		 * p3 = 2.0+4.0*x+6.0*x^2
		 */
		
		System.out.println("5. p2.getCoefficient(1)= " + p2.getCoefficient(1));
		printError(p2.getCoefficient(1) == 2.0,
				"the coefficient of x is 2.0");
		
		System.out.println("6. p3.getCoefficient(1)= " + p3.getCoefficient(1));
		printError(p3.getCoefficient(1) == 4.0,
				"the coefficient of x is 4.0");
		
		
		Polynomial p4 = p2.adds(p3);
		printPol(p4);
		/*
		 * p2 = 1.0+2.0*x+3.0*x^2
		 * p3 = 2.0+4.0*x+6.0*x^2
		 * p4 = 3.0+6.0*x+9.0*x^2
		 */
		System.out.println("7. p4.getDegree()= " + p4.getDegree());
		printError(p4.getDegree() == 2,
				"the degree of p4 is 2");
		
		Polynomial p5 = p4.getFirstDerivation();
		printPol(p5);
		
		/*
		 * p4 = 3.0+6.0*x+9.0*x^2
		 * p5 = 6.0 + 18*x
		 */
		
		System.out.println("8. p5.getDegree()= " + p5.getDegree());
		printError(p5.getDegree() == 1,
				"the degree of p5 is 1");
		
		
	}

	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	public static void printPol(Polynomial pol)
	{
		System.out.print("pol= ");
		for(int i=0; i<pol.getDegree()+1; i++)
		{
			if(i == pol.getDegree())
				System.out.print(pol.getCoefficient(i) + " * x^" + i);
			else System.out.print(pol.getCoefficient(i) + " * x^" + i + " + ");
		}
		System.out.println();
	}
}
