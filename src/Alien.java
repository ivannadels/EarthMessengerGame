/**
 * Represents an alien who tests the player in a specific chamber.
 *
 * Each alien is assigned to one of three chambers (Logic, Empathy, or Trustworthiness)
 * and asks questions to evaluate the player's humanity and intentions.
 * The alien evaluates the player's response and contributes to the final judgment.
 *
 * Aliens are instantiated with different data (name, chamber type, questions) but
 * share the same behavior.
 *
 * Example usage:
 *   Alien zyx = new Alien("Zyx", "logic", "What came first, chicken or egg?", acceptableAnswers);
 *   String greeting = zyx.greet();
 *   String question = zyx.askQuestion();
 *   boolean passed = zyx.evaluateAnswer(playerResponse);
 */