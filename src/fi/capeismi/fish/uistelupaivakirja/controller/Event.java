package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class Event extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	((Button)findViewById(R.id.Done)).setOnClickListener(this);
    	((Button)findViewById(R.id.Cancel)).setOnClickListener(this);
    }

	@Override
	public final void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.Done: 
			onDone(); 
			finish(); 
			break;
		case R.id.Cancel: finish(); break;
		}		
	}
	
	public abstract void onDone();
}
