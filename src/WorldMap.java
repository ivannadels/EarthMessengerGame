import java.util.*;
/**
 * Represents a usable world map item that displays all known locations
 * and their connections when activated by the player.
 *
 * The map provides a textual overview of the game's navigation structure,
 * helping the player understand how rooms are linked together.
 */
public class WorldMap extends Item {

    /**
     * Constructs a new world map item with a predefined name,
     * description, and globe graphic.
     */
    public WorldMap() {
        String description = "A map of the mysterious planet.";
        super("map", description, "\uD83C\uDF0D");
    }

    /**
     * Uses the world map, displaying a formatted overview of all
     * locations and their directional connections.
     *
     * @param player the player using the map
     */
    @Override
    public void use(Player player) {
        Map<String, Location> world = player.getGame().getLocations();
        displayWorldMap(world);
    }

    /**
     * Prints a formatted navigation map showing each location and
     * its connected rooms. Null connections are skipped.
     *
     * @param world a map of all locations in the game world
     */
    public void displayWorldMap(Map<String, Location> world) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════╗\n");
        sb.append("║        NAVIGATION MAP          ║\n");
        sb.append("╚════════════════════════════════╝\n\n");

        for (Location loc : world.values()) {
            sb.append("◈ ").append(loc.getName()).append("\n");

            for (Map.Entry<String, Location> conn : loc.getConnectedRooms().entrySet()) {
                Location target = conn.getValue();
                if (target == null) {
                    continue;
                }

                sb.append("   ↳ ")
                        .append(conn.getKey().toUpperCase())
                        .append(" → ")
                        .append(target.getName())
                        .append("\n");
            }

            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
