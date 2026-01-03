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
import java.util.*;
public class Alien{
private String name;
private String role;
private List<Question> questions;//we have a list of question(s)
    //game tracking
private int trustLevel;
private int currentQuestion;
private boolean hasMetPlayer;
private boolean testCompleted;
private String greeting;

 /**
     * Creates a new Alien with a name, chamber type, and list of questions.
     *Chamber names and alien's name are not revealing to the player to prevent mind biasing
     * @param name The alien's name (e.g., "Corn", "Marshmallow", "Water")
     * @param role The type of test ("logic", "empathy", or "trust")
     * @param questions List of Question objects to ask the player
     */
 public Alien(String name, String role, List<Question> questions) {
     this.name = name;
     this.role = role;
     this.greeting = greeting;
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
        System.out.println("hello");
        return "";
    }
    /**
     * Starts the test by showing the first question.
     *
     * @return Message indicating test has begun and the first question
     */
    public String startTest(Player player) {
        if (testCompleted) {
            return name + " has already tested you.";
        }

        return "\"Let us begin...\"\n\n" + askQuestion(player);
    }

    /**
     * Returns the current question text.
     * Shows question number and the question content (with options if multiple choice).
     *
     * @return Formatted question string, or message if no questions remain
     */
    public String askQuestion(Player player) {
        // Check if we have questions left
        if (currentQuestion >= questions.size()) {
            return "No more questions.";
        }

        // Get current question
        Question q = questions.get(currentQuestion);
        player.setCurrentQuestion(q);

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
    public String checkAnswer(Player player, String playerAnswer) {
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
            player.setCurrentQuestion(null);
            player.getCurrentLocation().setCompleted(true);
            response = response + "\n" + getTestResult(player);
        } else {
            response = response + "\n" + askQuestion(player);
        }

        return response;
    }
        /**
         * Generates the final result message after all questions are answered.
         * Verdict depends on final trust level:
         * - >= 15: Approved (worthy)
         * - >= 0: Acceptable (not opposed)
         * - < 0: Rejected (failed)
         *
         * @return Formatted test result with verdict and trust level
         */
        private String getTestResult(Player player) {
            String result = "═══════════════════════════════════════════════════════\n";
            result = result + name + " steps back and studies you carefully.\n\n";

            if (trustLevel >= 20) {
                result = result + "\"You have proven yourself worthy.\n";
                result = result + "I will speak in your favor.\"\n";
                player.getGame().addToChambersPassed();
                player.getCurrentLocation().setHasBeenPassed(true);

            } else if (trustLevel >= 10) {
                result = result + "\"You are... acceptable.\n";
                result = result + "I will not oppose you.\"\n";
                player.getGame().addToChambersPassed();
                player.getCurrentLocation().setHasBeenPassed(true);
            } else {
                result = result + "\"I am disappointed.\n";
                result = result + "You have failed this test.\"\n";
            }

            result = result + "\nTrust Level: " + trustLevel;
            result = result + "\n═══════════════════════════════════════════════════════\n";
            boolean allCompleted = player.getGame().getLocations()
                    .values()
                    .stream()
                    .allMatch(Location::hasBeenCompleted);

            if(allCompleted) {
                result += player.getGame().getOutro();
            }
            else{
                result += "\nYou may exit to the Nexus.";
            }

            return result;
        }


//        /**
//         * Returns appropriate message when player returns to the chamber.
//         *
//         * @return Greeting if first visit, acknowledgment if test complete, or waiting message
//         */
//        public String returnMessage() {
//            if (!hasMetPlayer) {
//                return greet();
//            }
//
//            if (testCompleted) {
//                if (trustLevel >= 0) {
//                    return name + " acknowledges you with a slight nod.";
//                } else {
//                    return name + " turns away from you.";
//                }
//            }
//
//            return name + " is waiting for your answer.";
//        }
        // ===== GETTER METHODS =====

        /**
         * @return Current trust level (-20 to 40 range)
         */
        public int getTrustLevel() {
            return trustLevel;
        }

        /**
         * @return The alien's name
         */
        public String getName() {
            return name;
        }

        /**
         * @return The role ("logic", "empathy", or "trust")
         */
        public String getRole() {
            return role;
        }

        /**
         * @return True if player has met this alien
         */
        public boolean hasMetPlayer() {
            return hasMetPlayer;
        }

        /**
         * @return True if all questions have been answered
         */
        public boolean isTestCompleted() {
            return testCompleted;
        }

        /**
         * @return True if there are more questions to ask
         */
        public boolean hasMoreQuestions() {
            return currentQuestion < questions.size();
        }

        /**
         * @return Number of questions answered so far
         */
        public int getQuestionsAnswered() {
            return currentQuestion;
        }

        /**
         * @return Total number of questions this alien has
         */
        public int getTotalQuestions() {
            return questions.size();
        }

        /**
         * Determines if this alien approves of the player.
         * Player must complete the test AND have trust level >= 15 to pass.
         * This means at least 2 correct answers out of 4 questions.
         *
         * @return True if test is complete and trust level is 15 or higher
         */
        public boolean approves() {
            return testCompleted && trustLevel >= 15;
        }
    }