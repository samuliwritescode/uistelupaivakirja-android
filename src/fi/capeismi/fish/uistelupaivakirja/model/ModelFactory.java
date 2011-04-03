package fi.capeismi.fish.uistelupaivakirja.model;

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
		private TripCollection m_tripCollection = null;
		private PlaceCollection m_placeCollection = null;
		private LureCollection m_lureCollection = null;
		private SpinnerItemCollection m_spinnerItems = null;
		
		private Model() {
			m_tripCollection = new TripCollection();
			m_placeCollection = new PlaceCollection();
			m_lureCollection = new LureCollection();
			m_spinnerItems = new SpinnerItemCollection();
			setupObjects(m_tripCollection, new Storer(), new XMLStorage(), new TripBuilder(), "trip");
			setupObjects(m_placeCollection, new Storer(), new XMLStorage(), new PlaceBuilder(), "place");
			setupObjects(m_lureCollection, new Storer(), new XMLStorage(), new LureBuilder(), "lure");
			setupObjects(m_spinnerItems, new Storer(), new XMLStorage(), new AbstractBuilder() {
				@Override
				public void build() {
					m_object =  new SpinnerItemObject();
					super.build();
				}
			}, "spinneritems");
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
			storage.load(file);
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
		
		public SpinnerItemCollection getSpinnerItems() {
			return this.m_spinnerItems;
		}
	}
}
