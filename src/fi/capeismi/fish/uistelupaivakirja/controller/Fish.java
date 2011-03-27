package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
    	int index = extras.getInt("event");
    	int tripindex = extras.getInt("tripindex");
    	
    	Log.i(TAG, "tripid:"+new Integer(tripindex).toString()+" event:"+new Integer(index).toString());

    	TripObject trip = ModelFactory.getModel().getTrips().getList().get(tripindex);
    	m_event = trip.getEvents().get(index);    	
    	EditText weight = (EditText)findViewById(R.id.Weight);
    	weight.setText(m_event.getWeight());
    	
    	weight.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				Log.i(TAG, "focus change");
			}
    		
    	});
    	Log.i(TAG, "new fish");
    }

	@Override
	public void onDone() {

		EditText weight = (EditText)findViewById(R.id.Weight);
		Double value = new Double(weight.getText().toString());
		m_event.setWeight(value.toString());
	}
}
