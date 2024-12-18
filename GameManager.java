import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private String playerName;
    private final Map<String, String> verbs = new HashMap<>();
    private final Map<String, String> nouns = new HashMap<>();
    public static final String LINE_BREAK = "----------------------------------------";

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addVerb(String verb, String description) {
        verbs.put(verb, description);
    }

    public void addNoun(String noun) {
        nouns.put(noun, noun);
    }

    public boolean validCommand(String[] command) {
        if (command.length > 0) {
            String verb = command[0];
            return verbs.containsKey(verb);
        }
        return false;
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void print(String message, boolean withLineBreak, boolean withLine) {
        if (withLineBreak) System.out.println(LINE_BREAK);
        System.out.println(message);
        if (withLine) System.out.println(LINE_BREAK);
    }

    public void printHelp() {
        print("Help - Available Commands:", true, false);
        print("Use the following commands to interact with the game:", false, false);

        // Command list
        print("- look: Describes your current location, lists items, and available exits.", false, false);
        print("- go [direction]: Moves to a new location (e.g., 'go north').", false, false);
        print("- take [item]: Picks up an item (e.g., 'take blueprint').", false, false);
        print("- solve: Solves a puzzle, if one exists in the current location.", false, false);
        print("- use [item] [target]: Uses an item on a target (e.g., 'use keycard vault').", false, false);
        print("- ask [entity]: Interacts with a specific entity (e.g., 'ask teller').", false, false);
        print("- help: Displays this quick reference for commands.", false, false);
        print("- instructions: Provides detailed tips and a guide for completing the game.", false, false); // Added instructions
        print("- quit: Exits the game.", false, false);

        print(LINE_BREAK, false, true);
    }

    public void printInstructions() {
        print("Instructions - How to Play and Beat the Game:", true, false);
        print("Your goal is to complete the heist by solving puzzles, collecting items, and bypassing obstacles.", false, false);
        print("", false, false);

        // Detailed instructions
        print("1. Navigate the Bank: Use the 'go [direction]' command to move between locations. Directions might include 'north', 'south', or 'vault'.", false, false);
        print("2. Examine Locations: Use the 'look' command to view your surroundings, including items and entities present in the current location.", false, false);
        print("3. Pick Up Items: Use the 'take [item]' command to collect useful items, such as the 'blueprint', 'keycard', or 'smokebomb'.", false, false);
        print("4. Solve the Puzzle: Visit the Puzzle Room and use the 'solve' command to solve the puzzle. The solution will help you progress in the game.", false, false);
        print("5. Use Items: Use the 'use [item] [target]' command to interact with objects or entities. For example:", false, false);
        print("   - 'use smokebomb guard': Use the smokebomb to bypass the guard.", false, false);
        print("   - 'use keycard vault': Unlock the vault to win the game.", false, false);
        print("6. Ask for Hints: Use the 'ask [entity]' command to get hints from characters like the teller. For example:", false, false);
        print("   - 'ask teller': The teller will give you a hint for solving the puzzle.", false, false);
        print("", false, false);

        // Winning the game
        print("To complete the game for Ending 1(Play the game to find the other endings(｡•̀ᴗ-)✧):", false, false);
        print("- Solve the puzzle in the Puzzle Room to acquire the keycard and smokebomb.", false, false);
        print("- Use the smokebomb to bypass the guard in the Vault Room.", false, false);
        print("- Use the keycard to unlock the vault and win the game.", false, false);
        print("", false, false);

        print("Type 'help' for a quick list of commands or 'instructions' to view these tips again.", false, true);
    }

    public void printExitMessage() {
        print("Thank you for playing, " + playerName + "! Goodbye!", true, true);
    }
}
