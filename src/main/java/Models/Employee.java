package Models;

public class Employee  {

	private int id;
	private String empUser;
	private String empPass;
	
	public Employee(String empUser, String empPass) {
		super();
		this.empUser = empUser;
		this.empPass = empPass;
	}
	
	public Employee() {}
	
	public String getEmpUser() {
		return empUser;
	}
	
	public void setEmpUser(String empUser) {
		this.empUser = empUser; 
	}
	
	public String getEmpPass() {
		return empPass;
	}
	
	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}
	
	public int getEmpId() {
		return id;
	}

	public void setEmpId(int id) {
		this.id = id;
	}
} 