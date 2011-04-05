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
		build();
		AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("species", specie);
	}
	
	public void addGetter(String getter)
	{
		build();
		AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("getter", getter);
	}
	
	public void addMethod(String method)
	{
		build();
		AlternativeItemObject item = (AlternativeItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("method", method);
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
