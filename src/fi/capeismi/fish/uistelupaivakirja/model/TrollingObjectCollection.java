package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public abstract class TrollingObjectCollection implements ObjectCollection {
	protected List<TrollingObject> m_trollingobjects = new ArrayList<TrollingObject>();
	private Builder m_builder;
	
	@Override
	public void onObjectCreate(TrollingObject object)
	{
		m_trollingobjects.add(object);
	}
	
	@Override
	public void setBuilder(Builder builder)
	{
		m_builder = builder;
	}
	
	protected void build()
	{
		m_builder.build();		
	}

}
