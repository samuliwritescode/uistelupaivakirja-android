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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBuilder implements BuildTarget, Builder {
	
	protected List<ObjectCollection> m_collections = new ArrayList<ObjectCollection>();
	protected Map<String, String> m_keyvalues = new HashMap<String, String>();
	protected List<Map<String, String>> m_proplist = new ArrayList<Map<String, String>>();
	private Map<String, String> m_pointer = null;
	protected int m_id = -1;
	protected TrollingObject m_object = null;
	protected Storer m_storer = null;
	
	@Override
	public void newObject(int id) {
		m_id = id;
		m_pointer = m_keyvalues;
	}

	@Override
	public void endObject() {
		build();
	}

	@Override
	public void newKeyValue(String key, String value) {
		if(m_pointer.containsKey(key))
		{
			String old = m_pointer.get(key);
			old += value;
			m_pointer.put(key, old);
		}else
		{
			m_pointer.put(key, value);
		}
	}

	@Override
	public void newProperty() {
		Map<String, String> hashmap = new HashMap<String, String>();
		m_pointer = hashmap;
		m_proplist.add(hashmap);
	}

	@Override
	public void endProperty() {
		m_pointer = m_keyvalues;
	}
	
	@Override
	public void build() {
		m_object.setStorer(m_storer);
		m_object.setId(m_id);
		m_object.setKeyValues(m_keyvalues);
		m_object.setPropItems(m_proplist);
		for(ObjectCollection collection: m_collections)
		{
			collection.onObjectCreate(m_object);
		}
		m_keyvalues = new HashMap<String, String>();
		m_proplist = new ArrayList<Map<String, String>>();
		m_id = -1;
		m_pointer = null;
	}
	
	@Override
	public void addListener(ObjectCollection collection) {
		m_collections.add(collection);		
	}
	
	@Override
	public void setStorer(Storer storer)
	{
		m_storer = storer;
	}
}
