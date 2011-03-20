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
	
	private static class Model {
		private TripCollection m_tripCollection = new TripCollection();
		private PlaceCollection m_placeCollection = new PlaceCollection();
		private LureCollection m_lureCollection = new LureCollection();
		
		private Model() {
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
	}
}
