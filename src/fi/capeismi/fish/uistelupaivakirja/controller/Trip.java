package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Trip extends Activity implements OnClickListener {
	
	private static final String TAG = "Trip";
	
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
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
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
			startActivity(intent);
		}
	}
}
