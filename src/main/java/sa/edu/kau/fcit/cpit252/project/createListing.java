package sa.edu.kau.fcit.cpit252.project;

// Program to specification: defines the factory contract
// Open for extension: subclasses override createListing() to produce new types
// Single responsibility: only responsible for the creation workflow

public abstract class createListing{

    public abstract Listing createListing();

    public final Listing submitListing(){
        Listing listing = createListing();
        validateListing(listing);
        log(listing);
        return listing;
    }

    private void validateListing(Listing listing){
        if(listing.getCourseCode() == null || listing.getCourseCode().isEmpty()){
            throw new IllegalStateException("Course code is required");
        }
    }

    private void log(Listing listing){
        System.out.println("Listing submitted: " + listing.getSummary());
    }
}
