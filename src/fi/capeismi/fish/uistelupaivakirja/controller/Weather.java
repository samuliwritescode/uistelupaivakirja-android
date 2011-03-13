package fi.capeismi.fish.uistelupaivakirja.controller;

import android.os.Bundle;
import android.util.Log;

public final class Weather extends Event {
	private static final String TAG = "Weather";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.weather);
    	super.onCreate(savedInstanceState);
    	
    	Log.i(TAG, "new fish");
    }

	@Override
	public void onDone() {
		// TODO Auto-generated method stub
		
	}
}
