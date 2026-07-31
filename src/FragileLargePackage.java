public class FragileLargePackage extends Parcel implements Fragile, Large {
    @Override
    public void displayInfo(){
        super.displayInfo();
        showFragileWarning();
        showLargeWarning();
    }

}
