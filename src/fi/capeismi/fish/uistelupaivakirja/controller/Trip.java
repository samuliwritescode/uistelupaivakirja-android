/*
 * Copyright 2011 Samuli Penttilä
 *
 * This file is part of Uistelupäiväkirja.
 * 
 * Uistelupäiväkirja is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Uistelupäiväkirja is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Uistelupäiväkirja. If not, see http://www.gnu.org/licenses/.
 */

package fi.capeismi.fish.uistelupaivakirja.controller;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.GPSInfo;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.NoGpsFixException;
import fi.capeismi.fish.uistelupaivakirja.model.PlaceObject;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class Trip extends ListActivity implements OnClickListener {
	
	private static final String TAG = "Trip";
	private TripObject m_trip = null;
	private LocationManager locationManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.trip);
    	((Button)findViewById(R.id.NewFish)).setOnClickListener(this);
    	((Button)findViewById(R.id.NewWeather)).setOnClickListener(this); 
    	((Button)findViewById(R.id.FishnWeather)).setOnClickListener(this);
    	((Button)findViewById(R.id.EndTrip)).setOnClickListener(this);
    	Log.i(TAG, "create trip");    	

    	final Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	int index = extras.getInt("listitem");
    	m_trip = ModelFactory.getModel().getTrips().getList().get(index);	  		
    	
    	Spinner spinner = (Spinner)findViewById(R.id.PlaceList);
    	ArrayAdapter<PlaceObject> adapter = new ArrayAdapter<PlaceObject>(this, android.R.layout.simple_spinner_item);
    	
    	List<PlaceObject> places = ModelFactory.getModel().getPlaces().getList();
    	for(PlaceObject place: places)
    	{
    		adapter.add(place);
    	}
		adapter.sort(new Comparator<PlaceObject>() {

			@Override
			public int compare(PlaceObject arg0, PlaceObject arg1) {
				return arg0.toString().compareTo(arg1.toString());					
			}
			
		});

    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    		{   
    			PlaceObject obj = (PlaceObject)parent.getItemAtPosition(position);
    			m_trip.setPlace(obj);
    			m_trip.save();
    			
    			Log.i(TAG, "item selected "+obj.toString());
    		}
    		
    		@Override
    		public void onNothingSelected(AdapterView<?> parent)
    		{
    			Log.i(TAG, "item not selected");
    		}
		});
    	
		if(m_trip.getPlace() != null)
		{	
			Log.i(TAG, "existing selection: "+m_trip.getPlace().toString());
			spinner.setSelection(adapter.getPosition(m_trip.getPlace()));
		}
		registerForContextMenu(getListView());
		
		if(!m_trip.isEndTime())
		{
			locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ModelFactory.getGpsInfo());
		}
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, 
			View v,
            ContextMenuInfo menuInfo) {
		  super.onCreateContextMenu(menu, v, menuInfo);
		  Log.i(TAG, "context menu"+((AdapterView.AdapterContextMenuInfo)menuInfo).position);
		  MenuInflater inflater = getMenuInflater();
		  inflater.inflate(R.menu.trip_menu, menu);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.i(TAG, "selected "+item.getItemId());
		switch(item.getItemId())
		{
		case R.id.remove:			
			int idx = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
			Map<String, Object> ob = (Map<String, Object>)getListAdapter().getItem(idx);
			EventItem event = (EventItem)ob.get("Object");
			
			m_trip.destroyEvent(event);
			m_trip.save();
			onResume();
			return true;
		}
		
		return super.onContextItemSelected(item);
	}
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Map<String, Object> ob = (Map<String, Object>)l.getAdapter().getItem(position);
    	EventItem event = (EventItem)ob.get("Object");
    	id = m_trip.getEvents().indexOf(event);
    	Log.i(TAG, "clicked event item"+event.toString());
		Intent intent = null;
		switch(m_trip.getEvents().get((int)id).getType())
		{
		case eFish: intent = new Intent(this, Fish.class); break;
		case eWeather: intent = new Intent(this, Weather.class); break;
		case eFishAndWeather: intent = new Intent(this, FishAndWeather.class); break;
		}
		intent.putExtra("event", (int)id);
		intent.putExtra("tripindex", ModelFactory.getModel().getTrips().getList().indexOf(m_trip));
		startActivity(intent);
    }
    
    private int createEvent(EventItem.EType type)
    {
    	EventItem event = m_trip.newEvent(type);
    	event.setPrefs(getSharedPreferences("Uistelu", 0));
    	event.setTime(new Date());
		GPSInfo gpsinfo = ModelFactory.getGpsInfo();
		try {
			event.setCoordinatesLat(new DecimalFormat("#0.00000").format(gpsinfo.getCurrentLat()));
			event.setCoordinatesLon( new DecimalFormat("#0.00000").format(gpsinfo.getCurrentLon()));
			event.setTrollingSpeed(new DecimalFormat("#0.0").format(gpsinfo.getCurrentSpeed()));    			    			
		} catch (NoGpsFixException e) {			
			
		}
		event.setupDefaultValues();
		m_trip.save();
		return m_trip.getEvents().indexOf(event);
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	TextView title = (TextView)findViewById(R.id.Title);
    	title.setText(m_trip.toString());
    	
    	((Button)findViewById(R.id.EndTrip)).setEnabled(!m_trip.isEndTime());
    	
    	Log.i(TAG, "draw fish list");
    	List<Map<String, Object> > data = new Vector<Map<String, Object> >();
        List<EventItem> events = m_trip.getEvents();
        for(int loop=events.size()-1; loop >= 0; loop--)
        {
        	EventItem event = events.get(loop);
            Map<String, Object> ob = new HashMap<String, Object>();
            ob.put("Object", event);
            ob.put("Title", event.getTime());
            ob.put("Content", event.toString());
            switch(event.getType())
            {
            case eFish: ob.put("Image", R.drawable.fish_event); break;
            case eWeather: ob.put("Image", R.drawable.weather_event); break;
            case eFishAndWeather: ob.put("Image", R.drawable.weatherfish_event); break;
            }
        	data.add(ob);
        }
        
        ListAdapter listadapter = new SimpleAdapter(
        		this,         		
        		data, 
        		R.layout.event_listitem,
        		new String[] {"Title", "Content", "Image"},         		
        		new int[] {R.id.eventItemTitle, R.id.eventItemContent, R.id.ImageViewEvent});        
        
        setListAdapter(listadapter);
    }
    
    private void canCreateEvent(final Intent intent, final EventItem.EType type)
    {
    	if(!m_trip.isEndTime())
    	{
			intent.putExtra("tripindex", ModelFactory.getModel().getTrips().getList().indexOf(m_trip));
			intent.putExtra("event", createEvent(type));
			startActivity(intent);
			return;
    	}
    	
    	AlertDialog.Builder dlg = new AlertDialog.Builder(this);
    	dlg.setMessage(R.string.opentrip).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				m_trip.clearEndTime();		
				m_trip.save();
				if(locationManager == null)
				{
					locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ModelFactory.getGpsInfo());
				}
				intent.putExtra("tripindex", ModelFactory.getModel().getTrips().getList().indexOf(m_trip));
				intent.putExtra("event", createEvent(type));
				startActivity(intent);
			}
		}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Do nothing
				
			}
		}).show();    	
    }
   
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.NewFish: 
			canCreateEvent(new Intent(this, Fish.class), EventItem.EType.eFish);
			break;
		case R.id.NewWeather: 
			canCreateEvent(new Intent(this, Weather.class), EventItem.EType.eWeather);
			break;
		case R.id.FishnWeather:
			canCreateEvent(new Intent(this, FishAndWeather.class), EventItem.EType.eFishAndWeather);
			break;
		case R.id.EndTrip: 
			m_trip.setEndTime(new Date());			
			m_trip.save();
			if(locationManager != null)
			{
				locationManager.removeUpdates(ModelFactory.getGpsInfo());
				locationManager = null;
			}
			finish();
			break;
		}		
	}
}
