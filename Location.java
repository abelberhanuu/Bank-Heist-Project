import java.util.HashMap;
import java.util.Map;

public class Location {
    private final String name;
    private final String description;
    private final Map<String, Item> items = new HashMap<>();
    private final Map<String, Location> exits = new HashMap<>();
    private final Map<String, Entity> entities = new HashMap<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Location location) {
        exits.put(direction.toLowerCase(), location);
    }

    public Location getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }

    public Item getItem(String itemName) {
        return items.get(itemName.toLowerCase());
    }

    public void removeItem(Item item) {
        items.remove(item.getName().toLowerCase());
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getName().toLowerCase(), entity);
    }

    public Entity getEntity(String entityName) {
        return entities.get(entityName.toLowerCase());
    }

    public String getItemNames() {
        if (items.isEmpty()) {
            return "There are no items here.";
        }
        StringBuilder result = new StringBuilder("Items here: ");
        for (String itemName : items.keySet()) {
            result.append(itemName).append(", ");
        }
        return result.substring(0, result.length() - 2) + ".";
    }

    public String getEntityNames() {
        if (entities.isEmpty()) {
            return "There are no notable entities here.";
        }
        StringBuilder result = new StringBuilder("You see: ");
        for (String entityName : entities.keySet()) {
            result.append(entityName).append(", ");
        }
        return result.substring(0, result.length() - 2) + ".";
    }

    public String getExitNames() {
        if (exits.isEmpty()) {
            return "There are no exits here.";
        }
        StringBuilder result = new StringBuilder("Exits: ");
        for (String exit : exits.keySet()) {
            result.append(exit).append(", ");
        }
        return result.substring(0, result.length() - 2) + ".";
    }
}
