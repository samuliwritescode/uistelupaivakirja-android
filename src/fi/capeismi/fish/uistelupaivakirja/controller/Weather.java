package fi.capeismi.fish.uistelupaivakirja.controller;

import android.os.Bundle;
import android.util.Log;

public class Weather extends Event {
	private static final String TAG = "Weather";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.weather);
    	Log.i(TAG, "new fish");
    }
}
