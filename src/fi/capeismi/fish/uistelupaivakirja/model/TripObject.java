/*
 * Copyright 2011 Samuli Penttilä
 *
 * This file is part of Uistelupäiväkirja.
 * 
 * Uistelupäiväkirja is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Uistelupäiväkirja is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Uistelupäiväkirja. If not, see http://www.gnu.org/licenses/.
 */

package fi.capeismi.fish.uistelupaivakirja.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public boolean isEndTime()
	{
		if(get("time_end").length() > 0)
			return true;
		
		return false;
	}
	
	public void clearEndTime()
	{
		set("time_end", "");
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
		m_items.add(item);
		return item;
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
			m_items.add(item);
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
