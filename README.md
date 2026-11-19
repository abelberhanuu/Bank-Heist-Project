# Bank Heist

A Java text-adventure learning project that combines command parsing, world navigation, inventory, and game state.

## Technical contribution

The source separates responsibilities across **9 Java classes**:

- `App` starts the input loop and dispatches commands.
- `CommandSystem` interprets player actions.
- `GameManager` manages commands, instructions, and player-facing messages.
- `GameState` and the player, item, entity, and location classes represent the world.
- `GameMap` provides a Swing map component.

**Outcome:** an interactive adventure demonstrating object-oriented modeling and coordination between game systems.

## Run locally

Install a JDK, then:

```bash
git clone https://github.com/abelberhanuu/Bank-Heist-Project.git
cd Bank-Heist-Project
javac -encoding UTF-8 *.java
java App
```

Enter a player name, then try `help`, `look`, `go north`, and `quit`. The `instructions` command explains the game's puzzle and item sequence.

## What to review

- [App.java](App.java): input and command-dispatch loop.
- [CommandSystem.java](CommandSystem.java): command processing.
- [GameState.java](GameState.java): world state.
- [GameMap.java](GameMap.java): Swing rendering.

The repository has no automated test suite. The nine-class count describes the checked-in source structure, not a benchmark or user outcome.
