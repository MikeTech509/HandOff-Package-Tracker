

/**
 * Represents a single package received at the front desk.
 * <p>
 * Stores recipient name, tracking number, carrier, date received,
 * physical location, and pickup status. All fields are private and
 * validated through their setters — empty or null values are rejected
 * at the model layer.
 *
 * @author Miketchly-zar Jean-Francois
 */


public class Parcel {
    private String recipientName;
    private String trackingNumber;
    private String carrier;
    private String dateReceived;
    private String location;
    private PackageStatus status = PackageStatus.ARRIVED;

    public Parcel(String recipientName, String trackingNumber, String carrier, String dateReceived, String location, PackageStatus status){
        setRecipientName(recipientName);
        setTrackingNumber(trackingNumber);
        setCarrier(carrier);
        setDateReceived(dateReceived);
        setLocation(location);
        setStatus(status);
    }


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

    public PackageStatus getStatus(){
    return status;
}

    public boolean isPickedUp(){
        return status == PackageStatus.RELEASED;
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

    public void setStatus(PackageStatus status) {
    if (status == null) {
        System.out.println("⚠️  Status can't be null. Change ignored.");
        return;
    }
    this.status = status;
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
        System.out.println("Status: " + status.getLabel());
    }
}