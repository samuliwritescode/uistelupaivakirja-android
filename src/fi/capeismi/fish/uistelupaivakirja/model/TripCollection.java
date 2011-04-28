/*
 * Copyright 2011 Samuli Penttilä
 *
 * This file is part of Uistelupäiväkirja.
 * 
 * Uistelupäiväkirja is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Uistelupäiväkirja is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Uistelupäiväkirja. If not, see http://www.gnu.org/licenses/.
 */

package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripCollection extends TrollingObjectCollection {

	
	public List<TripObject> getList()
	{
		List<TripObject> list = new ArrayList<TripObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			list.add((TripObject)object);
		}
		return list;
	}
	
	public void remove(int id)
	{
		TrollingObject obj = m_trollingobjects.get(id);
		m_trollingobjects.remove(id);
		obj.destroy();
	}

	public TripObject newTrip() {
		build();
		TripObject trip = (TripObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		trip.setDate(new Date());
		trip.setStartTime(new Date());	
		return trip;
	}
}
