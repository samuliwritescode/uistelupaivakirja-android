package fi.capeismi.fish.uistelupaivakirja.model;

interface Builder {
	void build();
	void addListener(ObjectCollection collection);
	void setStorer(Storer storer);
}
