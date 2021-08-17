package Models;

public class Payments {

	private int id;
	private int sid;
	private int cid;
	private double remainder;
	private double amount;
	private String name;
	
	public Payments(int id, int sid, int cid, double remainder, double amount) {
		this.id = id;
		this.sid = sid;
		this.cid = cid;
		this.remainder = remainder;
		this.amount = amount;
	}

	public Payments() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public double getRemainder() {
		return remainder;
	}
	public double getAmount() {
		return amount;
	}
	public int getSID() {
		return sid;
	}
	public int getCID() {
		return cid;
	}
	public int getID() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRemainder(double remainder) {
		// TODO Auto-generated method stub
		this.remainder = remainder;
	}
	public void setAmount(double amount) {
		// TODO Auto-generated method stub
		this.amount = amount;
	}
	public void setCID(int cid) {
		// TODO Auto-generated method stub
		this.cid = cid;
	}
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.id = id;	
	}
	public void setSID(int sid) {
		// TODO Auto-generated method stub
		this.sid = sid; 
	}
	
	@Override
	public String toString() {
		return "\nItem ID: " + this.getSID() + "\nItem name:" + this.getName() + "\nAmount remaining: $"
				+ this.getRemainder() + "\nAmount paid: $" + this.getAmount();
	}
}
