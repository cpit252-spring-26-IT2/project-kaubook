package sa.edu.kau.fcit.cpit252.project;

// Structural Design Pattern - Decorator

public class urgentDecorator extends listingDecorator {

    private double urgencyFee;

    public urgentDecorator(Listing listing) {
        super(listing);
        this.urgencyFee = 5.00;
    }

    @Override
    public double getPrice(){
        if(getType().equals("EXCHANGE")){
            return super.getPrice();
        }
        return super.getPrice() + urgencyFee;
    }
    @Override
    public String getSummary(){
        return "[URGENT] " + super.getSummary();
    }
}
