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
		m_fishImpl.readFishFields();
		
		//setupWeatherFields();
		m_weatherImpl.readWeatherFields();
		readCommonFields();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	m_fishImpl.readFishFields();
    	m_weatherImpl.readWeatherFields();
    	readCommonFields();
    }

	@Override
	public void onDone() {
		m_fishImpl.writeFishFields();
		m_weatherImpl.writeWeatherFields();
		getTrip().save();	
	}
}
