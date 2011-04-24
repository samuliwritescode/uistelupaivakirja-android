package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherInfo {
	private String m_city;
	private EventItem m_event;
	
	public WeatherInfo(String city, EventItem event) throws IllegalStateException, 
	IOException, URISyntaxException, 
	ParserConfigurationException, SAXException
	{
		m_city = city;
		m_event = event;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(new URI("http://www.google.fi/ig/api?weather="+m_city));
		HttpResponse response = httpclient.execute(get);
		InputStream instream = response.getEntity().getContent();
		parseXML(instream);
		instream.close();
	}
	
	/*
	 * Have to use DOM since weather service can send šŠŒ within data.
	 * SAX don't like this.
	 */
	private void parseXML(InputStream in) throws ParserConfigurationException, 
	IOException, SAXException
	{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document dom = builder.parse(in);
		Element root = dom.getDocumentElement();
		NodeList conditions = root.getElementsByTagName("current_conditions");
		if(conditions.getLength() == 1)
		{
			conditions = conditions.item(0).getChildNodes();
		}
		
		for(int loop=0; loop < conditions.getLength(); loop++)
		{
			Element condition = (Element)conditions.item(loop);
			String tagname = condition.getNodeName();
			String value = condition.getAttribute("data");
			if(tagname.equalsIgnoreCase("temp_c"))
			{
				m_event.setAirTemp(value);
			}
		}
	}
	
}
