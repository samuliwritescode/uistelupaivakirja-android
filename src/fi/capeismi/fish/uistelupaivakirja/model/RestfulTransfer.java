package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.BufferedReader;
import java.io.File;
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
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

class RestfulTransfer {
	private String serveraddr;
	private String username;
	private String password;
	
	public void setServerAddr(String serveraddr) {
		this.serveraddr = serveraddr;
	}
	
	public void setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void doPost(String doc, File file) throws URISyntaxException, ClientProtocolException, IOException, RestfulException {
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpGet login = new HttpGet(new URI(serveraddr+"/login?j_username="+username+"&j_password="+password));
		HttpResponse responselogin = httpclient.execute(login);
		if(responselogin.getStatusLine().getStatusCode() != 200)
		{
			throw new RestfulException("cant login");			
		}
		
		responselogin.getEntity().consumeContent();
		android.util.Log.e("http", "login successful");
		
		HttpPost post = new HttpPost(new URI(serveraddr+"/"+doc));
		FileEntity entity = new FileEntity(file, "text/xml");		
		post.setEntity(entity);
		
		HttpResponse responsesend = httpclient.execute(post);
		if(responsesend.getStatusLine().getStatusCode() != 200)
		{
			printErrorResponse(responsesend.getEntity().getContent());
		}
	}
	
	public InputStream doGet(String doc) throws URISyntaxException, RestfulException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpGet login = new HttpGet(new URI(serveraddr+"/login?j_username="+username+"&j_password="+password));
		HttpResponse responselogin = httpclient.execute(login);
		if(responselogin.getStatusLine().getStatusCode() != 200)
		{
			throw new RestfulException("cant login");			
		}
		
		responselogin.getEntity().consumeContent();
		android.util.Log.e("http", "login successful");
		
		HttpGet getDoc = new HttpGet(new URI(serveraddr+"/"+doc));
		
		HttpResponse responsesend = httpclient.execute(getDoc);
		if(responsesend.getStatusLine().getStatusCode() != 200)
		{
			printErrorResponse(responsesend.getEntity().getContent());
		}
		
		return responsesend.getEntity().getContent();
	}
	
	private void printErrorResponse(InputStream is) throws IOException, RestfulException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine()) != null) {
			android.util.Log.e("http", line);
			buf.append(line);
		}
		is.close();
		throw new RestfulException(buf.toString());
	}
	
	public static class RestfulException extends Exception {
		private String message;
		public RestfulException(String message) {
			this.message = message;
		}
		
		@Override
		public String toString() {
			return this.message;
		}
	}
}
