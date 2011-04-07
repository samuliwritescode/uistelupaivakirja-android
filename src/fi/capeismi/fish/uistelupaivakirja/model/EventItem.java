package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.Map;

public class EventItem extends TrollingObjectItem {
	
	public enum EType {eNaN, eFish, eWeather, eFishAndWeather}
    public enum EWindDirection{eNaN, eSouth, eSouthWest, eWest, eNorthWest, eNorth, eNorthEast, eEast, eSouthEast, eNoWindDirection};
    public enum EPressureChange{eNaN, eFastDecline, eModerateDecline, eSlowDecline, eNoChange, eSlowRaise, eModerateRaise, eFastRaise};
	
	private static final String FISH_TYPE = "type";
	private static final String FISH_WIND = "fish_wind";
	private static final String FISH_WEATHER = "fish_weather";
	private static final String FISH_LENGTH = "fish_length";
	private static final String FISH_WEIGHT = "fish_weight";
	private static final String FISH_SPOT_DEPTH = "fish_spot_depth";
	private static final String FISH_MISC_TEXT = "fish_misc_text";
	private static final String FISH_WATER_TEMP = "fish_water_temp";
	private static final String FISH_AIR_TEMP = "fish_air_temp";
	private static final String FISH_TOTAL_DEPTH = "fish_total_depth";
	private static final String FISH_TROLLING_SPEED = "fish_trolling_speed";
	private static final String FISH_LINE_WEIGHT = "fish_line_weight";
	private static final String FISH_RELEASE_WIDTH = "fish_release_width";
	private static final String FISH_SPECIES = "fish_species";
	private static final String FISH_PRESSURE = "fish_pressure";
	private static final String FISH_RAIN = "fish_rain";
	private static final String FISH_IS_GROUP = "fish_group";
	private static final String FISH_GROUP_AMOUNT = "fish_group_amount";
	private static final String FISH_IS_UNDERSIZE = "fish_undersize";
	private static final String FISH_IS_CATCHRELEASED = "fish_cr";
	private static final String FISH_WIND_DIRECTION = "fish_wind_direction";
	private static final String FISH_PRESSURE_CHANGE = "fish_pressure_change";
	private static final String FISH_TIME = "fish_time";
	private static final String FISH_GETTER = "fish_getter";
	private static final String FISH_METHOD = "fish_method";
	private static final String FISH_COORDINATES_LAT = "fish_coord_lat";
	private static final String FISH_COORDINATES_LON = "fish_coord_lon";
	private static final String FISH_USERFIELD = "fish_user";
	private static final String FISH_MEDIAFILES = "fish_mediafiles";
	private static final String FISH_LURE = "lure";
	
	public EventItem(Map<String, String> props)
	{
		super(props);
	}
	
	public void setLure(LureObject lure)
	{
		set(FISH_LURE, new Integer(lure.getId()).toString());
	}
	
	public LureObject getLure()
	{
		try
		{
			int id = new Integer(get(FISH_LURE)).intValue();
			return ModelFactory.getModel().getLures().getId(id);
		}catch(Exception e)
		{
			return null;
		}	
	}
	
	@Override
	public String toString()
	{
		return get("fish_species");
	}
	
	//Text field getters
	public String getWeight(){return get(FISH_WEIGHT);}
	public String getLength(){return get(FISH_LENGTH);}
	public String getSpotDepth(){return get(FISH_SPOT_DEPTH);}
	public String getWaterTemp(){return get(FISH_WATER_TEMP);}
	public String getAirTemp(){return get(FISH_AIR_TEMP);}
	public String getTotalDepth(){return get(FISH_TOTAL_DEPTH);}
	public String getTrollingSpeed(){return get(FISH_TROLLING_SPEED);}
	public String getLineWeight(){return get(FISH_LINE_WEIGHT);}
	public String getReleaseWidth(){return get(FISH_RELEASE_WIDTH);}
	public String getSpecies(){return get(FISH_SPECIES);}
	public String getGetter(){return get(FISH_GETTER);}
	public String getMethod(){return get(FISH_METHOD);}
	public String getCoordinatesLat(){return get(FISH_COORDINATES_LAT);}
	public String getCoordinatesLon(){return get(FISH_COORDINATES_LON);}
	
	//Text field setters
	public void setWeight(String value){set(FISH_WEIGHT, value);}
	public void setLength(String value){set(FISH_LENGTH, value);}
	public void setSpotDepth(String value){set(FISH_SPOT_DEPTH, value);}
	public void setWaterTemp(String value){set(FISH_WATER_TEMP, value);}
	public void setAirTemp(String value){set(FISH_AIR_TEMP, value);}
	public void setTotalDepth(String value){set(FISH_TOTAL_DEPTH, value);}
	public void setTrollingSpeed(String value){set(FISH_TROLLING_SPEED, value);}
	public void setLineWeight(String value){set(FISH_LINE_WEIGHT, value);}
	public void setReleaseWidth(String value){set(FISH_RELEASE_WIDTH, value);}
	public void setSpecies(String value){set(FISH_SPECIES, value);}
	public void setGetter(String value){set(FISH_GETTER, value);}
	public void setMethod(String value){set(FISH_METHOD, value);}
	public void setCoordinatesLat(String value){set(FISH_COORDINATES_LAT, value);}
	public void setCoordinatesLon(String value){set(FISH_COORDINATES_LON, value);}
		
	public EType getType()
	{
		return EType.values()[new Integer(get(FISH_TYPE)).intValue()];
	}
	
	public void setType(EType type)
	{
		set(FISH_TYPE, new Integer(type.ordinal()).toString());
	}
}
