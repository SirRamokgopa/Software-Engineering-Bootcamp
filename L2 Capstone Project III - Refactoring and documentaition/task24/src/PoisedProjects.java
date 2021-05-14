
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This is the main Poised Projects class. 
 * It handles the creation of projects and the creation of projects parties. 
 * This main class also allows for viewing all ongoing projects and all overdue
 * projects 
 */
public class PoisedProjects {

	// Refers to the only instance of ProjectsList
	protected static ProjectsList projectsList = ProjectsList.getprojectsList();

	// Attribute to hold singleton instance of 'this' class
	private static PoisedProjects app = null;

	// Constructor
	private PoisedProjects() {}

	// A method to create return only instance of 'this' class 
	public static PoisedProjects getApp() {
		if (app == null) {
			app = new PoisedProjects();
			// Gets the data for the app from files
			FileReader.readProjects();
		}
		return app;
	} 

	/**
	 *  A method that creates and returns a new {@code Party} Object. 
     * returns a Party object.
	 * 
	 * @param partyType the type of party to be created (Either "Customer", "Contractor", 
	 * or "Architect").
	 * @return Returns a new {@code Party} object.
	 */
	public Party createParty(String partyType) {
		
		// Get party details
		String name = HelperFunctions.stringInput("Enter " + partyType + " name.");
		String phone = HelperFunctions.stringInput("Enter " + partyType + " phone number.");
		String email = HelperFunctions.stringInput("Enter " + partyType + " email address.");
		String address = HelperFunctions.stringInput("Enter " + partyType + " address.");
		
		Party party =  new Party(name, phone, email, address, partyType);

		// Write person to file
		FileWriterr.writeToFile("Parties.txt", party.getTextOutput());

		// return party object
		return party;
	}
	
	/**
	 * This method creates the parameters for the overloaded {@code createProject()} 
	 * method then calls that method.
	 */
	public void createProject() {		
		// List of required parties
		List<String> parties = Arrays.asList(new String[] {"Customer", "Architect", "Contractor"});
		ArrayList<Party> partiesObjects = new ArrayList<Party>();

		// Get party objects
		parties.forEach((party) -> {
			// Prompt user to search for party or create one
			partiesObjects.add(createOrEditParty(party));
		});
		
		// Create project
		createProject(null, partiesObjects.get(1), partiesObjects.get(2), partiesObjects.get(0));
	}

	/**
	 * A method that allows a user to create new {@code Party} Objects for a project
	 * or search for exsisting {@code Party} Objecst.
	 * 
	 * @param party the type of {@code Party}. (Either Contractor, Architect, or 
	 * 				Customer.)
	 * @return Returns a {@code Party} Object.
	 */
	private Party createOrEditParty(String party) {
		
		// Get user input
		String userChoice;
		do {
			String message = "\n:: Would you like to:\n(1) search for " + party + "\n(2) create" + party + "\n" ; 
			userChoice = HelperFunctions.stringInput(message);
		} while (userChoice.equalsIgnoreCase("2") || userChoice.equalsIgnoreCase("1") || userChoice.equalsIgnoreCase("search"));

		if (userChoice.equalsIgnoreCase("1") || userChoice.equalsIgnoreCase("search")) {
			// Search for party
			String name = HelperFunctions.stringInput("Name of " + party + " to search: ");
			Party partyResult = projectsList.searchpParty(name, party);

			if (partyResult!=null) {
				return partyResult;
			}
			do {
				// If party is not found
				System.out.println("\n" + party + " not found.");
				boolean tryagain  = HelperFunctions.yesNoInput("Try again? ");

				// Prompt user to search again or not
				if (tryagain) {
					name = HelperFunctions.stringInput("\nName of " + party + " to search: ");
					partyResult = projectsList.searchpParty(name, party);
				}
				else {
					System.out.println("\nLet's try to create the " + party + " instead.");
					partyResult = createParty(party);
					return partyResult;
				}
			} while (partyResult == null);
		} 
		return  createParty(party); 
	}

	/**
	 * A method that creates and returns a {@code Project} object. 
	 * 
	 * @param projectName the name of the project.
	 * @param architect the Architect {@code Party} of the project.
	 * @param contractor the Contractor {@code Party} of the project.
	 * @param customer the Customer {@code Party} of the project.
	 * @return Returns a new {@code Project} Object.
	 */
	private Project createProject(String projectName, Party architect, Party contractor, Party customer) {
		
		// Get project details from user
		String projectNumber = HelperFunctions.stringInput("Enter project number\n::");
		String type = HelperFunctions.stringInput("Enter project type (Eg. House, appartment block, store, etc...)\n::");
		String address = HelperFunctions.stringInput("Enter project address\n::");
		String erf = HelperFunctions.stringInput("Enter ERFnumber\n::");
		double fee = HelperFunctions.dblInput("Enter fee (R)\n::");
		// Get date for deadline
		Date deadline = HelperFunctions.dateInput();

		// Use customer name for project if project name not supplied
		projectName = projectName!=null ? projectName : customer.getName() + " " + type;

		Project project = new Project(
			projectName, projectNumber, type, address, erf, fee, deadline, architect, contractor, customer, 0
		);

		// Write project to file
		FileWriterr.writeToFile("Projects.txt", project.getTextOutput());

		return project;
	}

	/**
	 *  A method that displays a list of projects in the console.
	 * 
	 * @return Returns an {@code ArrayList<Project>} of  projects.
	 */
	public ArrayList<Project> viewCurrentProjects() {
		// Get array of overdue projects
		ArrayList<Project> projects = projectsList.getProjects();

		// Print each project
		int numToChoose = 1;
		for (Project project: projects) {
            System.out.println("\n" + project.toString());
            System.out.println("==============================("+numToChoose+")");
			numToChoose++;
        }
		return projects;
	}

	/**
	 *  A method that displays a list of overdue projects in the console.
	 * 
	 * @return Returns an {@code ArrayList<Project>} of overdue projects.
	 */
	public ArrayList<Project> viewOverdueProjects() {
		// Get array of overdue projects
		ArrayList<Project> projects = projectsList.getOverdueProjects();
		
		// Print each project
		int numToChoose = 1;
		for (Project project: projects) {
            System.out.println("\n" + project.toString());
            System.out.println("==============================("+numToChoose+")");
			numToChoose++;
        }
		return projects;
	}


	/**
	 * A method that that searches for a {@code Project} by name and by project 
	 * number.
	 * 
	 * @param input the name or project number to be searched.
	 * @return Returns a matching {@code Project} Object or null if no matches are
	 * 		   found.
	 */
	public Project searchProject(String input) {
		// Search by name
		Project result =  projectsList.searchProjectByName(input);
		
		// Search by project number if name is not found
		if (result == null) {
			result = projectsList.searchProjectByNumber(input);
		}

		// Set result to null if no matches are found
		if (result == null) {
			result = null;
			System.out.println("\nResult not found");
		}
		return result;
	}

	/**
	 * A method that allows a user to update the {@code Project.amountPaid} attribute 
	 * of a {@code Project}.
	 * 
	 * @param project the {@code Project} to be updated.
	 */
	public void addProjectPayment(Project project) {
		// Get user input
		double amount = HelperFunctions.dblInput(":: Amount to be added");
		
		// Update project payment
		project.addPayment(amount);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

	/**
	 * A method that allows a user to update the {@code Project.name} attribute 
	 * of a {@code Project}.
	 * 
	 * @param project the {@code Project} to be updated.
	 */
	public void editProjectName(Project project) {
		// Get user input.
		String name = HelperFunctions.stringInput(":: Enter new project name");
		
		// Set new name
		project.setName(name);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

	/**
	 * A method that allows a user to update the {@code Project.deadline} attribute 
	 * of a {@code Project}.
	 * 
	 * @param project the {@code Project} to be updated.
	 */
	public void editProjectDeadline(Project project) {
		// Get user input
		Date deadlineUpdDate = HelperFunctions.dateInput();

		// Set new date
		project.setDeadline(deadlineUpdDate);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

	/**
	 * A method that allows a user to edit a {@code Party} of a {@code Project}.
	 * 
	 * @param project the {@code Project} Object to be updated.
	 * @param partyType the type of {@code Party} Object to be updated.
	 */
	public void editParty(Project project, String partyType) {
		switch (partyType) {
			case "Architect":
				Party architect = project.getArchitect();
				System.out.println("\n==================== Architect");
				System.out.println(architect);
				
				// Update architect
				project.setArchitect(createOrEditParty("Architect"));	
				break;
			
			case "Contractor":
				Party contractor = project.getContractor();
				System.out.println("\n==================== Contractor");
				System.out.println(contractor);
				
				// Update architect
				project.setArchitect(createOrEditParty("Contractor"));	
				break;

			default:
				System.out.println("Something went wrong in PoisedProjects.editParty()");
				break;
		}
		
		// Write updates to file
		projectsList.updatePartiesFile();

		System.out.println("All done :)\n");
	}
	
	/**
	 * A method that sets {@code Project.completed} to true and then the prints the 
	 * invoice to console if payment for the project is still due. This method then
	 * adds the project to the completedProjects set and writes the project to 
	 * file. 
	 * 
	 * @param project the project to be finalized.
	 */
	public void finaliseProject(Project project) {
		// Set project to complete and get invoice if payment is still due
		String[] invoice = project.finalizePoject();

		// Format invoice
		if (invoice != null) {
			// Create invoice to print
			String message = "\n";
			message += "==================== Invoice\n";
			message += "Customer:  " + invoice[0] + "\n";
			message += "email:     " + invoice[1] + "\n";
			message += "phone:     " + invoice[2] + "\n";
			message += "=========== Total amount due\n";
			message += "R"+ invoice[3] + "\n";

			System.out.println(message);
		}

		// Add project to completed list and write completed project to file 
		projectsList.addCompletedProject(project);
		FileWriterr.writeCompleted(project.getTextOutput() + ", " + new Date());

		System.out.println("Nice! Another project in the bag.\n");
	}

}
