package Models;

public class Customer  {


	private int CID;
	private String userUser;
	private String userPass;
	
	public Customer(int CID, String userUser, String userPass) {
		super();
		this.CID = CID;
		this.userUser = userUser;
		this.userPass = userPass;
		
	}
	public Customer(String userUser, String userPass) {
		super();
		this.userUser = userUser;
		this.userPass = userPass;
	}
	public Customer() {
		super();
	}
	
	public int getCID() {
		return CID;
	}
	
	public void setCID(int CID) {
		this.CID = CID;
	}
	
	public String getUserUser() {
		return userUser;
	}
	
	public void setUserUser(String userUser) {
		this.userUser = userUser;
	}
	
	public String getUserPass() {
		return userPass;
	}
	
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
} 