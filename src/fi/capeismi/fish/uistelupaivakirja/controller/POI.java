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

package fi.capeismi.fish.uistelupaivakirja.controller;

import android.os.Bundle;
import android.widget.EditText;

public final class POI extends Event {
	private EditText m_description = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.poi);
    	super.onCreate(savedInstanceState);
    	m_description =  (EditText)findViewById(R.id.Description);
    }
    
    
    @Override
    public void onResume() {
    	super.onResume();

    	readCommonFields();
    	m_description.setText(getEvent().getDescription());
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	getEvent().setDescription(m_description.getText().toString());
		getTrip().save();
    }
}
