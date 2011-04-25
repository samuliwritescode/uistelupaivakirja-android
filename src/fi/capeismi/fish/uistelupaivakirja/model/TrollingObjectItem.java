package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.HashMap;
import java.util.Map;

public abstract class TrollingObjectItem {
	private Map<String, String> m_props;
	
	public TrollingObjectItem(Map<String, String> props)
	{
		m_props = props;
	}
	
	public Map<String, String> getProps()
	{
		return new HashMap<String, String>(m_props);
	}
	
	protected String get(String key)
	{
		if(!m_props.containsKey(key))
			return "";
		
		return m_props.get(key);
	}
	
	protected void set(String key, String value)
	{
		m_props.put(key, value);
	}
}
