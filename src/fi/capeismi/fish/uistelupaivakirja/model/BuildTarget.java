package fi.capeismi.fish.uistelupaivakirja.model;

interface BuildTarget {
	void newObject(int id);
	void endObject();
	void newKeyValue(String key, String value);
	void newProperty();
	void endProperty();
}
