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
        System.out.print("What is your name? ");
        String playerName = scanner.next();
        EarthMessenger earthMessenger = new EarthMessenger(playerName);
        if(earthMessenger.load(earthMessenger.player)){
            earthMessenger.start();
        };

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

        Location spaceship = new Location(false, "Spaceship", "Description");
        Item pizza = new Pizza();
        Item iPhone = new IPhone();
        Item watterBottle = new WaterBottle();
        spaceship.addItem(pizza);
        spaceship.addItem(iPhone);
        spaceship.addItem(watterBottle);

        Locations.put(spaceship.getName(),spaceship);
        player.setCurrentLocation(Locations.get(spaceship.getName()));

        return true;
    }

    public void displayIntro() {
        System.out.println("Welcome to the Earth Messenger game!");
    }

    public void displayOutro() {
        System.out.println("Game over!");
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