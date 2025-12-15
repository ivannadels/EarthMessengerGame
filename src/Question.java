import java.util.List;

public class Question {
    private String text;
    private List<String> options; //if it is null or empty: word-answer question
    private List<String> acceptableAnswers;
    private boolean isSolved;

    public Question(String text, List<String> options, List<String> acceptableAnswers) {
        this.text = text;
        this.options = options;
        this.acceptableAnswers = acceptableAnswers;
        this.isSolved = false;
    }

    // text of the question for the player
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append("\n");

        if (options != null && !options.isEmpty()) {
            char label = 'A';
            for (String option : options) {
                sb.append("  ").append(label).append(") ").append(option).append("\n");
                label++;
            }
        }
        return sb.toString();
    }

    //checking the answer
    public boolean checkAnswer(String input) {
        if (input == null || isSolved) return false;
        String cleaned = input.trim().toLowerCase();

        for (String ans : acceptableAnswers) {
            if (cleaned.equals(ans.toLowerCase())) {
                isSolved = true;
                return true;
            }
        }
        return false;
    }

    public boolean isSolved() { return isSolved; }
}