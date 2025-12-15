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
private List<Question> questions;//we have a list of question(s)
    //game tracking
private int trustLevel;
private int currentQuestion;

public Alien(String name, String role, List<Question> questionَs) {
    this.name = name;
    this.role = role;
    this.questions = questions;
    this.trustLevel = 0;
    this.currentQuestion = 0;
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
    /**
     * Get the next question
     */
    public String askQuestion() {
        // Check if we have questions left
        if (currentQuestion >= questions.size()) {
            return "I have no more questions for you.";
        }

        // Get current question and return it
        Question q = questions.get(currentQuestion);
        return q.getQuestionText();
    }

    /**
     * Check if player's answer is correct
     */
    public boolean checkAnswer(String playerAnswer) {
        // Make sure we still have questions
        if (currentQuestion >= questions.size()) {
            return false;
        }

        // Get current question
        Question q = questions.get(currentQuestion);

        // Check if answer is correct
        boolean correct = q.checkAnswer(playerAnswer);

        // Update trust level
        if (correct) {
            trustLevel = trustLevel + 10;
        } else {
            trustLevel = trustLevel - 5;
        }

        // Move to next question
        currentQuestion = currentQuestion + 1;

        return correct;
    }

    /**
     * Check if there are more questions
     */
    public boolean hasMoreQuestions() {
        return currentQuestion < questions.size();
    }

    /**
     * Get the trust level
     */
    public int getTrustLevel() {
        return trustLevel;
    }

    /**
     * Get the alien's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get how many questions we've answered
     */
    public int getQuestionsAnswered() {
        return currentQuestion;
    }

    /**
     * Get total number of questions
     */
    public int getTotalQuestions() {
        return questions.size();
    }
}