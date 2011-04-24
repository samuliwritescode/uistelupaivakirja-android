package fi.capeismi.fish.uistelupaivakirja.model;

public class LureObject extends TrollingObject {
	
	public String toString()
	{
		String retval = "";
		retval += get("maker");
		retval += " ";
		retval += get("model");
		retval += " ";
		retval += get("size");
		retval += " ";
		retval += get("color");
		retval += " ";
		retval += get("lure_type");
		return retval;
	}

}
