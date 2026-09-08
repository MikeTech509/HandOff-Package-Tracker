import java.time.LocalDate;                    //scanner class is used to read input from the user
import java.time.format.DateTimeFormatter;                  //local date class is used to get the current date
import java.time.format.DateTimeParseException;
import java.util.Scanner;   //date time formatter class is used to format the date

public class InputHelper {

    /**
     * Repeatedly prompts the user until they enter a non-empty value.
     *
     * @param info Scanner for reading user input
     * @param prompt the message to display
     * @return the valid, non-empty input
     */

    public static String promptNonEmpty(Scanner info, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = info.nextLine();

            if (input != null && !input.trim().isEmpty()) {
                return input;
            }

            System.out.println("⚠️  This field can't be empty. Try again.");
        }
    } // End of promptNonEmpty method

    /**
     * Repeatedly prompts for a tracking number until it's non-empty
     * and at least 5 characters long.
     *
     * @param info Scanner for reading user input
     * @return the valid tracking number
     */
    public static String promptTrackingNumber(Scanner info) {
        while (true) {
            System.out.print("Enter a tracking number: ");
            String input = info.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("⚠️  Tracking number can't be empty. Try again.");
                continue;
            }

            if (input.trim().length() < 5) {
                System.out.println("⚠️  Tracking number too short (min 5 characters). Try again.");
                continue;
            }

            return input;
        }
    } // End of promptTrackingNumber method

    /**
     * Prompts for a yes/no answer and returns it as a boolean.
     *
     * @param info Scanner for reading user input
     * @param prompt the message to display
     * @return true if the user answered yes, false otherwise
     */

    public static boolean promptYesNo(Scanner info, String prompt) {
        System.out.print(prompt);
        String input = info.nextLine();
        return input.equalsIgnoreCase("yes");
    }
    

    /**
 * Repeatedly prompts until the user enters a valid integer
 * within the given range (inclusive).
 *
 * @param info Scanner for reading user input
 * @param prompt the message to display
 * @param min the smallest acceptable value
 * @param max the largest acceptable value
 * @return the valid integer choice
 */
public static int promptMenuChoice(Scanner info, String prompt, int min, int max) {
    while (true) {
        System.out.print(prompt);
        String input = info.nextLine();

        try {
            int choice = Integer.parseInt(input.trim());
            if (choice >= min && choice <= max) {
                return choice;
            }
            System.out.println("⚠️  Please enter a number between " + min + " and " + max + ".");
        } catch (NumberFormatException e) {
            System.out.println("⚠️  That's not a valid number. Please try again.");
        }
    }
} // End of promptMenuChoice method



/**
 * Returns today's date, formatted as MM/dd/yyyy.
 *
 * @return today's date as a formatted string
 */
public static String getTodayFormatted() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return today.format(formatter);

} // End of getTodayFormatted method

/**
 * Repeatedly prompts for a date in MM/dd/yyyy format until a valid
 * calendar date is entered.
 *
 * @param info Scanner for reading user input
 * @return the valid date as a formatted string
 */
public static String promptValidDate(Scanner info) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    while (true) {
        System.out.print("Enter the date received (MM/dd/yyyy): ");
        String input = info.nextLine();

        try {
            LocalDate parsed = LocalDate.parse(input.trim(), formatter);
            return parsed.format(formatter);
        } catch (DateTimeParseException e) {
            System.out.println("⚠️  That's not a valid date. Please use MM/dd/yyyy (e.g., 08/13/2026).");
        }
    }
}

} // End of InputHelper class
