/*
 * Copyright 2011 Samuli PenttilŠ
 *
 * This file is part of UistelupŠivŠkirja.
 * 
 * UistelupŠivŠkirja is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * UistelupŠivŠkirja is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with UistelupŠivŠkirja. If not, see http://www.gnu.org/licenses/.
 */

package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.Observable;
import java.util.Observer;

public class ModelFactory {
	private static Model instance;
	private static GPSInfo gpsinfo;
	private static ModelExceptionHandler exceptionhandler;
	private ModelFactory() {
		
	}
	
	public static Model getModel() {
		if(instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	public static GPSInfo getGpsInfo()
	{
		if(gpsinfo == null) {
			gpsinfo = new GPSInfo();
		}
		return gpsinfo;
	}
	
	public static ModelExceptionHandler getExceptionHandler()
	{
		if(exceptionhandler == null) {
			exceptionhandler = new ModelExceptionHandler();
		}
		return exceptionhandler;
	}
	
	public static class ModelExceptionHandler {
		private ExceptionHandler m_exceptionhandler = null;
		
		public void setExceptionListener(ExceptionHandler handler)
		{
			m_exceptionhandler = handler;
		}
		
		public void sendException(Exception e)
		{
			if(m_exceptionhandler != null)
			{
				m_exceptionhandler.catchedException(e);
			}
		}
	}
	
	public static class Model {
		private TripCollection m_tripCollection = null;
		private PlaceCollection m_placeCollection = null;
		private LureCollection m_lureCollection = null;
		private AlternativeItemCollection m_spinnerItems = null;
		private XMLSender m_sender = null;
		
		private Model() {
			m_tripCollection = new TripCollection();
			m_placeCollection = new PlaceCollection();
			m_lureCollection = new LureCollection();
			m_spinnerItems = new AlternativeItemCollection();
			
			XMLStorage tripstorage = new XMLStorage();
			m_sender = new XMLSender(tripstorage);
			m_sender.addObserver(clearTripsAfterUpload());
			
			setupObjects(m_tripCollection, new Storer(), tripstorage, new TripBuilder(), "trip");
			setupObjects(m_placeCollection, new Storer(), new XMLStorage(), new PlaceBuilder(), "place");
			setupObjects(m_lureCollection, new Storer(), new XMLStorage(), new LureBuilder(), "lure");
			setupObjects(m_spinnerItems, new Storer(), new XMLStorage(), new AbstractBuilder() {
				@Override
				public void build() {
					m_object =  new AlternativeItemObject();
					super.build();
				}
			}, "spinneritems");
		}
		
		private Observer clearTripsAfterUpload() {
			return new Observer() {
				@Override
				public void update(Observable observable, Object o) {
					if(o instanceof Boolean && o.equals(Boolean.TRUE))
					{
						while(m_tripCollection.getList().size() > 0)
						{
							m_tripCollection.remove(0);
						}
					}					
				}				
			};
		}
		
		private void setupObjects(TrollingObjectCollection collection, 
				Storer storer,
				XMLStorage storage,
				Object builder,
				String file) {
			collection.setBuilder((Builder)builder);
			((Builder)builder).addListener(collection);
			((Builder)builder).setStorer(storer);
			storer.setStorage(storage);
			storage.addListener((BuildTarget)builder);
			try
			{
				storage.load(file);
			}catch(Exception e)
			{
				getExceptionHandler().sendException(e);
			}
		}
		
		public static void reload()
		{
			instance = null;
		}		
		
		public TripCollection getTrips() {
			return this.m_tripCollection;
		}
		
		public PlaceCollection getPlaces() {
			return this.m_placeCollection;
		}
		
		public LureCollection getLures() {
			return this.m_lureCollection;
		}
		
		public AlternativeItemCollection getSpinnerItems() {
			return this.m_spinnerItems;
		}
		
		public XMLSender getUploader() {
			return m_sender;
		}
	}
}
