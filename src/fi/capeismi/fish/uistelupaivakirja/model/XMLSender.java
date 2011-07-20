package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class XMLSender {
	private XMLSenderCallback _callback;
	private String _filename;
	
	public XMLSender(XMLSenderCallback callback, String filename) {
		this._callback = callback;
		this._filename = filename;
		new HttpUploader().execute();
	}
	
	private boolean doPost() throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String serveraddr = "http://anteex.capeismi.fi:8080/uistelu/";
		
		HttpGet login = new HttpGet(new URI(serveraddr+"login?j_username=cape&j_password=kek"));
		HttpResponse responselogin = httpclient.execute(login);
		if(responselogin.getStatusLine().getStatusCode() != 200)
			return false;
		
		responselogin.getEntity().consumeContent();
		android.util.Log.e("http", "login successful");
		
		HttpPost post = new HttpPost(new URI(serveraddr+"/trips"));
		
		StringEntity entity = new StringEntity(fromFile());
		post.setEntity(entity);
		
		HttpResponse responsesend = httpclient.execute(post);
		if(responsesend.getStatusLine().getStatusCode() != 200)
		{
			printResponse(responsesend.getEntity().getContent());
			return false;
		}
		
		printResponse(responsesend.getEntity().getContent());
		
		return true;
	}
	
	private String fromFile() throws IOException {
		StringBuffer buf = new StringBuffer();
		File file = new File(this._filename);
		InputStream is = new FileInputStream(file);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while((line = bf.readLine()) != null)
		{
			buf.append(line);
		}
		is.close();
		
		return buf.toString();
	}
	
	private void printResponse(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while((line=br.readLine()) != null) {
			android.util.Log.e("http", line);	
		}
		is.close();
	}
	
	public interface XMLSenderCallback {
		void sendDone(String filename);
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
				return Boolean.FALSE;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result)
		{
			if(result == Boolean.TRUE)
			{
				_callback.sendDone(_filename);
			}
		}
    	
    }
}
