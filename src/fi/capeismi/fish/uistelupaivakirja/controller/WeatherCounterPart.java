package fi.capeismi.fish.uistelupaivakirja.controller;


import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;

public class WeatherCounterPart extends CounterPart  {

	
	public WeatherCounterPart(EventItem eventitem, TripObject trip,
			Event.PrivateConduit activity) {
		super(eventitem, trip, activity);
		setupWeatherFields();
		readWeatherFields();
	}

	protected void setupWeatherFields()
    {
    	m_activity.setSpinners(R.id.PressureChange,
    			EventItem.getPressureChanges(),
    			null);  
    	m_activity.setSpinners(R.id.WindDirection,
    			EventItem.getWindDirections(),
    			null);
    	
    	OnSeekBarChangeListener seekbarlistener = new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekbar, int value, boolean arg2) {
				switch(seekbar.getId())
				{
				case R.id.WindSpeed:
					m_activity.setEditText(R.id.WindSpeedLabel, 
							EventItem.getHumanReadableWindspeed(value));
					break;
				case R.id.Clouds:
					m_activity.setEditText(R.id.CloudLabel, 
							EventItem.getHumanReadableClouds(value));
					break;
				case R.id.Rain: 
					m_activity.setEditText(R.id.RainLabel, 
							EventItem.getHumanReadableRain(value));
					break;
				case R.id.Pressure: 
					if(value == 0)
					{
						m_activity.setEditText(R.id.PressureLabel, 
								"n/a");
					}
					else
					{
						m_activity.setEditText(R.id.PressureLabel, 
								new Integer(940+value).toString());
					}
					break;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}	
    	};
    	
    	m_activity.setSeekbarListener(R.id.WindSpeed, seekbarlistener);
    	m_activity.setSeekbarListener(R.id.Clouds, seekbarlistener);
    	m_activity.setSeekbarListener(R.id.Rain, seekbarlistener);
    	m_activity.setSeekbarListener(R.id.Pressure, seekbarlistener);
    }
	
	public void readWeatherFields()
    {
    	m_activity.setEditText(R.id.AirTemp, m_event.getAirTemp());
    	m_activity.setEditText(R.id.WaterTemp, m_event.getWaterTemp());
    	
    	m_activity.setProgress(R.id.WindSpeed, m_event.getWindSpeed());
    	m_activity.setProgress(R.id.Clouds, m_event.getClouds());
    	m_activity.setProgress(R.id.Rain, m_event.getRain());
    	m_activity.setProgress(R.id.Pressure, m_event.getPressure());
    	    	
    	m_activity.setSpinnerDefaultValue(R.id.WindDirection, EventItem.getWindDirections().get(m_event.getWindDirection()));
    	m_activity.setSpinnerDefaultValue(R.id.PressureChange, EventItem.getPressureChanges().get(m_event.getPressureChange()));
    }
	    

    
    public void writeWeatherFields()
    {
		m_event.setWaterTemp(m_activity.getEditText(R.id.WaterTemp));
		m_event.setAirTemp(m_activity.getEditText(R.id.AirTemp));
		
		m_event.setWindSpeed(m_activity.getProgress(R.id.WindSpeed));
		m_event.setClouds(m_activity.getProgress(R.id.Clouds));
		m_event.setRain(m_activity.getProgress(R.id.Rain));
		m_event.setPressure(m_activity.getProgress(R.id.Pressure));
		
		Object windDirection = m_activity.getSelectedItem(R.id.WindDirection);
		Object pressureChange =m_activity.getSelectedItem(R.id.PressureChange);
		m_event.setWindDirection(EventItem.getWindDirections().indexOf(windDirection));
		m_event.setPressureChange(EventItem.getPressureChanges().indexOf(pressureChange));
    }

}
