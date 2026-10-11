
// LostItem.java
public class LostItem extends Item {
    private String dateLost; // simplified as String (e.g., "2025-10-01")

    public LostItem(String itemName, String category, String description, String location, String dateLost) {
        super(itemName, category, description, location, "Lost");
        this.dateLost = dateLost;
    }

    public String getDateLost() { return dateLost; }
    public void setDateLost(String d) { dateLost = d; }

    @Override
    public String toString() {
        return super.toString() + " | Date Lost: " + dateLost;
    }
}