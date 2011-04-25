package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import android.os.Bundle;

public final class Fish extends Event
{
	private FishCounterPart m_fishImpl = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.fish);
    	super.onCreate(savedInstanceState);

		setMembers(EventItem.EType.eFish);
				
		m_fishImpl = new FishCounterPart(getEvent(), getTrip(), new PrivateConduit());
		//readCommonFields();
    }
    
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	m_fishImpl.readFishFields();
    	readCommonFields();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
		m_fishImpl.writeFishFields();
		getTrip().save();
    }
}
