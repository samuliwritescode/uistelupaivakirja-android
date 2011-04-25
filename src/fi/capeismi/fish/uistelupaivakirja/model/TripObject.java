package fi.capeismi.fish.uistelupaivakirja.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class TripObject extends TrollingObject{
	
	protected List<EventItem> m_items = new ArrayList<EventItem>();	
	
	public void setDate(Date date)
	{
		set("date", new SimpleDateFormat("yyyy-MM-dd").format(date));		
	}
	
	public void setStartTime(Date date)
	{
		set("time_start", new SimpleDateFormat("HH:mm:00").format(date));
	}
	
	public void setEndTime(Date date)
	{
		set("time_end", new SimpleDateFormat("HH:mm:00").format(date));
	}
	
	public void setPlace(PlaceObject place)
	{
		set("place", new Integer(place.getId()).toString());
	}
	
	public PlaceObject getPlace()
	{
		try
		{
			int id = new Integer(get("place")).intValue();
			return ModelFactory.getModel().getPlaces().getId(id);
		}catch(Exception e)
		{
			return null;
		}			
	}
	
	@Override
	public String toString()
	{
		String retval = getTitle();
		retval += " ";
		retval += getContentText();
		return retval;
	}
	
	public String getTitle()
	{
		String retval = "";
		if(getPlace() != null)
		{		
			retval += getPlace().toString();
		}
		retval += " ";
		retval += get("date");
		
		return retval;
	}
	
	public String getContentText()
	{
		String retval = new String();
		if(get("time_start").length() == 8)
			retval += get("time_start").substring(0, 5);
		retval += " -> ";
		if(get("time_end").length() == 8)
			retval += get("time_end").substring(0, 5);
		return retval;
	}	
	
	public EventItem newEvent(EventItem.EType type)
	{
		EventItem item = new EventItem(new HashMap<String, String>());
		item.setType(type);
		item.setTrip(this);
		return item;
	}
	
	public void addEvent(EventItem event)
	{
		if(!m_items.contains(event))
			m_items.add(event);
	}
	
	public void destroyEvent(int id)
	{
		m_items.remove(id);
	}
	
	public List<EventItem> getEvents()
	{
		List<EventItem> list = new ArrayList<EventItem>();
		for(EventItem item: m_items)
		{		
			list.add(item);
		}
		return list;
	}
	
	@Override
	public void setPropItems(List<Map<String, String>> propitems)
	{
		m_items.clear();
		for(Map<String, String> items : propitems)
		{
			EventItem item = new EventItem(items);
			item.setTrip(this);
			addEvent(item);
		}
	}
	
	@Override
	public List<Map<String, String>> getPropItems()
	{
		List<Map<String, String>> retval = new ArrayList<Map<String, String>>();
		for(EventItem item: m_items)
		{
			Map<String, String> vals = item.getProps();
			retval.add(vals);
		}
		
		return retval;
	}

}
