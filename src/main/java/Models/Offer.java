package Models;

public class Offer {
	
	private int ID;
	private int SID;
	private int CID;
	private double price;
	private boolean approved;
	private String name;
	private String author;

	public Offer(int ID, int SID, int CID, boolean approved, double price) {
		super();
		this.ID = ID;
		this.SID = SID;
		this.CID = CID;
		this.approved = approved;
		this.price = price;
	}
	
	public Offer(int ID, int SID, int CID, boolean approved, double price, String name, String author) {
		super();
		this.ID = ID;
		this.SID = SID;
		this.CID = CID;
		this.approved = approved;
		this.price = price;
		this.name = name;
		this.author = author;
	}
	
	public Offer() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public boolean getApproved() {
		return approved;
	}
	
	public void setApproved(boolean approved) {
		this.approved = approved; 
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getSID() {
		return SID;
	}
	
	public void setSID(int SID) {
		this.SID = SID;
	}
	
	public int getCID() {
		return CID;
	}
	
	public void setCID(int CID) {
		this.CID = CID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double d) {
		this.price = d;
	}

	@Override
	public String toString() { 
	    return "\nOffer ID: " + this.getID() + ", \n" +
	           "Customer ID: " + this.getCID() + ", \n" + "Book ID: " +
	    		this.getSID() + ", \n" + "Price: $" + this.getPrice() + ", \n"+ "Approved status: " +
	           this.getApproved() + ". \nName: " + this.getName() + "\nAuthor: " + this.getAuthor();
	}
}