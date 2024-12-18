import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameManager game = new GameManager();
        GameState state = new GameState(game);
        CommandSystem commandSystem = new CommandSystem(state, game);

        // Initialize verbs
        game.addVerb("help", "Show the list of available commands.");
        game.addVerb("look", "Look around at your current location.");
        game.addVerb("go", "Move in a specified direction.");
        game.addVerb("take", "Pick up an item.");
        game.addVerb("solve", "Solve a puzzle.");
        game.addVerb("use", "Use an item on something.");
        game.addVerb("quit", "Exit the game.");

        // Greet the player and ask for their name
        System.out.println(GameManager.LINE_BREAK);
        System.out.println("Welcome to the Bank Heist Game!");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        game.setPlayerName(playerName);

        System.out.println("Hello, " + playerName + "! Your mission is to complete the heist.");
        System.out.println("Type 'help' for a list of commands.");
        System.out.println(GameManager.LINE_BREAK);

        boolean running = true;

        while (running) {
            System.out.println("What would you like to do?");
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println("No input detected. Exiting game...");
                break;
            }
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                running = false;
                game.printExitMessage();
            } else {
                String[] command = input.split(" ");
                commandSystem.processCommand(command);
            }
        }

        scanner.close();
        System.out.println("Game has ended.");
    }
}
