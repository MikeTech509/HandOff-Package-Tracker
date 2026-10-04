public class FragilePackage extends Parcel implements Fragile{

    public FragilePackage(String recipientName, String trackingNumber, String carrier, String dateReceived, String location, PackageStatus status){
        
        super(recipientName, trackingNumber, carrier, dateReceived, location, status);

    } // Constructor for FragilePackage class

    @Override
    public void displayInfo(){
        super.displayInfo();
        showFragileWarning();

    } // displayInfo method for FragilePackage class
    
} // FragilePackage class