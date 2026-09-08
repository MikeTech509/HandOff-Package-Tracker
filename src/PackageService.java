

/**
 * Provides operations for managing the collection of packages.
 * <p>
 * Includes adding new packages, searching by recipient name or
 * tracking number (partial, case-insensitive), marking packages as
 * picked up, and filtering pending pickups. All methods operate on
 * an {@code ArrayList<Parcel>} passed in by the caller.
 *
 * @author Miketchly-Zar Jean-Francois
 */



import java.util.ArrayList;
import java.util.Scanner;

public class PackageService {

    public static void displayPendingPackages(ArrayList<Parcel> packages) {
    boolean foundAny = false;

    System.out.println("\n=== Packages waiting for pickup ===\n");
    for (Parcel p : packages) {
        if (!p.isPickedUp()) {          // ← ! means NOT
            p.displayInfo();
            System.out.println();
            foundAny = true;
        }
    }

    if (!foundAny) {
        System.out.println("All packages have been picked up! 🎉");
    }
} // End of displayPendingPackages method

public static Parcel findByTrackingNumber(ArrayList<Parcel> packages, String trackingNumber){
    for(Parcel p : packages){
        if(p.getTrackingNumber().equalsIgnoreCase(trackingNumber)){
            return p;
        }
    }
    return null;
} // End of findByTrackingNumber method

public static void  markAsPickedUp(Scanner info, ArrayList<Parcel> packages){
    System.out.print("\nEnter the tracking number to mark as picked up: ");
    String trackingNumber = info.nextLine();

    Parcel found = findByTrackingNumber(packages, trackingNumber);

    if (found == null) {
        System.out.println("No package found with tracking number: " + trackingNumber);
        return;
    }

    if (found.isPickedUp()) {
        System.out.println("This package was already picked up.");
        return;
    }

    found.setPickedUp(true);
    System.out.println("Marked as picked up for: " + found.getRecipientName());

} // End of markAsPickedUp method


    public static void addPackage(Scanner info, ArrayList<Parcel> packages) {
    System.out.println("\n--- Adding a new package ---");

    String myRecipientName = InputHelper.promptNonEmpty(info, "Enter a recipient name: ");
    String myTrackingNumber = InputHelper.promptTrackingNumber(info);
    String myCarrier = InputHelper.promptNonEmpty(info, "Enter the Carrier: ");

    // Ask the user if they want to use today's date or enter a custom date
    boolean useToday = InputHelper.promptYesNo(info, "Use today's date? (yes/no): ");
String myDateReceived;
if (useToday) {
    myDateReceived = InputHelper.getTodayFormatted();
    System.out.println("Date received set to: " + myDateReceived);
} else {
    myDateReceived = InputHelper.promptValidDate(info); 
}
    String myLocation = InputHelper.promptNonEmpty(info, "Enter the location: ");
    boolean pickedUp = InputHelper.promptYesNo(info, "Is it picked up? (yes/no): ");

    // Ask the user what type of package it is
    System.out.println("Package type: ");
    System.out.println(" 1 = Regular");
    System.out.println(" 2 = Fragile");
    System.out.println(" 3 = Large");
    System.out.println(" 4 = Fragile and Large");
    System.out.print("Enter type (1-4): ");
    String typeInput = info.nextLine();

    Parcel currentParcel;
    switch(typeInput){
        case "2":
            currentParcel = new FragilePackage(myRecipientName, myTrackingNumber, myCarrier,
                                                 myDateReceived, myLocation, pickedUp);
            break;
        case "3":
            currentParcel = new LargePackage(myRecipientName, myTrackingNumber, myCarrier,
                                               myDateReceived, myLocation, pickedUp);
            break;
        case "4":
            currentParcel = new FragileLargePackage(myRecipientName, myTrackingNumber, myCarrier,
                                                      myDateReceived, myLocation, pickedUp);
            break;
        default:
            currentParcel = new Parcel(myRecipientName, myTrackingNumber, myCarrier,
                                         myDateReceived, myLocation, pickedUp);
            break;
    }

    packages.add(currentParcel);
    System.out.println("Package added successfully!");
} // End of addPackage method


/**
 * Prompts the user for a tracking number, shows the matching package,
 * and deletes it after confirmation.
 * <p>
 * If no package matches, prints an error and returns without changes.
 *
 * @param info Scanner for reading user input
 * @param packages the collection to delete from
 */

public static void deletePackage(Scanner info, ArrayList<Parcel> packages) {
        System.out.println("\nDelete by:");
        System.out.println(" 1 = Tracking number");
        System.out.println(" 2 = Recipient name");
        System.out.print("Choose (1-2): ");
        String choice = info.nextLine();

        if (choice.equals("2")) {
            deletePackageByName(info, packages);
        } else {
            deletePackageByTrackingNumber(info, packages);
        }
    } // End of deletePackage method

private  static void deletePackageByTrackingNumber(Scanner info, ArrayList<Parcel> packages) {
    System.out.print("\nEnter the tracking number to delete: ");
    String trackingNumber = info.nextLine();

    Parcel found = findByTrackingNumber(packages, trackingNumber);

    if (found == null) {
        System.out.println("No package found with tracking number: " + trackingNumber);
        return;
    }

    System.out.println("\nFound this package:");
    found.displayInfo();

    boolean confirmed = InputHelper.promptYesNo(info, "\nAre you sure you want to delete this package? (yes/no): ");

    if (!confirmed) {
        System.out.println("Delete cancelled.");
        return;
    }

    packages.remove(found);
    System.out.println("Package deleted successfully.");

} // End of deletePackageByTrackingNumber method

private static void deletePackageByName(Scanner info, ArrayList<Parcel> packages) {
        System.out.print("\nEnter the recipient name to delete: ");
        String searchTerm = info.nextLine();

        ArrayList<Parcel> matches = new ArrayList<>();
        for (Parcel p : packages) {
            if (p.getRecipientName().toLowerCase().contains(searchTerm.toLowerCase())) {
                matches.add(p);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No packages found for: " + searchTerm);
            return;
        }

        Parcel found;
        if (matches.size() == 1) {
            found = matches.get(0);
        } else {
            System.out.println("\nMultiple packages match \"" + searchTerm + "\":\n");
            for (Parcel p : matches) {
                p.displayInfo();
                System.out.println();
            }
            System.out.print("Enter the tracking number of the one to delete: ");
            String trackingNumber = info.nextLine();
            found = findByTrackingNumber(matches, trackingNumber);

            if (found == null) {
                System.out.println("No match found with that tracking number among the results.");
                return;
            }
        }

        System.out.println("\nFound this package:");
        found.displayInfo();

        boolean confirmed = InputHelper.promptYesNo(info, "\nAre you sure you want to delete this package? (yes/no): ");

        if (!confirmed) {
            System.out.println("Delete cancelled.");
            return;
        }

        packages.remove(found);
        System.out.println("Package deleted successfully.");
    } // End of deletePackageByName method


public static void displayAllPackages(ArrayList<Parcel> packages) {
    if (packages.isEmpty()) {
        System.out.println("\nNo packages in the system yet.");
        return;
    }

    System.out.println("\n=== All packages ===\n");
    for (Parcel p : packages) {
        p.displayInfo();
        System.out.println();
    }
    System.out.println("Total packages: " + packages.size());

} // End of displayAllPackages method



    public static void searchByRecipient(ArrayList<Parcel> packages, String searchTerm){
        boolean foundAny = false;

        for(Parcel parcel : packages){

            if(parcel.getRecipientName().toLowerCase().contains(searchTerm.toLowerCase())){
                parcel.displayInfo();
                System.out.println();
                foundAny = true;
            }
        }

        if(!foundAny){
            System.out.println("No Packages found for: " + searchTerm);
        }
    } // End of searchByRecipient method


    static void searchByTrackingNumber(ArrayList<Parcel> packages, String searchTerm ){
        boolean foundAny = false;

        for(Parcel parcel : packages){
            if (parcel.getTrackingNumber().toLowerCase().contains(searchTerm.toLowerCase())){
                parcel.displayInfo();
                System.out.println();
                foundAny = true;
            }
        }

        if (!foundAny){
            System.out.println("No packages found for: " + searchTerm);
        }
    }

} // End of PackageService class