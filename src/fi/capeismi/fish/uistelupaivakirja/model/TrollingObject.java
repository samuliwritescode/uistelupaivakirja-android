package fi.capeismi.fish.uistelupaivakirja.model;

abstract class TrollingObject implements Storable {
	@Override
	public int getId()
	{
		return 0;
	}
	
	@Override
	public void setId(int id)
	{

	}
	
	@Override
	public Object getKeyValues()
	{
		return null;	
	}
	
	@Override
	public Object getPropItems()
	{
		return null;
	}
	
	@Override
	public void setStorer(Storer storer)
	{
		
	}
}
