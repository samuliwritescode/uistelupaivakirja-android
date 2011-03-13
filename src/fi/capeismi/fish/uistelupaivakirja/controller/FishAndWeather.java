package fi.capeismi.fish.uistelupaivakirja.controller;

import android.os.Bundle;
import android.util.Log;

public class FishAndWeather extends Event {
	private static final String TAG = "FishnWeather";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fishnweather);
    	super.onCreate(savedInstanceState);
    	Log.i(TAG, "new fish&weather");
    }

	@Override
	public void onDone() {
		// TODO Auto-generated method stub
		
	}
}
