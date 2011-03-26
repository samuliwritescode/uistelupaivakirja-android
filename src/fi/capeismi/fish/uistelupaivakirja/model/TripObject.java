package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TripObject extends TrollingObject{

	@Override
	public String toString()
	{
		return get("description");
	}
	
	@Override
	protected TrollingObjectItem newItem(Map<String, String> props)
	{
		return new EventItem(props);
	}
	
	public List<EventItem> getEvents()
	{
		List<EventItem> list = new ArrayList<EventItem>();
		for(TrollingObjectItem item: m_items)
		{		
			list.add((EventItem)item);
		}
		return list;
	}
}
