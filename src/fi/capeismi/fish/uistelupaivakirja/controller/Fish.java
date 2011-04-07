package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.Comparator;
import java.util.List;

import fi.capeismi.fish.uistelupaivakirja.model.DuplicateItemException;
import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
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

public final class Fish extends Event
{
	private static final String TAG = "Fish";
	private EventItem m_event = null;
	private TripObject m_trip = null;
	private Comparator<Object> m_comparator = null;
	
	private class AlternativeHandler implements OnDismissListener, OnClickListener{		
		private int m_source;
		public AlternativeHandler(int source)
		{
			m_source = source;
		}
		
		@Override
		public void onClick(View arg0) {
			AddSpinnerItem adder = new AddSpinnerItem(Fish.this);
			adder.setOnDismissListener(this);
			adder.show();			
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			try
			{
				switch(m_source)
				{
				case R.id.Species:
					ModelFactory.getModel().getSpinnerItems().addSpecies(dialog.toString());
					setSpinners(R.id.Species, 
							ModelFactory.getModel().getSpinnerItems().getSpeciesList(), 
							dialog.toString());
					break;
				case R.id.Getter:
					ModelFactory.getModel().getSpinnerItems().addGetter(dialog.toString());
					setSpinners(R.id.Getter, 
							ModelFactory.getModel().getSpinnerItems().getGetterList(), 
							dialog.toString());
					break;
				case R.id.Method:
					ModelFactory.getModel().getSpinnerItems().addMethod(dialog.toString());
					setSpinners(R.id.Method, 
							ModelFactory.getModel().getSpinnerItems().getMethodList(), 
							dialog.toString());
					break;
				}
			}catch(DuplicateItemException e)
			{
				//No worry
			}
			
		}
		
	}
	
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
		
		m_comparator = new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString());					
			}			
		};		

    	m_trip = ModelFactory.getModel().getTrips().getList().get(tripindex);
    	m_event = m_trip.getEvents().get(index);    	
    
    	((Button)findViewById(R.id.AddSpecies)).setOnClickListener(new AlternativeHandler(R.id.Species));
    	((Button)findViewById(R.id.AddGetter)).setOnClickListener(new AlternativeHandler(R.id.Getter));
    	((Button)findViewById(R.id.AddMethod)).setOnClickListener(new AlternativeHandler(R.id.Method));
    	setSpinners(R.id.Species, 
    			ModelFactory.getModel().getSpinnerItems().getSpeciesList(), 
    			m_event.getSpecies());
    	
    	setSpinners(R.id.Getter, 
    			ModelFactory.getModel().getSpinnerItems().getGetterList(), 
    			m_event.getGetter());
    	
    	setSpinners(R.id.Method, 
    			ModelFactory.getModel().getSpinnerItems().getMethodList(), 
    			m_event.getMethod());
    	
    	readFishFields();
    }
    
    private <T extends Object> void setSpinners(int viewID, List<T> items, String defaultValue)
    {
    	Spinner spinner = (Spinner)findViewById(viewID);
    	ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item);
    	for(Object item: items)
    	{	
    		adapter.add(item);
    	}
		adapter.sort(m_comparator);
		spinner.setAdapter(adapter);
    	setSpinnerDefaultValue(viewID, defaultValue);
    }
    
    @SuppressWarnings("unchecked")
	private void setSpinnerDefaultValue(int viewID, String defaultValue)
    {
    	Spinner spinner = (Spinner)findViewById(viewID);
    	ArrayAdapter<Object> adapter = (ArrayAdapter<Object>)spinner.getAdapter();
    	Object object =  null;
    	for(int loop=0; loop < adapter.getCount(); loop++)
    	{
    		Object current = adapter.getItem(loop);
    		if(current.toString().equals(defaultValue))
    		{
    			object = current;
    		}
    	}
    	
    	if(object != null)
    	{
    		spinner.setSelection(adapter.getPosition(object));
    	}
    }
    
    private void readFishFields()
    {
    	((EditText)findViewById(R.id.Length)).setText(m_event.getLength());
    	((EditText)findViewById(R.id.Weight)).setText(m_event.getWeight());
    	((EditText)findViewById(R.id.SpotDepth)).setText(m_event.getSpotDepth());
    	((EditText)findViewById(R.id.Depth)).setText(m_event.getTotalDepth());
    	((EditText)findViewById(R.id.TrollingSpeed)).setText(m_event.getTrollingSpeed());
    	((EditText)findViewById(R.id.LureWeight)).setText(m_event.getLineWeight());
    	((EditText)findViewById(R.id.ReleaseWidth)).setText(m_event.getReleaseWidth());
    	
    }
    
    private void writeFishFields()
    {
		m_event.setLength(((EditText)findViewById(R.id.Length)).getText().toString());
		m_event.setWeight(((EditText)findViewById(R.id.Weight)).getText().toString());
		m_event.setSpotDepth(((EditText)findViewById(R.id.SpotDepth)).getText().toString());
		m_event.setTotalDepth(((EditText)findViewById(R.id.Depth)).getText().toString());
		m_event.setTrollingSpeed(((EditText)findViewById(R.id.TrollingSpeed)).getText().toString());
		m_event.setLineWeight(((EditText)findViewById(R.id.LureWeight)).getText().toString());
		m_event.setReleaseWidth(((EditText)findViewById(R.id.ReleaseWidth)).getText().toString());
		
		Object species = ((Spinner)findViewById(R.id.Species)).getSelectedItem();
		Object getter = ((Spinner)findViewById(R.id.Getter)).getSelectedItem();
		Object method = ((Spinner)findViewById(R.id.Method)).getSelectedItem();
		if(species != null)	m_event.setSpecies(species.toString());
		if(getter != null)	m_event.setGetter(getter.toString());
		if(method != null)	m_event.setMethod(method.toString());		
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	readFishFields();
    }

	@Override
	public void onDone() {
		writeFishFields();
		m_trip.save();
	}
}
