package fi.capeismi.fish.uistelupaivakirja.controller;

import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.WeatherInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public final class Weather extends Event {
	private WeatherCounterPart m_weatherImpl = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.weather);
    	super.onCreate(savedInstanceState);
    	
    	setMembers(EventItem.EType.eWeather);
				
    	m_weatherImpl = new WeatherCounterPart(getEvent(), getTrip(), new PrivateConduit());

		readCommonFields();
		
		Button autofetch = ((Button)findViewById(R.id.AutoFetch));
		autofetch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new WeatherDownloader().execute(getTrip().getPlace().getCity());
			}			
		});
    }
    
    private class WeatherDownloader extends AsyncTask<String, Void, WeatherInfo>
    {

		@Override
		protected WeatherInfo doInBackground(String... city) {
			try
			{
				WeatherInfo weather = new WeatherInfo(city[0], getEvent());
				return weather;
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(WeatherInfo weather)
		{
			Log.i("async", "onpostexec");
			m_weatherImpl.readWeatherFields();
		}
    	
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
    	m_weatherImpl.readWeatherFields();
    	readCommonFields();
    }
    
	@Override
	public void onDone() {
		m_weatherImpl.writeWeatherFields();
		getTrip().save();		
	}
}
