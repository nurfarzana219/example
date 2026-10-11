
// Item.java
public abstract class Item {
    private static int nextId = 1;
    protected int itemId;
    protected String itemName;
    protected String category;
    protected String description;
    protected String location;
    protected String status; // "Lost" or "Found"

    public Item(String itemName, String category, String description, String location, String status) {
        this.itemId = nextId++;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.status = status;
    }

    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }

    public void setItemName(String s) { itemName = s; }
    public void setCategory(String s) { category = s; }
    public void setDescription(String s) { description = s; }
    public void setLocation(String s) { location = s; }
    public void setStatus(String s) { status = s; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) - %s | Loc: %s | Status: %s",
                itemId, itemName, category, description, location, status);
    }
}
