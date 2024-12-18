import java.util.*;

public class Player {
    private int health;
    private Set<Item> inventory;

    public Player() {
        this.health = 100; // Default health
        this.inventory = new HashSet<>();
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Set<Item> getInventory() {
        return inventory;
    }
}
