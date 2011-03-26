package fi.capeismi.fish.uistelupaivakirja.model;

final class PlaceBuilder extends AbstractBuilder {

	@Override
	public void build() {
		m_object = new PlaceObject();
		super.build();
	}

}
