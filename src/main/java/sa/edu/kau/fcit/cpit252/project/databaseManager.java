package sa.edu.kau.fcit.cpit252.project;

import java.util.ArrayList;
import java.util.List;

public class databaseManager {
    private static databaseManager instance;
    private List<String> students;
    private List<Listing> listings;
   // private List<String> requests;

    private databaseManager (){
        students = new ArrayList<>();
        listings = new ArrayList<>();
      //  requests = new ArrayList<>();
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
    public void removeListing(String courseID){

        for(Listing listing : listings){
            if (courseID.equals(listing.getCourseCode())){
                listings.remove(listing);
            }
        }
    }
    public void addStudent(String student){
        students.add(student);
    }
    public List<String> getStudent(){
        return students;
    }
}
