package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;

class XMLSender {
	private XMLStorage _storageTrip;
	private Observable _observable;
	private RestfulTransfer transferclient;
	
	public XMLSender(XMLStorage storage) {
		this.transferclient = new RestfulTransfer();
		this._storageTrip = storage;
		_observable = new Observable() {
			@Override
			public void notifyObservers(Object arg)
			{
				setChanged();
				super.notifyObservers(arg);
			}
			
			@Override
			public void notifyObservers()
			{
				setChanged();
				super.notifyObservers();
			}
		};					
	}
	
	public void setCredentials(String username, String password) {
		this.transferclient.setCredentials(username, password);
	}
	
	public void setServerAddr(String addr) {
		this.transferclient.setServerAddr(addr);
	}
	
	public void upload() {
		new HttpUploader().execute();
	}
	
	public void addObserver(Observer o) {
		_observable.addObserver(o);
	}

	private void error(String error) {
		_observable.notifyObservers(error);
	}
	
    private class HttpUploader extends AsyncTask<Void, Void, Boolean>
    {

		@Override
		protected Boolean doInBackground(Void... params) {
			try
			{
				transferclient.doPost("trip", new File(_storageTrip.getFullPath()));
				android.util.Log.e("http", "OK");
				return Boolean.TRUE;

			}catch(Exception e)
			{
				//e.printStackTrace();
				error(e.toString());
				return Boolean.FALSE;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result)
		{
			_observable.notifyObservers(result);
		}
    	
    }
}
