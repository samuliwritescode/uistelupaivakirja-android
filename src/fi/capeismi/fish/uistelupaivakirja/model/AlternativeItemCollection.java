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
	
	public void addSpecies(String specie)
	{
		if(!contains("species", specie))
		{
			build();
			AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
			item.setValues("species", specie);
		}
	}
	
	public void addGetter(String getter)
	{
		if(!contains("getter", getter))
		{
			build();
			AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
			item.setValues("getter", getter);
		}
	}
	
	public void addMethod(String method)
	{
		if(!contains("method", method))
		{
			build();
			AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
			item.setValues("method", method);
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
