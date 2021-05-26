import java.util.ArrayList;

/**
 * {@code App} is the class that handles the user interface for PoisedProjects
 */
public class App {
	// Get the only instance of PoisedProjects
	static PoisedProjects app = PoisedProjects.getApp();

    public static void main(String[] args) {
		// Display the start of the app
		String message = "";
		message += "======================================================================\n";
		message += "                                                     Poised Projects\n";
		message += "======================================================================";
		System.out.println(message);

		// Go to main menu 
		mainMenu();
	}

	/**
	 * The main menu for PoisedProjects
	 */
	private static void mainMenu() {
		String message = "\n";
		message += "	(1) View projects\n";
		message += "	(2) View overdue projects\n";
		message += "	(3) Search for project\n";
		message += "	(4) Create project\n";
		message += ":: Enter choice below. (-1) to quit";

		// Get user input
		int userInput = UserInputHandler.intInput(message);

		switch (userInput) {
			case -1:
				break;
			case 1:
				projectsView();
				break;
			case 2:
				overdueProjectsView();
				break;
			case 3:
				projectSearchView();
				break;
			case 4:
				createProjectView();
				break;
		
			default:
				System.out.println("\nThat was not a valid option, my guy.");
				mainMenu();
				break;
		}
	}

	/**
	 * A method that allows a user to create a project
	 */
	private static void createProjectView() {
		String message = "\n";
		message += "======================================================================\n";
		message += "                                                          Create Project";
		System.out.println(message);

		// Get user input
		message = ":: Would you like to create a new Project? ";
		boolean userAgree = UserInputHandler.yesNoInput(message);

		if (userAgree) {
			System.out.println("Let's do this, boiii!");
			app.createProject();
			mainMenu();
		}
		else {
			System.out.println("Okay, cool");
			mainMenu();
		}
			
	}

	/**
	 * A method that allows a user to search for a project
	 */
	private static void projectSearchView() {
		String message = "\n";
		message += "======================================================================\n";
		message += "                                                         Search Projects\n";
		System.out.println(message);

		// Get user input
		message = ":: Enter the name or project number of the project you would like to search for\nor enter -1 to go back";
		String userInput = UserInputHandler.stringInput(message);
		
		if (userInput.equalsIgnoreCase("-1")) mainMenu();

		// Search for project
		Project project = app.searchProject(userInput);

		if (project == null) {
			projectSearchView();
		} 
		else {
			projectView(project);
		}
	}

	/**
	 * A method that allows a user to view all overdue projects.
	 */
	private static void overdueProjectsView() {
		String message = "\n";
		message += "======================================================================\n";
		message += "                                                        Overdue Projects";
		System.out.println(message);

		// View all overdue projects
		ArrayList<Project> projects = app.viewOverdueProjects();

		// Get user input
		int userInput;
		do{
			message = ":: Select a project by number or enter (-1) to go back";
			userInput = UserInputHandler.intInput(message);
		} while (userInput > projects.size() || userInput == 0 || userInput < -1);
		
		if (userInput == -1)  {
			mainMenu();
		}
		else {
			projectView(projects.get(userInput-1));
		}
	}

	/**
	 * A method that allows a user to view all projects.
	 */
	private static void projectsView() {
		String message = "\n";
		message += "======================================================================\n";
		message += "                                                        Ongoing Projects";
		System.out.println(message);

		// View all projects
		ArrayList<Project> projects = app.viewCurrentProjects();

		// Get user input
		int userInput;
		do{
			message = ":: Select a project by number or enter (-1) to go back";
			userInput = UserInputHandler.intInput(message);
		} while (userInput > projects.size() || userInput == 0 || userInput < -1);
		
		if (userInput == -1)  {
			mainMenu();
		}
		else {
			projectView(projects.get(userInput-1));
		}

	}

	/**
	 * A method that allows a user to view a specific project project and edit it.
	 * 
	 * @param project
	 */
	private static void projectView(Project project) {
		String message = "\n";
		message += "======================================================================\n";

		System.out.println(message);
		System.out.println(project);

		message = "\n";
		message += "	(1) Edit project name\n";
		message += "	(2) Edit project deadline\n";
		message += "	(3) Edit project architect\n";
		message += "	(4) Edit project  contractor\n";
		message += "	(5) Add payment to project\n";
		message += "	(6) Finalize project\n";
		message += "	(-1) go back\n";
		message += ":: Enter choice below";

		// Get user input
		int userInput = UserInputHandler.intInput(message);

		switch (userInput) {
			case -1:
				mainMenu();
				break;
			case 1:
				app.editProjectName(project);
				projectView(project);
				break;
			case 2:
				app.editProjectDeadline(project);
				projectView(project);
				break;
			case 3:
				app.editParty(project, "Architect");
				projectView(project);
				break;
			case 4:
				app.editParty(project, "Contractor");
				projectView(project);
				break;
			case 5:
				app.addProjectPayment(project);
				projectView(project);
				break;
			case 6:
				app.finaliseProject(project);
				mainMenu();
				break;
		
			default:
				System.out.println("\nThat was not a valid option, my guy.\n");
				projectView(project);
				break;
		}
	}

}
