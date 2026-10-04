public enum PackageStatus {
    ARRIVED("Arrived"),
    NOTIFIED("Notified"),
    READY_FOR_PICKUP("Ready for pickup"),
    RELEASED("Released");

    private final String label;

    PackageStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}