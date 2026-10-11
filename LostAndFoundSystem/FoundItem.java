
// FoundItem.java
public class FoundItem extends Item {
    private String dateFound;

    public FoundItem(String itemName, String category, String description, String location, String dateFound) {
        super(itemName, category, description, location, "Found");
        this.dateFound = dateFound;
    }

    public String getDateFound() { return dateFound; }
    public void setDateFound(String d) { dateFound = d; }

    @Override
    public String toString() {
        return super.toString() + " | Date Found: " + dateFound;
    }
}