package fi.capeismi.fish.uistelupaivakirja.model;

interface Storage {
	void save(Storable storable);
	void remove(int id);

}
