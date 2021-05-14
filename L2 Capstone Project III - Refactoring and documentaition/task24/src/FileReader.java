import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 * {@code FileReader} class handles the reading of the Projects and Party data 
 * for PoisedProjects from text files.
 */
public class FileReader {
    
	// Get instance of ProjectsList
	protected static ProjectsList projectsList = ProjectsList.getprojectsList();

    /** 
	 * A method that reads the projects from "Projects.txt" and adds them to the 
	 * projects list. These are the projects that are still ongoing.
	 */
	protected static void readProjects() {

        // Info about parties is required for this
        readParties();

		try {
			// Open file
			File projectsFile = new File("Projects.txt");
			Scanner scannerInput = new Scanner(projectsFile);

			// Read contents
			while(scannerInput.hasNextLine()) {
				String projectData [] = scannerInput.nextLine().split(", ");
				
				String name = projectData[0];
				String projectNumber = projectData[1];
				String type = projectData[2];
				String address = projectData[3];
				String erf = projectData[4];
				double fee = Double.parseDouble(projectData[5]);
				double paidAmount = Double.parseDouble(projectData[11]);
                
                SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
				Date deadline = dateParser.parse(projectData[6]);
				
				// Get party objects for Project
				Party architect = projectsList.searchpParty(projectData[8], "Architect");
				Party contractor = projectsList.searchpParty(projectData[9], "Contractor");
				Party customer = projectsList.searchpParty(projectData[10], "Customer");

				// Create Project instance and add to projectsList
				projectsList.addProject(new Project(name, projectNumber, type, address, erf, fee, deadline, architect, contractor, customer, paidAmount));
			}

			// Close file
            scannerInput.close();
		}
		catch (Exception err) {
			System.out.println("Project file reading has failed");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
		}
	}

	/**
	 * A method that reads the contents of Parities.txt. The method then creates
	 * {@code Party} Objects from the contents and then adds the the sets in the
	 * {@code ProjectsList} instance.
	 */
    protected static void readParties() {
        try {
			// Open file
			File partiesFile = new File("Parties.txt");
			Scanner scannerInput = new Scanner(partiesFile);

			// Read file contents
			while(scannerInput.hasNextLine()) {
				String partyData [] = scannerInput.nextLine().split(", ");
				
				String partyType = partyData[0];
				String name = partyData[1];
				String telephone = partyData[2];
				String address = partyData[3];
				String email = partyData[4];
                
				// Create Party Objects then add them to ProjectsList
                projectsList.addPary(new Party(name, telephone, email, address, partyType));
			}

			// Close scanner
            scannerInput.close();
		}
		catch (Exception err) {
			System.out.println("Party file reading has failed");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
		}
        
    }
}
