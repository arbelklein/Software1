package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;
import java.util.Set;

public class TransportShip extends myAbstractSpaceship{
	
	private int cargoCapacity;
	private int passengerCapcity;
	private final int BASICANNUALMAINTAIN = 3000;

	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.cargoCapacity = cargoCapacity;
		this.passengerCapcity = passengerCapacity;	
	}
	
	public int getCargoCapacity()
	{
		return this.cargoCapacity;
	}
	
	public int getPassengerCapacity()
	{
		return this.passengerCapcity;
	}
	
	public int getAnnualMaintenanceCost()
	{
		return (this.BASICANNUALMAINTAIN + 5 * this.cargoCapacity + 3 * this.passengerCapcity);
	}
	
	public String toString()
	{
		return ("TransportShip\n" + super.toString() + "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost() + "\n\tCargoCapacity=" + this.getCargoCapacity() + 
				"\n\tPassengerCapacity=" + this.getPassengerCapacity());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cargoCapacity, passengerCapcity);
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
		TransportShip other = (TransportShip) obj;
		return cargoCapacity == other.cargoCapacity && passengerCapcity == other.passengerCapcity;
	}

}
