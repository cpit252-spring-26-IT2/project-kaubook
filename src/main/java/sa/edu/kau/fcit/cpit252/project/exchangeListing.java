package sa.edu.kau.fcit.cpit252.project;

// Single responsibility: handles only exchange listings
// Closed for modification, open for extension via Listing interface

public class exchangeListing implements Listing{
    private String courseCode;
    private String condition;
    private String wantedCourseCode;

    public exchangeListing(String courseCode, String condition, String wantedCourseCode){
        this.courseCode = courseCode;
        this.condition = condition;
        this.wantedCourseCode = wantedCourseCode;
    }

    @Override
    public String getCourseCode(){
        return courseCode;
    }
    @Override
    public String getCondition(){
        return condition;
    }
    @Override
    public String getType(){
        return "EXCHANGE";
    }
    @Override
    public double getPrice(){
        return 0.0;
    }
    @Override
    public String getSummary(){
        return "[EXCHANGE] "+ courseCode + " | Condition: " + condition + " | Wants: " + wantedCourseCode;
    }
}
