package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public class TripCollection extends TrollingObjectCollection {

	
	public List<TripObject> getList()
	{
		List<TripObject> list = new ArrayList<TripObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			list.add((TripObject)object);
		}
		return list;
	}

	public TripObject newTrip() {
		build();
		return (TripObject)m_trollingobjects.get(m_trollingobjects.size()-1);
	}
}
