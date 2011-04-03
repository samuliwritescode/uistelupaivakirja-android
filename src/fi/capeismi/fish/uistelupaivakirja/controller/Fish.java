package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.Comparator;
import java.util.List;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.SpinnerItemObject;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import fi.capeismi.fish.uistelupaivakirja.model.TrollingObjectItem;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;

public final class Fish extends Event implements OnDismissListener
{
	private static final String TAG = "Fish";
	private EventItem m_event = null;
	private TripObject m_trip = null;
	
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

    	m_trip = ModelFactory.getModel().getTrips().getList().get(tripindex);
    	m_event = m_trip.getEvents().get(index);    	
    	EditText weight = (EditText)findViewById(R.id.Weight);
    	weight.setText(m_event.getWeight());
    	Log.i(TAG, "new fish");
    	
    	Button addbtn = (Button)findViewById(R.id.AddSpecies);
    	addbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddSpinnerItem adder = new AddSpinnerItem(Fish.this);
				adder.setOnDismissListener(Fish.this);
				adder.show();
			}
    		
    	});
    	
    	Spinner spinner = (Spinner)findViewById(R.id.Species);
    	ArrayAdapter<SpinnerItemObject> adapter = new ArrayAdapter<SpinnerItemObject>(this, android.R.layout.simple_spinner_item);
    	
    	List<SpinnerItemObject> items = ModelFactory.getModel().getSpinnerItems().getSpeciesList();
    	for(SpinnerItemObject item: items)
    	{
    		adapter.add(item);

    	}
		adapter.sort(new Comparator<SpinnerItemObject>() {

			@Override
			public int compare(SpinnerItemObject arg0, SpinnerItemObject arg1) {
				return arg0.toString().compareTo(arg1.toString());					
			}
			
		});

    	spinner.setAdapter(adapter);
    }   
    
    @Override
    public void onResume() {
    	super.onResume();
    }

	@Override
	public void onDone() {

		EditText weight = (EditText)findViewById(R.id.Weight);
		m_event.setWeight(weight.getText().toString());
		m_trip.save();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		Log.i(TAG, "dismissed: "+dialog.toString());
		ModelFactory.getModel().getSpinnerItems().addSpecies(dialog.toString());
	}
}
