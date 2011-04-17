package fi.capeismi.fish.uistelupaivakirja.model;

public interface WeatherItem {
	public void setWaterTemp(String value);
	public void setAirTemp(String value);
	
	public String getWaterTemp();
	public String getAirTemp();
	public int getWindSpeed();
	public int getClouds();
	public int getPressure();
	public int getRain();
	public int getWindDirection();
	public int getPressureChange();
	
	public void setWindSpeed(int value);
	public void setClouds(int value);
	public void setPressure(int value);
	public void setRain(int value);
	public void setWindDirection(int value);
	public void setPressureChange(int value);
}
