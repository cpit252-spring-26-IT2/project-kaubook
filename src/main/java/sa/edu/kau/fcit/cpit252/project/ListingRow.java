package sa.edu.kau.fcit.cpit252.project;

public class ListingRow {

    private final String  courseCode;
    private final String  type;
    private final String  condition;
    private final String  price;
    private final String  tags;
    private final String  summary;
    private final String  owner;
    private final Listing originalListing;

    public ListingRow(Listing l) {
        this.originalListing = l;

        Listing base = l;
        if (l instanceof OwnedListing ol) {
            this.owner = ol.getOwnerUsername();
            base       = ol.getWrappedListing();
        } else {
            this.owner = "system";
        }

        this.courseCode = base.getCourseCode();
        this.type       = base.getType();
        this.condition  = base.getCondition();
        this.price      = base.getType().equals("SELL")
                ? String.format("%.2f SAR", base.getPrice()) : "Free";
        this.tags       = buildTags(base);
        this.summary    = base.getSummary();
    }

    private String buildTags(Listing l) {
        boolean urgent   = false;
        boolean verified = false;
        Listing current  = l;
        while (current instanceof listingDecorator d) {
            if (current instanceof urgentDecorator)   urgent   = true;
            if (current instanceof verifiedDecorator) verified = true;
            current = d.getWrapped();
        }
        return (urgent ? "🔥 Urgent " : "") + (verified ? "✓ Verified" : "");
    }

    public String  getCourseCode()     { return courseCode;     }
    public String  getType()           { return type;           }
    public String  getCondition()      { return condition;      }
    public String  getPrice()          { return price;          }
    public String  getTags()           { return tags;           }
    public String  getSummary()        { return summary;        }
    public String  getOwner()          { return owner;          }
    public Listing getOriginalListing(){ return originalListing;}
}

