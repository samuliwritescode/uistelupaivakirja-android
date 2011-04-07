package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public class LureCollection extends TrollingObjectCollection {

	public List<LureObject> getList()
	{
		List<LureObject> list = new ArrayList<LureObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			list.add((LureObject)object);
		}
		return list;
	}
	
	public LureObject getId(int id)
	{
		for(TrollingObject object: m_trollingobjects)
		{
			if(object.getId() == id)
			{
				return (LureObject)object;
			}
		}
		
		return null;
	}
}
