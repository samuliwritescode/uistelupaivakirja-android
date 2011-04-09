package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class TrollingObject implements Storable {
	
	private int m_id = -1;
	private List<Map<String, String>> m_propitems = new ArrayList<Map<String, String>>();
	private Map<String, String> m_keyvalues;
	protected List<TrollingObjectItem> m_items = new ArrayList<TrollingObjectItem>();
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
		m_propitems.clear();
		for(Map<String, String> items : propitems)
		{
			insertPropItem(items);
		}
	}
	
	protected void destroyItem(int id)
	{
		m_items.remove(id);
		m_propitems.remove(id);
	}
	
	protected TrollingObjectItem insertPropItem(Map<String, String> propitem)
	{
		TrollingObjectItem item = newItem(propitem);
		m_items.add(item);
		m_propitems.add(propitem);
		return item;
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
	
	public void destroy()
	{
		m_storer.remove(this);
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
