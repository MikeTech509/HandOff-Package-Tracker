/**
 * Provides operations for managing the collection of packages.
 * <p>
 * Includes adding new packages, searching by recipient name or
 * tracking number (partial, case-insensitive), marking packages as
 * picked up, deleting packages, and filtering pending pickups.
 *
 * @author Miketchly-Zar Jean-Francois
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PackageService {

    public static void displayPendingPackages(ArrayList<Parcel> packages) {
        boolean foundAny = false;

        System.out.println("\n=== Packages waiting for pickup ===\n");
        for (Parcel p : packages) {
            if (!p.isPickedUp()) {
                p.displayInfo();
                System.out.println();
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("All packages have been picked up! 🎉");
        }
    } // End of displayPendingPackages method

    /**
     * Fast lookup of a package by tracking number using the HashMap index.
     */
    public static Parcel findByTrackingNumber(HashMap<String, Parcel> packagesByTracking, String trackingNumber) {
        return packagesByTracking.get(trackingNumber.toUpperCase());
    } // End of findByTrackingNumber method

    /**
     * Searches a small, already-filtered list for an exact tracking number match.
     * Used only when narrowing down results within a subset (e.g., name matches),
     * not for general lookups — use findByTrackingNumber for those.
     */
    private static Parcel findInList(ArrayList<Parcel> list, String trackingNumber) {
        for (Parcel p : list) {
            if (p.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                return p;
            }
        }
        return null;
    } // End of findInList method

    public static void markAsPickedUp(Scanner info, ArrayList<Parcel> packages,
                                        HashMap<String, Parcel> packagesByTracking) {
        System.out.print("\nEnter the tracking number to mark as picked up: ");
        String trackingNumber = info.nextLine();

        Parcel found = findByTrackingNumber(packagesByTracking, trackingNumber);

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

    public static void addPackage(Scanner info, ArrayList<Parcel> packages,
                                    HashMap<String, Parcel> packagesByTracking) {
        System.out.println("\n--- Adding a new package ---");

        String myRecipientName = InputHelper.promptNonEmpty(info, "Enter a recipient name: ");
        String myTrackingNumber = InputHelper.promptTrackingNumber(info);
        String myCarrier = InputHelper.promptNonEmpty(info, "Enter the Carrier: ");

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

        System.out.println("Package type: ");
        System.out.println(" 1 = Regular");
        System.out.println(" 2 = Fragile");
        System.out.println(" 3 = Large");
        System.out.println(" 4 = Fragile and Large");
        System.out.print("Enter type (1-4): ");
        String typeInput = info.nextLine();

        Parcel currentParcel;
        switch (typeInput) {
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
        }

        packages.add(currentParcel);
        packagesByTracking.put(currentParcel.getTrackingNumber().toUpperCase(), currentParcel);
        System.out.println("Package added successfully!");
    } // End of addPackage method

    public static void deletePackage(Scanner info, ArrayList<Parcel> packages,
                                       HashMap<String, Parcel> packagesByTracking) {
        System.out.println("\nDelete by:");
        System.out.println(" 1 = Tracking number");
        System.out.println(" 2 = Recipient name");
        System.out.print("Choose (1-2): ");
        String choice = info.nextLine();

        if (choice.equals("2")) {
            deletePackageByName(info, packages, packagesByTracking);
        } else {
            deletePackageByTrackingNumber(info, packages, packagesByTracking);
        }
    } // End of deletePackage method

    private static void deletePackageByTrackingNumber(Scanner info, ArrayList<Parcel> packages,
                                                         HashMap<String, Parcel> packagesByTracking) {
        System.out.print("\nEnter the tracking number to delete: ");
        String trackingNumber = info.nextLine();

        Parcel found = findByTrackingNumber(packagesByTracking, trackingNumber);

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
        packagesByTracking.remove(found.getTrackingNumber().toUpperCase());
        System.out.println("Package deleted successfully.");
    } // End of deletePackageByTrackingNumber method

    private static void deletePackageByName(Scanner info, ArrayList<Parcel> packages,
                                              HashMap<String, Parcel> packagesByTracking) {
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
            found = findInList(matches, trackingNumber);

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
        packagesByTracking.remove(found.getTrackingNumber().toUpperCase());
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

    public static void searchByRecipient(ArrayList<Parcel> packages, String searchTerm) {
        boolean foundAny = false;

        for (Parcel parcel : packages) {
            if (parcel.getRecipientName().toLowerCase().contains(searchTerm.toLowerCase())) {
                parcel.displayInfo();
                System.out.println();
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No Packages found for: " + searchTerm);
        }
    } // End of searchByRecipient method

    static void searchByTrackingNumber(ArrayList<Parcel> packages, String searchTerm) {
        boolean foundAny = false;

        for (Parcel parcel : packages) {
            if (parcel.getTrackingNumber().toLowerCase().contains(searchTerm.toLowerCase())) {
                parcel.displayInfo();
                System.out.println();
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No packages found for: " + searchTerm);
        }
    } // End of searchByTrackingNumber method

} // End of PackageService class