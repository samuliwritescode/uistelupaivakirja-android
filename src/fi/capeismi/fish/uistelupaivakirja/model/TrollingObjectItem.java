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

import java.util.HashMap;
import java.util.Map;

public abstract class TrollingObjectItem {
	private Map<String, String> m_props;
	
	public TrollingObjectItem(Map<String, String> props)
	{
		m_props = props;
	}
	
	public Map<String, String> getProps()
	{
		return new HashMap<String, String>(m_props);
	}
	
	protected String get(String key)
	{
		if(!m_props.containsKey(key))
			return "";
		
		return m_props.get(key);
	}
	
	protected void set(String key, String value)
	{
		m_props.put(key, value);
	}
}
