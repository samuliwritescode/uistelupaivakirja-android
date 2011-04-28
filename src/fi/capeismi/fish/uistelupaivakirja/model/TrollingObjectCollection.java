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
import java.util.List;

public abstract class TrollingObjectCollection implements ObjectCollection {
	protected List<TrollingObject> m_trollingobjects = new ArrayList<TrollingObject>();
	private Builder m_builder;
	
	@Override
	public void onObjectCreate(TrollingObject object)
	{
		m_trollingobjects.add(object);
	}
	
	@Override
	public void setBuilder(Builder builder)
	{
		m_builder = builder;
	}
	
	protected void build()
	{
		m_builder.build();		
	}

}
