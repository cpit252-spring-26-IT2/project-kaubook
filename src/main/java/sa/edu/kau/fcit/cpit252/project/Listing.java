package sa.edu.kau.fcit.cpit252.project;

public interface Listing {
    String getCourseCode();  // CPIT-252, CPIT-380
    String getCondition();   // New, Old
    String getType();        // Borrow, Sell, Exchange
    double getPrice();       // Free, 10 Sar
    String getSummary();     // All information above
}
