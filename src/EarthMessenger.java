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