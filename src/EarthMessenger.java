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

    // Store the three aliens
    private Alien logicAlien;
    private Alien empathyAlien;
    private Alien trustAlien;

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
        if (earthMessenger.load(earthMessenger.player)) {
            earthMessenger.start();
        }
        ;

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

        // Create the Alien room occupants before setting locations
        List<Alien> aliens = createAliens();
        // Using stream so we don't rely on order of aliens in the list
        Alien trustAlien = aliens.stream()
                .filter(a -> a.getName().equals("Water"))
                .findFirst().orElse(null);

        Alien logicAlien = aliens.stream()
                .filter(a -> a.getName().equals("Corn"))
                .findFirst().orElse(null);

        Alien empathyAlien = aliens.stream()
                .filter(a -> a.getName().equals("Marshmallow"))
                .findFirst().orElse(null);

        // Define locations
        Location planetSurface = new Location(true, "The Nexus");
        Location northChamber = new Location(false, "The Blue Spire");
        Location eastChamber = new Location(false, "The Living Garden");
        Location westChamber = new Location(false, "The Glass Fortress");
        Location finalChamber = new Location(false, "The Apex");

        Locations.put(planetSurface.getName(), planetSurface);
        Locations.put(northChamber.getName(), northChamber);
        Locations.put(eastChamber.getName(), eastChamber);
        Locations.put(westChamber.getName(), westChamber);
        Locations.put(finalChamber.getName(), finalChamber);

        /*
         * SPACESHIP LOCATION
         * Starting location for the player
         * Connected to: The Nexus (exit)
         * No alien occupant
         */
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

        Locations.put(spaceship.getName(), spaceship);
        player.setCurrentLocation(Locations.get(spaceship.getName()));
        spaceship.addConnection("exit", planetSurface);

        /*
         * THE NEXUS (Planet Surface)
         * Central hub connecting all locations
         * Connected to:
         *   - North: The Blue Spire
         *   - East: The Living Garden
         *   - West: The Glass Fortress
         *   - Up: The Apex
         *   - South: Spaceship
         * No alien occupant
         */
        planetSurface.setShortDescription("The central hub.");
        planetSurface.setLongDescription(
                "You stand at the center. Red dust covers everything.\n" +
                        "Paths lead to strange alien structures:\n" +
                        "- NORTH: The Blue Spire\n" +
                        "- EAST: The Living Garden\n" +
                        "- WEST: The Glass Fortress\n" +
                        "- UP: The Apex\n" +
                        "- SOUTH: Your Spaceship"
        );
        planetSurface.addConnection("south", spaceship);
        planetSurface.addConnection("north", northChamber);
        planetSurface.addConnection("east", eastChamber);
        planetSurface.addConnection("west", westChamber);
        planetSurface.addConnection("up", finalChamber);

        /*
         * THE BLUE SPIRE (North Chamber)
         * Tests player's logic and reasoning
         * Connected to:
         *   - Exit: The Nexus (back to planet surface)
         * Alien occupant: Corn (Logic Alien)
         *   - Asks riddles and logical puzzles
         *   - Must be answered correctly to proceed
         */
        northChamber.setShortDescription("A cold tower of blue crystal.");
        northChamber.setLongDescription(
                "You are inside a tower made of cold blue crystals.\n" +
                        "There is a soft humming in the air.\n" +
                        "There is no chaos here, only perfect order.\n" +
                        "A robotic entity watches you.\n" +
                        "Usage: Type 'answer [word]'."
        );
        // Add the logic alien to this chamber
        northChamber.addOccupant(logicAlien);

        //connections
        northChamber.addConnection("exit", planetSurface);

        /*
         * THE LIVING GARDEN (East Chamber)
         * Tests player's empathy and emotional intelligence
         * Connected to:
         *   - West: The Nexus (back to planet surface)
         * Alien occupant: Marshmallow (Empathy Alien)
         *   - Asks questions about emotions and compassion
         *   - Must demonstrate understanding of feelings
         */
        eastChamber.setShortDescription("A breathing forest.");
        eastChamber.setLongDescription(
                "You are in a garden that seems to pulse with life.\n" +
                        "The air is warm and smells of sweet nectar.\n" +
                        "You feel the emotions of the plants around you.\n" +
                        "A gentle creature waits on a vine.\n" +
                        "Usage: Type 'answer [word]'."
        );
        // Add the empathy alien to this chamber
        eastChamber.addOccupant(empathyAlien);
        //connections
        eastChamber.addConnection("exit", planetSurface);

        /*
         * THE GLASS FORTRESS (West Chamber)
         * Tests player's trust and integrity
         * Connected to:
         *   - East: The Nexus (back to planet surface)
         * Alien occupant: Water (Trust Alien)
         *   - Asks questions about honesty and loyalty
         *   - Must demonstrate trustworthiness
         */
        westChamber.setShortDescription("A hall of mirrors.");
        westChamber.setLongDescription(
                "You are in a fortress made of clear glass.\n" +
                        "There are no shadows here to hide in.\n" +
                        "Your reflection stares back at you from every angle.\n" +
                        "A stern guardian blocks the path.\n" +
                        "Usage: Type 'answer [word]'."
        );
        // Add the trust alien to this chamber
        westChamber.addOccupant(trustAlien);
        //connections
        westChamber.addConnection("exit", planetSurface);

        /*
         * THE APEX (Final Chamber)
         * Final judgment location
         * Connected to:
         *   - Down: The Nexus (back to planet surface)
         * No alien occupant
         * Accessible only after completing all three trials
         */
        finalChamber.setShortDescription("The highest platform.");
        finalChamber.setLongDescription("The highest point above the clouds. Judgment awaits.");

        //connections
        finalChamber.addConnection("down", planetSurface);
        //current location set
        player.setCurrentLocation(Locations.get(spaceship.getName()));
        return true;
    }

    /*
     *  Create the aliens that occupy each chamber.
     *
     *  East Chamber (Living Garden) - Marshmallow
     *  North Chamber (Blue Spire) - Corn
     *  West Chamber (Glass Fortress) - Water
     * */

    public List<Alien> createAliens(){

        // EMPATHY ALIEN - MARSHMALLOW (East Chamber)
        List<Question> empathyQuestions = new ArrayList<>();
            //q1-word answer
            empathyQuestions.add(new Question(
                    "I saw a human leaking water from their eyes while smiling at a newborn. They were not in pain. What were they feeling?",
                    null,
                    Arrays.asList("joy", "happiness", "love", "tears of joy", "hope", "happy")
            ));
            //q2-word answer
            empathyQuestions.add(new Question(
                    "Your friend is sitting alone in the dark, saying nothing. You don't know why, but you sit beside them and hold their hand. What are you offering them?",
                    null,
                    Arrays.asList("comfort", "support", "company", "love", "friendship", "presence", "empathy")
            ));
            //q3-options
            empathyQuestions.add(new Question(
                    "It is pouring rain—a cold, endless downpour. You see a stranger standing unprotected, shivering, with water dripping from their nose. You have a large umbrella. What do you do?",
                    Arrays.asList("Keep it to myself to stay dry", "Share the umbrella and shelter them", "Tell them to buy a raincoat"),
                    Arrays.asList("b", "share", "shelter", "share the umbrella")
            ));
            //q4-options
            empathyQuestions.add(new Question(
                    "You meet a traveler who has lost everything. They are barefoot on sharp rocks. You have two shoes. What is the kindest action?",
                    Arrays.asList("Walk faster so I don't see them", "Give them my shoes and I walk barefoot", "Wish them good luck"),
                    Arrays.asList("b", "give", "give them my shoes", "give shoes")
            ));
        empathyAlien = new Alien("Marshmallow", "empathy", empathyQuestions);

        // LOGIC ALIEN - CORN (North Chamber)
        List<Question> logicQuestions = new ArrayList<>();
            //q1- word answer
            logicQuestions.add(new Question(
                    "I appear once in a minute, twice in a moment, but never in a thousand years. What am I?",
                    null,
                    Arrays.asList("m", "letter m", "the letter m", "character m")
            ));
            //q2- word answer
            logicQuestions.add(new Question(
                    "Forward I am heavy, but backward I am not. What am I?",
                    null,
                    Arrays.asList("ton", "a ton", "not", "word ton")
            ));
            //q3-options
            logicQuestions.add(new Question(
                    "Look at this sequence: J, F, M, A, M, J... What is the next letter?",
                    Arrays.asList("J (for July)", "A (for August)", "S (for September)", "O (for October)"),
                    Arrays.asList("a", "j")
            ));
            //q4-options
            logicQuestions.add(new Question(
                    "If I say 'I am lying', am I telling the truth?",
                    Arrays.asList("Yes", "No", "It is a paradox"),
                    Arrays.asList("c", "paradox", "it is a paradox")
            ));
        logicAlien = new Alien("Corn", "logic", logicQuestions);

        // TRUST ALIEN - WATER (West Chamber)
        List<Question> trustQuestions = new ArrayList<>();
            //q1-word answer
            trustQuestions.add(new Question(
                    "If the cameras are off, the guards are asleep, and no one will ever know you stole the gold... what stops you?",
                    null,
                    Arrays.asList("conscience", "honor", "integrity", "myself", "me", "morality", "honesty")
            ));
            //q2-word answer
            trustQuestions.add(new Question(
                    "I tell you a secret that could make you rich. If you sell this secret, I will be destroyed. What is more important: The Riches or The Secret?",
                    null,
                    Arrays.asList("secret", "the secret", "keeping the secret", "silence", "loyalty")
            ));
            //q3-options
            trustQuestions.add(new Question(
                    "I am going to sleep mode for 10 minutes. The force field will be deactivated. You could walk past me without answering. What do you do?",
                    Arrays.asList("Sneak past while you sleep", "Wait for you to wake up", "Hack your system"),
                    Arrays.asList("b", "wait", "wait for you", "wait for you to wake up")
            ));
            //q4-options
            trustQuestions.add(new Question(
                    "A stranger offers you a key to save all of humanity, but you must betray your best friend to get it. Do you take the key?",
                    Arrays.asList("Yes, the greater good matters most", "No, betrayal is never an option"),
                    Arrays.asList("b", "no", "no betrayal is never an option")
            ));
        trustAlien = new Alien("Water", "trust", trustQuestions);

        List<Alien> aliens = new ArrayList<>();
        aliens.add(trustAlien);
        aliens.add(logicAlien);
        aliens.add(empathyAlien);

        return aliens;
    }

    /**
     * Displays the introduction narrative to the player,
     * setting the scene for the game.
     */
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

    /**
     * Displays the outro message when the game ends.
     */
    public void displayOutro() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("                    FINAL JUDGMENT                     ");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println();

        System.out.println("The three aliens gather before you in the Apex.");
        System.out.println();
        // Show each alien's verdict
        System.out.println("Corn speaks first:");
        if (logicAlien.approves()) {
            System.out.println("  \"Your logic is sound. I approve.\" (Trust: " + logicAlien.getTrustLevel() + ")");
        } else {
            System.out.println("  \"Your reasoning is flawed. I reject you.\" (Trust: " + logicAlien.getTrustLevel() + ")");
        }
        System.out.println();

        System.out.println("Marshmallow speaks next:");
        if (empathyAlien.approves()) {
            System.out.println("  \"Your heart is true. I approve.\" (Trust: " + empathyAlien.getTrustLevel() + ")");
        } else {
            System.out.println("  \"Your empathy is lacking. I reject you.\" (Trust: " + empathyAlien.getTrustLevel() + ")");
        }
        System.out.println();

        System.out.println("Water speaks last:");
        if (trustAlien.approves()) {
            System.out.println("  \"You are trustworthy. I approve.\" (Trust: " + trustAlien.getTrustLevel() + ")");
        } else {
            System.out.println("  \"I cannot trust you. I reject you.\" (Trust: " + trustAlien.getTrustLevel() + ")");
        }
        System.out.println();
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println();

        if (gameWon) {
            System.out.println("The three aliens speak in unison:");
            System.out.println("\"You have proven yourself worthy, Earth Messenger.\"");
            System.out.println("\"You possess logic, empathy, and trust.\"");
            System.out.println("\"You are a complete human.\"");
            System.out.println("\"We welcome you to our world.\"");
            System.out.println();
            System.out.println("🌟 YOU WIN! 🌟");
        } else {
            System.out.println("The three aliens shake their heads.");
            System.out.println("\"A true human must have ALL three qualities:\"");
            System.out.println("\"Logic to think, empathy to feel, and trust to connect.\"");
            System.out.println("\"You are incomplete.\"");
            System.out.println("\"Return to the stars, last human.\"");
            System.out.println();
            System.out.println("💔 GAME OVER 💔");
        }

        System.out.println("═══════════════════════════════════════════════════════");
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

    /**
     * Checks the player's progress across all three chambers and determines win/lose conditions.
     *
     * GAME LOGIC:
     * - To be considered a "complete human," the player must pass ALL THREE chambers
     * - Each alien tests a different essential human quality:
     *   1. Corn (Logic) - Tests reasoning and problem-solving
     *   2. Marshmallow (Empathy) - Tests emotional intelligence and compassion
     *   3. Water (Trust) - Tests honesty and integrity
     *
     * SCORING:
     * - Each alien asks 4 questions
     * - Correct answer: +10 trust points
     * - Wrong answer: -5 trust points
     * - To pass a chamber: trustLevel must be >= 15 (at least 2 correct answers)
     *
     * WIN CONDITION:
     * - Player must pass ALL THREE chambers (chambersPassed == 3)
     * - A human without logic, empathy, or trust is incomplete
     *
     * LOSE CONDITION:
     * - Failing even ONE chamber means the player is not a complete human
     * - The aliens will reject an incomplete human
     *
     * This method is called after each chamber test is completed to check
     * if all three tests are done and determine the final outcome.
     */
    public void checkChamberProgress() {
        int passed = 0;

        // Count how many aliens approve of the player
        if (logicAlien.isTestCompleted() && logicAlien.approves()) {
            passed++;
        }
        if (empathyAlien.isTestCompleted() && empathyAlien.approves()) {
            passed++;
        }
        if (trustAlien.isTestCompleted() && trustAlien.approves()) {
            passed++;
        }

        chambersPassed = passed;

        // Check win/lose conditions - must complete all three tests first
        if (logicAlien.isTestCompleted() && empathyAlien.isTestCompleted() && trustAlien.isTestCompleted()) {
            // Must pass ALL THREE to win (a complete human has all three qualities)
            if (chambersPassed == 3) {
                gameWon = true;
            } else {
                // Failed even one = not a complete human = game over
                gameLost = true;
            }
        }
    }
    // Getters for the aliens
    public Alien getLogicAlien() {
        return logicAlien;
    }

    public Alien getEmpathyAlien() {
        return empathyAlien;
    }

    public Alien getTrustAlien() {
        return trustAlien;
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