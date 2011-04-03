package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceCollection extends TrollingObjectCollection {
	
	public List<PlaceObject> getList()
	{
		List<PlaceObject> list = new ArrayList<PlaceObject>();
		for(TrollingObject object: m_trollingobjects)
		{
			list.add((PlaceObject)object);
		}
		return list;
	}
	
	public PlaceObject getId(int id)
	{
		for(TrollingObject object: m_trollingobjects)
		{
			if(object.getId() == id)
			{
				return (PlaceObject)object;
			}
		}
		
		return null;
	}

}
