

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
}

public static Parcel findByTrackingNumber(ArrayList<Parcel> packages, String trackingNumber){
    for(Parcel p : packages){
        if(p.getTrackingNumber().equalsIgnoreCase(trackingNumber)){
            return p;
        }
    }
    return null;
}

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
}


    public static void addPackage(Scanner info, ArrayList<Parcel> packages) {
    System.out.println("\n--- Adding a new package ---");

    System.out.print("Enter a recipient name: ");
    String myRecipientName = info.nextLine();

    System.out.print("Enter a tracking number: ");
    String myTrackingNumber = info.nextLine();

    System.out.print("Enter the Carrier: ");
    String myCarrier = info.nextLine();

    System.out.print("Enter the date received: ");
    String myDateReceived = info.nextLine();

    System.out.print("Enter the location: ");
    String myLocation = info.nextLine();

    System.out.print("Is it picked up? (yes/no): ");
    String input = info.nextLine();
    boolean pickedUp = input.equalsIgnoreCase("yes");

    Parcel currentParcel = new Parcel();
    currentParcel.setRecipientName(myRecipientName);
    currentParcel.setTrackingNumber(myTrackingNumber);
    currentParcel.setCarrier(myCarrier);
    currentParcel.setDateReceived(myDateReceived);
    currentParcel.setLocation(myLocation);
    currentParcel.setPickedUp(pickedUp);

    packages.add(currentParcel);
    System.out.println("Package added successfully!");
}

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
}



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
    }

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

}