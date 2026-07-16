
import java.util.Scanner;
import java.util.ArrayList;

public class HandoffPacketTracker {
    public static void main(String[] args) {

    Scanner info = new Scanner(System.in);
    ArrayList<Parcel> myPackages = new ArrayList<>();

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
        System.out.println("7. Quit");
        System.out.print("Choose an option (1-7): ");

        int choice = info.nextInt();
        info.nextLine();   // ← don't forget the newline fix!

        switch (choice) {
            case 1:
                addPackage(info, myPackages);
                break;
            case 2:
                System.out.print("Enter recipient name to search: ");
                String nameSearch = info.nextLine();
                searchByRecipient(myPackages, nameSearch);
                break;
            case 3:
                System.out.print("Enter tracking number to search: ");
                String trackingSearch = info.nextLine();
                searchByTrackingNumber(myPackages, trackingSearch);
                break;
            case 4:
                displayAllPackages(myPackages);
                break;
            case 5:
                markAsPickedUp(info, myPackages);
                break;
            case 6 :
                displayPendingPackages(myPackages);
                break;
            case 7:
                running = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please pick 1-7.");
        }
    }

    info.close();
}

public static void displayPendingPackages(ArrayList<Parcel> packages) {
    boolean foundAny = false;

    System.out.println("\n=== Packages waiting for pickup ===\n");
    for (Parcel p : packages) {
        if (!p.isPickedUp) {          // ← ! means NOT
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
        if(p.trackingNumber.equalsIgnoreCase(trackingNumber)){
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

    if (found.isPickedUp) {
        System.out.println("This package was already picked up.");
        return;
    }

    found.isPickedUp = true;
    System.out.println("Marked as picked up for: " + found.recipientName);
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

    System.out.print("Is it picked up? (true/false): ");
    boolean pickedUp = info.nextBoolean();
    info.nextLine();

    Parcel currentParcel = new Parcel();
    currentParcel.recipientName = myRecipientName;
    currentParcel.trackingNumber = myTrackingNumber;
    currentParcel.carrier = myCarrier;
    currentParcel.dateReceived = myDateReceived;
    currentParcel.location = myLocation;
    currentParcel.isPickedUp = pickedUp;

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

            if(parcel.recipientName.toLowerCase().contains(searchTerm.toLowerCase())){
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
            if (parcel.trackingNumber.toLowerCase().contains(searchTerm.toLowerCase())){
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

class Parcel {
    String recipientName;
    String trackingNumber;
    String carrier;
    String dateReceived;
    String location;
    boolean isPickedUp;

    public void displayInfo() {
        System.out.println("Recipient: " + recipientName);
        System.out.println("Tracking number: " + trackingNumber);
        System.out.println("Carrier: " + carrier);
        System.out.println("Date received: " + dateReceived);
        System.out.println("Location: " + location);
        System.out.println("Picked up: " + (isPickedUp ? "yes" : "no"));
    }
}
