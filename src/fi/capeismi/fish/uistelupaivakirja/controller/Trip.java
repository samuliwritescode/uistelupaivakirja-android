package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Trip extends Activity implements OnClickListener {
	
	private static final String TAG = "Trip";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.trip);
    	((Button)findViewById(R.id.NewFish)).setOnClickListener(this);
    	((Button)findViewById(R.id.NewWeather)).setOnClickListener(this); 
    	((Button)findViewById(R.id.FishnWeather)).setOnClickListener(this);
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
