package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class TrollingObject implements Storable {
	
	private int m_id = -1;
	
	private Map<String, String> m_keyvalues;
	
	private Storer m_storer;
	
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
		return new ArrayList<Map<String, String>>();
	}
	
	public void setKeyValues(Map<String, String> keyvalues)
	{
		m_keyvalues = keyvalues;
	}
	
	public void setPropItems(List<Map<String, String>> propitems)
	{
		
	}
	
	@Override
	public void setStorer(Storer storer)
	{
		m_storer = storer;
	}
	
	public void save()
	{
		try
		{
			m_storer.save(this);
		}catch(Exception e)
		{
			ModelFactory.getExceptionHandler().sendException(e);
		}
	}
	
	public void destroy()
	{
		try
		{
			m_storer.remove(this);
		}catch(Exception e)
		{
			ModelFactory.getExceptionHandler().sendException(e);
		}
	}
	
	protected void set(String key, String value)
	{
		m_keyvalues.put(key, value);
	}
	
	protected String get(String key)
	{
		if(m_keyvalues.containsKey(key))
			return m_keyvalues.get(key);
		else
			return "";
	}
}
