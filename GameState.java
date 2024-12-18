import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Location currentLocation;
    private final Map<String, Item> inventory = new HashMap<>();

    public GameState(GameManager game) {
        Location entrance = new Location("Entrance", "The main entrance to the bank. You see a blueprint lying here.");
        Location lobby = new Location("Lobby", "The bank lobby is eerily quiet, with marble floors. A teller is standing behind the counter.");
        Location puzzleRoom = new Location("Puzzle Room", "A mysterious room with a locked safe and a keypad.");
        Location vault = new Location("Vault", "The vault door looms large. A guard stands watch. You hear a faint beeping sound from an alarm system.");
        Location managersOffice = new Location("Manager's Office", "The office of the bank manager. It's an elegant room, but looks heavily guarded.");

        entrance.addExit("north", lobby);
        lobby.addExit("south", entrance);
        lobby.addExit("right", puzzleRoom);
        puzzleRoom.addExit("left", lobby);
        lobby.addExit("vault", vault);
        managersOffice.addExit("lobby", lobby);
        vault.addExit("west", lobby);
        lobby.addExit("managersOffice", managersOffice);

        entrance.addItem(new Item("blueprint", "A detailed blueprint of the bank's layout."));
        lobby.addItem(new Item("bribe", "Useful for persuading the guard."));
        puzzleRoom.addItem(new Item("keycard", "Required to unlock the vault."));
        puzzleRoom.addItem(new Item("smokebomb", "A device to create a diversion and sneak past the guard."));
        vault.addItem(new Item("money", "A stash of money from the vault. Escape with it!"));

        lobby.addEntity(new Entity("teller", "A bank teller who might know something about the puzzle."));
        vault.addEntity(new Entity("alarm", "A security alarm that will trigger if the vault isn’t unlocked in time."));
        managersOffice.addEntity(new Entity("manager", "The bank manager, who seems quite suspicious and is watching the room closely."));

        currentLocation = entrance;

        game.addNoun("north");
        game.addNoun("south");
        game.addNoun("right");
        game.addNoun("vault");
        game.addNoun("blueprint");
        game.addNoun("keycard");
        game.addNoun("bribe");
        game.addNoun("smokebomb");
        game.addNoun("teller");
        game.addNoun("alarm");
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        currentLocation = location;
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName.toLowerCase());
    }

    public void addItem(Item item) {
        inventory.put(item.getName().toLowerCase(), item);
    }

    public void removeItem(String itemName) {
        inventory.remove(itemName.toLowerCase());
    }
}
