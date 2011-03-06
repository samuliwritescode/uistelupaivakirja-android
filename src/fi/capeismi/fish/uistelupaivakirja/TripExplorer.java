package fi.capeismi.fish.uistelupaivakirja;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

public class TripExplorer extends Activity {
	
	private static final String TAG = "TripExplorer";
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
		  super.onCreateContextMenu(menu, v, menuInfo);
		  Log.i(TAG, "context menu");
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.i(TAG, "options menu");
    	return super.onCreateOptionsMenu(menu);
    }
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripexplorer);
        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
        
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
        	Log.i(TAG, Environment.getExternalStorageDirectory().toString());
        }
        else {
        	Log.e(TAG, state);        	
        }
        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
      
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	Log.i(TAG, "pause");
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.i(TAG, "resume");
    }
      
}