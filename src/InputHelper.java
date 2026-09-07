import java.util.Scanner;

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

} // End of InputHelper class
