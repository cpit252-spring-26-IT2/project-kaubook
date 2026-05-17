package sa.edu.kau.fcit.cpit252.project;

/**
 * Hello world!
 */

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== KAUBook - Factory Method Pattern ===\n");

        // --- Factory Method: create listings ---
        createListing sellCreator = new sellListingCreator("CPIT-252", Condition.GOOD, 45.0);
        Listing sellListing = sellCreator.submitListing();
        databaseManager.getInstance().addListing(sellListing);


        createListing borrowCreator = new borrowListingCreator("MATH-101", Condition.FAIR, 7);
        Listing borrowListing = borrowCreator.submitListing();
        databaseManager.getInstance().addListing(borrowListing);

        createListing exchangeCreator = new exchangeListingCreator("PHYS-110", Condition.NEW, "CHEM-101");
        Listing exchangeListing = exchangeCreator.submitListing();
        databaseManager.getInstance().addListing(exchangeListing);

        System.out.println("\n=== KAUBook - Decorator Pattern ===\n");

        // Urgent sell listing — adds 5.00 SAR urgency fee
        Listing urgentSell = new urgentDecorator(sellListing);
        databaseManager.getInstance().addListing(urgentSell);
        System.out.println("Urgent:   " + urgentSell.getSummary());
        System.out.println("Price after urgency fee: " + urgentSell.getPrice() + " SAR");

        System.out.println();

        // Verified borrow listing — verifier name + rating
        Listing verifiedBorrow = new verifiedDecorator(borrowListing, "Ahmed", 4.5, 4.0);
        databaseManager.getInstance().addListing(verifiedBorrow);
        System.out.println("Verified: " + verifiedBorrow.getSummary());

        System.out.println();

        // Stacked: urgent + verified on exchange listing
        Listing urgentVerifiedExchange = new urgentDecorator(
                new verifiedDecorator(exchangeListing, "Sara", 4.8, 4.0));
        databaseManager.getInstance().addListing(urgentVerifiedExchange);
        System.out.println("Urgent + Verified: " + urgentVerifiedExchange.getSummary());
        System.out.println("Price after urgency fee: " + urgentVerifiedExchange.getPrice() + " SAR");


        // Stage 3: apply Strategy Pattern to sort listings
        System.out.println("\n=== KAUBook - Strategy Pattern ===\n");

        List<Listing> listings = databaseManager.getInstance().getListings();

//        List<Listing> listings = new ArrayList<>();
//        listings.add(sellListing);
//        listings.add(borrowListing);
//        listings.add(exchangeListing);
//        listings.add(urgentSell);
//        listings.add(verifiedBorrow);
//        listings.add(urgentVerifiedExchange);

        ListingManager manager = new ListingManager();

        // Choose a sorting strategy at runtime
        manager.setStrategy(new SortByPriceStrategy());
        System.out.println("Sorted By Price:");
        // Print listings after applying the selected sort strategy
        printListings(manager.sortListings(listings));

        System.out.println();

        manager.setStrategy(new SortByTypeStrategy());
        System.out.println("Sorted By Type:");
        printListings(manager.sortListings(listings));

        System.out.println();

        manager.setStrategy(new SortByCourseCodeStrategy());
        System.out.println("Sorted By Course Code:");
        printListings(manager.sortListings(listings));
    }

    private static void printListings(List<Listing> listings) {
        for (Listing listing : listings) {
            System.out.println(listing.getSummary());
        }
    }
}
