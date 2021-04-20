package task7;

import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

public class PoisedProjects {
	
	public static Party createParty(String partyType) {
		/* Takes a string input, either customer, contractor, or architect and 
		 * returns a Party object.
		 * */

		// Get party details
		String name = JOptionPane.showInputDialog("Enter " + partyType + " name.");
		String phone = JOptionPane.showInputDialog("Enter " + partyType + " phone number.");
		String email = JOptionPane.showInputDialog("Enter " + partyType + " email address.");
		String address= JOptionPane.showInputDialog("Enter " + partyType + " address.");
		
		// return party object
		return new Party(name, phone, email, address);
	}
	
	public static Project createProject(String projectName, Party architect, Party contractor, Party customer) throws HeadlessException, ParseException {
		/* This method creates and returns project object. 
		 * The inputs are optional and may be replaced with a null
		 * Not too sure of why it needs to throw HeadlessException, ParseException
		 * */
		
		// Get project details from user
		String projectNumber = JOptionPane.showInputDialog("Enter project number");
		String type = JOptionPane.showInputDialog("Enter project type (Eg. House, appartment block, store, etc...)");
		String address = JOptionPane.showInputDialog("Enter project address");
		String erf = JOptionPane.showInputDialog("Enter ERFnumber");
		int fee = Integer.parseInt(JOptionPane.showInputDialog("Enter fee (R)"));
		// Get date for deadline
		DateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date deadline = dateParser.parse(JOptionPane.showInputDialog("Enter project deadline (dd/mm/yyyy)"));

		// Get the values from the arguments or ask user to enter them
		architect = architect!=null ? architect : createParty("architect");
		contractor = contractor!=null ? contractor : createParty("contractor");
		customer = customer!=null ? customer : createParty("customer");					
		// Use customer name for project if project name not supplied
		projectName = projectName!=null ? projectName : customer.getName() + " " + type;

		return new Project(projectName, projectNumber, type,
						address, erf, fee, deadline,
						architect, contractor, customer);
	}

	
	public static void main(String[] args) throws HeadlessException, ParseException {
		// Create a person
		Party architect = createParty("Architect");

		// Create a project
		Project thatThing = createProject(null, architect, null, null);
		System.out.println(thatThing);

		// Change the due date of a project
		System.out.println("\n\n");
		System.out.println(thatThing.getDeadline());
		thatThing.setDeadline("23/05/3036");
		System.out.println(thatThing.getDeadline());

		// Update a contractor's details
		System.out.println("\n\n");
		System.out.println(thatThing.getContractor());
		thatThing.setContractor(createParty("contractor"));
		System.out.println(thatThing.getContractor());

		// Change the total amount of fee paid
		System.out.println("\n\n");
		System.out.println(thatThing.getPaidAmount());
		thatThing.setPaidAmount(666);
		System.out.println(thatThing.getPaidAmount());

		// Finalize a project
		System.out.println("\n\n");
		System.out.println(thatThing.isComplete());
		thatThing.finaliseProject();
		System.out.println(thatThing.isComplete());
	}
}
