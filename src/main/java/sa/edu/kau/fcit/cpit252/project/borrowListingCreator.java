package sa.edu.kau.fcit.cpit252.project;

// Each creator has a single responsibility: create its specific listing type.
// New listing types can be added by extending ListingCreator — no existing code modified.

public class borrowListingCreator extends createListing {
    private String courseCode;
    private String condition;
    private int durationDays;

    public borrowListingCreator(String courseCode, String condition, int durationDays) {
        this.courseCode = courseCode;
        this.condition = condition;
        this.durationDays = durationDays;
    }

    @Override
    public Listing createListing() {
        return new borrowListing(courseCode, condition, durationDays);
    }
}
