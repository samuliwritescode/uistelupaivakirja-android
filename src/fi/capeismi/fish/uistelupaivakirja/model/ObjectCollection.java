package fi.capeismi.fish.uistelupaivakirja.model;

interface ObjectCollection {
	void onObjectCreate(TrollingObject object);
	void setBuilder(Builder builder);
}
