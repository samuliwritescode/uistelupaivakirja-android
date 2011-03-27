package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.Map;

public class EventItem extends TrollingObjectItem {
	
	public enum EType {eNaN, eFish, eWeather, eFishAndWeather}
	
	public EventItem(Map<String, String> props)
	{
		super(props);
	}
	
	@Override
	public String toString()
	{
		return get("fish_species");
	}
	
	public String getWeight()
	{
		return get("fish_weight");
	}
	
	public void setWeight(String weight)
	{
		set("fish_weight", weight);
	}
	
	public EType getType()
	{
		return EType.values()[new Integer(get("type")).intValue()];
	}
}
