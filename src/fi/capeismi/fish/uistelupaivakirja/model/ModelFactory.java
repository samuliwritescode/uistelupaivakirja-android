package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
	private static Model instance;
	private ModelFactory() {
		
	}
	
	public static Model getModel() {
		if(instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	public static class Model {
		private TripCollection m_tripCollection = new TripCollection();
		private PlaceCollection m_placeCollection = new PlaceCollection();
		private LureCollection m_lureCollection = new LureCollection();
		private List<XMLStorage> m_storages = new ArrayList<XMLStorage>();
		
		private Model() {	
			setupObjects(m_tripCollection, new Storer(), new XMLStorage(), new TripBuilder(), "trip");
			setupObjects(m_placeCollection, new Storer(), new XMLStorage(), new PlaceBuilder(), "place");
			setupObjects(m_lureCollection, new Storer(), new XMLStorage(), new LureBuilder(), "lure");
		}
		
		private void setupObjects(TrollingObjectCollection collection, 
				Storer storer,
				XMLStorage storage,
				Object builder,
				String file) {
			m_tripCollection.setBuilder((Builder)builder);
			((Builder)builder).addListener(m_tripCollection);
			storer.setStorage(storage);
			storage.addListener((BuildTarget)builder);
			storage.load(file);
			m_storages.add(storage);
		}
		
		
		public TripCollection getTrips() {
			return this.m_tripCollection;
		}
		
		public PlaceCollection getPlaces() {
			return this.m_placeCollection;
		}
		
		public LureCollection getLures() {
			for(XMLStorage storage: m_storages)
				storage.load();
			return this.m_lureCollection;
		}
	}
}
