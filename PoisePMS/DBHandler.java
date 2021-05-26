import java.sql.*;
import java.util.Map;

/**
 * {@code DBHandler} is a class that handles the interactions between the PoiseProjects
 * application and the PoisePMS database.
 */
public class DBHandler {

    // Attribute to hold the only instance of DBHandler
    private static DBHandler DB = null;
    // Attribute to hold the connection to the database
    Connection connection;
    
    // Get instance of ProjectsList
	protected static ProjectsList projectsList = ProjectsList.getprojectsList();

    /**
     * Private Constructor. Implements a singleton design pattern so that there is only
     * 1 instance of DBHandler.
     */
    private DBHandler() {
        try {
            // Connect to the ebokostore database, via the jdbc:mysql:channel on localhost 
            this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
                "kingquack",
                "kingquack"
            );
        } 
        catch (SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    } 

    /**
     * The method that creates and returns only one instance of DBHandler. 
     * @return Returns only 1 instance of DBHandler.
     */
    protected static DBHandler getDbHandler() {
        if (DB == null) {
            DB = new DBHandler();

        }
        return DB;
    }

	/**
	 * A method that reads the contents of PoisedPMS. The method then creates
	 * {@code Party} Objects from the contents and then adds the the sets in the
	 * {@code ProjectsList} instance.
     * @param type The type of party to search for. (Architect, Contractor, or Customer)
	 */
    private void readParties(String type) {
        try {
            // Create a statement and results variable
            Statement statement = connection.createStatement();
            ResultSet results;

            // Execute query
            results = statement.executeQuery("SELECT * FROM "+type+"s");

            // Add results to projects list
            String name, phone, address, email;
            while (results.next()) {
                String partyType = type;
                name = results.getString("name");
                phone = results.getString("phone");
                address = results.getString("address");
                email = results.getString("email");

                // Create Party Objects then add them to ProjectsList
                projectsList.addPary(new Party(name, phone, email, address, partyType));
            }		
		}
		catch (Exception err) {
			System.out.println("Party reading has failed");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
		}
    }

    /**
     * A method that calls {@code readParties(String type)} with type as "Constractor",
     * "Customer", and "Architect".
     */
    protected void readParties() {
        for (String party: new String[] {"Contractor", "Architect", "Customer"}) {
            this.readParties(party);
        }
    }

    /** 
	 * A method that reads the projects from PoisePMS and adds them to the 
	 * projects list. These are the projects that are still ongoing.
	 */
	protected void readProjects() {
		try {

            // Create a statement and results variable
            Statement statement = connection.createStatement();
            ResultSet results, partyResult;

            // Execute query
            results = statement.executeQuery("SELECT * FROM Projects");

            // Add results to projects list
            String name, projectNumber, type, address, erf;
            double fee, paidAmount;
            Party architect=null, contractor=null, customer=null;
            Date deadline;
            
            while (results.next()) {
                name = results.getString("name");
				projectNumber = results.getString("proj_num");
				type = results.getString("type");
				address = results.getString("address");
				erf = results.getString("erf");
				fee = results.getDouble("fee");
				paidAmount = results.getDouble("paid_amount");
                deadline = results.getDate("deadline");

                // System.out.println(deadline + name);
                
				// Get party objects for Project
                statement = connection.createStatement();
                partyResult = statement.executeQuery("SELECT * FROM Architects WHERE id = "+ results.getInt("architect"));
                while (partyResult.next()) {
                    architect = new Party(partyResult.getString("name"), partyResult.getString("phone"), partyResult.getString("email"), partyResult.getString("address"), "Architect");
                    // System.out.println(architect);
                }
                statement = connection.createStatement();
                partyResult = statement.executeQuery("SELECT * FROM Contractors WHERE id = "+ results.getInt("contractor"));
                while (partyResult.next()) {
                    contractor = new Party(partyResult.getString("name"), partyResult.getString("phone"), partyResult.getString("email"), partyResult.getString("address"), "Contractor");
                    // System.out.println(contractor);
                }
                statement = connection.createStatement();
                partyResult = statement.executeQuery("SELECT * FROM Customers WHERE id = "+ results.getInt("customer"));
                while (partyResult.next()) {
                    customer = new Party(partyResult.getString("name"), partyResult.getString("phone"), partyResult.getString("email"), partyResult.getString("address"), "Customer");
                }

                // Create Project instance and add to projectsList
				projectsList.addProject(new Project(name, projectNumber, type, address, erf, fee, deadline, architect, contractor, customer, paidAmount));
		
            }
		}
		catch (Exception err) {
			System.out.println("Project reading has failed");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
		}
	}

    /**
     * A method that takes the attributes of a {@code Party} and writes them to the 
     * database. 
     * @param party The attributes of the {@code Party} to write to the database.
     */
    protected void writeParty(Map<String,String> party) {
        try {
            Statement statement = connection.createStatement();
            int rowsAffected;
            
            // Add a a record
            rowsAffected = statement.executeUpdate(
                "INSERT INTO "+party.get("partyType")+"s (name, phone, email, address)"
                + "VALUES ('"+party.get("name")+"', '"+party.get("phone")+"', '"+party.get("email")+"', '"+party.get("address")+"')"
            );
            System.out.println("\nQuery complete, " + rowsAffected + " rows added. :)\n");
        } 
        catch (Exception err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }

    /**
     * This method is used to get the id of the parties from the names of {@code Project}
     * Objects. It searches for the name and returns the id.
     * @param name The name of the Party.
     * @param type The type of the party. ("Architect", "Contractor", or "Cusotomer")
     * @return Returns the id of the party from the database.
     */
    private int getPartyID(String name, String type) {
        try {
            // Create a statement and results variable
            Statement statement = connection.createStatement();
            ResultSet results;

            // Execute query
            results = statement.executeQuery("SELECT id FROM "+type+"s WHERE name = '"+name+"'");

            int id =-1;
            while (results.next()) {
                id = results.getInt("id");
            }
            return id;
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
        return -1;
    }

    /**
     * A method to wite {@code Project} Objects to the database.
     * @param project The project to be written to the database.
     */
    protected void writeProject(Map<String,String> project) {
        // Fields to write to database
        String name = project.get("name");
        String proj_num = project.get("proj_num");
        String type = project.get("type");
        String address = project.get("address");
        String erf = project.get("erf");
        double fee = Double.parseDouble(project.get("fee"));
        double paid_amount = Double.parseDouble(project.get("paid_amount"));
        String deadline = project.get("deadline");
        int architect = this.getPartyID(project.get("architect"), "Architect");
        int contractor = this.getPartyID(project.get("contractor"), "Contractor");
        int customer = this.getPartyID(project.get("customer"), "customer");

        try {
            // Create a statement and rows affected variable
            Statement statement = connection.createStatement();
            int rowsAffected;
            
            // Add a a record
            rowsAffected = statement.executeUpdate(
                "INSERT INTO Projects (name, proj_num, type, address, erf, fee, paid_amount, deadline, architect, contractor, customer)"
                + "VALUES" 
                +"('"+name+"','"+proj_num+"', '"+type+"', '"+address+"', '"+erf+"', "+fee+", "+paid_amount+", '"+deadline+"', "+architect+", "+contractor+", "+customer+")"
            );
            System.out.println("\nQuery complete, " + rowsAffected + " rows added.\n");
        } 
        catch (Exception err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }
    
    /**
     * A method to write completed projects to the database.
     * @param project The project to be written to the database.
     */
    protected void writeCompletedPrjoject(Map<String,String> project) {
        // Fields to write to database
        String name = project.get("name");
        String proj_num = project.get("proj_num");
        String type = project.get("type");
        String address = project.get("address");
        String erf = project.get("erf");
        double fee = Double.parseDouble(project.get("fee"));
        String compl_date = project.get("compl_date");
        String deadline = project.get("deadline");
        int architect = this.getPartyID(project.get("architect"), "Architect");
        int contractor = this.getPartyID(project.get("contractor"), "Contractor");
        int customer = this.getPartyID(project.get("customer"), "customer");

        try {
            // Create a statement and rows affected variable
            Statement statement = connection.createStatement();
            int rowsAffected;
            
            // Add a a record
            rowsAffected = statement.executeUpdate(
                "INSERT INTO CompletedProjects (name, proj_num, type, address, erf, fee, compl_date, deadline, architect, contractor, customer)"
                + "VALUES" 
                +"('"+name+"','"+proj_num+"', '"+type+"', '"+address+"', '"+erf+"', "+fee+", '"+compl_date+"', '"+deadline+"', "+architect+", "+contractor+", "+customer+")"
            );
            System.out.println("\nQuery complete, " + rowsAffected + " rows added.\n");
        } 
        catch (Exception err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }
    
    /**
     * A method that updates the fields of a {@code Project} Object in the database.
     * @param project The project to be written to the database.
     */
    protected void updateProject(Map<String,String> project) {
        // Fields to write to database
        String name = project.get("name");
        String proj_num = project.get("proj_num");
        String erf = project.get("erf");
        double paid_amount = Double.parseDouble(project.get("paid_amount"));
        String deadline = project.get("deadline");
        int architect = this.getPartyID(project.get("architect"), "Architect");
        int contractor = this.getPartyID(project.get("contractor"), "Contractor");
        int customer = this.getPartyID(project.get("customer"), "customer");

        try {
            // Create a statement and rows affected variable
            Statement statement = connection.createStatement();
            int rowsAffected;
            
            // Add a a record
            rowsAffected = statement.executeUpdate(
                "UPDATE Projects "
                +"SET name='"+name+"',  paid_amount="+paid_amount+", deadline='"+deadline+"', architect="+architect+", contractor="+contractor+", customer="+customer+" "
                + "wHERE erf='"+erf+"' AND proj_num='"+proj_num+"';"
            );
            System.out.println("\nQuery complete, " + rowsAffected + " rows edited. \n");
        } 
        catch (Exception err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }

    /**
     * A method to remove a project record from the database.
     * @param proj_num The project number of the project to be removed.
     * @param erf The ERF number of the project to be removed.
     */
    protected void removeProject(String proj_num, String erf) {
        try {
            Statement statement = connection.createStatement();
            int rowsAffected;

            // Delete the record
            rowsAffected = statement.executeUpdate("DELETE FROM Projects WHERE proj_num = '"+proj_num+"' and erf='"+erf+"';");
            System.out.println("\nQuery complete, " + rowsAffected + " rows deleted.");
        } 
        catch (Exception err) {
            System.out.println("Error deleting Project");
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }

}
