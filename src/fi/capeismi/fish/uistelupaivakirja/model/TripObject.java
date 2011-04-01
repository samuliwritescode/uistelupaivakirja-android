package fi.capeismi.fish.uistelupaivakirja.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class TripObject extends TrollingObject{
	
	public void setDate(Date date)
	{
		set("date", new SimpleDateFormat("yyyy-MM-dd").format(date));		
	}
	
	public void setStartTime(Date date)
	{
		set("time_start", new SimpleDateFormat("HH:mm").format(date));
	}
	
	public void setEndTime(Date date)
	{
		set("end_start", new SimpleDateFormat("HH:mm").format(date));
	}
	
	@Override
	public String toString()
	{
		String retval = get("date");
		retval += " ";
		retval += get("time_start");
		retval += " -> ";
		retval += get("time_end");
		return retval;
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
