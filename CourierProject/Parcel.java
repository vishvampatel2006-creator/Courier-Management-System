public class Parcel {
    private String trackingID;
    private String recipientName;
    private String destination;
    private String status;

    // Parameterized Constructor
    public Parcel(String trackingID, String recipientName, String destination, String status) {
        this.trackingID = trackingID;
        this.recipientName = recipientName;
        this.destination = destination;
        this.status = status;
    }

    // Public Getters
    public String getTrackingID() { return trackingID; }
    public String getRecipientName() { return recipientName; }
    public String getDestination() { return destination; }
    public String getStatus() { return status; }

    // Public Setters
    public void setStatus(String status) { this.status = status; }

    // Format string for File I/O
    public String toCSV() {
        return trackingID + "," + recipientName + "," + destination + "," + status;
    }

    // Clean UI output format
    @Override
    public String toString() {
        return String.format("Tracking ID: %-6s | Recipient: %-15s | Destination: %-12s | Status: %s", 
                             trackingID, recipientName, destination, status);
    }
}