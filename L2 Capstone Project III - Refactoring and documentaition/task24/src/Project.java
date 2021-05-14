
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code Project} is a class that represents Project objects in Poised Projects.
 * All projects are implemented as instances of this class.
 */
public class Project {
	// Attributes
	private String name;
	private String projectNumber;
	private String type;
	private String address;
	private String erf;
	private double fee;
	private double paidAmount = 0;
	private Date deadline;
	private boolean completed = false;
	private Party architect;
	private Party contractor;
	private Party customer;

	// To format the project fee in strings
	DecimalFormat priceFormat = new DecimalFormat("#.##");
	// To format date in strings
	DateFormat dateFormat = new SimpleDateFormat("EEE dd MMM YYYY");
	
	// Constructor
	protected Project(String name, 
				   String projectNumber, 
				   String type, 
				   String address, 
				   String erf, 
				   double fee, 
				   Date deadline,
				   Party architect,
				   Party contractor,
				   Party customer,
				   double paidAmount) {
		this.name = name; 
		this.projectNumber = projectNumber;
		this.type = type;
		this.address = address;
		this.erf = erf;
		this.fee = fee;
		this.deadline = deadline;
		this.architect = architect;
		this.contractor = contractor;
		this.customer = customer;
		this.paidAmount = paidAmount;
	}	
	

	// Methods /////////////////////////////////////////////////////////////////////

	
	/**
	 * A method that returns the project name of this instance of {@code Project}.
	 * 
	 * @return returns the {@code String} project name of this instance.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A method that sets the name for this instance of {@code Project}.
	 * 
	 * @param name the name to update this instance if {@code Project} with.
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * A method that returns the project number of this instance of {@code Project}
	 * 
	 * @return returns the {@code String} project number of this instance.
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}

	/**
	 * A method that returns the project number of this instance of {@code Project}
	 * 
	 * @return returns the {@code String} project number of this instance.
	 */
	public double getFee() {
		return this.fee;
	}

	/**
	 * A method that returns the amount paid for this instance of {@code Project}.
	 * 
	 * @return returns the amount paid for this instance.
	 */
	public double getPaidAmount() {
		return this.paidAmount;
	}

	/**
	 * A method that sets the total amount paid for this instance of {@code Project}.
	 * Not to be confused with {@code addPayment}.
	 * 
	 * @param amount the amount to set {@code this.paidAmount}.
	 */
	protected void setPaidAmount(Double amount) {
		this.paidAmount = amount;
	}

	/**
	 * A method that adds to the amount paid for this instance of {@code Project}.
	 * 
	 * @param amount the amount to be added to {@code this.paidAmount}.
	 */
	protected void addPayment(Double amount) {
		this.paidAmount += amount;
	} 

	/**
	 * A method that gets the due date for for this instance of {@code Project}.
	 * 
	 * @return Returns a {@code Date} object for the due date of this instance of
	 * 		   {@code Project}. 
	 */
	protected Date getDeadline() {
		return this.deadline;
	}

	/**
	 * A method that sets the due date for this instance of {@code Project}. This 
	 * method calls {@code HelperFunctions.dateInput()} to get the date from a user's 
	 * input. 
	 * 
	 * @param date the date to set the project deadline to.
	 */
	protected void setDeadline(Date date) {
		this.deadline = date;
	}

	/**
	 * A method that finalises this instance of {@code Project}. It will generate an
	 * invoice if the project {@code this.paidAmount} is less than the project 
	 * {@code this.fee}. It will aslo add the date of completion and set 
	 * {@code this.completed} to true for this inscance of {@code Project}.
	 * 
	 * @return Reaturns a {@code String[]} with the invoice details.
	 */
	protected String[] finalizePoject() {
		this.completed = true;

		// Generate invoice only if there is an amount due
		double amountDue = this.fee - this.paidAmount;

		if (paidAmount > 0) {
			String[] invoice = new String[4];
			invoice[0] = this.customer.getName();
			invoice[1] = this.customer.getEmail();
			invoice[2] = this.customer.getPhone();
			invoice[3] = priceFormat.format(amountDue) + "";

			return invoice;
		}
		return null;
	}

	/**
	 * A method that gets the value of {@code this.completed} for for this instance of 
	 * {@code Project}.
	 * 
	 * @return Returns a {@code boolean} for the completion status of this instance of
	 *  	   {@code Project}. 
	 */
	protected boolean isComplete() {
		return this.completed;
	}

	/**
	 * A method that gets the value of {@code this.completed} for for this instance of 
	 * {@code Project}.
	 * 
	 * @return Returns a {@code Party} object of the Archictect for this instance of 
	 * 		   {@code Project}. 
	 */
	protected Party getArchitect() {
		return this.architect;
	}

	/**
	 * A method that sets the Archictect value of this instance of {@code Project}.
	 *  
	 * @param architectUpdate the {@code Party} object that is to be used to set the 
	 * 		  Architect the project.
	 */
	protected void setArchitect(Party architectUpdate) {
		this.architect = architectUpdate;
	}
	
	/**
	 * A method that gets the Contractor for this instance of {@code Project}.
	 *  
	 * @return Returns a {@code Party} object for the Contractor of this instace
	 * of {@code Project}.
	 */
	protected Party getContractor() {
		return this.contractor;
	}

	/**
	 * A method that sets the Contractor value of this instance of {@code Project}.
	 *  
	 * @param contractorUpdate the {@code Party} object that is to be used to set the 
	 * 		  Contractor the project.
	 */
	protected void setContractor(Party contractorUpdate) {
		this.contractor = contractorUpdate;
	}

	/**
	 * A method that gets the Customer for this instance of {@code Project}.
	 *  
	 * @return Returns a {@code Party} object for the Customer of this instace
	 * of {@code Project}.
	 */
	protected Party getCustomer() {
		return this.customer;
	}

	/**
	 * A method that returns the details of this instance of {@code Project} in a 
	 * {@code String} format.
	 * 
	 * @return Returns a String representation of this {@code Project} instance.  
	 */
	public String toString() {
		String output = "\nName: " + this.name;
		output += "\nProject number: " + this.projectNumber;
		output += "\nProject type: " + this.type;
		output += "\nProject Address: " + this.address;
		output += "\nProject ERF: " + this.erf;
		output += "\nProject fee: R" + priceFormat.format(this.fee);
		output += "\nOutstanding fee: R" + priceFormat.format(this.fee - this.paidAmount);
		output += "\nDeadline: " + dateFormat.format(this.deadline);
		output += "\nCompleted: " + this.completed;
		output += "\nArchitect: " + this.architect.getName();
		output += "\nContractor: " + this.contractor.getName();
		output += "\nCustomer: " + this.customer.getName();
		return output;
	}

	/** 
	 * This method returns the attributes of this instance of {@code Project} so 
	 * that they can be saved to a file.  
	 * 
	 * @return Returns a string of all the attributes sepparated by a comma and space. 
	 */
	protected String getTextOutput() {
		String output = this.name + ", ";
		output += this.projectNumber + ", ";
		output +=  this.type + ", ";
		output +=  this.address + ", ";
		output +=  this.erf + ", ";
		output +=  this.fee + ", ";
		output +=  this.deadline + ", ";
		output +=  this.completed + ", ";
		output +=  this.architect.getName() + ", ";
		output +=  this.contractor.getName() + ", ";
		output +=  this.customer.getName() + ", ";
		output +=  this.paidAmount;
		return output;
	}

	
	/* Methods to disallow duplicates in sets ///////////////////////////////////////
	These methodes are used to esure that there are no duplicates in the sets of
	the PoisedProjects class by adding a hashcode and an is equal method.
	*/
	
	@Override 
	public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((projectNumber == null) ? 0 : projectNumber.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((erf == null) ? 0 : erf.hashCode());
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
        Project other = (Project)obj;
  
        // Comparing the states of compared Object with the state of 'this' Object
        if (!type.equals(other.type))
			return false;
        if (!name.equals(other.name))
			return false;
		if (!address.equals(other.address))
			return false;
		if (!projectNumber.equals(other.projectNumber))
			return false;
		if (!erf.equals(other.erf))
            return false;
		// if (deadline.compareTo(other.deadline) != 0)
        //     return false;
        return true;
    }
}
