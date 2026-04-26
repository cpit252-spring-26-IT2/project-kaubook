package sa.edu.kau.fcit.cpit252.project;

// Single responsibility: handles only borrow listings
// Closed for modification, open for extension via Listing interface

public class borrowListing implements Listing{
    private String courseCode;
    private Condition condition;
    private int durationDays;

    public borrowListing(String courseCode, Condition condition, int durationDays) {
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
        return condition.name();
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
        return "[BORROW] "+ courseCode + " | Condition: " + condition.name() + " | Duration: " + durationDays + " days";
    }
}
