package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.Date;
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
	
	public void remove(int id)
	{
		TrollingObject obj = m_trollingobjects.get(id);
		m_trollingobjects.remove(id);
		obj.destroy();
	}

	public TripObject newTrip() {
		build();
		TripObject trip = (TripObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		trip.setDate(new Date());
		trip.setStartTime(new Date());	
		return trip;
	}
}
