package fi.capeismi.fish.uistelupaivakirja.controller;

import android.util.Log;
import android.os.Bundle;

public class Fish extends Event 
{
	private static final String TAG = "Fish";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.fish);
    	Log.i(TAG, "new fish");
    }

}
