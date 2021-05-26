
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;


/**
 * {@code HelperFunctions} is class contains the helper functions for handling user
 * input for PoisedProjects.
 */
public class UserInputHandler {

	/**
	 * Gets a date input from the user and checks that it is a date, then returns a 
	 * date object.
	 * 
	 * @return Returns a Date object from the user's input
	 */
    protected static Date dateInput() {
		Scanner userInput = new Scanner(System.in);

        // Get date for deadline
		DateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date;
		
		// Get user input
		try {
			System.out.println("Enter project deadline (dd/mm/yyyy)");
			date = dateParser.parse(userInput.nextLine());
		} 
        catch (ParseException err ){
			System.out.println("\nInvaldid date.");
			return dateInput();
		} 

        return date;
    }

	/**
	 * Accepts a string containing a message to prompt the user for input.
	 * Then checks that the input is a number and then returns the input as
	 * an integer.
	 * @param message A message to prompt the user for input.
	 * @return Returns an {@code int} parsed from the user input
	 */
    protected static int intInput(String message) {
		Scanner userInput = new Scanner(System.in);

		System.out.println(message);		
		int number;
		
		try {
			number = userInput.nextInt();
		} 
        catch (Exception err ){
			System.out.println();
			return intInput("That's not a valid input, bro. 😅\n" + message);
		} 

        return number;
    }

	/**
	 * A method that takes a string message and uses it to prompt a user 
	 * for an input. Then parses a {@code double} from the input and returns it.
	 * 
	 * @param message A message to prompt the user for input.
	 * @return Returns a {@code double} parsed from user input.
	 */
	protected static double dblInput(String message) {
		Scanner userInput = new Scanner(System.in);

		System.out.println(message);		
		double number;
		
		try {
			number = userInput.nextDouble();
		} 
        catch (Exception err ){
			System.out.println();
			return intInput("Eish. That's not a valid input, bro.\n" + message);
		}  

        return number;
    }

	/**
	 * Accepts a string containing a message to prompt the user for input.
	 * Then checks that the input is not empty and then returns the input as
	 * a string.
	 * 
	 * @param message  A message to prompt the user for input.
	 * @return A string of the user input
	 */
    protected static String stringInput(String message) {
		Scanner userInput = new Scanner(System.in);

		System.out.println(message);
		String str;
		
		try {
			str = userInput.nextLine();
            // Make sure that input is not empty
            if (str.split("\s")[0].equals("") || str.split("\s")[0].equals("\n")) {
                System.out.println("\nYou did not enter enything.");
			    return stringInput(message);
            }
		} 
        catch (Exception err ){
                System.out.println("Invalid input.\n");
				return stringInput(message);
		} 
		
        return str;
    }

	/**
	 * This gets the yes no inpuut from user.
	 * 
	 * @param message A string message to prompt the user for input.
	 * @return Returns a {@code boolean} true for yes and false for no.
	 */
	protected static boolean yesNoInput(String message) {
		Scanner userInput = new Scanner(System.in);

		message += "(y/n)\n::"; 
		System.out.println(message);
		String str;
		
		try {
			str = userInput.nextLine();
            // Make sure that input is not empty
            if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
				return true;
			}
			else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")) {
				return false;
			}
			else {
                System.out.println("\nYour input was wack, yo.");
			    return yesNoInput(message);
            }
		} 
        catch (NumberFormatException err ){
			return yesNoInput("That's not the kind of input we need, dude.\n" + message);
		} 
    }

    
}
