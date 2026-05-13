package sa.edu.kau.fcit.cpit252.project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByPriceStrategy implements ListingSortStrategy {

    @Override
    public List<Listing> sort(List<Listing> listings) {
        List<Listing> sortedListings = new ArrayList<>(listings);
        sortedListings.sort(Comparator.comparingDouble(Listing::getPrice));
        return sortedListings;
    }
}
