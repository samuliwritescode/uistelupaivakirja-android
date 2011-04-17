package fi.capeismi.fish.uistelupaivakirja.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	
	public static String getHumanReadableWindspeed(int value)
	{
		String retval = "n/a";
	    switch(value)
	    {
	    case 1: retval = "Tyyntä (< 0.2 m/s)"; break;
	    case 2: retval = "Hyvin heikko (0.3 – 1.5 m/s)"; break;
	    case 3: retval = "Heikkoa (1.6 – 3.3 m/s)"; break;
	    case 4: retval = "Kohtalaista (3.4 – 5.4 m/s)"; break;
	    case 5: retval = "Kohtalaista (5.5 – 7.9 m/s)"; break;
	    case 6: retval = "Navakkaa (8.0 – 10.7 m/s)"; break;
	    case 7: retval = "Navakkaa (10.8 – 13.8 m/s)"; break;
	    case 8: retval = "Kovaa (13.9 – 17.1 m/s)"; break;
	    case 9: retval = "Kovaa (17.2 – 20.7 m/s)"; break;
	    case 10: retval = "Myrskyä (> 20.8 m/s)"; break;
	    }
		return retval;
	}
	
	public static String getHumanReadableClouds(int value)
	{
		String retval = "n/a";
	    switch(value)
	    {
	    case 1: retval = "Selkeää (0/8)"; break;
	    case 2: retval = "Selkeää (1/8)"; break;
	    case 3: retval = "Melko selkeää (2/8)"; break;
	    case 4: retval = "Puolipilvistä (3/8)"; break;
	    case 5: retval = "Puolipilvistä (4/8)"; break;
	    case 6: retval = "Melko pilvistä (5/8)"; break;
	    case 7: retval = "Melko pilvistä (6/8)"; break;
	    case 8: retval = "Pilvistä (7/8)"; break;
	    case 9: retval = "Pilvistä (8/8)"; break;
	    case 10: retval = "Sumua (9/8)"; break;
	    }
		return retval;
	}
	
	public static String getHumanReadableRain(int value)
	{
		String retval = "n/a";
	    switch(value)
	    {
	    case 1: retval = "Pouta (<0.3 mm)"; break;
	    case 2: retval = "Vähän sadetta (0.3 - 0.9 mm)"; break;
	    case 3: retval = "Sadetta (1.0 - 4.4 mm)"; break;
	    case 4: retval = "Runsasta sadetta (>4.5 mm)"; break;
	    }
		return retval;
	}
	
	public static List<String> getWindDirections()
	{
		List<String> retval = new ArrayList<String>();
		retval.add("n/a");
		retval.add("Etelä");
		retval.add("Lounas");
		retval.add("Länsi");
		retval.add("Luode");
		retval.add("Pohjoinen");
		retval.add("Koillinen");
		retval.add("Itä");
		retval.add("Kaakko");
		retval.add("Ei suuntaa");
		return retval;
	}
	
	public static List<String> getPressureChanges()
	{
		List<String> retval = new ArrayList<String>();
		retval.add("n/a");
		retval.add("Laskee nopeasti");
		retval.add("Laskee");
		retval.add("Laskee hitaasti");
		retval.add("Muuttumaton");
		retval.add("Nousee hitaasti");
		retval.add("Nousee");
		retval.add("Nousee nopeasti");
		return retval;
	}
	
	public EventItem(Map<String, String> props)
	{
		super(props);
	}
	
	//Dont use default constructor
	private EventItem()
	{
		super(null);
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
		String retval = get(FISH_TIME);
		retval += " ";
		if(getType() == EType.eFish || getType() == EType.eFishAndWeather)
		{
			retval += formatted(get(FISH_SPECIES));
			retval += formatted(getWithUnit(FISH_WEIGHT, "g"));			
			retval += formatted(getWithUnit(FISH_LENGTH, "cm"));		
			if(getLure() != null)
			{
				retval += formatted(getLure().toString());
			}
		}
		
		if(getType() == EType.eWeather || getType() == EType.eFishAndWeather)
		{
			retval += formatted(getWithUnit(FISH_AIR_TEMP, "C"));
			retval += formatted(getWithUnit(FISH_WATER_TEMP, "C"));
			retval += formatted(getHumanReadableWindspeed(getInt(FISH_WIND)));
			retval += formatted(getHumanReadableClouds(getInt(FISH_WEATHER)));
		}
		
		if(retval.endsWith(", "))
			retval = retval.substring(0, retval.length()-2);
		
		return retval;
	}
	
	private String formatted(String str)
	{
		if(str.length() == 0)
			return "";
		else
		{
			return str+", ";
		}
	}
	
	private String getWithUnit(String type, String unit)
	{
		String value = get(type);
		if(value.length() == 0)
			return "";
		
		value += " "+unit;
		return value;
	}
	
	public void setTime(Date date)
	{
		set(FISH_TIME, new SimpleDateFormat("HH:mm:00").format(date));
	}
	
	public int getWindSpeed() { return getInt(FISH_WIND); }
	public int getClouds() { return getInt(FISH_WEATHER); }
	public int getPressure() { return getInt(FISH_PRESSURE); }
	public int getRain() { return getInt(FISH_RAIN); }
	public int getWindDirection() { return getInt(FISH_WIND_DIRECTION); }
	public int getPressureChange() { return getInt(FISH_PRESSURE_CHANGE); }
	public boolean getIsGroup() {return get(FISH_IS_GROUP).equalsIgnoreCase("true");}
	public boolean getIsUndersize() {return get(FISH_IS_UNDERSIZE).equalsIgnoreCase("true");}
	public boolean getIsCatchNReleased() {return get(FISH_IS_CATCHRELEASED).equalsIgnoreCase("true");}
	public String getGroupAmount() {return get(FISH_GROUP_AMOUNT);}
	
	public void setIsGroup(boolean bIs) {
		set(FISH_IS_GROUP, new Boolean(bIs).toString());
		if(bIs)
		{
			set(FISH_LENGTH, "");
		}
		else
		{
			set(FISH_GROUP_AMOUNT, "");
		}
	}
	public void setIsUndersize(boolean bIs){set(FISH_IS_UNDERSIZE, new Boolean(bIs).toString());}
	public void setIsCatchNReleased(boolean bIs){set(FISH_IS_CATCHRELEASED, new Boolean(bIs).toString());}
	public void setGroupAmount(String amount){set(FISH_GROUP_AMOUNT, amount);}
	
	public void setWindSpeed(int value) { setInt(FISH_WIND, value); }
	public void setClouds(int value) { setInt(FISH_WEATHER, value); }
	public void setPressure(int value) { setInt(FISH_PRESSURE, value); }
	public void setRain(int value) { setInt(FISH_RAIN, value); }	
	public void setWindDirection(int value) { setInt(FISH_WIND_DIRECTION, value); }
	public void setPressureChange(int value) { setInt(FISH_PRESSURE_CHANGE, value); }
	
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
	public String getTime(){return get(FISH_TIME);}
	
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
	
	private void setInt(String type, int value)
	{
		try
		{
			set(type, new Integer(value).toString());
		} catch(NumberFormatException e)
		{
			set(type, "");
		}
	}
	
	private int getInt(String type)
	{
		try
		{
			return new Integer(get(type)).intValue();
		} catch(NumberFormatException e)
		{
			return 0;
		}
	}
		
	public EType getType()
	{
		return EType.values()[new Integer(get(FISH_TYPE)).intValue()];
	}
	
	public void setType(EType type)
	{
		set(FISH_TYPE, new Integer(type.ordinal()).toString());
	}
}
