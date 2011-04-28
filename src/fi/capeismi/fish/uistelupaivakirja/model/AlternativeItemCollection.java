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

public class AlternativeItemCollection extends TrollingObjectCollection {
	public List<AlternativeItemObject> getSpeciesList()
	{
		return getList("species");
	}
	
	public List<AlternativeItemObject> getGetterList()
	{
		return getList("getter");
	}
	
	public List<AlternativeItemObject> getMethodList()
	{
		return getList("method");
	}
	
	public void addSpecies(String specie) throws DuplicateItemException
	{
		add("species", specie);
	}
	
	public void addGetter(String getter) throws DuplicateItemException
	{
		add("getter", getter);
	}
	
	public void addMethod(String method) throws DuplicateItemException
	{
		add("method", method);
	}
	
	private void add(String type, String value) throws DuplicateItemException
	{
		if(!contains(type, value))
		{
			build();
			AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
			item.setValues(type, value);
		}
		else
		{
			throw new DuplicateItemException();
		}
	}
	
	private boolean contains(String type, String value)
	{
		for(TrollingObject item: m_trollingobjects)
		{
			AlternativeItemObject alternative = (AlternativeItemObject)item;
			if(alternative.getType().equalsIgnoreCase(type) &&
					alternative.toString().equalsIgnoreCase(value))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private List<AlternativeItemObject> getList(String type)
	{
		List<AlternativeItemObject> list = new ArrayList<AlternativeItemObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			AlternativeItemObject item = (AlternativeItemObject)object;
			if(item.getType().compareTo(type) == 0)
			{
				list.add(item);
			}
		}
		return list;
	}
}
