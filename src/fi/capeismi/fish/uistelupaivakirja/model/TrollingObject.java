package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class TrollingObject implements Storable {
	
	private int m_id = -1;
	private List<Map<String, String>> m_propitems;
	private Map<String, String> m_keyvalues;
	protected List<TrollingObjectItem> m_items = new ArrayList<TrollingObjectItem>();
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
	
	protected TrollingObjectItem newItem(Map<String, String> props)
	{
		return null;
	}
	
	public void setPropItems(List<Map<String, String>> propitems)
	{
		m_items.clear();
		for(Map<String, String> items : propitems)
		{
			m_items.add(newItem(items));
		}
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
	
	protected void set(String key, String value)
	{
		m_keyvalues.put(key, value);
	}
	
	protected String get(String key)
	{
		return m_keyvalues.get(key);
	}
}
