/**
 * {@code Party} class represents Pary objects in poised projects
 * All Contractor, Architect, and Customers are instances of this class.
 */
public class Party {
	// Attributes
	private String partyType;
	private String name;
	private String telephone;
	private String email;
	private String address;
	
	/**
	 * Constructes a {@code Party} instance. That holds the details for a Contractor, 
	 * Architect, or Customer.
	 */ 
	protected Party(String name, String telephone, String email, String address, String partyType) {
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
		this.partyType = partyType;
	}
	 

	// Methods /////////////////////////////////////////////////////////////////////

	/**
	 * This method is used to set the partyType attribute of this instance of 
	 * {@code Party } to either "Contractor", or "Architect". The partyType 
	 * attribute is set to "Customer" by default.
	 */
	public String getPartyType() {
		return this.partyType;
	}

	/**
	 * A method that gets the name of this instance of {@code Party}.
	 * @return Returns the name of this instance of {@code Party}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A method that gets the phone number of this instance of {@code Party}.
	 * @return Returns the phone number of this instance of {@code Party}.
	 */
	public String getPhone() {
		return this.telephone;
	}

	/**
	 * A method that gets the email address of this instance of {@code Party}.
	 * @return Returns the email address of this instance of {@code Party}.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * A method that gets the the physical address of this instance of {@code Party}.
	 * @return Returns the address of this instance of {@code Party}.
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * A method that sets the phone number of this instance of {@code Party}.
	 */
	public void setPhone(String phone) {
		this.telephone = phone;
	}
	
	/**
	 * A method that sets the email address of this instance of {@code Party}.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * A method that sets the physical address of this instance of {@code Party}.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * A method that returns the details of this instance of {@code Party} in a 
	 * {@code String} format.
	 * @return Returns a String representation of this {@code Party} instance.  
	 */
	public String toString() {
		String output = "\nName: " + this.name;
		output += "\nTelephone: " + this.telephone;
		output += "\nemail: " + this.email;
		output += "\nAddress: " + this.address;
		return output;
	}

	/** 
	 * This method returns the attributes of this instance of {@code Project} so 
	 * that it can be saved to a file.  
	 * 
	 * @return Returns a string of all the attributes sepparated by a comma and space. 
	 */
	protected String getTextOutput() {
		String output = this.partyType + ", ";
		output += this.name + ", ";
		output += this.telephone + ", ";
		output +=  this.email + ", ";
		output +=  this.address;
		return output;
	}



	// Methods to disallow duplicates in sets ///////////////////////////////////////

	@Override 
	public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((partyType == null) ? 0 : partyType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((telephone== null) ? 0 : telephone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    // Checks if both the object references are referring to the same object.
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
  
        // Casting of the argument to type party.
        Party other = (Party)obj;
  
        // Comparing the states of compared Object with the state of 'this' Object
        if (!partyType.equals(other.partyType))
			return false;
        if (!name.equals(other.name))
			return false;
		if (!address.equals(other.address))
			return false;
		if (!email.equals(other.email))
			return false;
		if (!telephone.equals(other.telephone))
            return false;
        return true;
    }
}
