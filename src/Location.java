/**
 * Represents a location in the game world that the player can visit.
 *
 * Rooms contain descriptions, connections to other rooms (via directional commands
 * like north/south/east/west or action commands like enter/exit), items that can
 * be picked up, and potentially an alien occupant.
 *
 * Rooms track whether they've been visited and whether they're outside locations
 * (like Planet Surface) or indoor locations (like chambers and spaceship).
 *
 * All locations use the same Room class with different data - no subclasses needed.
 *
 * Example usage:
 *   Room spaceship = new Room("Spaceship", "A small one-person spaceship...", false);
 *   spaceship.addItem(iphone);
 *   spaceship.addConnection("exit", planetSurface);
 *   String desc = spaceship.getDescription();
 */
