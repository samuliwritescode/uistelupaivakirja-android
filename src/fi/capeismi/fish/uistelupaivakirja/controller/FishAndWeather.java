package fi.capeismi.fish.uistelupaivakirja.controller;


import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import android.os.Bundle;

public final class FishAndWeather extends Event {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fishnweather);
    	super.onCreate(savedInstanceState);

		setMembers(EventItem.EType.eFishAndWeather);
				
		setupFishFields();
		readFishFields();
		
		setupWeatherFields();
		readWeatherFields();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	readFishFields();
    	readWeatherFields();
    }

	@Override
	public void onDone() {
		writeFishFields();
		writeWeatherFields();
		getTrip().save();	
	}
}
