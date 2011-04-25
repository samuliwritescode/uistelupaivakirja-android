package fi.capeismi.fish.uistelupaivakirja.controller;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.GPSInfo;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.NoGpsFixException;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public abstract class Event extends Activity implements OnClickListener{
	
	private EventItem m_event = null;
	private TripObject m_trip = null;
	
	private static String TAG = "Event";
	
	protected class PrivateConduit
	{
		public Activity getActivity()
		{
			return Event.this;
		}
		
		public void setCheckboxListener(int id, OnCheckedChangeListener listener)
	    {
	    	CheckBox check = (CheckBox)findViewById(id);
	    	check.setOnCheckedChangeListener(listener);
	    }
	    
	    public void setEditText(int id, String text)
	    {
	    	TextView edit = (TextView)findViewById(id);
	    	edit.setText(text);
	    }
	    
	    public String getEditText(int id)
	    {
	    	TextView edit = (TextView)findViewById(id);
	    	return edit.getText().toString();
	    }
	    
	    public void setEnabled(int id, boolean enable)
	    {
	    	findViewById(id).setEnabled(enable);
	    }
	    
	    public void setButtonListener(int id, OnClickListener listener)
	    {
	    	Button button = (Button)findViewById(id);
	    	button.setOnClickListener(listener);
	    }
	    
	    public void setSeekbarListener(int id, OnSeekBarChangeListener listener)
	    {
	    	SeekBar seekbar = (SeekBar)findViewById(id);
	    	seekbar.setOnSeekBarChangeListener(listener);
	    }
	    
	    public void setProgress(int id, int progress)
	    {
	    	SeekBar seekbar = (SeekBar)findViewById(id);
	    	seekbar.setProgress(progress);
	    }
	    
	    public int getProgress(int id)
	    {
	    	SeekBar seekbar = (SeekBar)findViewById(id);
	    	return seekbar.getProgress();
	    }
	    
	    public void setChecked(int id, boolean checked)
	    {
	    	CheckBox cb = (CheckBox)findViewById(id);
	    	cb.setChecked(checked);
	    }
	    
	    public boolean getChecked(int id)
	    {
	    	CheckBox cb = (CheckBox)findViewById(id);
	    	return cb.isChecked();
	    }
	    
	    public Object getSelectedItem(int id)
	    {
	    	Spinner spinner = (Spinner)findViewById(id);
	    	return spinner.getSelectedItem();
	    }
	    
	    
	    
	    public <T extends Object> void setSpinners(int viewID, List<T> items, Comparator<Object> comparator)
	    {
	    	Spinner spinner = (Spinner)findViewById(viewID);
	    	ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(Event.this, android.R.layout.simple_spinner_item);
	    	for(Object item: items)
	    	{	
	    		adapter.add(item);
	    	}
	    	if(comparator != null)
	    		adapter.sort(comparator);
			spinner.setAdapter(adapter);
	    }
	    
	    @SuppressWarnings("unchecked")
		public void setSpinnerDefaultValue(int viewID, String defaultValue)
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
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	((Button)findViewById(R.id.Done)).setOnClickListener(this);
    	
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
    	m_trip = ModelFactory.getModel().getTrips().getList().get(eventindex);

		if(!extras.containsKey("event"))
    	{
    		Log.i(TAG, "no previous event. create new one");
        	
        	m_event = m_trip.newEvent(type);
        	getEvent().setPrefs(getSharedPreferences("Uistelu", 0));
    		getEvent().setTime(new Date());
    		GPSInfo gpsinfo = ModelFactory.getGpsInfo();
    		try {
    			getEvent().setCoordinatesLat(new DecimalFormat("#0.00000").format(gpsinfo.getCurrentLat()));
    			getEvent().setCoordinatesLon( new DecimalFormat("#0.00000").format(gpsinfo.getCurrentLon()));
    			getEvent().setTrollingSpeed(new DecimalFormat("#0.0").format(gpsinfo.getCurrentSpeed()));    			    			
    		} catch (NoGpsFixException e) {			
    			
    		}
    		getEvent().setupDefaultValues();
    		intent.putExtra("event", m_trip.getEvents().indexOf(m_event));
    	}
		else
		{
			index = extras.getInt("event");
	    	m_event = m_trip.getEvents().get(index); 
	    	getEvent().setPrefs(getSharedPreferences("Uistelu", 0));
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
    
	@Override
	public final void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.Done: 
			finish(); 
			break;
		}		
	}
}
