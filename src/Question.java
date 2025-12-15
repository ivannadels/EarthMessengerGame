/**
 * Represents a question that an alien scholar asks the player.
 *
 * QUESTION TYPES:
 * 1. Multiple Choice - Has options (A, B, C, D) for player to choose from
 * 2. Open Answer (Word Question) - Player types their own answer
 *
 * DESIGN PHILOSOPHY:
 * - All questions use the same class (no subclasses needed)
 * - Questions differ only in their data (text, options, answers)
 * - Behavior is identical: display question, check answer
 *
 * EXAMPLES:
 *
 * Multiple Choice Question:
 *   new Question(
 *     "What color is the sky?",
 *     Arrays.asList("Red", "Blue", "Green"),
 *     Arrays.asList("b", "blue")
 *   )
 *
 * Open Answer Question:
 *   new Question(
 *     "What do humans need to breathe?",
 *     null,  // No options = open answer
 *     Arrays.asList("oxygen", "air", "o2")
 *   )
 */
import java.util.List;

public class Question {
    private String text;
    private List<String> options; //if it is null or empty: open answer question
    private List<String> acceptableAnswers;

    /**
     * Create a new Question
     * For multiple choice: provide options
     * For open answer: pass null or empty list for options
     */
    public Question(String text, List<String> options, List<String> acceptableAnswers) {
        this.text = text;
        this.options = options;
        this.acceptableAnswers = acceptableAnswers;
    }

    /**
     * Returns the full question text formatted for display.
     * For multiple choice questions, adds lettered options (A, B, C, D).
     * For open answer questions, just shows the question.
     *
     * @return Formatted question string ready to display to player
     */
    public String getQuestionText() {
        String result = text + "\n";

        // Check if this is a multiple choice question
        if (options != null && !options.isEmpty()) {
            // Add A) B) C) D) labels
            char letter = 'A';
            for (String option : options) {
                result = result + "  " + letter + ") " + option + "\n";
                letter++;
            }
        }

        return result;
    }

    /**
     * Check if the player's answer is correct
     */
    public boolean checkAnswer(String playerAnswer) {
        // Check for empty answer
        if (playerAnswer == null) {
            return false;
        }

        // Clean up the answer (remove spaces, make lowercase)
        String cleanAnswer = playerAnswer.trim().toLowerCase();

        // Check if it matches any acceptable answer
        for (String correct : acceptableAnswers) {
            String cleanCorrect = correct.toLowerCase();
            if (cleanAnswer.equals(cleanCorrect)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if this is a multiple choice question
     */
    public boolean isMultipleChoice() {
        return options != null && !options.isEmpty();
    }

    /**
     * Get the question text only (without options)
     */
    public String getText() {
        return text;
    }

    /**
     * Get the options (for multiple choice)
     */
    public List<String> getOptions() {
        return options;
    }
}