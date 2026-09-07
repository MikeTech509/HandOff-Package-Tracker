public class LargePackage extends Parcel implements Large {

    public LargePackage(String recipientName, String trackingNumber, 
                    String carrier, String dateReceived, String location, boolean isPickedUp){
        
                        super(recipientName, trackingNumber, carrier, 
                        dateReceived, location, isPickedUp);

    } // Constructor for LargePackage class

    @Override
    public void displayInfo(){
        super.displayInfo();
        showLargeWarning();
    } 
    
}
