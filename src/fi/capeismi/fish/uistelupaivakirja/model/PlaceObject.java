package fi.capeismi.fish.uistelupaivakirja.model;

public class PlaceObject extends TrollingObject {
	public String toString()
	{
		String retval = get("name");
		retval += " ";
		retval += get("city");
		return retval;
	}
}
