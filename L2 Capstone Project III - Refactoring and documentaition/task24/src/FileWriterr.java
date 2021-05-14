import java.io.FileWriter;

/**
 * {@code FileWriterr} is a class that hadles writing to file for PoisedProjects.
 */
public class FileWriterr {
    
	/**
	 * Writes a completed project to "Completed projects.txt". This method calls 
	 * the {@code writeToFile()} method with append set to true.
	 * 
	 * @param output the data to be written to file
	 */
	protected static void writeCompleted(String output) {
		writeToFile("Completed.txt", output, true);
	}

	/**
	 * A method that calls the {@code writeToFile()} method with append set to true
	 * 
	 * @param filename the name of the file to be written to.
	 * @param output the data to be written to file.
	 */
	protected static void writeToFile(String filename, String output) {
		writeToFile(filename, output, true);
	}

	/**
	 * Writes the contents of the {@code outut} parameter to a file specified by the 
	 * {@code filename} parameter.
	 * 
	 * @param filename The name(including extention) of the file that will be written.  
	 * @param output A fromatted string that will be written to file.
	 * @param append Appends to file if set to true and overwites the file if set to 
	 * 				 false.
	 */
	protected static void writeToFile(String filename, String output, boolean append) {

		if (append) {
			output = "\n" + output;
		}

		try {
            // Create a file writer object. Set true for appending.
            FileWriter writer = new FileWriter(filename, append);
            
            // Write output to file
            writer.write(output);

            // Close file
            writer.close();
        } 
        catch (Exception err) {
            // Catch errors in writing to file
			System.out.println("Error writing to file!");
            System.out.println(err.getMessage());
            System.out.println("");
            err.printStackTrace();
        } 
	}
}
