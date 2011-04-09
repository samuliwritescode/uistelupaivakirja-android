package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TrollingObjectItem;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public final class Weather extends Event {
	private static final String TAG = "Weather";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.weather);
    	super.onCreate(savedInstanceState);
    	
    	final Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	int index = -1;
    	int weatherindex = extras.getInt("tripindex");

		if(!extras.containsKey("event"))
    	{
    		Log.i(TAG, "no previous event. create new one");
    		TrollingObjectItem item = ModelFactory.getModel().getTrips().getList().get(weatherindex).newEvent(EventItem.EType.eWeather);
    		index = ModelFactory.getModel().getTrips().getList().get(weatherindex).getEvents().indexOf(item);
    	}
		else
		{
			index = extras.getInt("event");
		}
				
		setupWeatherFields(weatherindex, index);
		readWeatherFields();
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	readWeatherFields();
    }
    
	@Override
	public void onDone() {
		writeWeatherFields();
		getTrip().save();		
	}
}
