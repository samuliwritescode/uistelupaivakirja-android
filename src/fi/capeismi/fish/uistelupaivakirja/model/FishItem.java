/*
 * Copyright 2011 Samuli Penttilä
 *
 * This file is part of Uistelupäiväkirja.
 * 
 * Uistelupäiväkirja is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Uistelupäiväkirja is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Uistelupäiväkirja. If not, see http://www.gnu.org/licenses/.
 */

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
