public class LargePackage extends Parcel implements Large {

    public LargePackage(String recipientName, String trackingNumber, 
                    String carrier, String dateReceived, String location, PackageStatus status){
        
                        super(recipientName, trackingNumber, carrier, 
                        dateReceived, location, status);

    } // Constructor for LargePackage class

    @Override
    public void displayInfo(){
        super.displayInfo();
        showLargeWarning();
    } 
    
}
