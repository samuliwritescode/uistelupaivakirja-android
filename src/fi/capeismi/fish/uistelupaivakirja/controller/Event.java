package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fi.capeismi.fish.uistelupaivakirja.model.DuplicateItemException;
import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.LureObject;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import fi.capeismi.fish.uistelupaivakirja.model.TrollingObjectItem;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public abstract class Event extends Activity implements OnClickListener{
	
	private EventItem m_event = null;
	private TripObject m_trip = null;
	private Comparator<Object> m_comparator = null;
	private static String TAG = "Event";
	
	private class AlternativeHandler implements OnDismissListener, OnClickListener{		
		private int m_source;
		public AlternativeHandler(int source)
		{
			m_source = source;
		}
		
		@Override
		public void onClick(View arg0) {
			AddSpinnerItem adder = new AddSpinnerItem(Event.this);
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
							ModelFactory.getModel().getSpinnerItems().getSpeciesList());
					break;
				case R.id.Getter:
					ModelFactory.getModel().getSpinnerItems().addGetter(dialog.toString());
					setSpinners(R.id.Getter, 
							ModelFactory.getModel().getSpinnerItems().getGetterList());
					break;
				case R.id.Method:
					ModelFactory.getModel().getSpinnerItems().addMethod(dialog.toString());
					setSpinners(R.id.Method, 
							ModelFactory.getModel().getSpinnerItems().getMethodList());					
					break;
				}
				
				setSpinnerDefaultValue(m_source, dialog.toString());
			}catch(DuplicateItemException e)
			{
				//No worry
			}
			
		}
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	((Button)findViewById(R.id.Done)).setOnClickListener(this);
    	((Button)findViewById(R.id.Cancel)).setOnClickListener(this);
    }
    
    protected TripObject getTrip()
    {
    	return m_trip;
    }
    
    protected EventItem getEvent()
    {
    	return m_event;
    }
    
    protected void setMembers(EventItem.EType type)
    {
    	final Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	int index = -1;
    	int eventindex = extras.getInt("tripindex");

		if(!extras.containsKey("event"))
    	{
    		Log.i(TAG, "no previous event. create new one");
    		TrollingObjectItem item = ModelFactory.getModel().getTrips().getList().get(eventindex).newEvent(type);
    		index = ModelFactory.getModel().getTrips().getList().get(eventindex).getEvents().indexOf(item);
        	m_trip = ModelFactory.getModel().getTrips().getList().get(eventindex);
        	m_event = m_trip.getEvents().get(index); 
    		getEvent().setTime(new Date());
    	}
		else
		{
			index = extras.getInt("event");
	    	m_trip = ModelFactory.getModel().getTrips().getList().get(eventindex);
	    	m_event = m_trip.getEvents().get(index); 
		}
		
    }

    protected void setupFishFields()
    {   	
		m_comparator = new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString());					
			}			
		};
		
       	((CheckBox)findViewById(R.id.GroupCB)).setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				findViewById(R.id.GroupSize).setEnabled(arg1);
				findViewById(R.id.Length).setEnabled(!arg1);
			}
    		
    	});
    
    	((Button)findViewById(R.id.AddSpecies)).setOnClickListener(new AlternativeHandler(R.id.Species));
    	((Button)findViewById(R.id.AddGetter)).setOnClickListener(new AlternativeHandler(R.id.Getter));
    	((Button)findViewById(R.id.AddMethod)).setOnClickListener(new AlternativeHandler(R.id.Method));
    	setSpinners(R.id.Species, 
    			ModelFactory.getModel().getSpinnerItems().getSpeciesList());
    	
    	setSpinners(R.id.Getter, 
    			ModelFactory.getModel().getSpinnerItems().getGetterList());
    	
    	setSpinners(R.id.Method, 
    			ModelFactory.getModel().getSpinnerItems().getMethodList());
    	
    	setSpinners(R.id.Lure,
    			ModelFactory.getModel().getLures().getList());   
    	    	
    }
    
    protected void setupWeatherFields()
    {
    	setSpinners(R.id.PressureChange,
    			EventItem.getPressureChanges());  
    	setSpinners(R.id.WindDirection,
    			EventItem.getWindDirections());
    	
    	OnSeekBarChangeListener seekbarlistener = new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekbar, int value, boolean arg2) {
				TextView textview = null;
				switch(seekbar.getId())
				{
				case R.id.WindSpeed:
					textview = (TextView)findViewById(R.id.WindSpeedLabel);
					textview.setText(EventItem.getHumanReadableWindspeed(value));
					break;
				case R.id.Clouds: 
					textview = (TextView)findViewById(R.id.CloudLabel); 
					textview.setText(EventItem.getHumanReadableClouds(value));
					break;
				case R.id.Rain: 
					textview = (TextView)findViewById(R.id.RainLabel); 
					textview.setText(EventItem.getHumanReadableRain(value));
					break;
				case R.id.Pressure: 
					textview = (TextView)findViewById(R.id.PressureLabel);
					if(value == 0)
						textview.setText("n/a");
					else
						textview.setText(new Integer(940+value).toString());
					break;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}	
    	};
    	
    	((SeekBar)findViewById(R.id.WindSpeed)).setOnSeekBarChangeListener(seekbarlistener);
    	((SeekBar)findViewById(R.id.Clouds)).setOnSeekBarChangeListener(seekbarlistener);
    	((SeekBar)findViewById(R.id.Rain)).setOnSeekBarChangeListener(seekbarlistener);
    	((SeekBar)findViewById(R.id.Pressure)).setOnSeekBarChangeListener(seekbarlistener);
    }
    
    private <T extends Object> void setSpinners(int viewID, List<T> items)
    {
    	Spinner spinner = (Spinner)findViewById(viewID);
    	ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item);
    	for(Object item: items)
    	{	
    		adapter.add(item);
    	}
    	if(m_comparator != null)
    		adapter.sort(m_comparator);
		spinner.setAdapter(adapter);
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
    
    protected void readCommonFields()
    {
    	String timecoord = "";
    	timecoord += m_event.getTime();
    	timecoord += " ";
    	timecoord += m_event.getCoordinatesLat();
    	timecoord += " ";
    	timecoord += m_event.getCoordinatesLon();
    	((TextView)findViewById(R.id.TimeAndCoordinates)).setText(timecoord);
    }
    
    protected void readFishFields()
    {
    	((EditText)findViewById(R.id.Length)).setText(m_event.getLength());
    	((EditText)findViewById(R.id.Weight)).setText(m_event.getWeight());
    	((EditText)findViewById(R.id.SpotDepth)).setText(m_event.getSpotDepth());
    	((EditText)findViewById(R.id.Depth)).setText(m_event.getTotalDepth());
    	((EditText)findViewById(R.id.TrollingSpeed)).setText(m_event.getTrollingSpeed());
    	((EditText)findViewById(R.id.LureWeight)).setText(m_event.getLineWeight());
    	((EditText)findViewById(R.id.ReleaseWidth)).setText(m_event.getReleaseWidth());
    	
    	((EditText)findViewById(R.id.GroupSize)).setText(m_event.getGroupAmount());
    	
    	setSpinnerDefaultValue(R.id.Getter, m_event.getGetter());
    	setSpinnerDefaultValue(R.id.Method, m_event.getMethod());
    	setSpinnerDefaultValue(R.id.Species, m_event.getSpecies());
    	
    	((CheckBox)findViewById(R.id.GroupCB)).setChecked(m_event.getIsGroup());
    	((CheckBox)findViewById(R.id.CrCB)).setChecked(m_event.getIsCatchNReleased());
    	((CheckBox)findViewById(R.id.UnderSizeCB)).setChecked(m_event.getIsUndersize());
    	
    	if(m_event.getLure() != null)
    		setSpinnerDefaultValue(R.id.Lure, m_event.getLure().toString());
    }
    
    protected void readWeatherFields()
    {
    	((EditText)findViewById(R.id.AirTemp)).setText(m_event.getAirTemp());
    	((EditText)findViewById(R.id.WaterTemp)).setText(m_event.getWaterTemp());
    	
    	((SeekBar)findViewById(R.id.WindSpeed)).setProgress(m_event.getWindSpeed());
    	((SeekBar)findViewById(R.id.Clouds)).setProgress(m_event.getClouds());
    	((SeekBar)findViewById(R.id.Rain)).setProgress(m_event.getRain());
    	((SeekBar)findViewById(R.id.Pressure)).setProgress(m_event.getPressure());
    	    	
    	setSpinnerDefaultValue(R.id.WindDirection, EventItem.getWindDirections().get(m_event.getWindDirection()));
    	setSpinnerDefaultValue(R.id.PressureChange, EventItem.getPressureChanges().get(m_event.getPressureChange()));
    }
    
    protected void writeFishFields()
    {
		m_event.setLength(((EditText)findViewById(R.id.Length)).getText().toString());
		m_event.setWeight(((EditText)findViewById(R.id.Weight)).getText().toString());
		m_event.setSpotDepth(((EditText)findViewById(R.id.SpotDepth)).getText().toString());
		m_event.setTotalDepth(((EditText)findViewById(R.id.Depth)).getText().toString());
		m_event.setTrollingSpeed(((EditText)findViewById(R.id.TrollingSpeed)).getText().toString());
		m_event.setLineWeight(((EditText)findViewById(R.id.LureWeight)).getText().toString());
		m_event.setReleaseWidth(((EditText)findViewById(R.id.ReleaseWidth)).getText().toString());
		m_event.setGroupAmount(((EditText)findViewById(R.id.GroupSize)).getText().toString());
    	m_event.setIsGroup(((CheckBox)findViewById(R.id.GroupCB)).isChecked());
    	m_event.setIsUndersize(((CheckBox)findViewById(R.id.UnderSizeCB)).isChecked());
    	m_event.setIsCatchNReleased(((CheckBox)findViewById(R.id.CrCB)).isChecked());    	    
		
		Object species = ((Spinner)findViewById(R.id.Species)).getSelectedItem();
		Object getter = ((Spinner)findViewById(R.id.Getter)).getSelectedItem();
		Object method = ((Spinner)findViewById(R.id.Method)).getSelectedItem();
		Object lure = ((Spinner)findViewById(R.id.Lure)).getSelectedItem();
		if(species != null)	m_event.setSpecies(species.toString());
		if(getter != null)	m_event.setGetter(getter.toString());
		if(method != null)	m_event.setMethod(method.toString());
		if(lure != null)	m_event.setLure((LureObject) lure);
    }
    
    protected void writeWeatherFields()
    {
		m_event.setWaterTemp(((EditText)findViewById(R.id.WaterTemp)).getText().toString());
		m_event.setAirTemp(((EditText)findViewById(R.id.AirTemp)).getText().toString());
		
		m_event.setWindSpeed(((SeekBar)findViewById(R.id.WindSpeed)).getProgress());
		m_event.setClouds(((SeekBar)findViewById(R.id.Clouds)).getProgress());
		m_event.setRain(((SeekBar)findViewById(R.id.Rain)).getProgress());
		m_event.setPressure(((SeekBar)findViewById(R.id.Pressure)).getProgress());
		
		Object windDirection = ((Spinner)findViewById(R.id.WindDirection)).getSelectedItem();
		Object pressureChange = ((Spinner)findViewById(R.id.PressureChange)).getSelectedItem();
		m_event.setWindDirection(EventItem.getWindDirections().indexOf(windDirection));
		m_event.setPressureChange(EventItem.getPressureChanges().indexOf(pressureChange));
    }

	@Override
	public final void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.Done: 
			onDone(); 
			finish(); 
			break;
		case R.id.Cancel: finish(); break;
		}		
	}
	
	public abstract void onDone();
}
