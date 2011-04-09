package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import android.os.Bundle;

public final class Fish extends Event
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fish);
    	super.onCreate(savedInstanceState);

		setMembers(EventItem.EType.eFish);
				
		setupFishFields();
		readFishFields();
		readCommonFields();
    }
    
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	readFishFields();
    	readCommonFields();
    }

	@Override
	public void onDone() {
		writeFishFields();
		getTrip().save();
	}
}
