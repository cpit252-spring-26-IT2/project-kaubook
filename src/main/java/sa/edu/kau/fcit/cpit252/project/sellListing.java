package sa.edu.kau.fcit.cpit252.project;

// Single responsibility: handles only sell listings
// Closed for modification, open for extension via Listing interface

public class sellListing implements Listing{
    private String courseCode;
    private String condition;
    private double price;

    public sellListing(String courseCode, String condition, double price) {
        this.courseCode = courseCode;
        this.condition = condition;
        this.price = price;

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
        return "SELL";
    }
    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public String getSummary() {
        return "[SELL] "+courseCode+" | Condition: "+condition+" | Price: "+price + " SAR";
    }
}
