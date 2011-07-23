package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;

public class XMLSender {
	private XMLStorage _storage;
	private Observable _observable;
	
	public XMLSender(XMLStorage storage) {
		this._storage = storage;
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
	
	public void upload() {
		new HttpUploader().execute();
	}
	
	public void addObserver(Observer o) {
		_observable.addObserver(o);
	}
	
	private boolean doPost() throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String serveraddr = "http://anteex.capeismi.fi:8080/uistelu/";
		
		HttpGet login = new HttpGet(new URI(serveraddr+"login?j_username=cape&j_password=kek"));
		HttpResponse responselogin = httpclient.execute(login);
		if(responselogin.getStatusLine().getStatusCode() != 200)
		{
			error("cant login");
			return false;
		}
		
		responselogin.getEntity().consumeContent();
		android.util.Log.e("http", "login successful");
		
		HttpPost post = new HttpPost(new URI(serveraddr+"/trips"));
		
		File file = new File(this._storage.getFullPath());
		FileEntity entity = new FileEntity(file, "text/xml");
		post.setEntity(entity);
		
		HttpResponse responsesend = httpclient.execute(post);
		if(responsesend.getStatusLine().getStatusCode() != 200)
		{
			printErrorResponse(responsesend.getEntity().getContent());
			return false;
		}
		
		return true;
	}
	
	private void error(String error) {
		_observable.notifyObservers(error);
	}
	
	private void printErrorResponse(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine()) != null) {
			android.util.Log.e("http", line);
			buf.append(line);
		}
		is.close();
		error(buf.toString());
	}
	
    private class HttpUploader extends AsyncTask<Void, Void, Boolean>
    {

		@Override
		protected Boolean doInBackground(Void... params) {
			try
			{
				if(doPost())
				{
					android.util.Log.e("http", "OK");
					return Boolean.TRUE;
				}
				else
				{
					android.util.Log.e("http", "failure");
					return Boolean.FALSE;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
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
