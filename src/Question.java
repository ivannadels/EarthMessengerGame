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
     * Get the question text to show the player
     * Includes options if it's multiple choice
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