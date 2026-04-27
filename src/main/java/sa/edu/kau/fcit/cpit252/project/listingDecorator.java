package sa.edu.kau.fcit.cpit252.project;

// Structural Pattern - Decorator

public abstract class listingDecorator implements Listing {

    private Listing listing;

    public listingDecorator(Listing listing) {

        this.listing = listing;
    }

    @Override
    public String getCourseCode() {
        return listing.getCourseCode();}
    @Override
    public String getCondition() {

        return listing.getCondition();
    }
    @Override
    public String getType(){

        return listing.getType();
    }
    @Override
    public double getPrice(){

        return listing.getPrice();
    }
    @Override
    public String getSummary(){

        return listing.getSummary();
    }
}
