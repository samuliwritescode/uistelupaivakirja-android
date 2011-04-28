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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * Have to use DOM since weather service can send öäå within data.
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
			else if(tagname.equalsIgnoreCase("condition"))
			{
				int clouds = parseCondition(value);
				if(clouds > 0)
				{
					m_event.setClouds(clouds);
				}
			}
			else if(tagname.equalsIgnoreCase("wind_condition"))
			{
				int wind = parseWindSpeed(value);
				int direction = parseWindDirection(value);
				if(wind > 0)
				{
					m_event.setWindSpeed(wind);
				}
				
				if(direction > 0)
				{
					m_event.setWindDirection(direction);
				}
			}
		}
	}
	
	private int parseWindSpeed(String condition)
	{
		Pattern regex = Pattern.compile("Tuuli: (\\w{1}) nopeudella (\\d+) m/s");
		Matcher match = regex.matcher(condition);
		if(match.find())
		{
			int speed = new Integer(match.group(2)).intValue();
			if(between(speed, -1, 1))
				return 1;
			else if(between(speed, 1, 2))
				return 2;
			else if(between(speed, 2, 3))
				return 3;
			else if(between(speed, 3, 5))
				return 4;
			else if(between(speed, 5, 8))
				return 5;
			else if(between(speed, 8, 11))
				return 6;
			else if(between(speed, 12, 14))
				return 7;
			else if(between(speed, 14, 17))
				return 8;
			else if(between(speed, 17, 21))
				return 9;
			else if(between(speed, 21, 1000))
				return 10;
		}

		return 0;
	}
	
	private boolean between(int val, int start, int end)
	{
		if(val > start && val <= end)
			return true;
		
		return false;
	}
	
	private int parseWindDirection(String condition)
	{
		Pattern regex = Pattern.compile("Tuuli: (\\w{1})");
		Matcher match = regex.matcher(condition);
		if(match.find())
		{
			String wind = match.group(1);
			if(wind.equalsIgnoreCase("E"))
				return 1;
			else if(wind.equalsIgnoreCase("L"))
				return 3;
			else if(wind.equalsIgnoreCase("P"))
				return 5;
			else if(wind.equalsIgnoreCase("I"))
				return 7;
		}

		return 0;
	}
	
	private int parseCondition(String condition)
	{
		if(condition.matches("^Selke\\S{2}$"))
		{
			return 1;
		} else if(condition.matches("^Enimm.{1}kseen aurinkoista$"))
		{
			return 3;
		}
		return 0;
	}

	
}
