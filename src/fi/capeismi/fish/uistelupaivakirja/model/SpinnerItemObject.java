package fi.capeismi.fish.uistelupaivakirja.model;

final public class SpinnerItemObject extends TrollingObject {
	public void setValues(String type, String name)
	{
		set("type", type);
		set("value", name);
		save();
	}
	
	public SpinnerItemObject()
	{
		super();
	}
	
	public String toString()
	{
		return get("value");
	}
	
	public String getType()
	{
		return get("type");
	}
	
}
