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

import fi.capeismi.fish.uistelupaivakirja.model.WeatherInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public final class Weather extends Event {
	private WeatherCounterPart m_weatherImpl = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.weather);
    	super.onCreate(savedInstanceState);
				
    	m_weatherImpl = new WeatherCounterPart(getEvent(), getTrip(), new PrivateConduit());
		
		Button autofetch = ((Button)findViewById(R.id.AutoFetch));
		autofetch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				try
				{
					m_weatherImpl.writeWeatherFields();
					findViewById(R.id.ProgressBar).setVisibility(View.VISIBLE);
					new WeatherDownloader().execute(getTrip().getPlace().getCity());
				} 
				catch(Exception e)
				{
				}
			}			
		});
    }
    
    private class WeatherDownloader extends AsyncTask<String, Void, WeatherInfo>
    {

		@Override
		protected WeatherInfo doInBackground(String... city) {
			try
			{
				WeatherInfo weather = new WeatherInfo(city[0], getEvent());
				return weather;
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(WeatherInfo weather)
		{
			m_weatherImpl.readWeatherFields();
			findViewById(R.id.ProgressBar).setVisibility(View.INVISIBLE);
		}
    	
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	m_weatherImpl.readWeatherFields();
    	readCommonFields();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
		m_weatherImpl.writeWeatherFields();
		getTrip().save();		
    }
}
