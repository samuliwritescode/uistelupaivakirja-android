package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBuilder implements BuildTarget, Builder {
	
	protected List<ObjectCollection> m_collections = new ArrayList<ObjectCollection>();
	protected Map<String, String> m_keyvalues = new HashMap<String, String>();
	protected List<Map<String, String>> m_proplist = new ArrayList<Map<String, String>>();
	private Map<String, String> m_pointer = null;
	protected int m_id = -1;
	protected TrollingObject m_object = null;
	protected Storer m_storer = null;
	
	@Override
	public void newObject(int id) {
		m_id = id;
		m_pointer = m_keyvalues;
	}

	@Override
	public void endObject() {
		build();
	}

	@Override
	public void newKeyValue(String key, String value) {
		if(m_pointer.containsKey(key))
		{
			String old = m_pointer.get(key);
			old += value;
			m_pointer.put(key, old);
		}else
		{
			m_pointer.put(key, value);
		}
	}

	@Override
	public void newProperty() {
		Map<String, String> hashmap = new HashMap<String, String>();
		m_pointer = hashmap;
		m_proplist.add(hashmap);
	}

	@Override
	public void endProperty() {
		m_pointer = m_keyvalues;
	}
	
	@Override
	public void build() {
		m_object.setStorer(m_storer);
		m_object.setId(m_id);
		m_object.setKeyValues(m_keyvalues);
		m_object.setPropItems(m_proplist);
		for(ObjectCollection collection: m_collections)
		{
			collection.onObjectCreate(m_object);
		}
		m_keyvalues = new HashMap<String, String>();
		m_proplist = new ArrayList<Map<String, String>>();
		m_id = -1;
		m_pointer = null;
	}
	
	@Override
	public void addListener(ObjectCollection collection) {
		m_collections.add(collection);		
	}
	
	@Override
	public void setStorer(Storer storer)
	{
		m_storer = storer;
	}
}
