package fi.capeismi.fish.uistelupaivakirja.model;

public class TripObject extends TrollingObject{

	@Override
	public String toString()
	{
		return m_keyvalues.get("description");
	}
}
