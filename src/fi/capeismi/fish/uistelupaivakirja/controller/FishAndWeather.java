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

package fi.capeismi.fish.uistelupaivakirja.controller;


import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import android.os.Bundle;

public final class FishAndWeather extends Event {
	
	private WeatherCounterPart m_weatherImpl = null;
	private FishCounterPart m_fishImpl = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fishnweather);
    	super.onCreate(savedInstanceState);

		setMembers(EventItem.EType.eFishAndWeather);
		
		m_weatherImpl = new WeatherCounterPart(getEvent(), getTrip(), new PrivateConduit());
		m_fishImpl = new FishCounterPart(getEvent(), getTrip(), new PrivateConduit());
				
		//setupFishFields();
		//m_fishImpl.readFishFields();
		
		//setupWeatherFields();
		//m_weatherImpl.readWeatherFields();
		//readCommonFields();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	m_fishImpl.readFishFields();
    	m_weatherImpl.readWeatherFields();
    	readCommonFields();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
		m_fishImpl.writeFishFields();
		m_weatherImpl.writeWeatherFields();
		getTrip().save();	
    }
}
