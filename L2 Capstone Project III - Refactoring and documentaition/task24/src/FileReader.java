import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class FileReader {
    
	protected static ProjectsList projectsList = ProjectsList.getprojectsList();

    /** 
	 * Reads the projects from "Projects.txt" and adds them to the projects list. 
	 * These are the projects that are still ongoing.
	 */
	protected static void readProjects() {

        // Info about parties is required for this
        readParties();

		try {
			File projectsFile = new File("Projects.txt");
			Scanner scannerInput = new Scanner(projectsFile);

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
				
				Party architect = projectsList.searchpParty(projectData[8], "Architect");
				Party contractor = projectsList.searchpParty(projectData[9], "Contractor");
				Party customer = projectsList.searchpParty(projectData[10], "Customer");


				projectsList.addProject(new Project(name, projectNumber, type, address, erf, fee, deadline, architect, contractor, customer, paidAmount));
			}

            scannerInput.close();
		}
		catch (Exception err) {
			System.out.println("Project file reading has failed");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
		}
	}

    protected static void readParties() {
        try {
			File partiesFile = new File("Parties.txt");
			Scanner scannerInput = new Scanner(partiesFile);

			while(scannerInput.hasNextLine()) {
				String partyData [] = scannerInput.nextLine().split(", ");
				
				String partyType = partyData[0];
				String name = partyData[1];
				String telephone = partyData[2];
				String address = partyData[3];
				String email = partyData[4];
                
                projectsList.addPary(new Party(name, telephone, email, address, partyType));
			}

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
