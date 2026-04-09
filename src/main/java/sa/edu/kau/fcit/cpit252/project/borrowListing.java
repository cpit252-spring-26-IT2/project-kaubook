package sa.edu.kau.fcit.cpit252.project;

// Single responsibility: handles only borrow listings
// Closed for modification, open for extension via Listing interface

public class borrowListing implements Listing{
    private String courseCode;
    private String condition;
    private int durationDays;

    public borrowListing(String courseCode, String condition, int durationDays) {
        this.courseCode = courseCode;
        this.condition = condition;
        this.durationDays = durationDays;
    }

    @Override
    public String getCourseCode() {
        return courseCode;
    }
    @Override
    public String getCondition() {
        return condition;
    }
    @Override
    public String getType(){
        return "BORROW";
    }
    @Override
    public double getPrice(){
        return 0.0;
    }
    @Override
    public String getSummary(){
        return "[BORROW] "+ courseCode + " | Condition: " + condition + " | Duration: " + durationDays + " days";
    }
}
