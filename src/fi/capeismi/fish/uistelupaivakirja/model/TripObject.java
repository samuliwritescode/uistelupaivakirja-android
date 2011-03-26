package fi.capeismi.fish.uistelupaivakirja.model;

public class TripObject extends TrollingObject{

	@Override
	public String toString()
	{
		return get("description");
	}
}
