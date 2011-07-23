package fi.capeismi.fish.uistelupaivakirja.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;


public class Settings extends Activity {
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
    
    @Override
    protected void onPause()  {
    	super.onPause();
    	SharedPreferences prefs = getSharedPreferences("Uistelu", 0);
    	EditText server = (EditText)findViewById(R.id.ServiceAddr);
    	EditText user = (EditText)findViewById(R.id.ServiceUser);
    	EditText password = (EditText)findViewById(R.id.ServicePass);
    	
    	Editor editor = prefs.edit();
    	editor.putString("ServerAddress", server.getText().toString());
    	editor.putString("ServerUsername", user.getText().toString());
    	editor.putString("ServerPassword", password.getText().toString());
    	editor.commit();
    }
    
    @Override
    protected void onResume() {
    	SharedPreferences prefs = getSharedPreferences("Uistelu", 0);
    	EditText server = (EditText)findViewById(R.id.ServiceAddr);
    	EditText user = (EditText)findViewById(R.id.ServiceUser);
    	EditText password = (EditText)findViewById(R.id.ServicePass);
    	server.setText(prefs.getString("ServerAddress", "http://fish.capeismi.fi:8080/uistelu/"));
    	user.setText(prefs.getString("ServerUsername", ""));
    	password.setText(prefs.getString("ServerPassword", ""));
    	
    	super.onResume();
    }
}
