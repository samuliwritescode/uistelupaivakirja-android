package fi.capeismi.fish.uistelupaivakirja.model;

import android.util.Log;

final class XMLStorage implements Storage {

	private static String TAG = "XMLStorage";
	@Override
	public void save(Storable storable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public void addListener(BuildTarget target)
	{
		
	}
	
	public void load(String file)
	{
		Log.i(TAG, "loading "+file);
	}

}
