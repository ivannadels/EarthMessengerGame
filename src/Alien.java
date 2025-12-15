/**
 * Represents an alien who tests the player in a specific chamber.
 *
 * GAME ROLE:
* Each alien tests a specific aspect of humanity
 * Each alien is assigned to one of three chambers (Logic, Empathy, or Trustworthiness)
 * - Corn (Logic Chamber) - Tests reasoning and problem-solving
 * - Marshmallow (Empathy Chamber) - Tests emotional intelligence and compassion
 * - Water (Trust Chamber) - Tests honesty and integrity
 *
 * FUNCTIONALITY:
 * - Greets the player when they enter the chamber
 * - Asks a series of questions (both multiple choice and open-ended)
 * - Evaluates player's answers and adjusts trust level
 * - Determines if the player passes the test (trust level >= 15)
 *
 * SCORING SYSTEM:
 * - Starts at trust level 0
 * - Correct answer: +10 points
 * - Wrong answer: -5 points
 * - To pass: Need at least 15 points (2+ correct answers out of 4)
 * and poses questions to evaluate the player's humanity and intentions.
 * The alien evaluates the player's response and contributes to the final judgment.
 *
 * Aliens are instantiated with different data (name, role, questions) but
 * share the same behavior.
 *
 * STATE TRACKING:
 * - hasMetPlayer: Has the player entered this chamber?
 * - testCompleted: Has the player finished all questions?
 * - currentQuestion: Which question are we on? (0-3)
 * Example usage:
 *   Alien zyx = new Alien("Zyx", "logic", "What came first, chicken or egg?", acceptableAnswers);
 *   String greeting = zyx.greet();
 *   String question = zyx.askQuestion();
 *   boolean passed = zyx.checkAnswer(playerAnswer);
 */
import java.util.List;
public class Alien{
private String name;
private String role;
private List<Question> questions;//we have a list of question(s)
    //game tracking
private int trustLevel;
private int currentQuestion;
private boolean hasMetPlayer;
private boolean testCompleted;

 /**
     * Creates a new Alien with a name, chamber type, and list of questions.
     *Chamber names and alien's name are not revealing to the player to prevent mind biasing
     * @param name The alien's name (e.g., "Corn", "Marshmallow", "Water")
     * @param chamberType The type of test ("logic", "empathy", or "trust")
     * @param questions List of Question objects to ask the player
     */
 public Alien(String name, String role, List<Question> questionَs) {
     this.name = name;
     this.role = role;
     this.questions = questions;
     this.trustLevel = 0;
     this.currentQuestion = 0;
     this.hasMetPlayer = false;
     this.testCompleted = false;
 }
    /**
     * First greeting when player enters the chamber.
     * Sets hasMetPlayer to true and introduces the alien and their purpose.
     *
     * @return A formatted greeting message
     */
    public String greet() {
        hasMetPlayer = true;

        String greeting = "═══════════════════════════════════════════════════════\n";
        greeting = greeting + "A figure emerges from the shadows...\n\n";
        greeting = greeting + "\"Greetings, Earth Messenger.\n";
        greeting = greeting + "I am " + name + ", Scholar of " + getChamberTypeName() + ".\n";
        greeting = greeting + "I have studied your kind for decades.\n\n";
        greeting = greeting + "Prove to me that you are truly human.\"\n";
        greeting = greeting + "═══════════════════════════════════════════════════════\n";

        return greeting;
    }
    /**
     * Starts the test by showing the first question.
     *
     * @return Message indicating test has begun and the first question
     */
    public String startTest() {
        if (testCompleted) {
            return name + " has already tested you.";
        }

        return "\"Let us begin...\"\n\n" + askQuestion();
    }

    /**
     * Returns the current question text.
     * Shows question number and the question content (with options if multiple choice).
     *
     * @return Formatted question string, or message if no questions remain
     */
    public String askQuestion() {
        // Check if we have questions left
        if (currentQuestion >= questions.size()) {
            return "No more questions.";
        }

        // Get current question
        Question q = questions.get(currentQuestion);
        String questionNumber = "Question " + (currentQuestion + 1) + " of " + questions.size();

        return "───────────────────────────────────────────────────────\n" +
                questionNumber + "\n" +
                "───────────────────────────────────────────────────────\n" +
                q.getQuestionText();
    }
    /**
     * Evaluates the player's answer to the current question.
     * Updates trust level based on correctness and provides feedback.
     * Moves to next question or completes test if all questions answered.
     *
     * SCORING:
     * - Correct answer: +10 trust level
     * - Wrong answer: -5 trust level
     *
     * @param playerAnswer The player's answer (either "a", "b", "c" or a word/phrase)
     * @return Feedback message and next question (or test result if finished)
     */
    public String checkAnswer(String playerAnswer) {
        // Make sure we have questions
        if (currentQuestion >= questions.size()) {
            return "There are no more questions to answer.";
        }

        // Get current question
        Question q = questions.get(currentQuestion);

        // Check if answer is correct
        boolean correct = q.checkAnswer(playerAnswer);

        String response = "";

        // Update trust level
        if (correct) {
            trustLevel = trustLevel + 10;
        } else {
            trustLevel = trustLevel - 5;
        }

        // Move to next question
        currentQuestion = currentQuestion + 1;

        // Check if test is finished
        if (currentQuestion >= questions.size()) {
            testCompleted = true;
            response = response + "\n" + getTestResult();
        } else {
            response = response + "\n" + askQuestion();
        }

        return response;

        /**
         * Generates the final result message after all questions are answered.
         * Verdict depends on final trust level:
         * - >= 15: Approved (worthy)
         * - >= 0: Acceptable (not opposed)
         * - < 0: Rejected (failed)
         *
         * @return Formatted test result with verdict and trust level
         */
        private String getTestResult() {
            String result = "═══════════════════════════════════════════════════════\n";
            result = result + name + " steps back and studies you carefully.\n\n";

            if (trustLevel >= 15) {
                result = result + "\"You have proven yourself worthy.\n";
                result = result + "I will speak in your favor.\"\n";
            } else if (trustLevel >= 0) {
                result = result + "\"You are... acceptable.\n";
                result = result + "I will not oppose you.\"\n";
            } else {
                result = result + "\"I am disappointed.\n";
                result = result + "You have failed this test.\"\n";
            }

            result = result + "\nTrust Level: " + trustLevel;
            result = result + "\n═══════════════════════════════════════════════════════\n";
            result = result + "\nYou may return to the Nexus.";

            return result;
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