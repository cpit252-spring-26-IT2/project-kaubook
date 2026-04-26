package sa.edu.kau.fcit.cpit252.project;

public class verfiedDecorator extends listingDecorator {

    private String verifierName;
    private double userRating;
    private double minimumRating;

    public verfiedDecorator(Listing listing, String verifierName, double userRating, double minimumRating) {
        super(listing);
        this.verifierName = verifierName;
        this.userRating = userRating;
        this.minimumRating = minimumRating;
    }
    @Override
    public String getSummary(){
        if (verifierName != null && userRating >= minimumRating){
            return super.getSummary() + "Verified by " + verifierName + " & Rating: " + userRating;
        }
        else if (verifierName != null && userRating < minimumRating){
            return super.getSummary() + " Verified by: " + verifierName;
        }
        else if (verifierName == null && userRating >= minimumRating){
            return super.getSummary() + " Verified by Rating: " + userRating;
        }
        return super.getSummary();
    }

}
