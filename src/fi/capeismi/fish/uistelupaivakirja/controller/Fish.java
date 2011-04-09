package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TrollingObjectItem;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;

public final class Fish extends Event
{
	private static final String TAG = "Fish";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fish);
    	super.onCreate(savedInstanceState);
    	final Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	int index = -1;
    	int tripindex = extras.getInt("tripindex");

		if(!extras.containsKey("event"))
    	{
    		Log.i(TAG, "no previous event. create new one");
    		TrollingObjectItem item = ModelFactory.getModel().getTrips().getList().get(tripindex).newEvent(EventItem.EType.eFish);
    		index = ModelFactory.getModel().getTrips().getList().get(tripindex).getEvents().indexOf(item);
    	}
		else
		{
			index = extras.getInt("event");
		}
				
		setupFishFields(tripindex, index);
		readFishFields();	
    }
    
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	readFishFields();
    }

	@Override
	public void onDone() {
		writeFishFields();
		getTrip().save();
	}
}
