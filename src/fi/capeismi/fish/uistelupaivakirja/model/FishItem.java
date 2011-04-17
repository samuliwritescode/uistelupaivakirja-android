package fi.capeismi.fish.uistelupaivakirja.model;

public interface FishItem {
	public void setTotalDepth(String value);
	public void setTrollingSpeed(String value);
	public void setLineWeight(String value);
	public void setReleaseWidth(String value);
	public void setSpecies(String value);
	public void setGetter(String value);
	public void setMethod(String value);
	public void setWeight(String value);
	public void setLength(String value);
	public void setSpotDepth(String value);
	public void setIsUndersize(boolean bIs);
	public void setIsCatchNReleased(boolean bIs);
	public void setGroupAmount(String amount);
	public void setIsGroup(boolean bIs);
	public void setLure(LureObject lure);
	
	public String getTotalDepth();
	public String getTrollingSpeed();
	public String getLineWeight();
	public String getReleaseWidth();
	public String getSpecies();
	public String getGetter();
	public String getMethod();
	public String getWeight();
	public String getLength();
	public String getSpotDepth();
	public boolean getIsGroup();
	public boolean getIsUndersize();
	public boolean getIsCatchNReleased();
	public String getGroupAmount();
	public LureObject getLure();
}
