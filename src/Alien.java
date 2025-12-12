/**
 * Represents an alien who tests the player in a specific chamber.
 *
 * Each alien is assigned to one of three chambers (Logic, Empathy, or Trustworthiness)
 * and poses questions to evaluate the player's humanity and intentions.
 * The alien evaluates the player's response and contributes to the final judgment.
 *
 * Aliens are instantiated with different data (name, role, questions) but
 * share the same behavior.
 *
 * Example usage:
 *   Alien zyx = new Alien("Zyx", "logic", "What came first, chicken or egg?", acceptableAnswers);
 *   String greeting = zyx.greet();
 *   String question = zyx.askQuestion();
 *   boolean passed = zyx.evaluateAnswer(playerResponse);
 */
import java.util.List;
public class Alien{
private String name;
private String role;
private String question;
private List<String> acceptableAnswers;
private int trustLevel;
private boolean isChallengePassed;

public Alien(String name, String role, String question, List<String> acceptableAnswers) {
    this.name = name;
    this.role = role;
    this.question = question;
    this.acceptableAnswers = acceptableAnswers;
    this.trustLevel = 0;
    this.isChallengePassed = false;
}
}