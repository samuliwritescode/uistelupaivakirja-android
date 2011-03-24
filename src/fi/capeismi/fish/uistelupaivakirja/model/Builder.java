package fi.capeismi.fish.uistelupaivakirja.model;

interface Builder {
	TrollingObject build();
	void addListener(ObjectCollection collection);

}
