public class LargePackage extends Parcel implements Large {

    @Override
    public void displayInfo(){
        super.displayInfo();
        showLargeWarning();
    } 
    
}
