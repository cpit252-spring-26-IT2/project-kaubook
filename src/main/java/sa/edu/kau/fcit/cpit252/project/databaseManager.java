package sa.edu.kau.fcit.cpit252.project;

import java.util.ArrayList;
import java.util.List;

public class databaseManager {
    private static databaseManager instance;
    private List<User> users;
    private List<Listing> listings;
    private User loggedUser;
    private List<ListingRequest> requests = new ArrayList<>();
   // private List<String> requests;

    private databaseManager (){
        users = new ArrayList<>();
        listings = new ArrayList<>();
      //  requests = new ArrayList<>();

        // default accounts
        users.add(new User("admin", "admin123", true));
        users.add(new User("Ali",  "Ali123",  false));
        users.add(new User("Mohammed", "Mohammed123", false));
    }
    public static databaseManager getInstance(){
        if(instance == null){
            instance = new databaseManager();
        }
        return instance;
    }
    public void addListing(Listing listing){
        listings.add(listing);
    }
    public List<Listing> getListings(){
        return listings;
    }
    public void removeListing(Listing listing){
        listings.remove(listing);
        }

    // ── Users & Auth ────────────────────────────────────────────────
    public User login(String username, String password){
        for(User user : users){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                loggedUser = user;
                return loggedUser;
            }
        }
        return null; // wrong credentials
    }

    public User getLoggedUser(){
        return loggedUser;
    }

    public void logout(){
        loggedUser = null;
    }


    public void addRequest(ListingRequest request) {
        requests.add(request);
    }

    public List<ListingRequest> getRequests() {
        return requests;
    }

    public List<ListingRequest> getRequestsForOwner(String ownerUsername) {
        return requests.stream()
                .filter(r -> r.getOwnerUsername().equals(ownerUsername))
                .toList();
    }

    public List<ListingRequest> getRequestsByRequester(String requesterUsername) {
        return requests.stream()
                .filter(r -> r.getRequesterUsername().equals(requesterUsername))
                .toList();
    }
}
