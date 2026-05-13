package sa.edu.kau.fcit.cpit252.project;

import java.util.List;

public class ListingManager {

    private ListingSortStrategy strategy;

    public void setStrategy(ListingSortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Listing> sortListings(List<Listing> listings) {
        if (strategy == null) {
            throw new IllegalStateException("Sorting strategy is not set.");
        }
        return strategy.sort(listings);
    }
}
