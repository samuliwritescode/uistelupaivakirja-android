package fi.capeismi.fish.uistelupaivakirja.model;

import java.util.List;
import java.util.Map;

interface Storable {
	int getId();
	void setId(int id);
	Map<String, String> getKeyValues();
	List<Map<String, String>> getPropItems();
	void setStorer(Storer storer);
}
