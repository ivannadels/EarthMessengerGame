/**
 * The main controller class that manages the Earth Messenger text adventure game.
 *
 * This class controls the entire game experience: initializing the game world,
 * processing player commands, tracking game state, and determining win/lose conditions.
 *
 * GAME DESCRIPTION:
 * You wake up alone on a one-person ship—all humans have gone extinct, and you were
 * cryogenically frozen as humanity's last hope. Your ship has arrived at a distant planet
 * that scientists discovered could sustain human life. When you exit your vessel, you find
 * yourself on an alien world inhabited by a human-like species that has been studying Earth
 * through its abandoned structures and media for decades.
 * Three alien scholars await you in separate chambers scattered across the planet's surface.
 * Each will test a different aspect of your character. You must prove you are truly the last
 * human from Earth and that your intentions are pure. Only if you pass their trials will
 * they welcome you and help preserve what remains of humanity.
 *
 * GAME STATE TRACKING:
 * - chambersPassed: Counts how many chambers player has completed (0-3)
 * - gameWon: True if aliens accept the player
 * - gameLost: True if aliens reject the player
 *
 */
import java.util.*;

public class EarthMessenger {
    private Player player;
    private Map<String, Location> Locations;
    private CommandParser parser;
    private boolean gameWon;
    private boolean gameLost;
    private int chambersPassed;

    public EarthMessenger(String playerName) {
        this.Locations = new HashMap<>();
        this.player = new Player(playerName);
        this.gameWon = false;
        this.gameLost = false;
        this.chambersPassed = 0;
    }

    /**
     * Main class for the Earth Messenger game.
     * Creates a new game instance and runs the game loop.
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("           EARTH MESSENGER: THE LAST HUMAN             ");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println();
        System.out.print("Before we begin, what is your name? ");
        String playerName = scanner.nextLine().trim();
        System.out.println();

        // Initiate new game class object
        EarthMessenger earthMessenger = new EarthMessenger(playerName);
        if(earthMessenger.load(earthMessenger.player)){
            earthMessenger.start();
        };

        // Game loop starts
        while (!earthMessenger.isGameOver()) {
            System.out.print("> ");
            String input = scanner.nextLine();
            CommandParser parser = new CommandParser(earthMessenger.player);
            System.out.println(parser.parse(input));
        }

        earthMessenger.displayOutro();
        scanner.close();
    }

    public void start() {
        displayIntro();
    }

    public boolean load(Player player) {
        // Spaceship Location set up
        Location spaceship = new Location(false, "Spaceship");
        spaceship.setLongDescription(
                "The spaceship is small and cramped. Metal walls surround you. " +
                "Your cryopod sits open against one wall. Control panels flicker. " +
                "Three storage compartments line the opposite wall, their doors slightly\n" +
                "ajar. In the middle of the main control panel sits a small device...\n" +
                "Upon closer inspection, you notice it's an iPhone.\n\n" +
                "There's a sticky note on the phone's screen. It reads:\n" +
                "\"PLAY MESSAGE\"");
        spaceship.setShortDescription("\"You are inside your cramped one-person vessel.\"");
        Item pizza = new Pizza();
        Item iPhone = new IPhone();
        Item watterBottle = new WaterBottle();
        // Spaceship specific commands
        // Todo: Test + handle all the spaceship specific commands
        spaceship.addSpecialCommand("unlock door", "open door");
        spaceship.addSpecialCommand("open door", "open door");
        spaceship.addSpecialCommand("open compartments", "open compartments");
        spaceship.addSpecialCommand("check compartments", "open compartments");
        spaceship.addSpecialCommand("microwave pizza", "use microwave");
        spaceship.addSpecialCommand("use microwave", "use microwave");
        spaceship.addSpecialCommand("eat pizza", "eat pizza");
        spaceship.addSpecialCommand("drink water", "drink water");
        spaceship.addSpecialCommand("check systems", "check systems");
        spaceship.addSpecialCommand("examine systems", "examine systems");

        spaceship.addItem(pizza);
        spaceship.addItem(iPhone);
        spaceship.addItem(watterBottle);

        Locations.put(spaceship.getName(),spaceship);
        player.setCurrentLocation(Locations.get(spaceship.getName()));

        // Todo: add connected rooms to each exit to handle movement in and out of the spaceship

        return true;
    }

    public void displayIntro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println();
        System.out.println("A sharp jolt. Then silence.");
        System.out.println();
        System.out.println("The cryogenic chamber hisses and the glass door swings open.");
        System.out.println("Your muscles ache. Your head throbs.");
        System.out.println();
        pressEnterToContinue(scanner);

        System.out.println("Where... where are you?");
        System.out.println();
        System.out.println("Through the viewport, you see an almost familiar landscape.");
        System.out.println("You are on the top of a hill, looking down into a green");
        System.out.println("valley. You can see within the green there are shades of");
        System.out.println("yellow, pink and purple dancing in the wind... flowers.");
        System.out.println();
        System.out.println("It looks like the fields you remember playing in back home...");
        System.out.println();
        pressEnterToContinue(scanner);

        System.out.println("You finally look up. The sky is vast and a light shade of");
        System.out.println("blue. Enthralled by its beauty, you almost don't notice");
        System.out.println("that there is more than one moon where the sun is supposed");
        System.out.println("to be.");
        System.out.println();
        System.out.println("Where is the sun?");
        System.out.println();
        System.out.println("This isn't Earth.");
        System.out.println();
        pressEnterToContinue(scanner);

        System.out.println("You turn around. The spaceship is tiny, barely large enough");
        System.out.println("for one person. Storage compartments. A control panel with");
        System.out.println("dead screens. Your cryopod, now open and empty.");
        System.out.println();
        System.out.println("You are completely alone.");
        System.out.println();
        pressEnterToContinue(scanner);

        System.out.println("Your throat is dry. Your stomach growls. Why were you");
        System.out.println("frozen? How long have you been asleep? Where is everyone?");
        System.out.println();
        System.out.println("The questions pile up, but there are no answers.");
        System.out.println();
        pressEnterToContinue(scanner);

        System.out.println("───────────────────────────────────────────────────────");
        System.out.println();
        System.out.println("You are in your spaceship. You need to figure out what's");
        System.out.println("going on.");
        System.out.println();
        System.out.println("Type 'help' to see available commands.");
        System.out.println();
    }

    public void displayOutro() {
        System.out.println("Game over!");
    }

    /**
     * Prompts the user to press Enter to continue.
     *
     * @param scanner The Scanner object to read input
     */
    private void pressEnterToContinue(Scanner scanner) {
        System.out.println("[Press Enter to continue...]");
        scanner.nextLine();
        System.out.println();
    }

    public boolean isGameLost() {
        return gameLost;
    }
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }
    public boolean isGameWon() {
        return gameWon;
    }
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isGameOver() {
        return this.isGameLost() || this.isGameWon();
    }

    public void setChambersPassed(int chambersPassed) {
        this.chambersPassed = chambersPassed;
    }

    public int getChambersPassed() {
        return chambersPassed;
    }

}