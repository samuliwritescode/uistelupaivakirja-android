package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.List;
import java.util.Map;

abstract class TrollingObject implements Storable {
	
	private int m_id = -1;
	List<Map<String, String>> m_propitems;
	Map<String, String> m_keyvalues;
	Storer m_storer;
	
	@Override
	public int getId()
	{
		return m_id;
	}
	
	@Override
	public void setId(int id)
	{
		m_id = id;
	}
	
	@Override
	public Map<String, String> getKeyValues()
	{
		return m_keyvalues;
	}
	
	@Override
	public List<Map<String, String>> getPropItems()
	{
		return m_propitems;
	}
	
	public void setKeyValues(Map<String, String> keyvalues)
	{
		m_keyvalues = keyvalues;
	}
	
	public void setPropItems(List<Map<String, String>> propitems)
	{
		m_propitems = propitems;
	}
	
	@Override
	public void setStorer(Storer storer)
	{
		m_storer = storer;
	}
	
	public void save()
	{
		m_storer.save(this);
	}
}
