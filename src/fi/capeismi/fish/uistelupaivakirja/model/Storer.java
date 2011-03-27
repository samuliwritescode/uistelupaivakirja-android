package fi.capeismi.fish.uistelupaivakirja.model;

final class Storer {
	private Storage m_storage = null;
	public void save(Storable storable)
	{
		m_storage.save(storable);
	}
	
	public void remove(Storable storable)
	{
		m_storage.remove(storable.getId());		
	}
	
	public void setStorage(Storage storage)
	{
		m_storage = storage;
	}
}
