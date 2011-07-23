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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import fi.capeismi.fish.uistelupaivakirja.model.ExceptionHandler;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory.Model;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;

public class TripExplorer extends ListActivity implements OnClickListener, ExceptionHandler {
	
	private static final String TAG = "TripExplorer";
	
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
			ModelFactory.getModel().getTrips().remove(idx);
			onResume();
			return true;
		}
		
		return super.onContextItemSelected(item);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.server:
    		Log.i(TAG, "show settings");
    		Intent intent = new Intent(this, Settings.class);
    		startActivity(intent);
    		return true;
    	default: return super.onOptionsItemSelected(item);
    	}
    }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripexplorer);
        ModelFactory.getExceptionHandler().setExceptionListener(this);

        Button btn = (Button)findViewById(R.id.BeginTrip);
        btn.setOnClickListener(this);
        
        Button sync = (Button)findViewById(R.id.SyncTrips);
        sync.setOnClickListener(this);       
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Log.i(TAG, "clicked list item"+new Long(id).toString());
		Intent intent = new Intent(this, Trip.class);
		intent.putExtra("listitem", (int)id);
		startActivity(intent);
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	Log.i(TAG, "pause");
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Model.reload();
    	Log.i(TAG, "resume");
        List<Map<String, String> > data = new Vector<Map<String, String> >();             
        List<TripObject> trips = ModelFactory.getModel().getTrips().getList();
        
        for(TripObject trip: trips)
        {
            Map<String, String> ob = new HashMap<String, String>();
            ob.put("Title", trip.getTitle());
            ob.put("Content", trip.getContentText());
        	data.add(ob);
        }

        ListAdapter adapter = new SimpleAdapter(
        		this,         		
        		data, 
        		R.layout.trip_listitem,
        		new String[] {"Title", "Content"},         		
        		new int[] {R.id.tripItemTitle, R.id.tripItemContent});
        
        setListAdapter(adapter);
        registerForContextMenu(getListView());
        Button syncbutton = (Button)findViewById(R.id.SyncTrips);
        syncbutton.setEnabled(canSyncTrips());
        
        SharedPreferences prefs = getSharedPreferences("Uistelu", 0);
        
        ModelFactory.getModel().getUploader().setServerAddr(prefs.getString("ServerAddress", ""));
        ModelFactory.getModel().getUploader().setCredentials(prefs.getString("ServerUsername", ""), 
        		prefs.getString("ServerPassword", ""));
    }

	@Override
	public void onClick(View arg0) {
		Button btn = (Button)arg0;
		Log.i(TAG, "clicked"+btn.getText().toString());
		switch(btn.getId())
		{
		case R.id.BeginTrip: beginTrip(); break;
		case R.id.SyncTrips: syncTrips(); break;
		}	
	}
	
	private boolean canSyncTrips() {
		if(ModelFactory.getModel().getTrips().getList().size() == 0) {
			return false;
		}
		
		for(TripObject trip: ModelFactory.getModel().getTrips().getList()) {
			if(!trip.isEndTime()) {				
				return false;
			}
		}
		
		return true;
	}
	
	private void syncTrips() {	
		final ProgressDialog dialog = ProgressDialog.show(this, "", 
                getResources().getText(R.string.uploading), true);
        //Uploader notifications. Must show something to user
        //and reload UI when database gets cleared.
		ModelFactory.getModel().getUploader().addObserver(new Observer() {
			
			@Override
			public void update(Observable observable, Object data) {
				final Object error = data;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if(error instanceof String)
						{
							dialog.cancel();
							Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
						} else if(error instanceof Boolean)
						{
							Log.i(TAG, "done");
							dialog.dismiss();
							onResume();
						}											
					}					
				});
			}
		});
		

		ModelFactory.getModel().getUploader().upload();
	}
	
	private void beginTrip() {
		Intent intent = new Intent(this, Trip.class);
		TripObject trip = ModelFactory.getModel().getTrips().newTrip();
		trip.save();
		int index = ModelFactory.getModel().getTrips().getList().indexOf(trip);
		if(index < 0)
			return;
				
		intent.putExtra("listitem", index);
		startActivity(intent);
	}
	
	@Override
	public void catchedException(Exception e) {
		Toast.makeText(getApplicationContext(), getString(R.string.exception)+e.toString(), Toast.LENGTH_LONG).show();
		try
		{
			Model.reload();
		} catch(Exception e2) {
			
		}
		Intent intent = new Intent(this, TripExplorer.class);
		startActivity(intent);
	}
      
}