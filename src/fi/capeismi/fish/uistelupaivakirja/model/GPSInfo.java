package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSInfo implements LocationListener {

	private Date m_lastFix = null;
	private Location m_latest = null;
	private Location m_secondLatest = null;
	private final double TORADIANS = Math.PI / 180.0;
	@Override
	public void onLocationChanged(Location location) {
		m_secondLatest = m_latest;
		m_latest = location;		
		m_lastFix = new Date();
	}

	@Override
	public void onProviderDisabled(String provider) {

		
	}

	@Override
	public void onProviderEnabled(String provider) {

		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		
	}
	
	private boolean validFix()
	{
		if(m_lastFix == null)
		{
			return false;
		}
		
		if(m_lastFix.before(new Date(System.currentTimeMillis()-1000*60*5)))
		{
			return false;
		}
		
		return true;
	}
	
	public double getCurrentLon() throws NoGpsFixException
	{
		if(!validFix())
			throw new NoGpsFixException();
		
		return m_latest.getLongitude(); 
	}
	
	public double getCurrentLat() throws NoGpsFixException
	{
		if(!validFix())
			throw new NoGpsFixException();
		
		return m_latest.getLatitude(); 
	}
	
	public double getCurrentSpeed() throws NoGpsFixException
	{
		if(!validFix())
			throw new NoGpsFixException();
		
		if(m_secondLatest == null)
			throw new NoGpsFixException();
		
        double lon1 = m_secondLatest.getLongitude() * TORADIANS;
        double lat1 = m_secondLatest.getLatitude() * TORADIANS;
        double lon2 = m_latest.getLongitude() * TORADIANS;
        double lat2 = m_latest.getLatitude() * TORADIANS;

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        
        double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2.0), 2);
        double distance = 6372.797 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        long time = (m_latest.getTime() - m_secondLatest.getTime())/1000;
        return distance/time*3600;            
	}

}
