package fi.capeismi.fish.uistelupaivakirja.controller;

import android.util.Log;
import android.os.Bundle;

public class Fish extends Event 
{
	private static final String TAG = "Fish";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fish);
    	super.onCreate(savedInstanceState);
    	
    	Log.i(TAG, "new fish");
    }

	@Override
	public void onDone() {
		// TODO Auto-generated method stub
		
	}
}
