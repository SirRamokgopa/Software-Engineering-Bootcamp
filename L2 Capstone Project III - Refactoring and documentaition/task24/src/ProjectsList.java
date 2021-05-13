import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


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
	
	protected static ProjectsList getprojectsList() {
		if(projectsList == null) {
			projectsList = new ProjectsList();
        }
		return projectsList;
	}    


    protected void addProject(Project project) {
        projects.add(project);        
    }

    protected ArrayList<Project> getProjects() {
        return new ArrayList<Project> (projects);
    }

    protected ArrayList<Project> getOverdueProjects() {
        HashSet<Project> overdueProjects = new HashSet<Project>();

        for (Project project: projects) {
            if (project.getDeadline().compareTo(new Date()) < 0) {
                overdueProjects.add(project);
            }
        }
        return new ArrayList<Project> (overdueProjects);
    }

    protected void addCompletedProject(Project project) {
        completedProjects.add(project);  
        projects.remove(project);
        this.updateProjectsFile();
    }

    protected Project searchProjectByName(String projectName) {
        for (Project project: projects) {
            if(project.getName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }
    protected Project searchProjectByNumber(String projectNumber) {
        for (Project project: projects) {
            if(project.getProjectNumber().equalsIgnoreCase(projectNumber)) {
                return project;
            }
        }
        return null;
    }

    protected void addPary(Party party) {
        String type = party.getPartyType();
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

    protected Party searchpParty(String name, String partyType) {

        FileReader.readParties();

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

    protected void updatePartiesFile() {
        String[] output = new String[1];
        output[0] = "";
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

    protected void updateProjectsFile() {
        String[] output = new String[1];
        output[0] = "";
        projects.forEach((contractor) -> {
            output[0] += contractor.getTextOutput() + "\n";
        });

        // Write updated projects to file 
        FileWriterr.writeToFile("Projects.txt", output[0].trim(), false);

        // Update the projects list
        FileReader.readProjects();
    }
}
