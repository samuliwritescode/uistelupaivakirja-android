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
