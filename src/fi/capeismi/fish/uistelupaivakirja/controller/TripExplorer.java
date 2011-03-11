package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TripExplorer extends Activity implements OnClickListener {
	
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
        Button btn = (Button)findViewById(R.id.BeginTrip);
        btn.setOnClickListener(this);
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

	@Override
	public void onClick(View arg0) {
		Button btn = (Button)arg0;
		Log.i(TAG, "clicked"+btn.getText().toString());
		
		Intent intent = new Intent(this, Trip.class);
		startActivity(intent);
	}
      
}