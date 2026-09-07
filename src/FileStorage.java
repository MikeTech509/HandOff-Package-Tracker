

/**
 * Handles persistent storage of packages to and from disk.
 * <p>
 * Saves the current list of packages as CSV to a local data file
 * ({@code data/handoff_data.csv}) and loads them back at startup.
 * The data folder is created automatically if it doesn't exist.
 *
 * @author Miketchly-Zar Jean-Francois
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileStorage {

    static final String DATA_FILE = "data/handoff_data.csv";

public static void savePackages(ArrayList<Parcel> packages) {
    try {
        new File("data").mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE));
        for (Parcel p : packages) {
            writer.println(p.getRecipientName() + "," +
                           p.getTrackingNumber() + "," +
                           p.getCarrier() + "," +
                           p.getDateReceived() + "," +
                           p.getLocation() + "," +
                           p.isPickedUp() + "," +
                           (p instanceof FragileLargePackage ? "fragile-large" 
                            : p instanceof FragilePackage ? "fragile"
                            : p instanceof LargePackage ? "large" 
                            :"regular"));
        }
        writer.close();
        System.out.println("Saved " + packages.size() + " packages to handoff_data.csv");
    } catch (IOException e) {
        System.out.println("Error Message: " + e.getMessage());
    }
}

public static void loadPackages(ArrayList<Parcel> packages) {
    File file = new File(DATA_FILE);
    if (!file.exists()) {
        System.out.println("Starting fresh! No saved data found.");
        return;
    }
    try {
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] parts = line.split(",");

            boolean pickedUp = Boolean.parseBoolean(parts[5]); 
            String type = parts.length >= 7 ? parts [6] : "regular"; 
        
            Parcel p;

            switch (type) { 
                case "fragile":
                    p = new FragilePackage(parts[0], parts[1], parts[2], parts[3], parts[4], pickedUp); 
                    break;
                case "large":
                    p = new LargePackage(parts[0], parts[1], parts[2], parts[3], parts[4], pickedUp);
                    break;
                case "fragile-large":
                    p = new FragileLargePackage(parts[0], parts[1], parts[2], parts[3], parts[4], pickedUp);
                    break;
                default:
                    p = new Parcel(parts[0], parts[1], parts[2], parts[3], parts[4], pickedUp);
            }
            packages.add(p);
              
        }
        fileReader.close();
        System.out.println("Loaded " + packages.size() + " from disk.");
    } catch (FileNotFoundException e) {
        System.out.println("Error loading: " + e.getMessage());
    }
}

}
