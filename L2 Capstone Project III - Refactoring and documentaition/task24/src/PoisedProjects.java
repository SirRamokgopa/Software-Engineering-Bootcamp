
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

	// Refers to the only instance of projectsList
	protected static ProjectsList projectsList = ProjectsList.getprojectsList();

	
	private static PoisedProjects app = null;

	private PoisedProjects() {}

	public static PoisedProjects getApp() {
		if (app == null) {
			app = new PoisedProjects();
			// Gets the data for the app from files
			FileReader.readProjects();
		}
		return app;
	} 

	/** 
	 * Takes a string input, either customer, contractor, or architect and 
     * returns a Party object.
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
	
	public void createProject() {		
		List<String> parties = Arrays.asList(new String[] {"Customer", "Architect", "Contractor"});
		ArrayList<Party> partiesObjects = new ArrayList<Party>();

		parties.forEach((party) -> {
			// Prompt user to search for party or create one
			partiesObjects.add(createOrEditParty(party));
		});
		
		createProject(null, partiesObjects.get(1), partiesObjects.get(2), partiesObjects.get(0));
	}

	/**
	 * 
	 * @param party
	 * @return
	 */
	private Party createOrEditParty(String party) {
		
		// Get user input
		String userChoice;
		do {
			String message = "\n:: Would you like to:\n(1) search for " + party + "\n(2) create" + party + "\n" ; 
			userChoice = HelperFunctions.stringInput(message);
		} while (userChoice.equalsIgnoreCase("2") || userChoice.equalsIgnoreCase("1") || userChoice.equalsIgnoreCase("search"));

		if (userChoice.equalsIgnoreCase("1") || userChoice.equalsIgnoreCase("search")) {

			String name = HelperFunctions.stringInput("Name of " + party + " 🔎: ");
			Party partyResult = projectsList.searchpParty(name, party);

			if (partyResult!=null) {
				return partyResult;
			}
			do {
				// If party is not found
				System.out.println("\n" + party + " not found.");
				boolean tryagain  = HelperFunctions.yesNoInput("Try again? ");

				if (tryagain) {
					name = HelperFunctions.stringInput("\nName of " + party + " 🔎: ");
					partyResult = projectsList.searchpParty(name, party);
				}
				else {
					System.out.println("\nLet's try to create the " + party + " instead. 😅");
					partyResult = createParty(party);
					return partyResult;
				}
			} while (partyResult == null);
		} 
		return  createParty(party); 
	}

	/** 
	 * This method creates and returns project object. 
	 * The inputs are optional and may be replaced with a null
	 * Not too sure of why it needs to throw HeadlessException, ParseException
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
	 * Displays a list of ongoing projects
	 */
	public ArrayList<Project> viewCurrentProjects() {
		ArrayList<Project> projects = projectsList.getProjects();
		int numToChoose = 1;
		for (Project project: projects) {
            System.out.println("\n" + project.toString());
            System.out.println("==============================("+numToChoose+")");
			numToChoose++;
        }
		return projects;
	}

	/**
	 * Displays a list of overdue projects
	 */
	public ArrayList<Project> viewOverdueProjects() {
		ArrayList<Project> projects = projectsList.getOverdueProjects();
		int numToChoose = 1;
		for (Project project: projects) {
            System.out.println("\n" + project.toString());
            System.out.println("==============================("+numToChoose+")");
			numToChoose++;
        }
		return projects;
	}


	/**
	 *  Find and select a project by entering either the project number or project name
	*/
	public Project searchProject(String input) {
		Project result =  projectsList.searchProjectByName(input);
		if (result == null) {
			result = projectsList.searchProjectByNumber(input);
		}
		if (result == null) {
			result = null;
			System.out.println("\nResult not found");
		}
		return result;
	}

	public void addProjectPayment(Project project) {
		double amount = HelperFunctions.dblInput(":: Amount to be added");
		project.addPayment(amount);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

	public void editProjectName(Project project) {
		String name = HelperFunctions.stringInput(":: Enter new project name");
		project.setName(name);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

	public void editProjectDeadline(Project project) {
		Date deadlineUpdDate = HelperFunctions.dateInput();
		project.setDeadline(deadlineUpdDate);

		// Write updates to file
		projectsList.updateProjectsFile();

		System.out.println("All done :)\n");
	}

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
	 * 
	 */
	public void finaliseProject(Project project) {
		String[] invoice = project.finalizePoject();

		if (invoice != null) {
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

		System.out.println("Nice! Another project in the bag. 😎\n");
	}

}
