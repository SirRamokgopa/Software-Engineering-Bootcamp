import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * This class handles the collections that are used to hold {@code Project} and
 * {@code Party} Objects.  
 */
public class ProjectsList {

    private static ProjectsList projectsList = null;
	
	private HashSet<Project> projects;
	private HashSet<Project> completedProjects;
    private HashSet<Party> customers;
    private HashSet<Party> architects;
    private HashSet<Party> contractors;
	
	private ProjectsList() {
		projects  = new HashSet<Project>();

        customers = new HashSet<Party>();
        architects = new HashSet<Party>();
        contractors = new HashSet<Party>();
        
        completedProjects = new HashSet<Project>();
	}
	
    /**
     * A method to instanciate a singleton instance of this class.
     *   
     * @return Returns an instance of {@code ProjectsList} 
     */
	protected static ProjectsList getprojectsList() {
		if(projectsList == null) {
			projectsList = new ProjectsList();
        }
		return projectsList;
	}    


    /**
     * A method that adds a {@code Project} Object to 'this' projects set.
     * 
     * @param project the project to be added to the projects list
     */
    protected void addProject(Project project) {
        projects.add(project);        
    }

    /**
     * A method that returns the {@code Project} items in the projects set as an
     * {@code ArrayList<Project>}.
     * 
     * @return Returns an {@code ArrayList<Project>} of projects.
     */
    protected ArrayList<Project> getProjects() {
        return new ArrayList<Project> (projects);
    }

    /**
     * A method that returns the overdue {@code Project} items in the projects set as
     * an {@code ArrayList<Project>}. A {@code Project} is considered overdue if its
     * {@code Project.deadline} attibute is greater than 'today'.
     * 
     * @return Returns an {@code ArrayList<Project>} of overdue projects.
     */
    protected ArrayList<Project> getOverdueProjects() {
        // Create a buffer set for overdue projects 
        HashSet<Project> overdueProjects = new HashSet<Project>();

        // Add overdue projects to buffer set
        for (Project project: projects) {

            // Compare date to today
            if (project.getDeadline().compareTo(new Date()) < 0) {
                overdueProjects.add(project);
            }
        }
        return new ArrayList<Project> (overdueProjects);
    }

    /**
     * A method that adds a {@code Project} Objet to the completedProjects set and
     *  removes it from the projects set.
     * 
     * @param project the completed project.
     */
    protected void addCompletedProject(Project project) {
        // Add project to completedProjects set
        completedProjects.add(project);  

        // Remove project from projects set
        projects.remove(project);
        
        // Update the projects file 
        this.updateProjectsFile();
    }

    /**
     * A meathod that searches for a {@code Project} Object by the project name.
     * 
     * @param projectName The name of the project being searched for.
     * @return returns the {@code Project} Object with a that project name that 
     *         matches the search string. Returns null if no matches are found.
     */
    protected Project searchProjectByName(String projectName) {
        for (Project project: projects) {
            if(project.getName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }
    
    /**
     * A meathod that searches for a {@code Project} Object by the project number.
     * 
     * @param projectNumber The number of the project being searched for. 
     * @return returns the {@code Project} Object with a that project number
     *         that matches the search string. Returns null if no matches are found.  
     */
    protected Project searchProjectByNumber(String projectNumber) {
        for (Project project: projects) {
            if(project.getProjectNumber().equalsIgnoreCase(projectNumber)) {
                return project;
            }
        }
        return null;
    }

    /**
     * A method that adds a {@code Party} object to one of the party sets, depending
     * on the {@code Party.partyType}.
     * 
     * @param party The party to be added.
     */
    protected void addPary(Party party) {
        String type = party.getPartyType();

        // Add party to set, according to the partyType
        if (type.equalsIgnoreCase("Contractor")) {
            contractors.add(party);
        } 
        else if (type.equalsIgnoreCase("Architect")) {
            architects.add(party);
        } 
        else {
            customers.add(party);
        }        
    }

    /**
     * A method to search for a Party object in any of the parties sets.
     * 
     * @param name the name of the party to be seached.
     * @param partyType the type of the party being searched. (Contractor, Architect,
     *                  or Customer).
     * @return returns the {@code Party} Object that matches the searched name or 
     *         returns null if the party is not found.
     */
    protected Party searchpParty(String name, String partyType) {
        // Update parties sets
        FileReader.readParties();

        // Search for party
        switch (partyType) {
            case "Architect":
                for (Party architect: architects) {
                    if (architect.getName().equals(name)) {
                        return architect;
                    }
                }
        
            case "Contractor":
                for (Party contractor: contractors) {
                    if (contractor.getName().equals(name)) {
                        return contractor;
                    }
                }

            case "Customer":
                for (Party customer: customers) {
                    if (customer.getName().equals(name)) {
                        return customer;
                    }
                }

            default:
                return null;
        }
    }

    /**
     * A method that writes the parties from the customers, contractors, and 
     * architects sets to Parties.txt. 
     */
    protected void updatePartiesFile() {
        // Buffer four output 
        String[] output = new String[1];
        output[0] = "";

        // Add parties to buffer
        customers.forEach((customer) -> {
            output[0] += customer.getTextOutput() + "\n";
        });
        architects.forEach((architect) -> {
            output[0] += architect.getTextOutput() + "\n";
        });
        contractors.forEach((contractor) -> {
            output[0] += contractor.getTextOutput() + "\n";
        });

        // Write updated parties to file 
        FileWriterr.writeToFile("Parties.txt", output[0].trim(), false);
        
        // Update the projects list
        FileReader.readProjects();
    }

    /**
     * A method that writes the projects in the Projects set to Projects.txt.
     */
    protected void updateProjectsFile() {
        // Buffer four output 
        String[] output = new String[1];
        output[0] = "";

        // Add projects to buffer
        projects.forEach((project) -> {
            output[0] += project.getTextOutput() + "\n";
        });

        // Write updated projects to file 
        FileWriterr.writeToFile("Projects.txt", output[0].trim(), false);

        // Update the projects list
        FileReader.readProjects();
    }
}
