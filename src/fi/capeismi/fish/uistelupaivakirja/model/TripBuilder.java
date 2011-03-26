package fi.capeismi.fish.uistelupaivakirja.model;

final class TripBuilder extends AbstractBuilder {

	@Override
	public void build() {
		m_object = new TripObject();
		super.build();
	}

}
