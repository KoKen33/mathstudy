package officialInternalAssessment;

public class Feedback {
    public static String generateFeedback(int score, int totalQuestions, java.util.List<String> mistakes) {
        StringBuilder sb = new StringBuilder();
        sb.append("You scored ").append(score).append(" out of ").append(totalQuestions).append(".\n\n");

        if (score == totalQuestions) {
            sb.append("Excellent work! You got everything correct.\n");
        } else if (score >= totalQuestions * 0.7) {
            sb.append("Good job! Review the mistakes you made to improve");
        } else {
            sb.append("Keep practicing! Focus on your weak areas");
        }

        for (String m : mistakes) {
            sb.append("- ").append(m).append("\n");
        }
        return sb.toString();
    }
}
