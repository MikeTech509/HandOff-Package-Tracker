
/**
 * Main entry point for the Handoff Package Tracker application.
 * <p>
 * Loads existing packages on startup, displays an interactive menu,
 * and delegates each user action to {@link PackageService} or
 * {@link FileStorage}. Runs until the user chooses to quit.
 *
 * @author Miketchly-Zar Jean-Francois
 */



import java.util.ArrayList;
import java.util.Scanner;

public class HandoffPacketTracker {

    public static void main(String[] args) {

    Scanner info = new Scanner(System.in);
    ArrayList<Parcel> myPackages = new ArrayList<>();

    FileStorage.loadPackages(myPackages);                       // call my loadPackages Method

    boolean running = true;


    while (running) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   HANDOFF — Package Tracker  v1.0      ║");
        System.out.println("║   Every package. Every handoff.        ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("1. Add a package");
        System.out.println("2. Search by recipient name");
        System.out.println("3. Search by tracking number");
        System.out.println("4. Show all packages");
        System.out.println("5. Mark a package as picked up");
        System.out.println("6. Show pending pickups");
        System.out.println("7. Delete a package");
        System.out.println("8. Quit");

        int choice = InputHelper.promptMenuChoice(info, "Choose an option (1-8): ", 1, 8);

        switch (choice) {
            case 1:
                PackageService.addPackage(info, myPackages);
                FileStorage.savePackages(myPackages);
                break;
            case 2:
                System.out.print("Enter recipient name to search: ");
                String nameSearch = info.nextLine();
                PackageService.searchByRecipient(myPackages, nameSearch);
                break;
            case 3:
                System.out.print("Enter tracking number to search: ");
                String trackingSearch = info.nextLine();
                PackageService.searchByTrackingNumber(myPackages, trackingSearch);
                break;
            case 4:
                PackageService.displayAllPackages(myPackages);
                break;
            case 5:
                PackageService.markAsPickedUp(info, myPackages);
                FileStorage.savePackages(myPackages);
                break;
            case 6 :
                PackageService.displayPendingPackages(myPackages);
                break;
            case 7:
                PackageService.deletePackage(info, myPackages);      
                FileStorage.savePackages(myPackages);                 
                break;
            case 8:
                FileStorage.savePackages(myPackages);
                running = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please pick 1-8.");
        }
    } // end of while loop

    info.close();  // Close the scanner to prevent resource leaks

} // end of main method

} // end of HandoffPacketTracker class
 