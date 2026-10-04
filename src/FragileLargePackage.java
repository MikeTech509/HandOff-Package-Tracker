 public class FragileLargePackage extends Parcel implements Fragile, Large {

    public FragileLargePackage(String recipientName, String trackingNumber, 
                    String carrier, String dateReceived, String location, PackageStatus status){
        
                        super(recipientName, trackingNumber, carrier, 
                        dateReceived, location, status);

    } // Constructor for FragileLargePackage class
    @Override
    public void displayInfo(){
        super.displayInfo();
        showFragileWarning();
        showLargeWarning();
    }

}
