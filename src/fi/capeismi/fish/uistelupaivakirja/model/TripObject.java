package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

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
	
	public TrollingObjectItem newEvent(EventItem.EType type)
	{
		Log.i("tripobject", "new event");
		EventItem item = (EventItem)insertPropItem(new HashMap<String, String>());
		item.setType(type);
		return item;
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
