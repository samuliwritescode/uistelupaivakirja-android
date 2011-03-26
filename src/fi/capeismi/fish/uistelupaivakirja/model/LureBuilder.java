package fi.capeismi.fish.uistelupaivakirja.model;

final class LureBuilder extends AbstractBuilder {

	@Override
	public void build() {
		m_object = new LureObject();
		super.build();
	}
}
