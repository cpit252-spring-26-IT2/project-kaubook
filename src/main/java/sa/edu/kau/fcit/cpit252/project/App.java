package sa.edu.kau.fcit.cpit252.project;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== KAUBook - Factory Method Pattern ===\n");

        // --- Factory Method: create listings ---
        createListing sellCreator = new sellListingCreator("CPIT-252", "Good", 45.0);
        Listing sellListing = sellCreator.submitListing();

        createListing borrowCreator = new borrowListingCreator("MATH-101", "Fair", 7);
        Listing borrowListing = borrowCreator.submitListing();

        createListing exchangeCreator = new exchangeListingCreator("PHYS-110", "Excellent", "CHEM-101");
        Listing exchangeListing = exchangeCreator.submitListing();

        System.out.println("\n=== KAUBook - Decorator Pattern ===\n");

        // Urgent sell listing — adds 5.00 SAR urgency fee
        Listing urgentSell = new urgentDecorator(sellListing);
        System.out.println("Urgent:   " + urgentSell.getSummary());
        System.out.println("Price after urgency fee: " + urgentSell.getPrice() + " SAR");

        System.out.println();

        // Verified borrow listing — verifier name + rating
        Listing verifiedBorrow = new verifiedDecorator(borrowListing, "Ahmed", 4.5, 4.0);
        System.out.println("Verified: " + verifiedBorrow.getSummary());

        System.out.println();

        // Stacked: urgent + verified on exchange listing
        Listing urgentVerifiedExchange = new urgentDecorator(
                new verifiedDecorator(exchangeListing, "Sara", 4.8, 4.0));
        System.out.println("Urgent + Verified: " + urgentVerifiedExchange.getSummary());
        System.out.println("Price after urgency fee: " + urgentVerifiedExchange.getPrice() + " SAR");
    }
}
