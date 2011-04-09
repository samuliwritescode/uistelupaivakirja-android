package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.os.Environment;

final class XMLStorage implements Storage {

	private static String TAG = "XMLStorage";
	private String m_filename;
	private List<BuildTarget> m_listeners = new ArrayList<BuildTarget>();
	
	@Override
	public void save(Storable storable) {
		save(storable.getId(), storable);
	}
	
	private void save(int id, Storable storable) {
		Log.i(TAG, "save storable "+id);
		File file = new File(Environment.getExternalStorageDirectory().toString()+"/uistelu/"+m_filename+".xml");
		try {
			StringBuilder xml = new StringBuilder();
			int maxId = 1;
			if(file.exists())
			{
				InputStream files = new FileInputStream(file);
				BufferedReader is = new BufferedReader(new InputStreamReader(files));				
				String line;
				
				while((line = is.readLine()) != null)
				{
					if(line.indexOf("<TrollingObjects MaxId") != -1)
					{
						maxId = new Integer(line.substring(line.indexOf("\"")+1, line.lastIndexOf("\""))).intValue();
					}
					else if(line.indexOf("</TrollingObjects") != -1)
					{
						
					}
					else
					{ 				
						xml.append(line+"\n");
					}
				}
				files.close();
			}
			
			String xmlstr = xml.toString();
			String searchString = "<TrollingObject type=\""+m_filename+"\" id=\""+id+"\">";
			String searchStringEnd = "</TrollingObject>";
			int index = xmlstr.indexOf(searchString);
			int lastIndex = xmlstr.indexOf(searchStringEnd, index)+searchStringEnd.length();
			
			if(index == -1)
			{
				index = 0;
				lastIndex = 0;
				if(storable != null)
				{
					maxId++;
					storable.setId(maxId);
				}
			}
						
			BufferedWriter bos = new BufferedWriter(new FileWriter(file));
			
			bos.write("<TrollingObjects MaxId=\""+maxId+"\">\n");
			bos.write(xmlstr.substring(0, index));
			if(storable != null)
			{
				bos.write(serializeStorable(storable));
			}
			bos.write(xmlstr.substring(lastIndex));
			bos.write("</TrollingObjects>");
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String serializeStorable(Storable storable) {
		StringBuilder str = new StringBuilder();
		str.append("<TrollingObject type=\""+m_filename+"\" id=\""+storable.getId()+"\">\n");
		Map<String, String> keyvalues = storable.getKeyValues();
		for(Entry<String, String> entry: keyvalues.entrySet())
		{
			str.append("<"+entry.getKey()+">");
			str.append(entry.getValue());
			str.append("</"+entry.getKey()+">\n");
		}
		
		List<Map<String, String>> props = storable.getPropItems();
		if(props.size() > 0)
		{
			str.append("<PropertyList>\n");
			for(Map<String, String> prop: props)
			{
				str.append("<PropertyListItem>\n");
				for(Entry<String, String> entry: prop.entrySet())
				{
					str.append("<"+entry.getKey()+">");
					str.append(entry.getValue());
					str.append("</"+entry.getKey()+">\n");
				}
				str.append("</PropertyListItem>\n");
			}
			str.append("</PropertyList>\n");
		}
		str.append("</TrollingObject>\n");
		return str.toString();
	}
	

	@Override
	public void remove(int id) {
		save(id, null);		
	}
	
	public void addListener(BuildTarget target)
	{
		m_listeners.add(target);
	}
	
	public void load()
	{
		load(m_filename);
	}
	
	public void load(String filename)
	{
		m_filename = filename;
		File file = new File(Environment.getExternalStorageDirectory().toString()+"/uistelu/"+filename+".xml");

		Log.i(TAG, "loading "+file.toString());
		Log.i(TAG, "file found:"+new Boolean(file.exists()).toString());
		try
		{
			InputStream is = new FileInputStream(file);
			InputSource source = new InputSource(is);
			Log.i(TAG, "filesize: "+is.available());
			SAXParserFactory saxfactory = SAXParserFactory.newInstance();
			SAXParser saxparser = saxfactory.newSAXParser();
			XMLReader reader = saxparser.getXMLReader();
			reader.setContentHandler(new TrollingReader());
			reader.parse(source);
			
			is.close();
		} catch (Exception e)
		{
			Log.i(TAG, "Caught Exception "+e.toString());
		}
		
	}
	
	private class TrollingReader extends DefaultHandler {
		private String m_tag;
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			for(BuildTarget target: m_listeners)
			{
				if(localName.equalsIgnoreCase("TrollingObject"))
				{
					Integer id = new Integer(attributes.getValue("", "id"));
					String type = attributes.getValue("", "type");
					if(!type.equalsIgnoreCase(m_filename))
						throw new SAXException("type mismatch");
					
					target.newObject(id.intValue());
				} else if(localName.equalsIgnoreCase("TrollingObjects"))
				{
				} else if(localName.equalsIgnoreCase("PropertyListItem"))
				{
					target.newProperty();
				} else if(localName.equalsIgnoreCase("PropertyList"))
				{
				} else
				{
					m_tag = localName;
				}
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) {
			for(BuildTarget target: m_listeners)
			{
				if(localName.equalsIgnoreCase("TrollingObject"))
				{
					target.endObject();
				} else if(localName.equalsIgnoreCase("TrollingObjects"))
				{
					
				} else if(localName.equalsIgnoreCase("PropertyListItem"))
				{
					target.endProperty();
				} else if(localName.equalsIgnoreCase("PropertyList"))
				{
	
				} else
				{
	
				}
			}
			m_tag = null;
		}
		
		@Override
		public void characters (char[] ch, int start, int length) {
			if(m_tag != null)
			{
				for(BuildTarget target: m_listeners)
					target.newKeyValue(m_tag, new String(ch, start, length));
			}
		}
	}

}
