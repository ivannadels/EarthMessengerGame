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
private List<Question> questions;//we have a list of questions
private int currentQuestionIndex;
private int trustLevel;

public Alien(String name, String role, String question, List<String> acceptableAnswers) {
    this.name = name;
    this.role = role;
    this.question = question;
    this.acceptableAnswers = acceptableAnswers;
    this.trustLevel = 0;
}

    /**
     * Greets the player and explains the rules.
     * Does not reveal the specific role to avoid biasing the player.
     */
    public String greet(){
        return "I am " + name + ".\n" +
                "I have studied your kind for decades.\n" +
                "You are supposed to answer a few questions.\n" +
                "Each question should be answered only once.\n" +
                "Prove that you are truly human.";
}

public String askQuestion(){
        return question;
}
public boolean evaluateAnswer(String playerResponse){
        String response = playerResponse.trim().toLowerCase();
        for (String answer : acceptableAnswers) {
            if (response.contains(answer.trim().toLowerCase())) {
                trustLevel += 10;
                return true;
            }
        }
        trustLevel -= 5;
        return false;
}
public int getTrustLevel(){
        return trustLevel;
}
public String getName(){
        return name;
}
}