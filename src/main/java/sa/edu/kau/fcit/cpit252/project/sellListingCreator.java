package sa.edu.kau.fcit.cpit252.project;

// Each creator has a single responsibility: create its specific listing type.
// New listing types can be added by extending ListingCreator — no existing code modified.

public class sellListingCreator extends createListing{
    private String courseCode;
    private Condition condition;
    private double price;

    public sellListingCreator(String courseCode, Condition condition, double price)
    {
        this.courseCode = courseCode;
        this.condition = condition;
        this.price = price;
    }

    @Override
    public Listing createListing(){
        return new sellListing(courseCode, condition, price);
    }

}