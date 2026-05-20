package sa.edu.kau.fcit.cpit252.project;

public class ListingRequest {

    private Listing listing;
    private String ownerUsername;
    private String requesterUsername;
    private RequestStatus status;
    private String pickupTime;
    private String campusLocation;

    public ListingRequest(Listing listing, String ownerUsername, String requesterUsername) {
        this.listing = listing;
        this.ownerUsername = ownerUsername;
        this.requesterUsername = requesterUsername;
        this.status = RequestStatus.PENDING;
    }

    public Listing getListing() {
        return listing;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void approve() {
        status = RequestStatus.APPROVED;
    }

    public void reject() {
        status = RequestStatus.REJECTED;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getCampusLocation() {
        return campusLocation;
    }

    public void schedulePickup(String pickupTime, String campusLocation) {
        this.pickupTime = pickupTime;
        this.campusLocation = campusLocation;
    }

    @Override
    public String toString() {
        return listing.getCourseCode()
                + " | Owner: " + ownerUsername
                + " | Status: " + status;
    }


}
