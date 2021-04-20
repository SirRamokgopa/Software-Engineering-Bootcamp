package task7;

public class Party {
	// Attributes
	private String name;
	private String telephone;
	private String email;
	private String address;
	
	// Constructor
	public Party(String name, String telephone, String email, String address) {
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
	}
	
	// Methods
	public String getName() {
		return this.name;
	}
	public String getPhone() {
		return this.telephone;
	}
	public String getEmail() {
		return this.email;
	}
	public String getAddress() {
		return this.address;
	}
	public void setPhone(String phone) {
		this.telephone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String toString() {
		String output = "\nName: " + this.name;
		output += "\nTelephone: " + this.telephone;
		output += "\nemail: " + this.email;
		output += "\nAddress: " + this.address;
		
		return output;
	}
}
