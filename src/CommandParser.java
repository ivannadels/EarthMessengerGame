import java.util.*;

public class CommandParser {
    private Map<String, String> validCommands;  // Maps verbs to command types
    private Player player;

    /**
     * Creates a CommandParser that can execute commands for the given player.
     *
     * @param player The player object that will be modified by commands
     */
    public CommandParser(Player player) {
        this.player = player;
        validCommands = new HashMap<>();
        validCommands.put("go", "move");
        validCommands.put("move", "move");
        validCommands.put("north", "move");
        validCommands.put("south", "move");
        validCommands.put("east", "move");
        validCommands.put("west", "move");
        validCommands.put("enter", "move");
        validCommands.put("exit", "move");
        validCommands.put("take", "take");
        validCommands.put("get", "take");
        validCommands.put("use", "use");
        validCommands.put("look", "look");
        validCommands.put("examine", "look");
        validCommands.put("inventory", "inventory");
        validCommands.put("i", "inventory");
        validCommands.put("answer", "answer");
        validCommands.put("help", "help");
    }

    /**
     * Enum that maps string parameters/input to game nouns.
     */
    public enum Noun {
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west"),
        PIZZA("pizza"),
        IPHONE("iphone", "phone"),
        WATERBOTTLE("water bottle", "water");

        private final String[] parameters;

        Noun(String... parameter) {
            this.parameters = parameter;
        }

        public boolean matchesItem(Item item) {
            for (String p : parameters) {
                if (item.getName().equalsIgnoreCase(p)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Converts a string into the corresponding Noun enum constant.
         *
         * @param input the string parameter
         * @return the matching enum constant
         * @throws IllegalArgumentException if no match is found
         */
        public static Noun fromString(String input) {
            for (Noun noun : Noun.values()) {
                for (String parameter : noun.parameters) {
                    if (parameter.equalsIgnoreCase(input)) {
                        return noun;
                    }
                }
            }
            throw new IllegalArgumentException("Invalid parameter: " + input);
        }
    }

    /**
     * parses input and executes the corresponding command.
     *
     * @param input Raw player input string
     * @return Result message to display to the player
     */

    public String parse(String input) {
        input = input.toLowerCase().trim();
        String[] words = input.split("\\s+");

        if (words.length == 0) return "Please enter a command...";

        String action = words[0];
        String parameters = (words.length > 1)
                ? input.substring(action.length()).trim()
                : "";

        Noun noun = null;
        if (!parameters.isEmpty()) {
            try {
                noun = Noun.fromString(parameters);
            } catch (IllegalArgumentException e) {
                return "Unknown noun: " + parameters;
            }
        }

        /*
        * "action" is the verb command and "noun" is expected to be
        *  a direction or item - depending on the command
        * */
        switch (action) {
            case "move":
            case "go":
                return move(noun);
            case "take":
                return take(noun);
            case "use":
                return use(noun);
//            case "look":
//                return look(player);
//            case "inventory":
//                return inventory();
            default:
                return "This is not a valid command- type 'help' for more info :)";
    }}

    public String move(Noun direction) {
        String response = "I have moved " + direction.parameters[0];
        return response;
    }

    public String take(Noun itemType) {
        player.addItem(itemType);
        String response = "You have taken " + itemType.parameters[0];
        response += "\n The item is in your inventory. Type 'use' to use it.";
        return response;
    }

    public String use(Noun itemType) {
        player.useItem(itemType);
        String response = "You have used " + itemType.parameters[0];
        response += "\n The item is no longer usable.";
        return response;
    }
//
//    public void look(Player player) {
//        System.out.println(player.getCurrentLocation().getDescription());
//    }
//
//    public void inventory() {
//
//    }

//    public void showHelp() {
//    }
}
