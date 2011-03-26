package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripCollection;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;

public class TripExplorer extends ListActivity implements OnClickListener {
	
	private static final String TAG = "TripExplorer";
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, 
			View v,
            ContextMenuInfo menuInfo) {
		  super.onCreateContextMenu(menu, v, menuInfo);
		  Log.i(TAG, "context menu"+((AdapterView.AdapterContextMenuInfo)menuInfo).position);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.i(TAG, "options menu");
    	return super.onCreateOptionsMenu(menu);
    }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripexplorer);
        
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
        	Log.i(TAG, Environment.getExternalStorageDirectory().toString());
        }
        else {
        	Log.e(TAG, state);        	
        }

        Button btn = (Button)findViewById(R.id.BeginTrip);
        btn.setOnClickListener(this);
        
        ModelFactory.getModel();
        List<Map<String, String> > data = new Vector<Map<String, String> >();

        TripCollection collection = ModelFactory.getModel().getTrips();
        List<TripObject> trips = collection.getList();
        for(TripObject trip: trips)
        {
            Map<String, String> ob = new HashMap<String, String>();
            ob.put("Reissu", trip.toString());
        	data.add(ob);
        }

        ListAdapter adapter = new SimpleAdapter(
        		this,         		
        		data, 
        		R.layout.trip_listitem,
        		new String[] {"Reissu"},         		
        		new int[] {R.id.tripItem});
        
        setListAdapter(adapter);
        registerForContextMenu(getListView());
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Log.i(TAG, "clicked list item"+new Long(id).toString());
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	Log.i(TAG, "pause");
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.i(TAG, "resume");
    	ModelFactory.getModel().getLures();
    }

	@Override
	public void onClick(View arg0) {
		Button btn = (Button)arg0;
		Log.i(TAG, "clicked"+btn.getText().toString());
		
		Intent intent = new Intent(this, Trip.class);
		startActivity(intent);
	}
      
}