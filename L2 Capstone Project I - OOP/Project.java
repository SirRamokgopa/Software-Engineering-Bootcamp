package task7;

import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

public class Project {
	// Attributes
	private String name;
	private String projectNumber;
	private String type;
	private String address;
	private String erf;
	private int fee;
	private int paidAmount;
	private Date deadline;
	private boolean completed = false;
	private Party architect;
	private Party contractor;
	private Party customer;
	
	// Constructor
	public Project(String name, 
				   String projectNumber, 
				   String type, 
				   String address, 
				   String erf, 
				   int fee, 
				   Date deadline,
				   Party architect,
				   Party contractor,
				   Party customer) {
		this.name = name; 
		this.projectNumber = projectNumber;
		this.type = type;
		this.address = address;
		this.erf = erf;
		this.fee = 0;
		this.paidAmount = 0;
		this.deadline = deadline;
		this.architect = architect;
		this.contractor = contractor;
		this.customer = customer;
	}	
	
	// Methods
	public String getName() {
		return this.name;
	}
	public String getProjectNumber() {
		return this.projectNumber;
	}
	public int getFee() {
		return this.fee;
	}
	public int getPaidAmount() {
		return this.paidAmount;
	}
	public void setPaidAmount(int amount) {
		this.paidAmount = amount;
	}
	public Date getDeadline() {
		return this.deadline;
	}
	public void setDeadline(String deadlineUpdate) throws HeadlessException, ParseException {
		/* Takes a string (dd/MM/yyyy) and updates the deadline. */
		// Get date for deadline
		DateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date deadlineUpdDate = dateParser.parse(JOptionPane.showInputDialog("Enter project deadline (dd/mm/yyyy)"));
		this.deadline = deadlineUpdDate;
	}
	public void finaliseProject() {
		this.completed = true;
	}
	public boolean isComplete() {
		return this.completed;
	}
	public Party getArchitect() {
		return this.architect;
	}
	public void setArchitect(Party architectUpdate) {
		this.architect = architectUpdate;
	}
	public Party getContractor() {
		return this.contractor;
	}
	public void setContractor(Party contractorUpdate) {
		this.contractor = contractorUpdate;
	}
	public Party getCustomer() {
		return this.customer;
	}
	public String toString() {
		String output = "\nName: " + this.name;
		output += "\nProject number: " + this.projectNumber;
		output += "\nProject type: " + this.type;
		output += "\nProject Address: " + this.address;
		output += "\nProject ERF: " + this.erf;
		output += "\nProject fee: " + this.fee;
		output += "\nDeadline: " + this.deadline;
		output += "\nCompleted: " + this.completed;
		output += "\nArchitect: " + this.architect.getName();
		output += "\nContractor: " + this.contractor.getName();
		output += "\nCustomer: " + this.customer.getName();
		return output;
	}
}
