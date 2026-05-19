package sa.edu.kau.fcit.cpit252.project;

public class OwnedListing implements Listing {

    private final Listing listing;
    private final String  ownerUsername;

    public OwnedListing(Listing listing, String ownerUsername) {
        this.listing       = listing;
        this.ownerUsername = ownerUsername;
    }

    public String  getOwnerUsername() { return ownerUsername; }
    public Listing getWrappedListing(){ return listing; }

    @Override public String getCourseCode() { return listing.getCourseCode(); }
    @Override public String getCondition()  { return listing.getCondition();  }
    @Override public String getType()       { return listing.getType();       }
    @Override public double getPrice()      { return listing.getPrice();      }
    @Override public String getSummary()    { return listing.getSummary();    }
}