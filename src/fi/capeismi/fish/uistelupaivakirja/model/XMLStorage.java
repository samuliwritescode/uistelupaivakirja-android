package fi.capeismi.fish.uistelupaivakirja.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int id) {
		// TODO Auto-generated method stub
		
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
