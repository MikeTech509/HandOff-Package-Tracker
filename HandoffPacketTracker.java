
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class HandoffPacketTracker {

    static final String DATA_FILE = System.getProperty("user.home") + "/handoff_data.csv";

    public static void main(String[] args) {

    Scanner info = new Scanner(System.in);
    ArrayList<Parcel> myPackages = new ArrayList<>();

    loadPackages(myPackages);                       // call my loadPackages Method

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
                savePackages(myPackages);
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
                savePackages(myPackages);
                break;
            case 6 :
                displayPendingPackages(myPackages);
                break;
            case 7:
                savePackages(myPackages);
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

public static void savePackages(ArrayList<Parcel> packages){
    
   try{
    PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE));
    for(Parcel p: packages){
        writer.println(p.getRecipientName()  + "," +
                       p.getTrackingNumber() + "," +
                       p.getCarrier()        + "," +
                       p.getDateReceived()   + "," +
                       p.getLocation()       + "," +
                       p.isPickedUp()
        );
    }

    writer.close();
    System.out.println("Saved " + packages.size() + " packages to handoff_data.csv");

   } 
   
   catch(IOException e){
    System.out.println("Error Message: " + e.getMessage());
   }
}

public static void loadPackages(ArrayList<Parcel> packages){
    File file = new File(DATA_FILE);

    if(!file.exists()){
        System.out.println("Starting fresh! No saved data found.");
        return;
    }
    try{
        Scanner fileReader = new Scanner(file);

        while(fileReader.hasNextLine()){
        String line = fileReader.nextLine();
        String[] parts = line.split(",");

        Parcel p = new Parcel();
        p.setRecipientName(parts[0]); 
        p.setTrackingNumber(parts[1]);
        p.setCarrier(parts[2]);
        p.setDateReceived(parts[3]);
        p.setLocation(parts[4]);
        p.setPickedUp(Boolean.parseBoolean(parts[5]));

        packages.add(p);
        }

        fileReader.close();
        System.out.println("Loaded " + packages.size() + " from disk.");

    } 
    catch (FileNotFoundException e){

        System.out.println("Error loading: " + e.getMessage());
    }

    

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

class Parcel {
    private String recipientName;
    private String trackingNumber;
    private String carrier;
    private String dateReceived;
    private String location;
    private boolean isPickedUp;

    public String getRecipientName(){
        return recipientName;
    }

    public String getTrackingNumber(){
        return trackingNumber;
    }

    public String getCarrier(){
        return carrier;
    }

    public String getDateReceived(){
        return dateReceived;
    }

    public String getLocation(){
        return location;
    }

    public boolean isPickedUp(){
        return isPickedUp;
    }

    public void setRecipientName(String recipientName) {
    if (recipientName == null || recipientName.trim().isEmpty()) {
        System.out.println("⚠️  Recipient name can't be empty. Change ignored.");
        return;
    }
    this.recipientName = recipientName;
}

    public  void setTrackingNumber(String trackingNumber){
        if(trackingNumber == null || trackingNumber.trim().isEmpty()){
            System.out.println("⚠️  Tracking number can't be empty. Change ignored.");
        return;
        }

        if(trackingNumber.trim().length() < 5){
            System.out.println("⚠️  Tracking number too short (min 5 characters). Change ignored.");
        return;
        }
        this.trackingNumber = trackingNumber;
    }

    public void setCarrier(String carrier){

        if(carrier == null || carrier.trim().isEmpty()){
            System.out.println("Carrier can't be empty. Change ignored. ");
            return;
        }
        this.carrier = carrier;
    }


    

    public void setLocation(String location) {
    if (location == null || location.trim().isEmpty()) {
        System.out.println("⚠️  Location can't be empty. Change ignored.");
        return;
    }
    this.location = location;
}

    public void setPickedUp(boolean isPickedUp){
        this.isPickedUp = isPickedUp;
    }

    public void setDateReceived(String dateReceived) {
    if (dateReceived == null || dateReceived.trim().isEmpty()) {
        System.out.println("⚠️  Date received can't be empty. Change ignored.");
        return;
    }
    this.dateReceived = dateReceived;
}

    public void displayInfo() {
        System.out.println("Recipient: " + recipientName);
        System.out.println("Tracking number: " + trackingNumber);
        System.out.println("Carrier: " + carrier);
        System.out.println("Date received: " + dateReceived);
        System.out.println("Location: " + location);
        System.out.println("Picked up: " + (isPickedUp ? "yes" : "no"));
    }
}
