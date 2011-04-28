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

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import  android.view.View.OnClickListener;

public class AddSpinnerItem extends Dialog implements OnClickListener {

	private String m_value = "";
	
	public AddSpinnerItem(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addspinneritem);
		Button okBtn = (Button)findViewById(R.id.OkButton);
		okBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		m_value = ((EditText)findViewById(R.id.EditText)).getText().toString();
		if(m_value.length() == 0)
			cancel();
		dismiss();		
	}
	
	public String toString()
	{
		if(m_value == null)
			return "";
		return m_value;
	}
}
