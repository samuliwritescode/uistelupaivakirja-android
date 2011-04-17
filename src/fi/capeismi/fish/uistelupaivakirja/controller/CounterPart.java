package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;

public abstract class CounterPart {
	protected EventItem m_event = null;
	protected TripObject m_trip = null;
	protected Event.PrivateConduit m_activity = null;
	
	public CounterPart(EventItem eventitem, TripObject trip, Event.PrivateConduit activity)
	{
		m_event = eventitem;
		m_trip = trip;
		m_activity = activity;
	}
}
