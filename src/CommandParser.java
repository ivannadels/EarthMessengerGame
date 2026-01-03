import java.util.*;

public class CommandParser {
    private Map<String, String> validCommands;  // Maps verbs to command types
    private Player player;
    private EarthMessenger game;

    /**
     * Creates a CommandParser that can execute commands for the given player.
     * Initializes the valid commands map with all recognized commands and their aliases.
     *
     * @param player The player object that will be modified by commands
     */
    public CommandParser(EarthMessenger game, Player player) {
        this.player = player;
        this.game = game;

        // The validCommands map associates command aliases with their action names.
        // Multiple input strings (e.g., "go", "move", "enter") map to the same action for easier processing.
        validCommands = new HashMap<>();

        // Movement commands
        validCommands.put("go", "move");
        validCommands.put("move", "move");
        validCommands.put("enter", "move");
        validCommands.put("exit", "exit");

        // Item interaction commands
        validCommands.put("take", "take");
        validCommands.put("get", "take");
        validCommands.put("use", "use");

        // Observation commands
        validCommands.put("look", "look");
        validCommands.put("examine", "look");

        // Inventory commands
        validCommands.put("inventory", "inventory");
        validCommands.put("i", "inventory");

        // Puzzle/interaction commands
        validCommands.put("answer", "answer");
        validCommands.put("greet", "greet");
        validCommands.put("start",  "start");

        // Utility commands
        validCommands.put("help", "help");
        validCommands.put("play message", "play");


    }

    /**
     * Enum that maps string parameters/input to game nouns.
     *
     * We use an enum instead of strings for type safety (catching typos at compile time)
     * and to support multiple aliases for the same noun (e.g., "iphone" or "phone").
     */
    public enum Noun {
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west"),
        PIZZA("pizza"),
        IPHONE("iphone", "phone"),
        WATERBOTTLE("waterBottle", "water bottle", "water");

        private final String[] keywords;

        Noun(String... keywords) {
            this.keywords = keywords;
        }

        /**
         * Gets the primary name (first keyword) of this noun.
         *
         * @return The primary keyword string
         */
        public String getName() {
            return keywords[0];
        }

        /**
         * Checks whether this noun matches the given item by comparing names.
         *
         * @param item The item to check against
         * @return true if the item's name matches one of this noun's accepted
         * string form i.e. keywords, false otherwise
         *
         */
        public boolean matchesItem(Item item) {
            for (String p : keywords) {
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
                for (String parameter : noun.keywords) {
                    if (parameter.equalsIgnoreCase(input)) {
                        return noun;
                    }
                }
            }
            throw new IllegalArgumentException("Invalid parameter: " + input);
        }
    }

    /**
     * Parses input and executes the corresponding command.
     *
     * @param input Raw player input string
     * @return Result message to display to the player
     */

    public String parse(Scanner scanner, String input) {

        input = input.toLowerCase().trim();
        String[] words = input.split("\\s+");

        if (words.length == 0) return "Please enter a command...";

        // Check if this is a location-specific special command
        Location currentLocation = player.getCurrentLocation();
        if (currentLocation.hasSpecialCommand(input)) {
            return handleSpecialCommand(input, currentLocation);
        }
        else if(player.getCurrentQuestion()!=null){
            return currentLocation.getOccupant().checkAnswer(player, input);
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
                return "\"" + parameters + "\" is not recognizable.\nTry keeping commands simple. Type 'help' to see what you can do.";
            }
        }

        /*
        * "action" is the verb command and "noun" is expected to be
        *  a direction or item - depending on the command
        * */
        // Todo: Debug waterBottle item and take water commmand
        switch (action) {
            case "move":
            case "go":
                return move(noun);
            case "enter":
                return enter();
            case "exit":
                return exit();
            case "take":
                return take(noun);
            case "use":
                return use(noun);
            case "greet":
            case "talk":
                return greet();
            case "start":
                return startTest();
            case "look":
                return player.getCurrentLocation().getLongDescription();
            case "inventory":
                return displayInventory();
            case "help":
                return showHelp();
            default:
                return "This is not a valid command- type 'help' for more info :)";
    }}

    /**
     * Moves the player in the specified direction.
     * Checks if there's a connected room in the specified direction and moves the player there.
     *
     * @param direction The direction to move (NORTH, SOUTH, EAST, WEST, EXIT)
     * @return A message describing the movement result
     */
    public String move(Noun direction) {
        Location currentLocation = player.getCurrentLocation();
        String directionKey = direction.getName().toLowerCase();

        // Check if there's a connected room in this direction
        if (!currentLocation.hasConnection(directionKey)) {
            return "You can't go " + directionKey + " from here.";
        }

        // Get the connected location
        Location nextLocation = currentLocation.getConnectedRoom(directionKey);

        if (nextLocation == null) {
            return "There's no path in that direction.";
        }

        // Move the player to the new location
        player.setCurrentLocation(nextLocation);
        String response = nextLocation.getShortDescription();

        return response;
    }

    public String enter() {
        Location currentLocation = player.getCurrentLocation();

        String response = "You enter " + currentLocation.getName() + ".\n\n";

        if (!currentLocation.isVisited()) {
            response += currentLocation.getLongDescription();
            currentLocation.setVisited(true);
        }
        return response;
    }

    public String greet(){
        Location currentLocation = player.getCurrentLocation();
        if(!currentLocation.hasOccupant()){
            return "There is no one to speak to here...";
        }
        Alien occupant  = currentLocation.getOccupant();
        return occupant.greet();
    }

    public String startTest(){
        Location currentLocation = player.getCurrentLocation();
        Alien occupant = currentLocation.getOccupant();
        if(occupant == null){
            return "There is no one to speak to here...";
        }
        else if(occupant.hasMetPlayer()){
            return occupant.startTest(player);
        }
        else {
            return "You must first greet the chamber's keeper. Type \"greet\"";
        }
    }

    public String exit(){
        Location currentLocation = player.getCurrentLocation();

        if(currentLocation.hasBeenCompleted() && currentLocation.getConnectedRoom("exit") != null){
            Location exitRoom = currentLocation.getConnectedRoom("exit");
            player.setCurrentLocation(exitRoom);
            String response =  "You exit " + currentLocation.getName() + " and arrive in " + exitRoom.getName() + ".";
            if(!exitRoom.isVisited()){
                response += "\n" +  exitRoom.getLongDescription();
                exitRoom.setVisited(true);
            }
            return response;
        }
        else if(currentLocation.getName()!="Spaceship"){
            return "The chamber’s wards hold firm. You may not depart until its trial is complete.";
        }
        else if(currentLocation.getName().equals("Spaceship")){
            // if we are on the spaceship, use spaceship specific language
            return handleSpaceshipCommands("open door");
        }
        else{
            return "No exit reveals itself. Perhaps you should look around more carefully.";
        }
    }
    /**
     * Adds an item to the player's inventory.
     *
     * @param itemType The item type to take
     * @return A message confirming the item was taken
     */
    public String take(Noun itemType) {
        Location currentLocation = player.getCurrentLocation();

        // Find the matching item in the location using the noun's keywords
        Item itemToTake = null;
        for (Item item : currentLocation.getItems()) {
            if (itemType.matchesItem(item)) {
                itemToTake = item;
                break;
            }
        }

        // Check if the item is available in the current location
        if (itemToTake == null || !currentLocation.isItemAvailable(itemToTake.getName())) {
            return "There is no " + itemType.toString().toLowerCase() + " here to take.";
        }

        // Remove item from location and add to player inventory
        currentLocation.removeItem(itemToTake);
        player.addItem(itemToTake);

        String response = "You have taken the " + itemToTake.getName() + ".";
        response += "\nThe item is in your inventory. Type 'use " + itemToTake.getName() + "' to use it.";
        return response;
    }

    /**
     * Uses an item from the player's inventory.
     *
     * @param itemType The item type to use
     * @return A message describing the effect of using the item
     */
    public String use(Noun itemType) {
        player.useItem(itemType);
        String response = "You have used " + itemType.keywords[0];
        return response;
    }

    /**
     * Displays the player's inventory in a formatted string.
     *
     * @return A formatted inventory list or a message if empty
     */
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

    /**
     * Shows available commands and location-specific actions.
     *
     * @return A formatted help message
     */
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

    /**
     * Plays a stored message if the player has a phone.
     *
     * @return Empty string if message was played, or an error message
     */
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

    // -------------------- Special Commands --------------------

    /**
     * Handles location-specific special commands.
     *
     * @param command  The full command string
     * @return Result message after executing the special command
     */
    private String handleSpaceshipCommands(String command) {
        Pizza pizza = (Pizza) player.getItemFromInventory("pizza");
        Item water = (WaterBottle) player.getItemFromInventory("water");
        Item phone = (IPhone) player.getItemFromInventory("phone");
        boolean compartmentsOpened = player.getCurrentLocation().hasBeenSearched();
        boolean spaceshipCompleted = player.getCurrentLocation().hasBeenSearched() && player.hasListenedToMessage()
                                && player.getHungerLevel()!=5 && player.getThirstLevel()!=5;
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
                player.getCurrentLocation().setSearched(true);
                return "You open the compartments. Inside you find:\n"
                        + "- A frozen pizza\n"
                        + "- A water bottle\n"
                        + "- A microwave";

            case "microwave pizza":
            case "use microwave":
                if (!compartmentsOpened) {
                    return "There is no microwave here... maybe check the compartments.";
                }
                if (pizza!=null) {
                    if (pizza.isFrozen()) {
                        pizza.setFrozen(false);
                        return "You place the frozen pizza in the microwave. It warms up nicely, now you can eat it.";
                    } else {
                        return "The pizza is already microwaved and ready to eat.";
                    }
                } else {
                    return "You don't have a pizza to microwave. Try taking it from the compartments first.";
                }

            case "eat pizza":
                if (!compartmentsOpened) {
                    return "There is no pizza here... maybe check the compartments.";
                }
                if (pizza!=null) {
                    if (!pizza.isFrozen()){
                        pizza.use(player);
                        if(spaceshipCompleted) {
                            game.setChambersPassed(game.getChambersPassed() + 1);
                        }
                        return "You eat the warm pizza. You immediately feel stronger.";
                    } else {
                        return "The pizza is frozen solid. Maybe you should microwave it first.";
                    }
                } else {
                    return "There is no pizza here... maybe check the compartments.\"";
                }

            case "drink water":
                if (!compartmentsOpened) {
                    return "There is no water here... maybe check the compartments.";
                }
                if (water!=null) {
                    water.use(player);
                    if(spaceshipCompleted) {
                        game.setChambersPassed(game.getChambersPassed() + 1);
                    }
                    return "You drink the water. Your thirst is quenched.";
                } else {
                    return "You don't have a water bottle... maybe check the compartments.";
                }

            case "check systems":
            case "examine systems":
                return "The control systems are mostly dead. Only life support remains active.";
            default:
                return "You can't do that here.";
        }
    }}