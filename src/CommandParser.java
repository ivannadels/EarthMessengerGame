import java.util.*;

public class CommandParser {
    private Map<String, String> validCommands;  // Maps verbs to command types
    private Player player;

    /**
     * Creates a CommandParser that can execute commands for the given player.
     *
     * @param player The player object that will be modified by commands
     */
    // Todo: Handle movement commands
    public CommandParser(Player player) {
        this.player = player;
        validCommands = new HashMap<>();
        validCommands.put("go", "move");
        validCommands.put("move", "move");
        validCommands.put("enter", "move");
        validCommands.put("exit", "exit");
        validCommands.put("take", "take");
        validCommands.put("get", "take");
        validCommands.put("use", "use");
        validCommands.put("look", "look");
        validCommands.put("examine", "look");
        validCommands.put("inventory", "inventory");
        validCommands.put("i", "inventory");
        validCommands.put("answer", "answer");
        validCommands.put("help", "help");
        validCommands.put("play message", "play");
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

        // Check if this is a location-specific special command
        Location currentLocation = player.getCurrentLocation();
        if (currentLocation.hasSpecialCommand(input)) {
            return handleSpecialCommand(input, currentLocation);
        }

        String action = words[0];

        if (input.equals("play message")) {
            return playMessage();
        }

        // Check if the action is valid and get its command type
        if (!validCommands.containsKey(action)) {
            return "This is not a valid command- type 'help' for more info :)";
        }
        action = validCommands.get(action);

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
            case "look":
                return player.getCurrentLocation().getLongDescription();
            case "inventory":
                return displayInventory();
            case "help":
                return showHelp();
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
        return response;
    }

    public String displayInventory() {
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            return "═══════════════════════════════════════════════════════\n" +
                    "                    INVENTORY                          \n" +
                    "═══════════════════════════════════════════════════════\n" +
                    "   Your inventory is empty.                            \n" +
                    "═══════════════════════════════════════════════════════";
        }

        StringBuilder response = new StringBuilder();
        response.append("═══════════════════════════════════════════════════════\n");
        response.append("                    INVENTORY                          \n");
        response.append("═══════════════════════════════════════════════════════\n");

        for (Item item : inventory) {
            response.append("  ").append(item.getGraphic()).append(" ").append(item.getName()).append("\n");
            response.append("     ").append(item.getDescription()).append("\n");
            response.append("\n");
        }

        response.append("═══════════════════════════════════════════════════════");

        return response.toString();
    }

    public String showHelp() {
        StringBuilder help = new StringBuilder();

        help.append("\n═══════════════════════════════════════════════════════\n");
        help.append("                 AVAILABLE COMMANDS                    \n");
        help.append("═══════════════════════════════════════════════════════\n\n");

        help.append("MOVEMENT:\n");
        help.append("  • go [direction] - Move in a direction (north/south/east/west)\n");
        help.append("  • enter/exit - Enter or leave a location\n\n");

        help.append("ITEMS:\n");
        help.append("  • take [item] - Pick up an item\n");
        help.append("  • use [item] - Use an item from your inventory\n");
        help.append("  • inventory (i) - View what you're carrying\n\n");

        help.append("INFORMATION:\n");
        help.append("  • look - Examine your surroundings in detail\n");
        help.append("  • help - Show this message\n\n");

        // Show location-specific commands
        Location currentLocation = player.getCurrentLocation();
        List<String> specialCommands = currentLocation.getSpecialCommandDescriptions();

        if (!specialCommands.isEmpty()) {
            help.append("SPECIAL ACTIONS (here):\n");
            for (String cmd : specialCommands) {
                help.append("  • ").append(cmd).append("\n");
            }
            help.append("\n");
        }
        help.append("═══════════════════════════════════════════════════════\n");
        return help.toString();
    }

    public String playMessage() {
        Item phone = player.getItemFromInventory("iphone");
        if (phone != null) {
            phone.use(player);
            return "";
        } else {
            return "You do not have a phone to play the message.";
        }
    }

    /**
     * Handles location-specific special commands.
     *
     * @param command The full command string
     * @param location The current location
     * @return Result message
     */
    private String handleSpecialCommand(String command, Location location) {
        // Handle different special commands based on location
        switch (location.getName().toLowerCase()) {
            case "spaceship":
                return handleSpaceshipCommands(command);
            default:
                return "You can't do that here.";
        }
    }

    private String handleSpaceshipCommands(String command) {
        Pizza pizza = (Pizza) player.getItemFromInventory("pizza");
        Item water = (WaterBottle) player.getItemFromInventory("water");
        Item phone = (IPhone) player.getItemFromInventory("phone");

        switch (command) {
            case "unlock door":
            case "open door":
                if (player.getHungerLevel()==5 && player.getThirstLevel()==5 && player.hasListenedToMessage()) {
                    return "You unlock the door. You can now exit the spaceship.";
                } else {
                    String response = "";
                    if (!player.hasListenedToMessage()) response += "You should check that iPhone. ";
                    if (player.getHungerLevel()!=5) response += "You're too hungry. ";
                    if (player.getThirstLevel()!=5) response += "You're too thirsty. ";
                    return "The door won't budge. " + response;
                }
            case "open compartments":
            case "check compartments":
                return "You open the compartments. Inside you find:\n"
                        + "- A frozen pizza\n"
                        + "- A water bottle\n"
                        + "- A microwave";

            case "microwave pizza":
            case "use microwave":
                if (pizza!=null) {
                    if (pizza.isFrozen()) {
                        pizza.setFrozen(false);
                        return "You place the frozen pizza in the microwave. It warms up nicely, now you can eat it.";
                    } else {
                        return "The pizza is already microwaved and ready to eat.";
                    }
                } else {
                    return "You don’t have a pizza to microwave.";
                }

            case "eat pizza":
                if (pizza!=null) {
                    if (!pizza.isFrozen()){
                        pizza.use(player);
                        return "You eat the warm pizza. You immediately feel stronger.";
                    } else {
                        return "The pizza is frozen solid. Maybe you should microwave it first.";
                    }
                } else {
                    return "You don’t have a pizza to eat.";
                }

            case "drink water":
                if (water!=null) {
                    water.use(player);
                    return "You drink the water. Your thirst is quenched.";
                } else {
                    return "You don’t have a water bottle.";
                }
            case "check systems":
            case "examine systems":
                return "The control systems are mostly dead. Only life support remains active.";
            default:
                return "You can't do that here.";
        }
    }}