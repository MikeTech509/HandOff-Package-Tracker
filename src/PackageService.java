

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
    String myDateReceived = InputHelper.promptNonEmpty(info, "Enter the date received: ");
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