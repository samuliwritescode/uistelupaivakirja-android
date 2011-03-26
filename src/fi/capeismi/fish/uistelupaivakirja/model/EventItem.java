package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.Map;

public class EventItem extends TrollingObjectItem {
		
	public EventItem(Map<String, String> props)
	{
		super(props);
	}
	
	@Override
	public String toString()
	{
		return get("fish_species");
	}
}
