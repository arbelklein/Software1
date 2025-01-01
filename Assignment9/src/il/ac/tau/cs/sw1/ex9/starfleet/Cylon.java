package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class Cylon extends myAbstractCrewMember{
	
	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		super(name, age, yearsInService);
		this.modelNumber = modelNumber;
	}
	
	public int getModelNumber()
	{
		return this.modelNumber;
	}
	
	public String toString()
	{
		return ("Cylon\n" + super.toString() + "\n\tModelNumber=" + this.getModelNumber());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(modelNumber);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cylon other = (Cylon) obj;
		return modelNumber == other.modelNumber;
	}

}
