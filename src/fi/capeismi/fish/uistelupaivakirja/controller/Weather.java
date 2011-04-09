package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import android.os.Bundle;

public final class Weather extends Event {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.weather);
    	super.onCreate(savedInstanceState);
    	
    	setMembers(EventItem.EType.eWeather);
				
		setupWeatherFields();
		readWeatherFields();
		readCommonFields();
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	readWeatherFields();
    	readCommonFields();
    }
    
	@Override
	public void onDone() {
		writeWeatherFields();
		getTrip().save();		
	}
}
