package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import fi.capeismi.fish.uistelupaivakirja.model.TrollingObjectItem;
import android.util.Log;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

public final class Fish extends Event 
{
	private static final String TAG = "Fish";
	private EventItem m_event = null;
	
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
    	
    	Log.i(TAG, "tripid:"+new Integer(tripindex).toString()+" event:"+new Integer(index).toString());

    	TripObject trip = ModelFactory.getModel().getTrips().getList().get(tripindex);
    	m_event = trip.getEvents().get(index);    	
    	EditText weight = (EditText)findViewById(R.id.Weight);
    	weight.setText(m_event.getWeight());
    	Log.i(TAG, "new fish");
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }

	@Override
	public void onDone() {

		EditText weight = (EditText)findViewById(R.id.Weight);
		m_event.setWeight(weight.getText().toString());
	}
}
