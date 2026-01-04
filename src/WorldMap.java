import java.util.*;
public class WorldMap extends Item{
    public WorldMap() {
        String description = "A map of the mysterious planet.";
        super("map", description, "\uD83C\uDF0D");
    }
    @Override
    public void use(Player player) {
        Map<String, Location> world = player.getGame().getLocations();
        displayWorldMap(world);
    }
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

