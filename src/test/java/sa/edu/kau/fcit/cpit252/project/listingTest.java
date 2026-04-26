package sa.edu.kau.fcit.cpit252.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class listingTest {

    // ─── Factory Method Tests ───────────────────────────────────────────────

    @Test
    public void testSellListingCreator() {
        createListing creator = new sellListingCreator("CPIT-252", "Good", 45.0);
        Listing listing = creator.submitListing();

        assertEquals("CPIT-252", listing.getCourseCode());
        assertEquals("Good", listing.getCondition());
        assertEquals("SELL", listing.getType());
        assertEquals(45.0, listing.getPrice());
        assertTrue(listing.getSummary().contains("[SELL]"));
    }

    @Test
    public void testBorrowListingCreator() {
        createListing creator = new borrowListingCreator("MATH-101", "Fair", 7);
        Listing listing = creator.submitListing();

        assertEquals("MATH-101", listing.getCourseCode());
        assertEquals("Fair", listing.getCondition());
        assertEquals("BORROW", listing.getType());
        assertEquals(0.0, listing.getPrice());
        assertTrue(listing.getSummary().contains("[BORROW]"));
        assertTrue(listing.getSummary().contains("7 days"));
    }

    @Test
    public void testExchangeListingCreator() {
        createListing creator = new exchangeListingCreator("PHYS-110", "Excellent", "CHEM-101");
        Listing listing = creator.submitListing();

        assertEquals("PHYS-110", listing.getCourseCode());
        assertEquals("Excellent", listing.getCondition());
        assertEquals("EXCHANGE", listing.getType());
        assertEquals(0.0, listing.getPrice());
        assertTrue(listing.getSummary().contains("[EXCHANGE]"));
        assertTrue(listing.getSummary().contains("CHEM-101"));
    }

    @Test
    public void testEmptyCourseCodeThrowsException() {
        createListing creator = new sellListingCreator("", "Good", 45.0);
        assertThrows(IllegalStateException.class, creator::submitListing);
    }

    @Test
    public void testNullCourseCodeThrowsException() {
        createListing creator = new sellListingCreator(null, "Good", 45.0);
        assertThrows(IllegalStateException.class, creator::submitListing);
    }

    // ─── Decorator Tests ────────────────────────────────────────────────────

    @Test
    public void testUrgentDecoratorAddsFee() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing urgent = new urgentDecorator(listing);

        assertEquals(50.0, urgent.getPrice()); // 45.0 + 5.0 urgency fee
        assertTrue(urgent.getSummary().contains("[URGENT]"));
    }

    @Test
    public void testUrgentDecoratorOnBorrowListing() {
        Listing listing = new borrowListing("MATH-101", "Fair", 7);
        Listing urgent = new urgentDecorator(listing);

        assertEquals(5.0, urgent.getPrice()); // 0.0 + 5.0 urgency fee
        assertTrue(urgent.getSummary().contains("[URGENT]"));
    }

    @Test
    public void testUrgentDecoratorOnExchangeListingNoFee() {
        Listing listing = new exchangeListing("PHYS-110", "Excellent", "CHEM-101");
        Listing urgent = new urgentDecorator(listing);

        assertEquals(0.0, urgent.getPrice()); // no fee for exchange
        assertTrue(urgent.getSummary().contains("[URGENT]"));
    }

    @Test
    public void testVerifiedDecoratorWithNameAndHighRating() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing verified = new verifiedDecorator(listing, "Ahmed", 4.5, 4.0);

        assertTrue(verified.getSummary().contains("Verified by"));
        assertTrue(verified.getSummary().contains("Ahmed"));
        assertTrue(verified.getSummary().contains("4.5"));
    }

    @Test
    public void testVerifiedDecoratorWithNameAndLowRating() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing verified = new verifiedDecorator(listing, "Ahmed", 3.0, 4.0);

        assertTrue(verified.getSummary().contains("Verified by"));
        assertTrue(verified.getSummary().contains("Ahmed"));
        assertFalse(verified.getSummary().contains("Rating: 3.0"));
    }

    @Test
    public void testVerifiedDecoratorWithNoNameAndHighRating() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing verified = new verifiedDecorator(listing, null, 4.5, 4.0);

        assertTrue(verified.getSummary().contains("Rating: 4.5"));
    }

    @Test
    public void testVerifiedDecoratorWithNoNameAndLowRating() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing verified = new verifiedDecorator(listing, null, 3.0, 4.0);

        // Falls to default: just the original summary
        assertEquals(listing.getSummary(), verified.getSummary());
    }

    @Test
    public void testUrgentAndVerifiedDecoratorsStacked() {
        Listing listing = new sellListing("CPIT-252", "Good", 45.0);
        Listing urgentVerified = new urgentDecorator(new verifiedDecorator(listing, "Ahmed", 4.5, 4.0));

        assertEquals(50.0, urgentVerified.getPrice());
        assertTrue(urgentVerified.getSummary().contains("[URGENT]"));
        assertTrue(urgentVerified.getSummary().contains("Verified by"));
    }
}

