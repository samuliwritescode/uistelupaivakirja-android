package fi.capeismi.fish.uistelupaivakirja;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class Trip extends Activity {
	
	private static final String TAG = "Trip";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.trip);
    	Log.i(TAG, "creat trip");
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	Log.i(TAG, "pause trip");
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.i(TAG, "resume trip");
    }

}
