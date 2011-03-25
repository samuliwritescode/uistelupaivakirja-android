package fi.capeismi.fish.uistelupaivakirja.model;

import android.util.Log;

public abstract class AbstractBuildTarget implements BuildTarget {
	@Override
	public void newObject(int id) {
		Log.i("target", "id: "+new Integer(id).toString());
		
	}

	@Override
	public void endObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newKeyValue(String key, String value) {
		Log.i("target", key+"="+value);
	}

	@Override
	public void newProperty() {
		Log.i("target", "newProperty");
		
	}

	@Override
	public void endProperty() {
		// TODO Auto-generated method stub
		
	}
}
