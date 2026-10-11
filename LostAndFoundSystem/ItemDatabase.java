
// ItemDatabase.java
import java.util.ArrayList;
import java.util.List;

public class ItemDatabase {
    private List<Item> items;

    public ItemDatabase() {
        items = new ArrayList<>();
    }

    public void addItem(Item it) {
        items.add(it);
    }

    public List<Item> search(String keyword) {
        keyword = keyword == null ? "" : keyword.trim().toLowerCase();
        List<Item> results = new ArrayList<>();
        for (Item it : items) {
            if (it.getItemName().toLowerCase().contains(keyword)
             || it.getCategory().toLowerCase().contains(keyword)
             || it.getDescription().toLowerCase().contains(keyword)
             || it.getLocation().toLowerCase().contains(keyword)
             || String.valueOf(it.getItemId()).contains(keyword)) {
                results.add(it);
            }
        }
        return results;
    }

    public List<Item> getAll() {
        return new ArrayList<>(items);
    }

    public List<Item> getByStatus(String status) {
        List<Item> res = new ArrayList<>();
        for (Item it : items) if (it.getStatus().equalsIgnoreCase(status)) res.add(it);
        return res;
    }
}
