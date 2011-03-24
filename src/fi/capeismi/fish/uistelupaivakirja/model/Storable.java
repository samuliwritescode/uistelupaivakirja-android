package fi.capeismi.fish.uistelupaivakirja.model;

interface Storable {
	int getId();
	void setId(int id);
	Object getKeyValues();
	Object getPropItems();
	void setStorer(Storer storer);
}
