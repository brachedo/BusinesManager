package entities;

public interface User {
	public void addStock();
	public void readStock();
	public void editStock();
	public void removeStock();
	
	public void addAdmin();
	
	public void addSalRep();
	public void editSalRep();
	public void removeSalRep();
	
	public void addClient();
	public void editClient();
	public void removeClient();
	
	public void printCatalogue();
	public void printCatalogueForTime();
	public void printCatalogueFromRep();
	
	public void bringOrder();
	public void checkInbox();
}
