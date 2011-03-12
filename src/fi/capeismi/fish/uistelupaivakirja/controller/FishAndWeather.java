package fi.capeismi.fish.uistelupaivakirja.controller;

import android.os.Bundle;
import android.util.Log;

public class FishAndWeather extends Event {
	private static final String TAG = "FishnWeather";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.fishnweather);
    	Log.i(TAG, "new fish&weather");
    }
}
