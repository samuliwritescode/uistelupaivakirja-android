package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public class SpinnerItemCollection extends TrollingObjectCollection {
	public List<SpinnerItemObject> getSpeciesList()
	{
		return getList("species");
	}
	
	public List<SpinnerItemObject> getGetterList()
	{
		return getList("getter");
	}
	
	public List<SpinnerItemObject> getMethodList()
	{
		return getList("method");
	}
	
	public void addSpecies(String specie)
	{
		build();
		SpinnerItemObject item = (SpinnerItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("species", specie);
	}
	
	public void addGetter(String getter)
	{
		build();
		SpinnerItemObject item = (SpinnerItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("getter", getter);
	}
	
	public void addMethod(String method)
	{
		build();
		SpinnerItemObject item = (SpinnerItemObject)m_trollingobjects.get(m_trollingobjects.size()-1);
		item.setValues("method", method);
	}
	
	private List<SpinnerItemObject> getList(String type)
	{
		List<SpinnerItemObject> list = new ArrayList<SpinnerItemObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			SpinnerItemObject item = (SpinnerItemObject)object;
			if(item.getType().compareTo(type) == 0)
			{
				list.add(item);
			}
		}
		return list;
	}
}
