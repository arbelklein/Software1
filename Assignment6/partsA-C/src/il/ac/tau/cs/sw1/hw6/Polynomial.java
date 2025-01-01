package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {
	
	private double[] coefficients;
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		this.coefficients = new double[1];
		this.coefficients[0] = 0.0;
	} 
	
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		this.coefficients = new double[coefficients.length];
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
	}
	
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		int degree;
		double[] newCoefficients;
		if(this.getDegree() > polynomial.getDegree())
		{
			degree = this.getDegree();
			newCoefficients = new double[degree+1];
			for(int i=0; i<=polynomial.getDegree(); i++)
				newCoefficients[i] = this.getCoefficient(i) + polynomial.getCoefficient(i);
			for(int i=(polynomial.getDegree()+1); i<newCoefficients.length; i++)
				newCoefficients[i] = this.getCoefficient(i);
		}
		else 
		{
			degree = polynomial.getDegree();
			newCoefficients = new double[degree+1];
			for(int i=0; i<=this.getDegree(); i++)
				newCoefficients[i] = this.getCoefficient(i) + polynomial.getCoefficient(i);
			for(int i=(this.getDegree()+1); i<newCoefficients.length; i++)
				newCoefficients[i] = polynomial.getCoefficient(i);
		}
			
		return (new Polynomial(newCoefficients));
		
	}
	
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		double[] newCoefficients = new double[this.getDegree()+1];
		for(int i=0; i<newCoefficients.length; i++)
		{
			newCoefficients[i] = a * this.getCoefficient(i);
		}
		
		return (new Polynomial(newCoefficients));
		
	}
	
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		if(this.coefficients.length == 0) //the polynomial is 0
			return 0;
		
		int i = this.coefficients.length - 1;
		while(this.coefficients[i] == 0.0 && i > 0)
			i--;
		return i;
	}
	
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if(n > this.coefficients.length)
			return 0.0;
		return this.coefficients[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if(this.getDegree() < n)
		{
			double [] newCoefficients = new double[n+1];
			newCoefficients = Arrays.copyOf(this.coefficients, n+1);
			this.coefficients = newCoefficients;
		}
		
		this.coefficients[n] = c;
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		double[] newCoefficients = new double[this.getDegree()];
		for(int i=1; i<=newCoefficients.length; i++)
		{
			newCoefficients[i - 1] = i * this.getCoefficient(i);
		}
		
		return (new Polynomial(newCoefficients));
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double result = 0.0;
		if(this.coefficients.length == 0) //the polynomial is 0
			return result;
		
		for(int i=0; i<=this.getDegree(); i++)
			result += (this.getCoefficient(i) * Math.pow(x, i));
		
		return result;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial firstDeri = this.getFirstDerivation();
		Polynomial secondDeri = firstDeri.getFirstDerivation();
		
		if(firstDeri.computePolynomial(x) == 0 && secondDeri.computePolynomial(x) != 0)
			return true;
		
		return false;
	}

}
