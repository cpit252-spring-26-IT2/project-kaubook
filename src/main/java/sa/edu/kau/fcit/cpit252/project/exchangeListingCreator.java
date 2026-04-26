package sa.edu.kau.fcit.cpit252.project;

// Each creator has a single responsibility: create its specific listing type.
// New listing types can be added by extending ListingCreator — no existing code modified.

public class exchangeListingCreator extends createListing{
    private String courseCode;
    private Condition condition;
    private String wantedCourseCode;

    public exchangeListingCreator(String courseCode, Condition condition, String wantedCourseCode){
        this.courseCode = courseCode;
        this.condition = condition;
        this.wantedCourseCode = wantedCourseCode;
    }

    @Override
    public Listing createListing(){
        return new exchangeListing(courseCode, condition, wantedCourseCode);
    }
}
