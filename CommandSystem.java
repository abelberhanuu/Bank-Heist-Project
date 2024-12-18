import java.util.Scanner;

public class CommandSystem {
    private final GameState state;
    private final GameManager game;
    private boolean puzzleSolved = false;

    public CommandSystem(GameState state, GameManager game) {
        this.state = state;
        this.game = game;
    }

    public void processCommand(String[] command) {
        if (command.length == 0) {
            game.print("No command entered.");
            return;
        }

        String verb = command[0].toLowerCase();
        switch (verb) {
            case "look" -> executeLook();
            case "take" -> {
                if (command.length > 1) {
                    executeTake(String.join(" ", extractArguments(command, 1)));
                } else {
                    game.print("Please specify what to take.");
                }
            }
            case "go" -> {
                if (command.length > 1) {
                    executeGo(command[1]);
                } else {
                    game.print("Please specify a direction to go.");
                }
            }
            case "solve" -> executeSolve();
            case "use" -> {
                if (command.length > 1) {
                    String[] args = extractArguments(command, 1);
                    if (args.length == 1) {
                        executeUse(args[0], null); // No target specified
                    } else if (args.length >= 2) {
                        executeUse(args[0], String.join(" ", extractArguments(args, 1))); // Use item on target
                    } else {
                        game.print("Please specify what and how to use it.");
                    }
                } else {
                    game.print("Please specify what and how to use it.");
                }
            }
            case "ask" -> {
                if (command.length > 1) {
                    executeAsk(String.join(" ", extractArguments(command, 1)));
                } else {
                    game.print("Please specify whom to ask.");
                }
            }
            case "help" -> game.printHelp();
            case "instructions" -> game.printInstructions(); // Added support for instructions command
            default -> game.print("Unknown command. Type 'help' for a list of valid commands.");
        }
    }

    private String[] extractArguments(String[] command, int start) {
        String[] args = new String[command.length - start];
        System.arraycopy(command, start, args, 0, args.length);
        return args;
    }

    private void executeLook() {
        Location current = state.getCurrentLocation();
        game.print(current.getDescription(), false, true);
        game.print(current.getExitNames(), false, true);
        game.print(current.getItemNames(), false, true);
        game.print(current.getEntityNames(), false, true); // Show entities in the location
    }

    private void executeTake(String itemName) {
        Location current = state.getCurrentLocation();
        Item item = current.getItem(itemName);

        if (item != null) {
            state.addItem(item);
            current.removeItem(item);
            game.print("You take the " + item.getName() + ".", false, true);
        } else {
            game.print("There is no " + itemName + " here.", false, true);
        }
    }

    private void executeGo(String direction) {
        Location current = state.getCurrentLocation();
        Location nextLocation = current.getExit(direction);

        if (nextLocation != null) {
            state.setCurrentLocation(nextLocation);
            game.print("You move " + direction + " to " + nextLocation.getName() + ".", false, true);
            game.print(nextLocation.getDescription(), false, true);
        } else {
            game.print("You cannot go " + direction + ". There is no exit in that direction.", false, true);
        }
    }

    private void executeSolve() {
        Location current = state.getCurrentLocation();

        if (!current.getName().equalsIgnoreCase("Puzzle Room")) {
            game.print("There is nothing to solve here.", false, true);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        game.print("Solve this puzzle: What is 3 + 5 x 2?", true, false);
        String input = scanner.nextLine().trim();

        if ("13".equals(input)) {
            game.print("Correct! You solved the puzzle. A keycard and a smokebomb appear in the room.", false, true);
            current.addItem(new Item("keycard", "Required to unlock the vault."));
            current.addItem(new Item("smokebomb", "A device to create a diversion and sneak past the guard."));
            puzzleSolved = true;
        } else {
            game.print("Incorrect. Try again.", false, true);
        }
    }

    private void executeUse(String item, String target) {
        String normalizedItem = item.toLowerCase().trim();
        String normalizedTarget = target != null ? target.toLowerCase().trim() : null;
    
        if (normalizedItem.equals("smokebomb")) {
            if (normalizedTarget == null) {
                game.print("You need to specify what to use the smokebomb on.", false, true);
                return;
            }
    
            if (normalizedTarget.equals("guard")) {
                if (state.hasItem("smokebomb")) {
                    game.print("You use the smokebomb. Smoke fills the area, confusing the guard. You sneak past unnoticed!", false, true);
                    state.removeItem("smokebomb");
                } else {
                    game.print("You don't have a smokebomb to use.", false, true);
                }
                return;
            }
        }
    
        if (normalizedItem.equals("money")) {
            if (normalizedTarget == null) {
                game.print("You have to specify what to do with the money.", false, true);
                return;
            }


    
            if (normalizedTarget.equals("escape")) {
                if (state.hasItem("money")) {
                    triggerPursuitEnding(); // Escape with money but get pursued
                } else {
                    game.print("You don't have the money to escape.", false, true);
                }
                return;
            }
        }
    
        if (normalizedItem.equals("alarm")) {
            if (normalizedTarget != null && normalizedTarget.equals("trigger")) {
                  game.print("Are you sure you want to trigger the alarm? Type 'yes' or 'no':", true, false);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes")) {
                triggerCaughtEnding(); // Alarm is triggered, and player gets caught
            } else if (input.equals("no")) {
                game.print("You decide not to trigger the alarm. The heist continues.", false, true);
            } else {
                game.print("Invalid response. The heist continues.", false, true);
            }
            return;
        }
    }

    
        if (normalizedItem.equals("keycard")) {
            if (normalizedTarget == null) {
                game.print("You need to specify where to use the keycard.", false, true);
                return;
            }
    
            if (normalizedTarget.equals("vault")) {
                if (state.hasItem("keycard")) {
                    game.print("You use the keycard to unlock the vault. Congratulations! You've successfully completed the heist!", true, true);
                    game.printExitMessage();
                    System.exit(0); // End the game
                } else {
                    game.print("You don't have the keycard.", false, true);
                }
                return;
            }
        }
    
        if (normalizedItem.equals("blueprint")) {
            // Blueprint doesn't require a target
            game.print("You unfold the blueprint, revealing a map of the bank.", false, true);
            GameMap.displayMap(); // Show the map GUI (if implemented)
            return;
        }
    
        // If no valid use case is found
        if (normalizedTarget != null) {
            game.print("You can't use the " + item + " on the " + target + ".", false, true);
        } else {
            game.print("You can't use the " + item + ".", false, true);
        }
    }
    

    
    private void executeAsk(String entityName) {
        if (entityName == null || entityName.isBlank()) {
            game.print("You need to specify whom to ask.", false, true);
            return;
        }
    
        String normalizedEntity = entityName.toLowerCase().trim();
    
        if (entityName.equalsIgnoreCase("teller")) {
            if (state.getCurrentLocation().getName().equalsIgnoreCase("Lobby")) {
                game.print("The teller whispers: 'The answer to the puzzle is 13. Good luck!'", false, true);
            } else {
                game.print("There is no teller here to ask.", false, true);
            }
            return;
        }
    
    
        if (normalizedEntity.equals("guard")) {
            if (state.getCurrentLocation().getName().trim().equalsIgnoreCase("vault")) {
                game.print("Guard: 'You're not here to rob the bank, are you ಠಿ_ಠ??'", true, false);
                game.print("Type 'yes' or 'no':", true, false);
    
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine().trim().toLowerCase();
    
                if (input.equals("yes")) {
                    game.print("Guard: 'Wow really??! I mean, you totally could bribe me or anything.'", false, true);
    
                    if (state.hasItem("bribe")) {
                        game.print("Do you want to bribe the guard? Type 'yes' or 'no':", true, false);
                        String bribeInput = scanner.nextLine().trim().toLowerCase();
    
                        if (bribeInput.equals("yes")) {
                            game.print("Guard: 'It was fine doing work with you (｡•̀ᴗ-)✧'", false, true);
                            game.print("The guard allows you to proceed without raising the alarm.", false, true);
                            state.removeItem("bribe");
                        } else {
                            game.print("Guard: 'Oh wow, you're really stupid. GUARDS GET HIM!!!!'", false, true);
                            triggerCaughtEnding();
                        }
                    } else {
                        game.print("The guard seems disappointed you don't have anything to offer.", false, true);
                        game.print("Guard: 'Oh wow, you're really stupid. GUARDS GET HIM!!!!'", false, true);
                        triggerCaughtEnding();
                    }
                } else if (input.equals("no")) {
                    game.print("Guard: 'Yeah of course you aren't. That would be really stupid.'", false, true);
                    game.print("The guard looks at you suspiciously but does nothing. The heist continues.", false, true);
                } else {
                    game.print("The guard looks confused by your response. The heist continues.", false, true);
                }
            } else {
                game.print("There is no guard here to ask.", false, true);
            }
            return;
        }
    
        // Additional interaction for "manager"
        if (normalizedEntity.equals("manager")) {
            String currentLocation = state.getCurrentLocation().getName().trim();
            game.print("Current location: " + currentLocation, false, true);  // Debugging line
            
            if (currentLocation.equalsIgnoreCase("managersOffice")) {
                game.print("Manager: 'Why are you here? This is a restricted area!'", false, true);
                game.print("Type 'explain' or 'distract':", true, false);
            
                Scanner scanner = new Scanner(System.in);
                String managerResponse = scanner.nextLine().trim().toLowerCase();
            
                if (managerResponse.equals("explain")) {
                    game.print("You try to explain yourself, but the manager calls security. The heist fails.", false, true);
                    triggerCaughtEnding();
                } else if (managerResponse.equals("distract")) {
                    game.print("You manage to distract the manager with a clever excuse: \"Oh, I left the stove on!!!\", giving you time to escape unnoticed.", false, true);
                } else {
                    game.print("The manager looks at you suspiciously. Security is on its way.", false, true);
                    triggerCaughtEnding();
                }
            } else {
                game.print("There is no manager here to ask.", false, true);
            }
            return;
        }
        
    
        game.print("You can't ask " + entityName + ".", false, true);
    }
    
    private void triggerCaughtEnding() {
        game.print("The alarm blares loudly! Guards storm in and apprehend you. The heist has failed!", true, true);
        game.print("Game Over - You were caught by the guards. Better luck next time, " + game.getPlayerName() + "!", false, true);
        System.exit(0); // End the game
    }
    
    private void triggerPursuitEnding() {
        game.print("You successfully grab the money and dash out of the vault! But as you escape, the security cameras catch your every move.", true, true);
        game.print("Game Over - You're on the run, pursued by authorities. The heist is complete, but can you evade capture, " + game.getPlayerName() + "?", false, true);
        System.exit(0); // End the game
    }
}    
