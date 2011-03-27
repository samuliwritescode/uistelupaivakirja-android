package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.trip);
    	((Button)findViewById(R.id.NewFish)).setOnClickListener(this);
    	((Button)findViewById(R.id.NewWeather)).setOnClickListener(this); 
    	((Button)findViewById(R.id.FishnWeather)).setOnClickListener(this);
    	Log.i(TAG, "create trip");
    	Spinner spinner = (Spinner)findViewById(R.id.PlaceList);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
    	adapter.add(new String("item1"));
    	adapter.add(new String("item2"));
    	adapter.add(new String("item3"));
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    		{    			
    			Log.i(TAG, "item selected"+position);
    		}
    		
    		@Override
    		public void onNothingSelected(AdapterView<?> parent)
    		{
    			Log.i(TAG, "item not selected");
    		}
		});
    	final Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	int index = extras.getInt("listitem");
    	m_trip = ModelFactory.getModel().getTrips().getList().get(index);
    	TextView title = (TextView)findViewById(R.id.Title);
    	title.setText(m_trip.toString());
    	    	
        
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Log.i(TAG, "clicked event item"+new Long(id).toString());
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
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.i(TAG, "draw fish list");
    	List<Map<String, String> > data = new Vector<Map<String, String> >();
        List<EventItem> events = m_trip.getEvents();
        for(EventItem event: events)
        {
            Map<String, String> ob = new HashMap<String, String>();
            ob.put("Reissu", event.toString());
        	data.add(ob);
        }
        
        ListAdapter listadapter = new SimpleAdapter(
        		this,         		
        		data, 
        		R.layout.trip_listitem,
        		new String[] {"Reissu"},         		
        		new int[] {R.id.tripItem});
        
        setListAdapter(listadapter);
    }
   
	@Override
	public void onClick(View v) 
	{
		Intent intent = null;
	
		switch(v.getId())
		{
		case R.id.NewFish: intent = new Intent(this, Fish.class); break;
		case R.id.NewWeather: intent = new Intent(this, Weather.class); break;
		case R.id.FishnWeather: intent = new Intent(this, FishAndWeather.class); break;
		}
		
		if(intent != null)
		{
			intent.putExtra("tripindex", ModelFactory.getModel().getTrips().getList().indexOf(m_trip));
			startActivity(intent);
		}
	}
}
